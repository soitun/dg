from django.conf import settings
from django.contrib.staticfiles.urls import staticfiles_urlpatterns
from django.conf.urls import patterns, include, url
from new_website_admin.admin import website_admin

urlpatterns = patterns('',
                       (r'^', include(website_admin.urls)),
                       (r'^teammember/$', 'new_website_admin.views.member_view'),
                       )
