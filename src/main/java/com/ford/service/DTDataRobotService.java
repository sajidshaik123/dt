package com.ford.service;

import java.io.IOException;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ford.constants.DTConstants;
import com.ford.model.DataRobotCataLogItems;
import com.ford.model.DataRobotCataProject;
import com.ford.repository.DataRobotCataProjectRepository;
import com.ford.utils.DTUtils;
import com.ford.utils.RestClient;

@Service
public class DTDataRobotService {

	@Autowired
	RestClient restClient;

	@Autowired
	DataRobotCataProjectRepository dataRobotCataProjectRepository;

	public DataRobotCataLogItems getCatalogItems() throws IOException {

		HttpEntity<String> requestBody = new HttpEntity<String>("parameters",
				restClient.getHttpHeaders(MediaType.APPLICATION_JSON, DTConstants.API_TOKEN));

		String restObject = restClient.getForObject(DTConstants.DT_ENDPOINT + "catalogItems", requestBody, String.class)
				.getBody();
		JSONObject json = new JSONObject(restObject);
		ObjectMapper mapper = new ObjectMapper();
		DataRobotCataLogItems dataRobotCataLogItems = mapper.readValue(json.toString(), DataRobotCataLogItems.class);
		return dataRobotCataLogItems;
	}

	public List<DataRobotCataProject> getAllAiCatalogProjectsFromRest() throws IOException {
		List<DataRobotCataProject> dataRobotCataProjects = getCatalogItems().getData();
		return dataRobotCataProjects;
	}

	public List<DataRobotCataProject> getAllAiCatalogProjectsFromDB() throws IOException {
		List<DataRobotCataProject> dataRobotCataProjects = getAllAiCatalogProjectsFromRest();
		dataRobotCataProjectRepository.truncateDataRobotCataProject();
		DTUtils.setProjectsUrl(dataRobotCataProjects);
		List<DataRobotCataProject> dataRobotCataProjectsFromDb = dataRobotCataProjectRepository
				.saveAll(dataRobotCataProjects);
		return dataRobotCataProjectsFromDb;
	}
}
