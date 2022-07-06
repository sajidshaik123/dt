package com.ford.utils;

import java.io.File;
import java.io.FileReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ford.constants.DTConstants;
import com.ford.model.DataRobotCataProject;
import com.opencsv.CSVReader;

public class DTUtils {
	
	Logger logger = LoggerFactory.getLogger(DTUtils.class);

	public static List<String> getFilesNamesFromFolder(String path) {
		ArrayList<String> fileNames = new ArrayList<>();
		File[] files = new File(path).listFiles(obj -> obj.isFile() && obj.getName().endsWith(DTConstants.DOT_CSV));
		for (File file : files) {
			String fileName = file.getName();
			fileNames.add(fileName);
		}
		return fileNames;
	}

	public static String excludeFileExtension(String filename) {
		String excludeFileExtension = filename.replaceAll(DTConstants.DOT_CSV, StringUtils.EMPTY); // replaces all
																									// occurrences
																									// of'.csv' to
																									// 'empty'
		return excludeFileExtension;
	}

	public static String fileComponent(String fname) {
		int pos = fname.lastIndexOf("/");
		if (pos > -1)
			return fname.substring(pos + 1);
		else
			return fname;
	}

	public static List<String> getFileRecordsFromDirectory(String filePath) {
		CSVReader reader = null;
		List<String> list = new ArrayList<String>();

		try {
			reader = new CSVReader(new FileReader(filePath));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				StringBuilder str = new StringBuilder();
				for (String token : nextLine) {
					str.append(token.replaceAll("[^a-zA-Z0-9]", StringUtils.EMPTY) + "--");
				}
				list.add(str.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return list;
	}

	public static List<String> getLastModifiedFilePathListBasedOnTime(Integer seconds) throws ParseException {
		List<String> lastModifiedFilePathList = new ArrayList<String>();

		List<String> folderPathList = DTConstants.getFolderPathList();

		for (String folderPath : folderPathList) {
			List<String> fileNameList = DTUtils.getFilesNamesFromFolder(folderPath);
			for (String fileName : fileNameList) {
				String filePath = folderPath + "/" + fileName;
				Date lastModifiledFileDate = DTDateUtils.getLastModifiledFileDate(filePath);
				Date dateAfterUpdation = new Date(DTDateUtils.getTodayDate().getTime() - (seconds * 1000));
				if (lastModifiledFileDate.after(dateAfterUpdation)) {
					System.out.println(filePath + " was last modified at: " + lastModifiledFileDate.toString());
					lastModifiedFilePathList.add(filePath);
				}
			}
		}
		if (CollectionUtils.isEmpty(lastModifiedFilePathList)) {
			System.out.println("No recent modified files from last " + seconds + " seconds");
		}
		return lastModifiedFilePathList;
	}

	public static void setProjectsUrl(List<DataRobotCataProject> dataRobotCataProjects) {
		dataRobotCataProjects.forEach(d -> d.setProjectUrl(DTConstants.DT_WEBSITE_HOST + d.getId()));
	}

}
