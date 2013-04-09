import settings
from django.core.management import setup_environ
setup_environ(settings)
import csv,datetime, json, urllib2, uuid, base64
from dashboard.models import Person, PersonMeetingAttendance, PersonAdoptPractice
from userfile_functions import upload_file, write_person_detail

def get_user_data(user_id):
    BASE_URL = 'https://www.commcarehq.org/a/dgwithcase/api/v0.3/case/?limit=500'  #taking 500 as upper limit for now
    Realm = 'DJANGO'
    Username = 'nandinibhardwaj@gmail.com'
    Password = 'digitalgreen'
    URL = BASE_URL + '&user_id=' + user_id
    
    authhandler = urllib2.HTTPDigestAuthHandler()
    authhandler.add_password(Realm, URL, Username, Password)
    opener = urllib2.build_opener(authhandler)
    urllib2.install_opener(opener)
    page_content = urllib2.urlopen(URL)
    data = json.loads(page_content.read())
    case_ids = []
    return data['objects']

def check_person_id(data, person_id):
    exists = False
    for case in data:
        if case['properties'].has_key('id'):
            if case['properties']['id'] == person_id and case['closed']==False:
                print case
                exists = True
                return exists
    return exists

if __name__ == '__main__':
    user_id = 'f097d1d213ad3a08fee6643133465660'
    person_id = 5000001002
    data = get_user_data(user_id)
    already_exists = check_person_id(data, person_id)
    print already_exists
    if not already_exists:
        write_person_detail(person_id,'person.xml' )
        response = upload_file('person.xml')
        print response