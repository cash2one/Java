package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.logscan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class AdService {

	public void parseAdV1(JSONObject data_json,List<Map<String, String>> adList) throws Exception 
	{
		String date=data_json.getString("date");
		JSONArray ads=data_json.getJSONArray("ad_list");
		for (int i = 0; i < ads.size(); i++) {
			JSONObject ad=ads.getJSONObject(i);
			Map<String, String> adMap=new HashMap<String, String>();
			adMap.put("sn",logscan.t_sn.get());
			adMap.put("ip", logscan.t_ipaddr.get());
			adMap.put("regioncode", logscan.t_regioncode.get());
			adMap.put("date", date);
			adMap.put("click_count", ad.getString("click_count"));
			adMap.put("show_count", ad.getString("show_count"));
			adMap.put("ad_id", ad.getString("ad_id"));
			
			adList.add(adMap);
		}
		

	}
}
