package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.logscan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ErrorService {

	public void parseErrorV1(JSONObject data_json,List<Map<String, String>> errorList) throws Exception 
	{
		String platform=data_json.getString("paltform");
		String tv_model=data_json.getString("tv_model");
		String tv_software_version=data_json.getString("tv_software_version");
		String softid=data_json.getString("softid");
		
		JSONArray errorList1=data_json.getJSONArray("errorlist");
		
		for (int i = 0; i < errorList1.size(); i++) {
			Map<String, String> errorMap=new HashMap<String, String>();
			errorMap.put("sn",logscan.t_sn.get());
			errorMap.put("ip", logscan.t_ipaddr.get());
			errorMap.put("regioncode", logscan.t_regioncode.get());
			errorMap.put("platform", platform);
			errorMap.put("tv_model", tv_model);
			errorMap.put("tv_software_version", tv_software_version);
			errorMap.put("softid", softid);
			
			JSONObject error=errorList1.getJSONObject(i);
			
			errorMap.put("error_type", error.getString("error_type"));
			errorMap.put("error_count", error.getString("error_count"));
			errorMap.put("app_version_code", error.getString("app_version_code"));
			errorMap.put("app_package_name", error.getString("app_package_name"));
			errorMap.put("stack_info", error.getString("stack_info"));
			errorMap.put("date", error.getString("date"));
			errorMap.put("app_version_name", error.getString("app_version_name"));
			
			
			errorList.add(errorMap);
		}
		

		

	}
}
