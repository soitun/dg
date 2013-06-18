from django.conf import settings
from django.contrib.staticfiles.urls import staticfiles_urlpatterns
from django.conf.urls import patterns, include, url
from new_website_admin.admin import website_admin  
from views import media_view, member_view



urlpatterns = patterns('',
                       url(r'^press/$', media_view),
                       url(r'^teammember/$', member_view),
                       url(r'^', include(website_admin.urls)),                      
                       )