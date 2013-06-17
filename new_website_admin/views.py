# Create your views here.
from new_website_admin.models import Member, Article
from new_website_admin.models import team_choices
from new_website_admin.models import location_choices
from django.shortcuts import render_to_response
from django.template import RequestContext
from django.core.paginator import *


def member_view(request):
    
    location_list = []
    for location in location_choices:
        team_list = []
        for team in team_choices:
            member_list = Member.objects.filter(location=location[0],
                                                team=team[0])
            team_list.append({'team_name': team[0],
                              'team_members': member_list})
        location_list.append({'location_name': location[0],
                              'location_team_members': team_list})
    return render_to_response('team.html', {'location_list': location_list},
                              context_instance=RequestContext(request))
    
def media_view(request):
    media_list = Article.objects.all().order_by('-pub_date')
    paginator = Paginator(media_list, 6)
    page = request.GET.get('page')
    try:
        media = paginator.page(page)
    except PageNotAnInteger:
        media = paginator.page(1)
    except EmptyPage:
        media = paginator.page(paginator.num_pages)
            
    return render_to_response('base_press.html',{'media':media})