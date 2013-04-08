import settings
from django.core.management import setup_environ
setup_environ(settings)
import csv,datetime, json, urllib2, uuid
from dashboard.models import Person, PersonMeetingAttendance, PersonAdoptPractice
from poster.encode import multipart_encode
from poster.streaminghttp import register_openers

def write_userfile(file, data):
    f = open(file,'w')
    f.write(data)
        
def read_userfile(file):
    json_data=open(file).read()
    data = json.loads(json_data)
    return data

def write_person_detail(person, filename, i):
    f = open(filename,'w')
    num_people = 1
    f.write('<?xml version="1.0" ?>\n')
    f.write('<data uiVersion="1" version="8" name="New Form" xmlns:jrm="http://dev.commcarehq.org/jr/xforms" xmlns="http://openrosa.org/formdesigner/DB63E17D-B572-4F5B-926E-061583DAE9DA">\n')
    f.write('<num_people>' + str(num_people) + '</num_people>\n')
    case_id = uuid.uuid4()
    owner_id = 'f097d1d213ad3a08fee6643133465660'
    # Getting list of videos seen
    vids = PersonMeetingAttendance.objects.filter(person = person).values_list('screening__videoes_screened', flat = True)
    videos_seen = ''
    for vid in vids:
        videos_seen = videos_seen + str(vid) + ' '
    # Getting list of videos adopted
    adopts = PersonAdoptPractice.objects.filter(person = person).values_list('video', flat = True)
    videos_adopted = ''
    for vid in adopts:
        videos_adopted = videos_adopted + str(vid) + ' '
    # Putting all the info in xml tags
    f.write('<people>\n')
    f.write('<n'+str(i)+':case case_id="'+str(case_id)+ '" date_modified="'+ str(datetime.datetime.now().date()) + '" user_id="f097d1d213ad3a08fee6643133465660" xmlns:n'+str(i)+'="http://commcarehq.org/case/transaction/v2">\n')
    f.write('<n'+str(i)+':create>\n')
    f.write('<n'+str(i)+':case_type>person</n'+str(i)+':case_type>\n')
    f.write('<n'+str(i)+':owner_id>' + owner_id + '</n'+str(i)+':owner_id>\n')
    f.write('<n'+str(i)+':case_name>' + person.person_name + '</n'+str(i)+':case_name>\n')
    f.write('</n'+str(i)+':create>\n')
    f.write('<n'+str(i)+':update>\n')
    f.write('<n'+str(i)+':id>' + str(person.id) + '</n'+str(i)+':id>\n')
    f.write('<n'+str(i)+':group_id>' + str(person.group.id)+ '</n'+str(i)+':group_id>\n')
    f.write('<n'+str(i)+':videos_seen>' + videos_seen + '</n'+str(i)+':videos_seen>\n')
    f.write('<n'+str(i)+':videos_adopted>' + '' + '</n'+str(i)+':videos_adopted>\n')
    f.write('</n'+str(i)+':update>\n')
    f.write('</n'+str(i)+':case>\n')
    f.write('</people>\n')
    # Writing closing meta info of the form
    i += 1
    f.write('<n'+str(i) + ':meta xmlns:n' + str(i) + '="http://openrosa.org/jr/xforms">\n')
    f.write('<n'+str(i) + ':userID>f097d1d213ad3a08fee6643133465660</n' + str(i) + ':userID>\n')
    f.write('<n'+str(i) + ':instanceID>2729386f-7fd2-42cc-807f-786bf2dc952b</n' + str(i) + ':instanceID>\n')
    f.write('</n' + str(i) + ':meta>\n')
    f.write('</data>')
    f.close()
    return f

def make_upload_file(villages, filename):
    for person in Person.objects.filter(village__in = villages):
        i = 0
        write_person_detail(person, filename, i)
        response = upload_file(filename)
        print response
        break
    
        
def upload_file(file):
    register_openers()
    datagen, headers = multipart_encode({"xml_submission_file": open(file, "rb")})
    request = urllib2.Request("https://www.commcarehq.org/a/dgwithcase/receiver", datagen, headers)
    response = urllib2.urlopen(request).read()
    return response
    

if __name__ == '__main__':
#    file = 'userfile.json'
#    user_villages = []
#    json_data = json.dumps([{'user_id': 'f097d1d213ad3a08fee6643133465660',
#                          'username': 'y',
#                          'villages' : [10000000020974,10000000020975,10000000021293,
#                                        10000000020958,10000000020960,10000000020956]},
#                            {'user_id': 'f097d1d213ad3a08fee6643133465660',
#                          'username': 'y2',
#                          'villages' : [10000000020974,10000000020975,10000000021293,
#                                        10000000020958,10000000020960,10000000020956]}])
#    data = json.loads(json_data)
#    write_userfile(file,json_data)
#    read_userfile(file)
    upload_file('y.xml')