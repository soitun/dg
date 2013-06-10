from new_website_admin.models import Member
from new_website_admin.forms import ImageAdminValidation
from django.contrib import admin
from django.contrib.admin.sites import AdminSite


class WebsiteAdmin(AdminSite):
    pass
website_admin = WebsiteAdmin(name='local')


class TeamMember(admin.ModelAdmin):
    form = ImageAdminValidation
    fieldsets = [(None,  {'fields': ['name', 'email', 'designation',
                                     'team', 'personal_intro', 'location',
                                     'image']
                          }
                  )]
    list_display = ('name', 'email', 'designation')
    search_fields = ['name']
website_admin.register(Member, TeamMember)
