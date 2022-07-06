package com.ford.constants;

import java.util.*;

public interface DTConstants {

	static String DOT_CSV = ".csv";

	static long DT_SCHEDULER_GAP = 120000;

	static String API_TOKEN = "Bearer NjJjMDdmNDk4MTk2MWM4ODA5Y2U1MDE0Oms5OWRydXdRTE9SaGNxVnZ2R00wRjZNM0tMaTZqeUFPUlIwMVFQTVNHc0U9";
	static String DT_ENDPOINT = "https://app2.datarobot.com/api/v2/";
	static String DT_WEBSITE_HOST = "https://app2.datarobot.com/ai-catalog/";

	static String DT_TEMP_DIR = "src/main/resources/";

	public static List<String> getFolderPathList() {
		return Arrays.asList("C://Users/shaik/OneDrive/Documents/ford", "C://Users/shaik/OneDrive/Documents");
	}

}
