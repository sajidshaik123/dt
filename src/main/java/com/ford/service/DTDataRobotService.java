package com.ford.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
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

	@Autowired
	DTExcelRepository dTExcelRepository;

	@Autowired
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

	public Object moveToDataRobot(MultiValueMap<String, String> headers, MultipartFile file) throws IOException {
		/*
		 * List<DTFileData> dTFileDataObject =
		 * dTExcelRepository.findAllByFileName(fileName); List<String> dTFileRowDataList
		 * = dTFileDataObject.stream().map(r -> r.getFileRowData())
		 * .collect(Collectors.toList()); String fileHeading = dTFileRowDataList.get(0);
		 * splitStringAndReturnAsStringList(fileHeading); ArrayList<List<String>>
		 * rowList = new ArrayList<List<String>>(); dTFileRowDataList.remove(0);
		 */

		Map<String, Object> body = new HashMap<>();
		//body.add("file", new File("C://Users/shaik/OneDrive/Documents/ford/12.csv"));
		//body.put("file", new ByteArrayResource(file.getBytes()));
		headers.add("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
		headers.add("Accept", "application/json");
		headers.add("Authorization", DTConstants.API_TOKEN);

		HttpEntity<HashMap<String, Object>> requestEntity = new HttpEntity<HashMap<String, Object>>(headers);

		String serverUrl = "https://app2.datarobot.com/api/v2/datasets/fromFile";
		ResponseEntity<String> r = restClient.postForEntity(serverUrl, requestEntity, String.class);
		Map<String, Object> response = new HashMap<String, Object>();
		response.put("body", r.getBody());
		response.put("statusCode", r.getStatusCodeValue());
		return response;
	}

	private List<String> splitStringAndReturnAsStringList(String eachRow) {
		List<String> result = Arrays.asList(eachRow.split("--"));
		return result;
	}
}
