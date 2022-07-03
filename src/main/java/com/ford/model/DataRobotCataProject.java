package com.ford.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


import lombok.Data;

@Data
@Entity
@Table(name = "DataRobotCataProject")
public class DataRobotCataProject {

	@Id
	String id;
	String originalName;
	String catalogName;
	String description;
	String catalogType;
	String[] tags;
	String infoCreationDate;
	String infoCreatorFullName;
	String infoModificationDate;
	String infoModifierFullName;
	String lastModificationDate;
	String lastModifierFullName;
	int projectsUsedInCount;
	String uri;
	String processingState;
	String error;
	int dataSourceId;
	int dataEngineQueryId;
	String relevance;
	Boolean isDataEngineEligible;
	boolean canUseDatasetData;
	int dataMeshWorkspaceId;
	int pipelinesWorkspaceId;
	int userBlueprintId;
	//cusstomised fields
	String projectUrl;
	

}
