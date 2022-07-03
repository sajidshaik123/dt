package com.ford.constants;

import java.util.ArrayList;
import java.util.List;

public interface DTConstants {

	static String DOT_CSV = ".csv";

	static Integer DT_SCHEDULER_GAP = 60;

	static String API_TOKEN = "Bearer NjJjMDdmNDk4MTk2MWM4ODA5Y2U1MDE0Oms5OWRydXdRTE9SaGNxVnZ2R00wRjZNM0tMaTZqeUFPUlIwMVFQTVNHc0U9";
	static String DT_ENDPOINT = "https://app2.datarobot.com/api/v2/";
	static String DT_WEBSITE_HOST = "https://app2.datarobot.com/ai-catalog/";

	public static List<String> getFolderPathList() {
		List<String> folderPathList = new ArrayList<String>();
		folderPathList.add("C://Users/shaik/OneDrive/Documents/ford");
		folderPathList.add("C://Users/shaik/OneDrive/Documents");
		return folderPathList;
	}

}
