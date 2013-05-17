import settings
from django.core.management import setup_environ
setup_environ(settings)
import json
from pyes import *
from website.models import Collection

conn = ES(['127.0.0.1:9200'])
#try:
#    conn.delete_index("test-index")
#    print "Previous index deleted"
#except Exception, ex:
#    print "No Previous index"
#
#settings = { "settings":
#                {"index":{
#                          "analysis":{
#                                      "analyzer":{
#                                                  "analyzer_keyword":{
#                                                                      "tokenizer":{"edge":{
#                                                                                   "type": "edgeNGram",
#                                                                                   "min_gram" : 2,
#                                                                                        }
#                                                                                   }
#                                                                      }
#                                                  }
#                                      }
#                          }
#                 }
#            }
#
#conn.create_index("test-index")
#mapping = {   'text': {'boost': 1.0,
#                         'index': 'analyzed',
#                         'store': 'yes',
#                         'type': 'string',
##                         'analyzer': 'analyzer_keyword',
#                         "term_vector" : "with_positions_offsets"},
#              'url': {'boost': 0.0,
#                         'index': 'analyzed',
#                         'store': 'yes',
#                         'type': 'string',
#                         "term_vector" : "with_positions_offsets"},
#              'type': {'boost': 0.0,
#                         'index': 'analyzed',
#                         'store': 'yes',
#                         'type': 'string',
#                         "term_vector" : "with_positions_offsets"},
#            }
#conn.put_mapping("test-type", {'properties':mapping}, ["test-index"])
##conn.put_mapping("test-type2", {"_parent" : {"type" : "test-type"}}, ["test-index"])
#
## putting in the data
#i = 0
#print Collection.objects.all().count()
#for collection in Collection.objects.all():
#    data = json.dumps({"text":collection.title,"url":"","type":"Collection"})
#    print data
#    conn.index(data, "test-index", "test-type",i+1)
##    conn.index({"name":"data1", "value":"value1"}, "test-index", "test-type2", i+1, parent=i+1)
#    i+= 1
#    print i
#    
conn.default_indices="test-index"
conn.refresh("test-index")
q = {
    "fuzzy" : { "text" : "seed"}
    }
try :
    results = conn.search(q,indices=['test-index'])
    print "success"
    print len(results)
except Exception, ex:
    print ex
print type(results)
for r in results:
    print r 
    print r._meta.score
    