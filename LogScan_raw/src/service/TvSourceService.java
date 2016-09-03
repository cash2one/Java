package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.logscan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TvSourceService {

	public void parseTvSource(JSONObject jsonObject,
			List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		String date = jsonObject.getString("date");
		JSONArray area_list = jsonObject.getJSONArray("record_list");
		for (int i = 0; i < area_list.size(); i++) {
			JSONObject area = area_list.getJSONObject(i);
			String timeList[] = area.getString("use_recorder").split(";");

			for (String string : timeList) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("sn", logscan.t_sn.get());
				map.put("ip", logscan.t_ipaddr.get());
				map.put("regioncode", logscan.t_regioncode.get());
				map.put("record_date", date);
				map.put("name", area.getString("name"));
				map.put("use_date", area.getString("use_date"));
				map.put("time",string.split(",")[0]);
				map.put("count",string.split(",")[1]);
				map.put("duration",string.split(",")[2]);
				list.add(map);	
			}
			

				

			
		}

	}
}
