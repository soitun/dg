package com.digitalgreen.dashboardgwt.client.data;

import java.util.ArrayList;
import java.util.List;
import com.digitalgreen.dashboardgwt.client.common.Form;
import com.digitalgreen.dashboardgwt.client.common.OnlineOfflineCallbacks;
import com.digitalgreen.dashboardgwt.client.common.RequestContext;
import com.digitalgreen.dashboardgwt.client.data.validation.BaseValidator;
import com.digitalgreen.dashboardgwt.client.data.validation.DateValidator;
import com.digitalgreen.dashboardgwt.client.data.validation.ManyToManyValidator;
import com.digitalgreen.dashboardgwt.client.data.validation.StringValidator;
import com.digitalgreen.dashboardgwt.client.data.validation.UniqueConstraintValidator;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.gears.client.database.DatabaseException;
import com.google.gwt.user.client.Window;

public class TrainingsData extends BaseData {
	
	public static class Type extends BaseData.Type {
		protected Type() {}
		public final native String getTrainingPurpose() /*-{ return $wnd.checkForNullValues(this.fields.training_purpose); }-*/;
		public final native String getTrainingOutcome() /*-{ return $wnd.checkForNullValues(this.fields.training_outcome); }-*/;
		public final native String getTrainingStartDate() /*-{ return $wnd.checkForNullValues(this.fields.training_start_date); }-*/;
		public final native String getTrainingEndDate() /*-{ return $wnd.checkForNullValues(this.fields.training_end_date); }-*/;
		public final native VillagesData.Type getVillage() /*-{ return this.fields.village; }-*/;
		public final native String getDevelopmentManager() /*-{ return this.fields.development_manager_present }-*/;
		public final native String getFieldOfficer() /*-{ return this.fields.fieldofficer; }-*/;
	}
	
	public class Data extends BaseData.Data {
		
		final private String COLLECTION_PREFIX = "training";

		private String training_purpose;
		private String training_outcome;
		private String training_start_date;
		private String training_end_date;
		private VillagesData.Data village;
		private DevelopmentManagersData.Data development_manager_present;
		private FieldOfficersData.Data field_officer_present;
		private ArrayList animators_trained;
		
		public Data(){
			super();
			this.animators_trained = new ArrayList();
			this.addManyToManyRelationship("animator", 
					new TrainingAnimatorsTrainedData(), 
					"animators_trained");
		}
		
		public Data(String id){
			super();
			this.id = id;
		}
		
		public Data(String id, String training_purpose, String training_outcome, String training_start_date, String training_end_date, 
				VillagesData.Data village, DevelopmentManagersData.Data development_manager_present, 
				FieldOfficersData.Data field_officer_present ){
			super();
			this.id = id;
			this.training_purpose = training_purpose;
			this.training_outcome = training_outcome;
			this.training_start_date = training_start_date;
			this.training_end_date = training_end_date;
			this.village = village;
			this.development_manager_present = development_manager_present;
			this.field_officer_present = field_officer_present;
		}

		public Data(String id, String training_start_date){
			super();
			this.id = id;
			this.training_start_date = training_start_date;
		}
		

		public Data(String id, String training_start_date, VillagesData.Data village){
			super();
			this.id = id;
			this.training_start_date = training_start_date;
			this.village = village;	
		}


		public Data(String id, String training_purpose, String training_outcome){
			super();
			this.id = id;
			this.training_purpose = training_purpose;
			this.training_outcome = training_outcome;
		}
		
		
		public String getTrainingStartDate() {
			return this.training_start_date;
		}
		
		public String getTrainingEndDate() {
			return this.training_end_date;
		}
		
		public VillagesData.Data getVillage() {
			return this.village;
		}
		
		@Override
		public BaseData.Data clone() {
			Data obj = new Data();
			obj.village = (new VillagesData()).new Data();
			obj.development_manager_present = (new DevelopmentManagersData()).new Data();
			obj.field_officer_present = (new FieldOfficersData()).new Data();	
			return obj;
		}
		
		@Override
		public String getPrefixName() {
			return this.COLLECTION_PREFIX;
		}
		
		@Override
		public void setObjValueFromString(String key, String val) {
			super.setObjValueFromString(key, val);
			if(key.equals("id")) {
				this.id = val;
			}
			else if(key.equals("training_purpose")){
				this.training_purpose = val;
			}
			else if(key.equals("training_outcome")){
				this.training_outcome = val;
			}
			else if(key.equals("training_start_date")){
				this.training_start_date = val;
			}
			else if(key.equals("training_end_date")){
				this.training_end_date = val;
			}
			else if(key.equals("village")){
				VillagesData village1 = new VillagesData();
				this.village = village1.getNewData();
				this.village.id = val;
			}
			else if(key.equals("development_manager_present")){
				DevelopmentManagersData developmentmanager1 = new DevelopmentManagersData();
				this.development_manager_present = developmentmanager1.getNewData();
				this.development_manager_present.id = val;
			}
			else if(key.equals("fieldofficer")){
				FieldOfficersData fieldofficer1 = new FieldOfficersData();
				this.field_officer_present = fieldofficer1.getNewData();
				this.field_officer_present.id = val;
			} 
			else if(key.equals("animators_trained")) {
				animators_trained.add(val);
			} else {
				return;
			}
			this.addNameValueToQueryString(key, val);
		}
		
		@Override
		public boolean validate() {
			//Labels to print validation error messages
			String trainingPurposeLabel = "Training Purpose";
			String trainingOutcomeLabel = "Training Outcome";
			String trainingStartDateLabel = "Training StartDate";
			String trainingEndDateLabel = "Training EndDate";
			String fieldOfficerLabel = "Address";
			String villageLabel = "Village";
			String animatorsTrainedLabel = "Animators Trained";
			
			StringValidator trainingPurpose = new StringValidator(trainingPurposeLabel, this.training_purpose, true, false, 0, 1024, true);
			StringValidator trainingOutcome = new StringValidator(trainingOutcomeLabel, this.training_outcome, true, false, 0, 1024, true);
			DateValidator trainingStartDate = new DateValidator(trainingStartDateLabel, this.training_start_date, false, false);
			DateValidator trainingEndDate = new DateValidator(trainingEndDateLabel, this.training_end_date, false, false);
			StringValidator villageValidator = new StringValidator(fieldOfficerLabel, this.village.getId(), false, false, 1, 100);
			StringValidator fieldValidator = new StringValidator(villageLabel, this.field_officer_present.getId(), false, false, 1, 100);
			ManyToManyValidator animatorsTrained = new ManyToManyValidator(animatorsTrainedLabel, this.animators_trained, false);
			ArrayList predicateStartDate = new ArrayList();
			
			predicateStartDate.add("training_start_date");
			predicateStartDate.add(this.training_start_date);
			ArrayList predicateEndDate = new ArrayList();
			predicateEndDate.add("training_end_date");
			predicateEndDate.add(this.training_end_date);
			ArrayList predicateVillageId = new ArrayList();
			predicateVillageId.add("village_id");
			predicateVillageId.add(this.village.getId());
			ArrayList startDateEndDateVillageIdIndex = new ArrayList();
			startDateEndDateVillageIdIndex.add(predicateStartDate);
			startDateEndDateVillageIdIndex.add(predicateStartDate);
			startDateEndDateVillageIdIndex.add(predicateVillageId);
			ArrayList uniqueValidatorLabels = new ArrayList();
			uniqueValidatorLabels.add("Start Date");
			uniqueValidatorLabels.add("End Date");
			uniqueValidatorLabels.add("Village");
			UniqueConstraintValidator startDateEndDateVillageId = new UniqueConstraintValidator(uniqueValidatorLabels, 
					startDateEndDateVillageIdIndex,	new TrainingsData());
			startDateEndDateVillageId.setCheckId(this.getId());
			
			ArrayList validatorList = new ArrayList();
			validatorList.add(trainingPurpose);
			validatorList.add(trainingOutcome);
			validatorList.add(trainingStartDate);
			validatorList.add(trainingEndDate);
			validatorList.add(animatorsTrained);
			validatorList.add(villageValidator);
			validatorList.add(fieldValidator);
			validatorList.add(startDateEndDateVillageId);
			return this.executeValidators(validatorList);
		}

		@Override
		public void save() {
			TrainingsData trainingsDataDbApis = new TrainingsData();
			this.id = trainingsDataDbApis.autoInsert(this.id, 
					this.training_purpose, 
					this.training_outcome, 
					this.training_start_date, 
					this.training_end_date, 
					this.village.getId(),
					this.development_manager_present.getId(),
					this.field_officer_present.getId());
			this.addNameValueToQueryString("id", this.id);
		}
		
		@Override
		public String toQueryString(String id) {
			TrainingsData trainingsData = new TrainingsData();
			return this.rowToQueryString(trainingsData.getTableName(), trainingsData.getFields(), "id", id, "");
		}
		
		@Override
		public String getTableId() {
			TrainingsData trainingsDataDbApis = new TrainingsData();
			return trainingsDataDbApis.tableID;
		}
	}

	public static String tableID = "23";
	protected static String createTable = "CREATE TABLE IF NOT EXISTS `training` " +
												"(id BIGINT UNSIGNED PRIMARY KEY  NOT NULL ," +
												"TRAINING_PURPOSE TEXT NULL ," +
												"TRAINING_OUTCOME TEXT NULL ," +
												"TRAINING_START_DATE DATE  NOT NULL," +
												"TRAINING_END_DATE DATE  NOT NULL," +
												"village_id BIGINT UNSIGNED NOT NULL DEFAULT 0," +
												"dm_id BIGINT UNSIGNED NULL DEFAULT 0," +
												"fieldofficer_id BIGINT UNSIGNED NOT NULL DEFAULT 0, " +
												"FOREIGN KEY(village_id) REFERENCES village(id), " +
												"FOREIGN KEY(dm_id) REFERENCES development_manager(id), " +
												"FOREIGN KEY(fieldofficer_id) REFERENCES field_officer(id));" ;  
	protected static String dropTable = "DROP TABLE IF EXISTS `training`;";
	protected static String[] createIndexes = {"CREATE INDEX IF NOT EXISTS training_PRIMARY ON training(id);",
	   										"CREATE INDEX IF NOT EXISTS training_village_id ON training(village_id);",
	   										"CREATE INDEX IF NOT EXISTS training_dm_id ON training(dm_id);",
	   										"CREATE INDEX IF NOT EXISTS training_fieldofficer_id ON training(fieldofficer_id);"};
	protected static String selectTrainings = "SELECT id, TRAINING_PURPOSE, TRAINING_OUTCOME, TRAINING_START_DATE, TRAINING_END_DATE " +
			"FROM training ORDER BY (-id)";
	protected static String listTrainings = "SELECT training.id, training.training_purpose, training.training_outcome, " +
			"training.training_start_date, training.training_end_date, village.id, village.village_name, development_manager.id, " +
			"development_manager.name, field_officer.id, field_officer.name " +
			"FROM training LEFT JOIN village ON training.village_id = village.id LEFT JOIN development_manager " +
			"ON training.dm_id = development_manager.id JOIN field_officer ON training.fieldofficer_id = field_officer.id " +
			"ORDER BY LOWER(training.TRAINING_START_DATE);";
	protected static String saveTrainingOnlineURL = "/dashboard/savetrainingonline/";
	protected static String getTrainingsOnlineURL = "/dashboard/gettrainingsonline/";
	protected static String saveTrainingOfflineURL = "/dashboard/savetrainingoffline/";
	protected String table_name = "training";
	protected String[] fields = {"id", "training_purpose", "training_outcome", "training_start_date", "training_end_date", "village_id", "dm_id", "fieldofficer_id"};
	
	public TrainingsData() {
		super();
	}
	
	public TrainingsData(OnlineOfflineCallbacks callbacks) {
		super(callbacks);
	}
	
	public TrainingsData(OnlineOfflineCallbacks callbacks, Form form) {
		super(callbacks, form);
	}
	
	@Override
	public Data getNewData() {
		return new Data();
	}
	
	@Override
	protected String getTableId() {
		return TrainingsData.tableID;
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
		return TrainingsData.getTrainingsOnlineURL;
	}
	
	@Override
	public String getSaveOfflineURL(){
		return TrainingsData.saveTrainingOfflineURL;
	}
	
	@Override
	public String getSaveOnlineURL(){
		return TrainingsData.saveTrainingOnlineURL;
	}
	
	public final native JsArray<Type> asArrayOfData(String json) /*-{
		return eval(json);
	}-*/;
	
	public List serialize(JsArray<Type> trainingObjects) {
		List trainings = new ArrayList();
		VillagesData village = new VillagesData();
		DevelopmentManagersData developmentManager = new DevelopmentManagersData();
		FieldOfficersData fieldOfficer = new FieldOfficersData();

		for(int i = 0; i < trainingObjects.length(); i++){
			DevelopmentManagersData.Data dm = developmentManager.new Data();
			VillagesData.Data	v = village. new Data(trainingObjects.get(i).getVillage().getPk(), trainingObjects.get(i).getVillage().getVillageName());
			if(trainingObjects.get(i).getDevelopmentManager()!=null){
				dm = developmentManager.new Data(trainingObjects.get(i).getDevelopmentManager());
			}
			FieldOfficersData.Data fo = fieldOfficer.new Data(trainingObjects.get(i).getFieldOfficer());
			Data training = new Data(trainingObjects.get(i).getPk(),
					trainingObjects.get(i).getTrainingPurpose(),
					trainingObjects.get(i).getTrainingOutcome(),
					trainingObjects.get(i).getTrainingStartDate(),
					trainingObjects.get(i).getTrainingEndDate(),
					v,
					dm,
					fo);

			trainings.add(training);
		}
		return trainings;
	}
			
	@Override
	public List getListingOnline(String json){
		return this.serialize(this.asArrayOfData(json));
	}
	
	public List getTrainingsListingsOffline(String... pageNum) {
		BaseData.dbOpen();
		List trainings = new ArrayList();
		VillagesData village = new VillagesData();
		DevelopmentManagersData developmentmanager = new DevelopmentManagersData();
		FieldOfficersData fieldofficer = new FieldOfficersData();
		String listTemp;
		// Checking whether to return all villages or only limited number of villages
		if(pageNum.length == 0) {
			listTemp = listTrainings;
		}
		else {
			int offset = (Integer.parseInt(pageNum[0]) - 1)*pageSize;
			listTemp = listTrainings + " LIMIT "+ Integer.toString(offset) + " , "+Integer.toString(pageSize) +";";
		}
		this.select(listTemp);
		if(this.getResultSet().isValidRow()){
			try {
				for (int i = 0; this.getResultSet().isValidRow(); ++i, this.getResultSet().next()) {
					
					VillagesData.Data v = village. new Data(this.getResultSet().getFieldAsString(5), this.getResultSet().getFieldAsString(6));
					
					DevelopmentManagersData.Data dm = developmentmanager. new Data(this.getResultSet().getFieldAsString(7), this.getResultSet().getFieldAsString(8));
					
					FieldOfficersData.Data f = fieldofficer. new Data(this.getResultSet().getFieldAsString(9), this.getResultSet().getFieldAsString(10));
					
					Data training = new Data(this.getResultSet().getFieldAsString(0), this.getResultSet().getFieldAsString(1), 
							this.getResultSet().getFieldAsString(2), this.getResultSet().getFieldAsString(3), 
							this.getResultSet().getFieldAsString(4), v, dm, f);
					
					trainings.add(training);					
				}
			}
			catch (DatabaseException e) {
				Window.alert("Database Exception : " + e.toString());
				BaseData.dbClose();
			}
		}
		BaseData.dbClose();
		return trainings;
	}
	
	public List getAllTrainingsOffline(){
		BaseData.dbOpen();
		List trainings = new ArrayList();
		this.select(selectTrainings);
		if(this.getResultSet().isValidRow()){
			try {
				for (int i = 0; this.getResultSet().isValidRow(); ++i, this.getResultSet().next()) {
					Data training = new Data(this.getResultSet().getFieldAsString(0), this.getResultSet().getFieldAsString(1));
					trainings.add(training);
				}
			}
			catch (DatabaseException e) {
				Window.alert("Database Exception : " + e.toString());
				BaseData.dbClose();
			}
		}
		BaseData.dbClose();
		return trainings;
	}
	
	public List getTemplateDataOnline(String json){
		List relatedData = null;
		return relatedData;
	}
	
	public Object postPageData() {
		if(BaseData.isOnline()){
			this.post(RequestContext.SERVER_HOST + TrainingsData.saveTrainingOnlineURL, this.form.getQueryString());
		}
		else{
			if(this.validate()) {
				this.save();
				return true;
			}
		}
		return false;
	}
	
	public Object postPageData(String id) {
		if(BaseData.isOnline()){
			this.post(RequestContext.SERVER_HOST + TrainingsData.saveTrainingOnlineURL + id + "/", this.form.getQueryString());
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
			this.get(RequestContext.SERVER_HOST + TrainingsData.getTrainingsOnlineURL+Integer.toString(offset)+"/"+Integer.toString(limit)+ "/");
		}
		else{
			return true;
		}
		return false;
	}
	
	public String retrieveDataAndConvertResultIntoHtml() {
		VillagesData villageData = new VillagesData();
		List villages = villageData.getAllVillagesOffline();
		VillagesData.Data village;
		String htmlVillage = "<select name=\"village\" id=\"id_village\""+ 
		"<option value='' selected='selected'>---------</option>";
		for(int i=0; i < villages.size(); i++){
			village = (VillagesData.Data)villages.get(i);
			htmlVillage = htmlVillage + "<option value=\"" + village.getId() + "\">" + village.getVillageName() + "</option>";
		}
		htmlVillage = htmlVillage + "</select>";
		
		DevelopmentManagersData developmentmanagerData = new DevelopmentManagersData();
		List developmentmanagers = developmentmanagerData.getAllDevelopmentManagersOffline();
		DevelopmentManagersData.Data developmentmanager;
		String htmlDevelopmentManager = "<select name=\"development_manager_present\" id=\"id_development_manager_present\""+ 
		"<option value='' selected='selected'>---------</option>";
		for(int i = 0; i < developmentmanagers.size(); i++){
			developmentmanager = (DevelopmentManagersData.Data)developmentmanagers.get(i);
			htmlDevelopmentManager = htmlDevelopmentManager + "<option value=\"" + developmentmanager.getId() + "\">" + developmentmanager.getName() + "</option>";
		}
		htmlDevelopmentManager = htmlDevelopmentManager + "</select>";

		FieldOfficersData fieldofficerData = new FieldOfficersData();
		List fieldofficers = fieldofficerData.getAllFieldOfficersOffline();
		FieldOfficersData.Data fieldofficer;
		String htmlFO = "<select name=\"fieldofficer\" id=\"id_fieldofficer\""+ 
		"<option value='' selected='selected'>---------</option>";
		for(int i=0; i < fieldofficers.size(); i++){
			fieldofficer = (FieldOfficersData.Data)fieldofficers.get(i);
			htmlFO = htmlFO + "<option value=\"" + fieldofficer.getId() + "\">" + fieldofficer.getFieldOfficerName() + "</option>";
		}
		htmlFO = htmlFO + "</select>";
		
		AnimatorsData animatorData = new AnimatorsData();
		List animators = animatorData.getAllAnimatorsOffline();
		AnimatorsData.Data animator;
		String htmlAnimator = "<select name=\"animators_trained\" id=\"id_animators_trained\""+ 
		"<option value='' selected='selected'>---------</option>";
		for ( int i = 0; i < animators.size(); i++ ) {
			animator = (AnimatorsData.Data)animators.get(i);
			htmlAnimator = htmlAnimator + "<option value=\"" + animator.getId() + "\">" + animator.getAnimatorName() + "</option>";
		}
		htmlAnimator = htmlAnimator + "</select>";
		
		return htmlVillage + htmlDevelopmentManager + htmlFO + htmlAnimator;
	}
	
	public Object getAddPageData() {
		if(BaseData.isOnline()) {
			this.get(RequestContext.SERVER_HOST + TrainingsData.saveTrainingOnlineURL);
		}
		else{
			return retrieveDataAndConvertResultIntoHtml();
		}
		return false;
	}
	
	public Object getAddPageData(String id) {
		if(BaseData.isOnline()) {
			this.get(RequestContext.SERVER_HOST + TrainingsData.saveTrainingOnlineURL + id + "/");
		}
		else{
			this.form.toQueryString(id);
			return retrieveDataAndConvertResultIntoHtml();
		}
		return false;
	}
}