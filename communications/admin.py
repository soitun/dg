from django.contrib import admin
from models import Article

class ArticleAdmin(admin.ModelAdmin):
    fieldsets = [(None,{'fields':['title','source']}),
                ('Content',{'fields':['content','link','location']}),
                ('Date information',{'fields':['pub_date']}),]
   
    list_display = ('title','pub_date','source')
   
      
    list_filter = ['pub_date']
    search_fields = ['title','content']
    date_hierarchy = 'pub_date'
