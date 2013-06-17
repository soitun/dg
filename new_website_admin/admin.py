from new_website_admin.models import Member, Article
from new_website_admin.forms import ImageAdminForm
from django.contrib import admin
from django.contrib.admin.sites import AdminSite


class WebsiteAdmin(AdminSite):
    pass

website_admin = WebsiteAdmin(name='local')


class TeamMember(admin.ModelAdmin):
    form = ImageAdminForm
    fieldsets = [(None,  {'fields': ['name', 'email', 'designation',
                                     'team', 'personal_intro', 'location',
                                     'image']
                          }
                  )]
    list_display = ('name', 'email', 'designation')
    search_fields = ['name']

website_admin.register(Member, TeamMember)
#admin.site.register(Member,TeamMember)

class Press(admin.ModelAdmin):
    fieldsets = [(None,{'fields':['title','source']}),
                ('Content',{'fields':['content','link','location']}),
                ('Date information',{'fields':['pub_date']}),]
   
    list_display = ('title','pub_date','source')
   
      
    #format_date.short_description = 'Date'
    list_filter = ['pub_date']
    search_fields = ['title']
    date_hierarchy = 'pub_date'
    
#admin.site.register(Article,Press)
website_admin.register(Article, Press)

