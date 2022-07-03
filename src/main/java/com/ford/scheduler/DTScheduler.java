package com.ford.scheduler;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.ford.constants.DTConstants;
import com.ford.model.DTFileData;
import com.ford.repository.DTEcelRepository;
import com.ford.utils.DTUtils;

@Service
public class DTScheduler {

	@Autowired
	DTEcelRepository dTEcelRepository;

	@Scheduled(cron = "*/60 * * * * *")
	public void printLatestModifiedFiles() throws ParseException {
		List<DTFileData> dTFileExcelRowsList = new ArrayList<DTFileData>();

		List<String> lastModifiedFilePathList = DTUtils
				.getLastModifiedFilePathListBasedOnTime(DTConstants.DT_SCHEDULER_GAP);
		if (CollectionUtils.isNotEmpty(lastModifiedFilePathList)) {
			for (String lastModifiedFilePath : lastModifiedFilePathList) {
				dTEcelRepository.deleteAllByFilePath(lastModifiedFilePath);
				List<String> fileRecordsFromDirectory = DTUtils.getFileRecordsFromDirectory(lastModifiedFilePath);
				for (String fileRecord : fileRecordsFromDirectory) {
					DTFileData dTFileExcelRowDataObject = new DTFileData();
					dTFileExcelRowDataObject.setFileName(DTUtils.fileComponent(lastModifiedFilePath));
					dTFileExcelRowDataObject.setFileRowData(fileRecord);
					dTFileExcelRowDataObject.setFilePath(lastModifiedFilePath);
					dTFileExcelRowsList.add(dTFileExcelRowDataObject);
				}
			}
		}

		if (CollectionUtils.isNotEmpty(dTFileExcelRowsList)) {
			dTEcelRepository.saveAll(dTFileExcelRowsList);
		}
	}

}
