package com.ford.model;

import java.util.List;

import lombok.Data;

@Data
public class DataRobotCataLogItems {

	int count;
	String next;
	String previous;
	List <DataRobotCataProject> data;
	String cacheHit;
	int totalCount;

}
