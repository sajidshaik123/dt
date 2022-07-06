package com.ford.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ford.constants.DTConstants;
import com.ford.model.DTFileData;
import com.ford.model.DataRobotCataLogItems;
import com.ford.model.DataRobotCataProject;
import com.ford.repository.DTExcelRepository;
import com.ford.repository.DataRobotCataProjectRepository;
import com.ford.utils.DTUtils;
import com.ford.utils.RestClient;
import com.opencsv.CSVWriter;

@Service
public class DTDataRobotService {

	@Autowired
	RestClient restClient;

	@Autowired(required = false)
	DTExcelRepository dTExcelRepository;

	@Autowired(required = false)
	DataRobotCataProjectRepository dataRobotCataProjectRepository;

	public DataRobotCataLogItems getCatalogItems() throws IOException {

		HttpEntity<String> requestBody = new HttpEntity<String>("parameters",
				restClient.getHttpHeaders(MediaType.APPLICATION_JSON.toString(), DTConstants.API_TOKEN));

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

	public ResponseEntity<String> moveToDataRobot(MultiValueMap<String, String> headers, String fileName)
			throws IOException {

		List<DTFileData> dTFileDataObject = dTExcelRepository.findAllByFileName(fileName);
		List<String> dTFileRowDataList = dTFileDataObject.stream().map(r -> r.getFileRowData())
				.collect(Collectors.toList());

		List<String[]> dTFileFilteredDataList = new ArrayList<String[]>();
		for (String eachRow : dTFileRowDataList) {
			dTFileFilteredDataList.add(DTUtils.splitStringAndReturnAsStringArray(eachRow));
		}
		String tempFileDir = DTConstants.DT_TEMP_DIR + fileName;
		try (CSVWriter writer = new CSVWriter(new FileWriter(tempFileDir))) {
			writer.writeAll(dTFileFilteredDataList);
		}

		File storeTempFile = new File(tempFileDir);
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
		body.add("file", storeTempFile);
		
		HttpEntity<Object> requestEntity = new HttpEntity<Object>(body, headers);
		String serverUrl = DTConstants.DT_ENDPOINT + "/datasets/fromFile";
		ResponseEntity<String> serverResponse = restClient.postForEntity(serverUrl, requestEntity, String.class);
		storeTempFile.delete(); // Once upload is done then no use of storing in our directory, we can safe delete
		return serverResponse;
	}

}
