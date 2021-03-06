package com.digitalgreen.dashboardgwt.client.data;

import java.util.ArrayList;
import java.util.List;
import com.digitalgreen.dashboardgwt.client.common.Form;
import com.digitalgreen.dashboardgwt.client.common.OnlineOfflineCallbacks;
import com.digitalgreen.dashboardgwt.client.common.RequestContext;
import com.digitalgreen.dashboardgwt.client.data.VillagesData.Data;
import com.digitalgreen.dashboardgwt.client.data.validation.DateValidator;
import com.digitalgreen.dashboardgwt.client.data.validation.IntegerValidator;
import com.digitalgreen.dashboardgwt.client.data.validation.StringValidator;
import com.digitalgreen.dashboardgwt.client.data.validation.UniqueConstraintValidator;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.gears.client.database.DatabaseException;
import com.google.gwt.user.client.Window;

public class StatesData extends BaseData {
	
	public static class Type extends BaseData.Type{
		protected Type() {}
		public final native String getStateName() /*-{ return $wnd.checkForNullValues(this.fields.state_name); }-*/;
		public final native RegionsData.Type getRegion() /*-{ return this.fields.region }-*/;
		public final native CountriesData.Type getCountry() /*-{ return this.fields.country }-*/;
		public final native String getStartDate() /*-{ return $wnd.checkForNullValues(this.fields.start_date); }-*/;
	}
	
	public class Data extends BaseData.Data {
		
		final private static String COLLECTION_PREFIX = "state";
			
		private String state_name;
		private String start_date;
		private RegionsData.Data region;
		private CountriesData.Data country;
		
		
		public Data() {
			super();
		}
		
		public Data(String id, String state_name) {
			super();
			this.id = id;
			this.state_name = state_name;
		}
		

		public Data(String id, String state_name,String start_date, RegionsData.Data region, CountriesData.Data country) {
			super();
			this.id = id;
			this.state_name = state_name;
			this.start_date = start_date;
			this.region = region;
			this.country = country;
		}
		
		
		public String getStateName(){
			return this.state_name;
		}
		
		public String getStartDate(){
			return this.start_date;
		}
		
		public RegionsData.Data getRegion(){
			return this.region;
		}
		
		public CountriesData.Data getCountry(){
			return this.country;
		}
		
		public BaseData.Data clone() {
			Data obj = new Data();
			obj.region = (new RegionsData()).new Data();
			obj.country = (new CountriesData()).new Data();
			return obj;
		}
		
		@Override
		public String getPrefixName() {
			return Data.COLLECTION_PREFIX;
		}
		
		@Override
		public void setObjValueFromString(String key, String val) {		
			super.setObjValueFromString(key, val);
			if(key.equals("id")) {
				this.id = val;
			}else if(key.equals("state_name")) {
				this.state_name = val;
			} else if(key.equals("region")) {
				// Have to Create an instance of RegionsData to create an instance of RegionsData.Data -- any better way of doing this??
				RegionsData region = new RegionsData();
				this.region = region.getNewData();
				this.region.id = val;
				//Never ever use this -- this.region.id = ((Integer)val).intValue();
			} else if(key.equals("country")) {
				CountriesData country = new CountriesData();
				this.country = country.getNewData();
				this.country.id = val;
			} else if(key.equals("start_date")) {
				this.start_date = val;
			} else {
				return;
			}
			this.addNameValueToQueryString(key, val);	
		}
		
		@Override
		public boolean validate(){
			String nameLabel = "State Name";
			String regionLabel = "Region";
			String countryLabel = "Country";
			String startDateLabel = "Start Date";
			StringValidator stateValidator = new StringValidator(nameLabel, this.state_name, false, false, 1, 100, true);
			StringValidator regionValidator = new StringValidator(regionLabel, this.region.getId(), false, false);
			StringValidator countryValidator = new StringValidator(countryLabel, this.country.getId(), false, false);
			DateValidator dateValidator = new DateValidator(startDateLabel, this.start_date, true, true);
			ArrayList state_name = new ArrayList();
			state_name.add("state_name");
			state_name.add(this.state_name);
			ArrayList stateName = new ArrayList();
			stateName.add(state_name);
			ArrayList uniqueValidatorLabels = new ArrayList();
			uniqueValidatorLabels.add("StateName");
			UniqueConstraintValidator uniqueStateName = new UniqueConstraintValidator(uniqueValidatorLabels, stateName, new StatesData());
			uniqueStateName.setCheckId(this.getId());
			ArrayList validatorList = new ArrayList();
			validatorList.add(stateValidator);
			validatorList.add(regionValidator);
			validatorList.add(countryValidator);
			validatorList.add(dateValidator);
			validatorList.add(uniqueStateName);
			return this.executeValidators(validatorList);
		}
		
		@Override
		public void save() {
			StatesData statesDataDbApis = new StatesData();		
			this.id = statesDataDbApis.autoInsert(this.id, this.state_name, this.region.getId(), this.start_date, this.country.getId());
			this.addNameValueToQueryString("id", this.id);
		}
		
		@Override
		public String toQueryString(String id) {
			StatesData statesData = new StatesData();
			return this.rowToQueryString(statesData.getTableName(), statesData.getFields(), "id", id, "");
		}
		
		@Override
		public String getTableId() {
			StatesData statesDataDbApis = new StatesData();
			return statesDataDbApis.tableID;
		}
	}

	public static String tableID = "12";
	final protected static String createTable = "CREATE TABLE IF NOT EXISTS `state` " +
												"(id BIGINT UNSIGNED PRIMARY KEY  NOT NULL ," +
												"STATE_NAME VARCHAR(100)  NOT NULL ," +
												"region_id BIGINT UNSIGNED  NOT NULL DEFAULT 0," +
												"START_DATE DATE  NULL DEFAULT NULL, " +
												"country_id BIGINT UNSIGNED NOT NULL DEFAULT 0," +
												"FOREIGN KEY(country_id) references country(id)," +
												"FOREIGN KEY(region_id) references region(id));"; 
	protected static String dropTable = "DROP TABLE IF EXISTS `state`;";
	protected static String[] createIndexes = {"CREATE INDEX IF NOT EXISTS state_PRIMARY ON state(id);", 
										   "CREATE INDEX IF NOT EXISTS state_region_id ON state(region_id);",
										   "CREATE INDEX IF NOT EXISTS state_country_id ON state(country_id);"};
	protected static String selectStates = "SELECT id, state_name FROM state ORDER BY (state_name);";
	protected static String listStates = "SELECT * FROM state JOIN region ON state.region_id = region.id " +
			"JOIN country ON country.id = state.country_id ORDER BY (-state.id);";
	protected static String saveStateOnlineURL = "/dashboard/savestateonline/";
	protected static String getStateOnlineURL = "/dashboard/getstatesonline/";
	protected static String saveStateOfflineURL = "/dashboard/savestateoffline/";
	protected String table_name = "state";
	protected String[] fields = {"id", "state_name", "region_id", "start_date", "country_id"};
	
	public StatesData() {
		super();
	}
	
	public StatesData(OnlineOfflineCallbacks callbacks) {
		super(callbacks);
	}
	
	public StatesData(OnlineOfflineCallbacks callbacks, Form form) {
		super(callbacks, form);
	}

	@Override
	public Data getNewData() {
		return new Data();
	}
	
	@Override
	protected String getTableId() {
		return StatesData.tableID;
	}
	
	@Override
	public String getTableName() {
		return this.table_name;
	}
	
	@Override
	protected String[] getFields() {
		return this.fields;
	}
	
	@Override
	protected String getCreateTableSql(){
		return this.createTable;
	}
	
	@Override
	protected String getDeleteTableSql(){
		return this.dropTable;
	}
	
	
	@Override
	public String getListingOnlineURL(){
		return StatesData.getStateOnlineURL;
	}
	
	@Override
	public String getSaveOfflineURL(){
		return StatesData.saveStateOfflineURL;
	}
	
	@Override
	public String getSaveOnlineURL(){
		return StatesData.saveStateOnlineURL;
	}
	
	public final native JsArray<Type> asArrayOfData(String json) /*-{
		return eval(json);
	}-*/;
	
	public List serialize(JsArray<Type> stateObjects){
		List states = new ArrayList();
		RegionsData region = new RegionsData();
		CountriesData country = new CountriesData();
		for(int i = 0; i < stateObjects.length(); i++){
			RegionsData.Data r = region.new Data(stateObjects.get(i).getRegion().getPk(), stateObjects.get(i).getRegion().getRegionName(), stateObjects.get(i).getRegion().getStartDate());
			CountriesData.Data c = country.new Data(stateObjects.get(i).getCountry().getPk(), stateObjects.get(i).getCountry().getCountryName(), stateObjects.get(i).getCountry().getStartDate());
			Data state = new Data(stateObjects.get(i).getPk(), stateObjects.get(i).getStateName(), stateObjects.get(i).getStartDate(), r, c );
			states.add(state);
		}
		
		return states;
	}
	
	@Override
	public List getListingOnline(String json){
		return this.serialize(this.asArrayOfData(json));		
	}
	
	public List getStatesListingOffline(String... pageNum){
		BaseData.dbOpen();
		List states = new ArrayList();
		RegionsData region = new RegionsData();
		CountriesData country = new CountriesData();
		String listTemp;
		// Checking whether to return all villages or only limited number of villages
		if(pageNum.length == 0) {
			listTemp = listStates;
		}
		else {
			int offset = (Integer.parseInt(pageNum[0]) - 1)*pageSize;
			listTemp = listStates + " LIMIT "+ Integer.toString(offset) + " , "+Integer.toString(pageSize) +";";
		}
		this.select(listTemp);
		if (this.getResultSet().isValidRow()){
			try {
				for (int i = 0; this.getResultSet().isValidRow(); ++i, this.getResultSet().next()) {
					RegionsData.Data r = region.new Data(this.getResultSet().getFieldAsString(5),  this.getResultSet().getFieldAsString(6), this.getResultSet().getFieldAsString(7)) ;
					CountriesData.Data c = country.new Data(this.getResultSet().getFieldAsString(8),  this.getResultSet().getFieldAsString(9), this.getResultSet().getFieldAsString(10)) ;
					Data state = new Data(this.getResultSet().getFieldAsString(0), this.getResultSet().getFieldAsString(1), this.getResultSet().getFieldAsString(3), r, c);
					states.add(state);
				}				
			} catch (DatabaseException e) {
				Window.alert("Database Exception : " + e.toString());
				BaseData.dbClose();
			}
		}
		BaseData.dbClose();
		return states;
	}
	
	public List getAllStatesOffline(){
		BaseData.dbOpen();
		List states = new ArrayList();
		this.select(selectStates);
		if (this.getResultSet().isValidRow()){
			try {
				for (int i = 0; this.getResultSet().isValidRow(); ++i, this.getResultSet().next()) {
					Data state = new Data(this.getResultSet().getFieldAsString(0), this.getResultSet().getFieldAsString(1));
					states.add(state);
				}				
			} catch (DatabaseException e) {
				Window.alert("Database Exception : " + e.toString());
				// TODO Auto-generated catch block
				BaseData.dbClose();
			}
		}
		BaseData.dbClose();
		return states;
	}

	public Object postPageData() {
		if(BaseData.isOnline()){
			this.post(RequestContext.SERVER_HOST + StatesData.saveStateOnlineURL, this.form.getQueryString());
		}
		else{
			if(this.validate()){
				this.save();
				return true;
			}
		}
		
		return false;
	}
	
	public Object postPageData(String id) {
		if(BaseData.isOnline()){
			this.post(RequestContext.SERVER_HOST + this.saveStateOnlineURL + id + "/", this.form.getQueryString());
		}
		else{
			if(this.validate()) {
				this.save();
				return true;
			}
		}
		return false;
	}
	
	public Object getListPageData(String pageNum){
		if(BaseData.isOnline()){
			int offset = (Integer.parseInt(pageNum)-1)*pageSize;
			int limit = offset+pageSize;
			this.get(RequestContext.SERVER_HOST + StatesData.getStateOnlineURL+Integer.toString(offset)+"/"+Integer.toString(limit)+ "/");
		}
		else{
			return true;
		}
		return false;
	}	

	
	public String retrieveDataAndConvertResultIntoHtml(){
		RegionsData regionData = new RegionsData();
		List regions = regionData.getAllRegionsOffline();
		RegionsData.Data region;
		String html = "<select name=\"region\" id=\"id_region\">" + 
					"<option value='' selected='selected'>---------</option>";
		for(int i=0; i< regions.size(); i++){
			region = (RegionsData.Data)regions.get(i);
			html = html + "<option value = \"" + region.getId() +"\">" + region.getRegionName() + "</option>";
		}
		html = html + "</select>";
		
		CountriesData countryData = new CountriesData();
		List countries = countryData.getAllCountriesOffline();
		CountriesData.Data country;
		
		String countryHtml = "<select name=\"country\" id=\"id_country\">" + 
		"<option value='' selected='selected'>---------</option>";
		for(int i=0; i< countries.size(); i++){
			country = (CountriesData.Data)countries.get(i);
			countryHtml = countryHtml + "<option value = \"" + country.getId() +"\">" + country.getCountryName() + "</option>";
		}
		
		countryHtml = countryHtml + "</select>";
		return html + countryHtml;
	}
	
	public Object getAddPageData(){
		if(BaseData.isOnline()){
			this.get(RequestContext.SERVER_HOST + StatesData.saveStateOnlineURL);
		}
		else{
			return retrieveDataAndConvertResultIntoHtml();
		}
		return false;
	}
	
	
	public Object getAddPageData(String id){
		if(BaseData.isOnline()){
			this.get(RequestContext.SERVER_HOST + this.saveStateOnlineURL + id + "/" );
		}
		else {
			this.form.toQueryString(id);
			return retrieveDataAndConvertResultIntoHtml();
		}
		return false;
	}
}