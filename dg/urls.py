from django.conf import settings
from django.conf.urls.defaults import patterns, include, url
from django.contrib.staticfiles.urls import staticfiles_urlpatterns

import coco.urls
from dashboard.data_log import send_updated_log
from dashboard.views import feed_animators, get_person, redirect_url, search
from farmerbook import farmer_book_views
from output.views import video_analytics
from static_site_views import *

# Uncomment the next two lines to enable the admin:
from django.contrib import admin
admin.autodiscover()

urlpatterns = patterns('',
    (r'^coco/', include(coco.urls)),
    (r'^admin/doc/', include('django.contrib.admindocs.urls')),
    (r'^feeds/', include('dashboard.urls_feeds')),
    (r'^site_media/(?P<path>.*)$', 'django.views.static.serve',{'document_root': settings.STATIC_DOC_ROOT, 'show_indexes': True}),
    # Uncomment the next line to enable the admin:
    (r'^admin/', include(admin.site.urls)),
    # Imports from dashboard
    (r'^animators-by-village-id/(\d+)/$', feed_animators),
    (r'/search/', search),
    (r'^dashboard/', include('dashboard.urls')),
    (r'^get/person/$', get_person),
    (r'^get_log/?$', send_updated_log),
    # End imports from dashboard
    (r'^farmerbook/$', farmer_book_views.get_home_page),
    (r'^farmerbook/(?P<type>\D*)/(?P<id>\d*)/$', farmer_book_views.get_home_page),
    (r'^trial/?$', farmer_book_views.get_admin_panel),
    (r'^getvillagepage/?$', farmer_book_views.get_village_page),
    (r'^getserviceproviderpage/?$', farmer_book_views.get_csp_page),
    (r'^getpartnerpage/?$', farmer_book_views.get_partner_page),
    (r'^getpersonpage/?$', farmer_book_views.get_person_page),
    (r'^getgrouppage/?$', farmer_book_views.get_group_page),
    (r'^getvillages/?$', farmer_book_views.get_villages_with_images),
    (r'^getvideosproduced/?$', farmer_book_views.get_videos_produced),
    (r'^videotask/', include('video_practice_map.urls')),
    # Imports from static_site_views
    (r'^$', home),
    (r'^home/?$',home),
    (r'^farmerfunda/',farmerfunda),
    (r'^wondervillage/?$',wondervillage),
    (r'^wondervillagegame/?$',wondervillagegame),
    (r'^annualreports/?$',annualreports),
    (r'^featuredfarmer/?$',featuredfarmer),
    (r'^melissaho/?$',melissaho),
    (r'^aishwaryaratan/?$',aishwaryaratan),
    (r'^srikantvasan/?$',srikantvasan),
    (r'^videopage/?$',videopage),
    (r'^searchvideo_result/?$',searchvideo_result),
    (r'^aboutus/?$',aboutus),
    (r'^annualreport09/?$',annualreport09),
    (r'^annualreport10/?$',annualreport10),
    (r'^annualreport10pdf/?$',annualreport10pdf),
    (r'^annualletter/?$',annualletter),
    (r'^annualletter10/?$',annualletter10),
    (r'^projectprogress/?$',projectprogress),
    (r'^projectprogress10/?$',projectprogress10),
    (r'^partners10/?$',partners10),
    (r'^budgetprogress/?$',budgetprogress),
    (r'^financial10/?$',financial10),
    (r'^scalability/?$',scalability),
    (r'^lessonlearned/?$',lessonlearned),
    (r'^challenge10/?$',challenge10),
    (r'^keyprinciple/?$',keyprinciple),
    (r'^corevalue/?$',corevalue),
    (r'^sop/?$',sop),
    (r'^qualityassurance/?$',qualityassurance),
    (r'^overviewfarmer/?$',overviewfarmer),
    (r'^overviewdatabase/?$',overviewdatabase),
    (r'^overviewproduction/?$',overviewproduction),
    (r'^overviewsequence/?$',overviewsequence),
    (r'^overviewdiffusion/?$',overviewdiffusion),
    (r'^overviewscalability/?$',overviewscalability),
    (r'^overviewdistribution/?$',overviewdistribution),
    (r'^career/?$',career),
    (r'^careers/?$',careers),
    (r'^contact/?$',contact),
    (r'^team/?$',team),
    (r'^teamboard/?$',teamboard),
    (r'^teamadviser/?$',teamadviser),
    (r'^teamacclaw/?$',teamacclaw),
    (r'^teamintern/?$',teamintern),
    (r'^teamalumni/?$',teamalumni),
    (r'^teammember/?$',teammember),
    (r'^press/?$',press),
    (r'^partner/?$',partner),
    (r'^rfa/?$',rfa),
    (r'^partnerexecutive/?$',partnerexecutive),
    (r'^partnerresearch/?$',partnerresearch),
    (r'^partnerinvestor/?$',partnerinvestor),
    (r'^partnersupporter/?$',partnersupporter),
    (r'^careerid/?$',careerid),
    (r'^careersm/?$',careersm),
    (r'^careerpm/?$',careerpm),
    (r'^careernm/?$',careernm),
    (r'^careerpca/?$',careerpca),
    (r'^careernpc/?$',careernpc),
    (r'^careerts/?$',careerts),
    (r'^careerqam/?$',careerqam),
    (r'^careerrse/?$',careerrse),
    (r'^careeradm/?$',careeradm),
    (r'^donate/?$',donate),
    (r'^tech/?$',tech),
    (r'^photos/?$',photos),
    (r'^webvideos/?$',webvideos),
    (r'^keyfacts/?$',keyfacts),
    (r'^farmerpage/?$',farmerpage),
    (r'^villagepage/?$',villagepage),
    (r'^grouppage/?$',grouppage),
    (r'^retreat11/?$',retreat11),
    (r'^update/?$',update),
    (r'^latestupdate/?$',latestupdate),
    (r'^nexus/?$',nexus),
    (r'^analytics/', include('output.urls')),
    (r'^video/?$',video_analytics.video),
    (r'^path/', include('path.urls')),
    (r'^fbconnect/', include('fbconnect.urls')),
)

# Static files serving locally
if settings.DEBUG:
    urlpatterns += staticfiles_urlpatterns()
    