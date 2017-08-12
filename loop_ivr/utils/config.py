# -*- coding: utf-8 -*-
from dg.settings import MEDIA_ROOT

LOG_FILE = '%s/loop/loop_ivr_log.log'%(MEDIA_ROOT,)
AGGREGATOR_SMS_NO = '01139585707'

mandi_hi = 'मंडी'
indian_rupee = 'रु'
helpline_hi = 'हैल्पलाइन'
agg_sms_initial_line = 'लूप मंडी रेट\n'
agg_sms_crop_line = 'फसल'
agg_sms_no_price_for_combination = 'रेट उपलब्ध नही है\n'
agg_sms_no_price_available = 'इस मंडी और फसल के लिए पिछले तीन दिन के रेट उपलब्ध नही है.'
MONTH_NAMES = {1:'जनवरी', 2:'फरवरी', 3:'मार्च', 4:'अप्रैल', 5:'मई', 6:'जून', 7:'जुलाई', 8:'अगस्त',
				9:'सितंबर', 10:'अक्टूबर', 11:'नवंबर', 12:'दिसंबर'}

MARKET_INFO_CALL_RESPONSE_URL = 'http://www.digitalgreen.org/loopivr/market_info_response/'
PUSH_MESSAGE_SMS_RESPONSE_URL = 'http://www.digitalgreen.org/loopivr/push_message_sms_response/'
MARKET_INFO_APP = '137265'