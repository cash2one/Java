package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.logscan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TvSettingsService {

	public void parseTvSettings(JSONObject jsonObject,
			List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		String date = jsonObject.getString("date");
		JSONArray area_list = jsonObject.getJSONArray("record_list");
		for (int i = 0; i < area_list.size(); i++) {
			JSONObject area = area_list.getJSONObject(i);

				Map<String, String> map = new HashMap<String, String>();
				map.put("sn", logscan.t_sn.get());
				map.put("ip", logscan.t_ipaddr.get());
				map.put("regioncode", logscan.t_regioncode.get());
				map.put("record_date", date);
				map.put("option", area.getString("option"));
				map.put("suboption", area.getString("suboption"));
				map.put("value", area.getString("value"));
				map.put("time", area.getString("time"));
				
				list.add(map);
			

		}

	}
}
