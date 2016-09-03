package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.logscan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ActivityService {

	public void parseActivityV1(JSONObject data_json,List<Map<String, String>> activityList) throws Exception 
	{
		JSONArray activitys=data_json.getJSONArray("activity_list");
		for (int i = 0; i < activitys.size(); i++) {
			JSONObject activity=activitys.getJSONObject(i);
			
			String use_time[]=activity.getString("use_recorder").split(",");
			for (int j = 0; j < use_time.length/3; j++) {
				Map<String, String> activityMap=new HashMap<String, String>();
				activityMap.put("sn",logscan.t_sn.get());
				activityMap.put("ip", logscan.t_ipaddr.get());
				activityMap.put("regioncode", logscan.t_regioncode.get());
				activityMap.put("app_version_code", activity.getString("app_version_code"));
				activityMap.put("app_version_name", activity.getString("app_version_name"));
				activityMap.put("activity_name", activity.getString("activity_name"));
				activityMap.put("date", activity.getString("date"));
				activityMap.put("package_name", activity.getString("package_name"));
				activityMap.put("time", use_time[j*3+0]);
				activityMap.put("counts", use_time[j*3+1]);
				activityMap.put("holdtime", use_time[j*3+2]);
				
				activityList.add(activityMap);
			}
			
		}
		

	}
}
