package com.ford.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ford.service.DTExcelService;

@RestController
@RequestMapping("/dt/excel/")
public class DTExcelController {

	@Autowired
	DTExcelService dTExcelService;

	@GetMapping(value = "/get-excel-records")
	public List<String> getExcelRecords() {
		List<String> response = dTExcelService.getAllExcelRecords();
		return response;
	}

	@PostMapping(value = "/delete-and-save-every-excelrecords-to-database")
	public List<String> saveEveryExcelRecordsToDataBase() {
		return dTExcelService.saveEveryExcelRecordsToDataBase();
	}

}