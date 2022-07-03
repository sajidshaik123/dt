package com.ford.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ford.model.DataRobotCataProject;
import com.ford.service.DTDataRobotService;

@RestController
@RequestMapping("/dt/datarobot/")
public class DTDataRobotController {

	@Autowired
	DTDataRobotService dTDataRobotService;

	@GetMapping(value = "/get-all-ai-catalog-items-from")
	public Object getCatalogItems() throws IOException {
		return dTDataRobotService.getCatalogItems();
	}

	@GetMapping(value = "/get-all-ai-catalog-projects-from-rest")
	public List<DataRobotCataProject> getAllAiCatalogProjectsFromRest() throws IOException {
		return dTDataRobotService.getAllAiCatalogProjectsFromRest();
	}

	@GetMapping(value = "/get-all-ai-catalog-projects-from-db")
	public List<DataRobotCataProject> getAllAiCatalogProjectsFromDB() throws IOException {
		return dTDataRobotService.getAllAiCatalogProjectsFromDB();
	}

}
