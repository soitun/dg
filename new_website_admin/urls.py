from django.conf import settings
from django.contrib.staticfiles.urls import staticfiles_urlpatterns
from django.conf.urls import patterns, include, url
from new_website_admin.admin import website_admin
from views import member_view


urlpatterns = patterns('',
                       
                       (r'^teammember/$', member_view),
                       (r'^', include(website_admin.urls)),
                       )
