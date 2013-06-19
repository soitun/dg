from django.core.paginator import EmptyPage, PageNotAnInteger, Paginator
from django.shortcuts import render_to_response
from communications.models import Article

def media_view(request):
    media_list = Article.objects.all().order_by('-pub_date')
    paginator = Paginator(media_list, 6) #dividing 6 articles per page
    page = request.GET.get('page')
    try:
        media = paginator.page(page)
    except PageNotAnInteger:
        media = paginator.page(1)
    except EmptyPage:
        media = paginator.page(paginator.num_pages)  
    return render_to_response('base_press.html',{'media':media})
