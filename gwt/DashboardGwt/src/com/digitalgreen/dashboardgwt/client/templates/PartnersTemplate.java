package com.digitalgreen.dashboardgwt.client.templates;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.digitalgreen.dashboardgwt.client.common.Form;
import com.digitalgreen.dashboardgwt.client.common.RequestContext;
import com.digitalgreen.dashboardgwt.client.data.PartnersData;
import com.digitalgreen.dashboardgwt.client.servlets.Partners;
import com.google.gwt.user.client.ui.Hyperlink;

public class PartnersTemplate extends BaseTemplate {
	public PartnersTemplate (RequestContext requestContext) {
		super(requestContext);
		this.formTemplate = new Form((new PartnersData()).getNewData());
	}
	
	@Override
	public void fill() {
		String templateType = "Partner";
		String templatePlainType = "dashboard/partners/add/";
		RequestContext requestContext = new RequestContext();
		HashMap args = new HashMap();
		args.put("action", "add");
		requestContext.setArgs(args);
		requestContext.setForm(this.formTemplate);
		RequestContext saveRequestContext = new RequestContext(RequestContext.METHOD_POST);
		saveRequestContext.setForm(this.formTemplate);
		Partners addPartnersServlet = new Partners(requestContext);
		Partners savePartner = new Partners(saveRequestContext);
		
		// Draw the content of the template depending on the request type (GET/POST)
		super.fillDGTemplate(templateType, partnersListHtml, partnersAddHtml, addDataToElementID);
		// Add it to the rootpanel
		super.fill();
		//Now add listings
		List<Hyperlink> links =  this.fillListings();
		// Now add hyperlinks
		super.fillDgListPage(templatePlainType, templateType, partnersListFormHtml, addPartnersServlet, links);
		// Now add any submit control buttons
		super.fillDgFormPage(savePartner);
	}
	
	protected List<Hyperlink> fillListings() {
		HashMap queryArgs = this.getRequestContext().getArgs();
		String queryArg = (String)queryArgs.get("action");
		List<Hyperlink> links = new ArrayList<Hyperlink>();
		// If we're unsure, just default to list view
		if(queryArg.equals("list")) {
			// 	Add Listings
			List partners = (List)queryArgs.get("listing");			
			if(partners  != null){
				String tableRows ="";
				String style;
				PartnersData.Data partner;
				RequestContext requestContext = null;
				for (int row = 0; row < partners.size(); ++row) {
					if(row%2==0)
						style= "row2";
					else
						style = "row1";
					partner = (PartnersData.Data)partners.get(row);
					requestContext = new RequestContext();
					requestContext.getArgs().put("action", "edit");
					requestContext.getArgs().put("id", partner.getId());
					requestContext.setForm(this.formTemplate);
					links.add(this.createHyperlink("<a href='#dashboard/partner/"+ partner.getId() +"/'>" +
							partner.getPartnerName()+"</a>",
							"dashboard/partner/"+ partner.getId() +"/",
							new Partners(requestContext)));
					tableRows += "<tr class='" + style + "'><td><input type='checkbox' class='action-select' value='" +
									partner.getId() + "' name='_selected_action' /></td>" +
									"<th id = 'row" + row + "'></th></tr>";
				}
				partnersListFormHtml = partnersListFormHtml + tableRows + "</tbody></table>";
			}
		}
		return links;
	}

	final private String addDataToElementID[] = null;
	
	private String partnersListFormHtml = "<script type='text/javascript' src='/media/js/admin/DateTimeShortcuts.js'></script>" +
											"<script type='text/javascript' src='/media/js/calendar.js'></script>" +
								"<div class='actions'>" +
								"<label>Action: <select name='action'>" +
									"<option value='' selected='selected'>---------</option>" +
									"<option value='delete_selected'>Delete selected Partners</option>" +
									"</select>" +
								"</label>" +
								"<button type='submit' class='button' title='Run the selected action' name='index' value='0'>Go</button>" +
							"</div>" +
							"<table cellspacing='0'>" +
								"<thead>" +
									"<tr>" +
										"<th>" +
											"<input type='checkbox' id='action-toggle' />" +
										"</th>" +
										"<th>" +
										"<a href='?ot=asc&amp;o=1'>" +
											"Partner" +
										"</a>" +
								"</th>" +
							"</tr>" +
						"</thead>" +
						"<tbody>";
	
	final private String partnersListHtml = "<link rel='stylesheet' type='text/css' href='/media/css/forms.css' />" +
								"<div id='content' class='flex'>" +
									"<h1>Select Partner to change</h1>" +
									"<div id='content-main'>" +
										"<ul class='object-tools'>" +
											"<li id='add-link'>" +                // Insert add link here
											"</li>" +
										"</ul>" +
										"<div class='module' id='changelist'>" +
											"<form action='' method='post'>" +
												"<div id='listing-form-body'>" +  // Insert form data here
												"</div>" +
											"</form>" +
										"</div>" +
									"</div>" +
								"</div>";
	
	final private String partnersAddHtml = "<link rel='stylesheet' type='text/css' href='/media/css/forms.css' />" +
								"<div id='content' class='colM'>" +
									"<h1>Add Partner</h1>" +
									"<div id='content-main'>" +
										"<fieldset class='module aligned '>" +
													"<div class='form-row partner_name  '>" +
														"<div>" +
															"<label for='id_partner_name' class='required'>Partner name:</label><input id='id_partner_name' type='text' class='vTextField' name='partner_name' maxlength='100' />" +
														"</div>" +
													"</div>" +
													"<div class='form-row date_of_association  '>" +
														"<div>" +
															"<label for='id_date_of_association'>Date of association:</label>" +
															"<input id='id_date_of_association' type='text' class='vDateField' name='date_of_association' size='10' />" +
														"</div>" +
													"</div>" +
													"<div class='form-row phone_no  '>" +
														"<div>" +
															"<label for='id_phone_no'>Phone no:</label><input id='id_phone_no' type='text' class='vTextField' name='phone_no' maxlength='100' />" +
														"</div>" +
													"</div>" +
													"<div class='form-row address  '>" +
														"<div>" +
															"<label for='id_address'>Address:</label><input id='id_address' type='text' class='vTextField' name='address' maxlength='500' />" +
														"</div>" +
													"</div>" +
												"</fieldset>" +
												"<div class='submit-row' >" +
													"<input id='save' type='button' value='Save' class='default' name='_save' />" +
												"</div>" +
												"<script type='text/javascript'>document.getElementById('id_partner_name').focus();</script>" +
											"</div>" +
											"<br class='clear' />" +
										"</div>" ;
}