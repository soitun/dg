from django.contrib.admin.sites import AdminSite
from communications.admin import ArticleAdmin
from communications.models import Article
from human_resources.admin import JobAdmin, MemberAdmin
from human_resources.models import Job, Member

class WebsiteAdmin(AdminSite):
    pass

website_admin = WebsiteAdmin(name='media')

website_admin.register(Article, ArticleAdmin)
website_admin.register(Job, JobAdmin)
website_admin.register(Member, MemberAdmin)
