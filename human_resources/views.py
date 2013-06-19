from django.shortcuts import render_to_response
from django.template import RequestContext
from human_resources.models import location_choices, Member, team_choices

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
