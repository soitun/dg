from new_website_admin.models import Article, Member 
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


class ArticleAdmin(admin.ModelAdmin):
    fieldsets = [(None,{'fields':['title','source']}),
                ('Content',{'fields':['content','link','location']}),
                ('Date information',{'fields':['pub_date']}),]
   
    list_display = ('title','pub_date','source')
   
      
    list_filter = ['pub_date']
    search_fields = ['title','content']
    date_hierarchy = 'pub_date'
    

website_admin.register(Member, TeamMember)
website_admin.register(Article, ArticleAdmin)