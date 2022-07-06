package com.ford.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ford.constants.DTConstants;
import com.ford.model.DTFileData;
import com.ford.repository.DTExcelRepository;
import com.ford.utils.DTUtils;

@Service
public class DTExcelService {

	@Autowired
	DTExcelRepository dTExcelRepository;

	public List<String> getAllExcelRecords() {

		List<String> response = new ArrayList<String>();

		List<String> folderPathList = DTConstants.getFolderPathList();

		for (String folderPath : folderPathList) {
			List<String> fileNameList = DTUtils.getFilesNamesFromFolder(folderPath);
			for (String fileName : fileNameList) {
				String filePath = folderPath + "/" + fileName;
				String fileNameWithoutExtension = DTUtils.excludeFileExtension(fileName);
				response.add("File Name ->" + fileNameWithoutExtension);
				response.addAll(DTUtils.getFileRecordsFromDirectory(filePath));
				String line = "--------------------------------------------------------------------------------";
				response.add(line);
			}
		}

		return response;
	}

	public List<String> saveEveryExcelRecordsToDataBase() {
		List<String> response = new ArrayList<String>();
		List<String> folderPathList = DTConstants.getFolderPathList();

		List<DTFileData> dTFileExcelRowsList = new ArrayList<DTFileData>();
		List<String> fileNameListForAllDirectories = new ArrayList<String>();
		for (String folderPath : folderPathList) {
			List<String> fileNameList = DTUtils.getFilesNamesFromFolder(folderPath);
			fileNameListForAllDirectories.addAll(fileNameList);
			if (CollectionUtils.isEmpty(fileNameList)) {
				response.add("Empty files under " + folderPathList);
				return response;
			}
			for (String fileName : fileNameList) {
				String filePath = folderPath + "/" + fileName;
				List<String> fileRecordsFromDirectory = DTUtils.getFileRecordsFromDirectory(filePath);
				if (CollectionUtils.isNotEmpty(fileNameList)) {
					for (String fileRecord : fileRecordsFromDirectory) {
						DTFileData dTFileExcelRowDataObject = new DTFileData();
						dTFileExcelRowDataObject.setFileName(fileName);
						dTFileExcelRowDataObject.setFileRowData(fileRecord);
						dTFileExcelRowDataObject.setFilePath(filePath);
						dTFileExcelRowsList.add(dTFileExcelRowDataObject);
					}
				}

			}
		}

		dTExcelRepository.truncateDTFileData();

		List<DTFileData> dTFileExcelRowsDataBaseObject = dTExcelRepository.saveAll(dTFileExcelRowsList);

		for (String fileName : fileNameListForAllDirectories) {
			response.add(fileName);
			String line = "--------------------------------------------------------------------------------";
			response.add(line);
			response.addAll(dTFileExcelRowsDataBaseObject.stream().filter(f -> f.getFileName().equals(fileName))
					.map(f -> f.getFileRowData()).collect(Collectors.toList()));
		}

		return response;
	}
	
	public List<DTFileData> getAllExcelDBRecords() {
		
		return dTExcelRepository.findAll();

	}

}
