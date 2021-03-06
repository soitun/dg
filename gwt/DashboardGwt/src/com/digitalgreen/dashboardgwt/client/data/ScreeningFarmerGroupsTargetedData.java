package com.digitalgreen.dashboardgwt.client.data;

import java.util.ArrayList;
import java.util.List;

import com.digitalgreen.dashboardgwt.client.common.Form;
import com.digitalgreen.dashboardgwt.client.common.OnlineOfflineCallbacks;
import com.digitalgreen.dashboardgwt.client.common.RequestContext;
import com.digitalgreen.dashboardgwt.client.data.ScreeningFarmerGroupsTargetedData.Data;
import com.digitalgreen.dashboardgwt.client.data.ScreeningFarmerGroupsTargetedData.Type;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.user.client.Window;

public class ScreeningFarmerGroupsTargetedData extends BaseData {
	
	public static class Type extends BaseData.Type{
		protected Type(){}
		
		public final native String getScreening() /*-{ return this.fields.screening;}-*/;
		public final native String getPersonGroup() /*-{ return this.fields.persongroups;}-*/;		
	}
	
	public class Data extends BaseData.Data {
		final private static String COLLECTION_PREFIX = "screeningfarmergroupstargeted";
		
		private ScreeningsData.Data screening;// FK to the Screenings table
		private PersonGroupsData.Data persongroups;
				
		public Data() {
			super();
		}
		
		public Data(String id, ScreeningsData.Data screening, PersonGroupsData.Data persongroups) {
			this.id = id;
			this.screening = screening;
			this.persongroups = persongroups;
			}
		
		public Data(String id, PersonGroupsData.Data persongroups){
			super();
			this.id = id;
			this.persongroups = persongroups;
		}
			
		public ScreeningsData.Data getScreening(){
			return this.screening;
		}
		
		public PersonGroupsData.Data getPersonGroup(){
			return this.persongroups;
		}
		
		public BaseData.Data clone(){
			Data obj = new Data();
			obj.screening = (new ScreeningsData()).new Data();
			obj.persongroups = (new PersonGroupsData()).new Data();
				
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
			}else if(key.equals("screening")) {
				ScreeningsData screening = new ScreeningsData();
				this.screening = screening.getNewData();
				this.screening.id = val;
				
			} else if(key.equals("persongroups")) {
				PersonGroupsData persongroups = new PersonGroupsData();
				this.persongroups = persongroups.getNewData();
				this.persongroups.id = val;
			} else {
				return;
			}
			this.addNameValueToQueryString(key, val);
		}
	

		@Override		
		public void save() {
			ScreeningFarmerGroupsTargetedData screeningFarmerGroupsTargetedsDataDbApis = new ScreeningFarmerGroupsTargetedData();
			this.id = screeningFarmerGroupsTargetedsDataDbApis.autoInsert(this.id,
					this.screening.getId(),
					this.persongroups.getId());
			this.addNameValueToQueryString("id", this.id);
		}
		
		@Override
		public void save(BaseData.Data foreignKey){
			ScreeningFarmerGroupsTargetedData screeningFarmerGroupsTargetedsDataDbApis = new ScreeningFarmerGroupsTargetedData();
			this.id = screeningFarmerGroupsTargetedsDataDbApis.autoInsert(this.id,
					foreignKey.getId(), 
					this.persongroups.getId());
			this.addNameValueToQueryString("id", this.id);
			this.addNameValueToQueryString("screening", foreignKey.getId());
		}
		
		@Override
		public String toQueryString(String id) {
			ScreeningFarmerGroupsTargetedData screeningFarmerGroupsTargetedData = new ScreeningFarmerGroupsTargetedData();
			return this.rowToQueryString(screeningFarmerGroupsTargetedData.getTableName(), screeningFarmerGroupsTargetedData.getFields(), "id", id, "");
		}
		
		@Override
		public String getTableId() {
			ScreeningFarmerGroupsTargetedData screeningFarmerGroupsTargetedsDataDbApis = new ScreeningFarmerGroupsTargetedData();
			return screeningFarmerGroupsTargetedsDataDbApis.tableID;
		}
	}
	
	
	public static String tableID = "40";
	protected static String createTable = "CREATE TABLE IF NOT EXISTS `screening_farmer_groups_targeted` " +
												"(id BIGINT UNSIGNED PRIMARY KEY  NOT NULL ," +
												"screening_id BIGINT UNSIGNED  NOT NULL DEFAULT 0," +
												"persongroups_id BIGINT UNSIGNED  NOT NULL DEFAULT 0, " +
												"FOREIGN KEY(screening_id) REFERENCES screening(id), " +
												"FOREIGN KEY(persongroups_id) REFERENCES person_groups(id));" ;
	protected static String dropTable = "DROP TABLE IF EXISTS `screening_farmer_groups_targeted`;";
	protected static String[] createIndexes = {"CREATE INDEX IF NOT EXISTS screening_farmer_groups_targeted_PRIMARY ON screening_farmer_groups_targeted(id);", 
	   "CREATE INDEX IF NOT EXISTS screening_farmer_groups_targeted_screening_id ON screening_farmer_groups_targeted(screening_id);",
	   "CREATE INDEX IF NOT EXISTS screening_farmer_groups_targeted_persongroups_id ON screening_farmer_groups_targeted(persongroups_id);"};
	protected static String selectScreeningFarmerGroupsTargeted = "SELECT id FROM screening_farmer_groups_targeted ORDER BY (id);";
	protected static String listScreeningFarmerGroupsTargeted = "SELECT sfgt.id, pg.group_name FROM screening_farmer_groups_targeted sfgt" +
			",person_group pg WHERE sfgt.persongroups_id = pg.id ORDER BY (sfgt.id);";
	protected static String saveScreeningFarmerGroupsTargetedOfflineURL = "/dashboard/savescreeningfarmergroupstargetedoffline/";
	protected static String saveScreeningFarmerGroupsTargetedOnlineURL = "/dashboard/savescreeningfarmergroupstargetedonline/";
	protected static String getScreeningFarmerGroupsTargetedOnlineURL = "/dashboard/getscreeningfarmergroupstargetedsonline/";
	protected String table_name = "screening_farmer_groups_targeted";
	protected String[] fields = {"id", "screening_id","persongroups_id"};
		
	public ScreeningFarmerGroupsTargetedData() {
		super();
	}
	public ScreeningFarmerGroupsTargetedData(OnlineOfflineCallbacks callbacks) {
		super(callbacks);
	}
	
	public ScreeningFarmerGroupsTargetedData(OnlineOfflineCallbacks callbacks, Form form) {
		super(callbacks, form);
	}
	@Override
	public Data getNewData() {
		return new Data();
	}
	@Override
	protected String getTableId(){
		return ScreeningFarmerGroupsTargetedData.tableID;
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
		return ScreeningFarmerGroupsTargetedData.getScreeningFarmerGroupsTargetedOnlineURL;
	}

	@Override
	public String getSaveOfflineURL(){
		return ScreeningFarmerGroupsTargetedData.saveScreeningFarmerGroupsTargetedOfflineURL;
	}
	
	@Override
	public String getSaveOnlineURL(){
		return ScreeningFarmerGroupsTargetedData.saveScreeningFarmerGroupsTargetedOnlineURL;
	}
	
	public final native JsArray<Type> asArrayOfData(String json) /*-{
		return eval(json);
	}-*/;
	
	public List serialize(JsArray<Type> screeningFarmerGroupsTargetedObjects){
		List screeningFarmerGroupsTargeteds = new ArrayList();
		ScreeningsData screening = new ScreeningsData();
		PersonGroupsData persongroups = new PersonGroupsData();
		for(int i = 0; i < screeningFarmerGroupsTargetedObjects.length(); i++){
			ScreeningsData.Data sc = screening.new Data(screeningFarmerGroupsTargetedObjects.get(i).getScreening());
			PersonGroupsData.Data pg = persongroups.new Data(screeningFarmerGroupsTargetedObjects.get(i).getPersonGroup());
			
			Data screeningFarmerGroupsTargeted = new Data(screeningFarmerGroupsTargetedObjects.get(i).getPk(),sc,pg);
			screeningFarmerGroupsTargeteds.add(screeningFarmerGroupsTargeted);
		}
		
		return screeningFarmerGroupsTargeteds;
	}
	
	@Override
	public List getListingOnline(String json){
		return this.serialize(this.asArrayOfData(json));		
	}
	
}
