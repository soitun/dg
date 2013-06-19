from django.contrib import admin
from forms import ImageAdminForm
from models import ExperienceQualification, Job, KeyResponsibilities, Member

class MemberAdmin(admin.ModelAdmin):
    form = ImageAdminForm
    fieldsets = [(None,  {'fields': ['name', 'email', 'designation',
                                     'team', 'personal_intro', 'location',
                                     'image']
                          }
                  )]
    list_display = ('name', 'email', 'designation')
    search_fields = ['name']

class KeyResponsibilitiesInline(admin.TabularInline):
    model = KeyResponsibilities
    extra = 10
        
class ExperienceQualificationInline(admin.TabularInline):
    model = ExperienceQualification
    extra = 10
    
class JobAdmin(admin.ModelAdmin):
    fieldsets = [(None, {'fields':['title','description','conclusion']})]
    inlines = [ExperienceQualificationInline, KeyResponsibilitiesInline]
    list_display = ('title',)
    search_fields = ['title']
