package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.logscan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class SearchService {

	public void parseClickSearch(JSONObject jsonObject,
			List<Map<String, String>> list, Connection connection)
			throws SQLException {
		// TODO Auto-generated method stub
		JSONArray area_list = jsonObject
				.getJSONArray("click_search_result_list");
		for (int i = 0; i < area_list.size(); i++) {
			JSONObject area = area_list.getJSONObject(i);

			Map<String, String> map = new HashMap<String, String>();
			map.put("sn", logscan.t_sn.get());
			map.put("ip", logscan.t_ipaddr.get());
			map.put("regioncode", logscan.t_regioncode.get());
			map.put("keywords", area.getString("keywords"));

			String video_name = area.getString("video_name");
			String video_source = area.getString("video_source");
			String actors = area.getString("actors");
			String video_category = area.getString("video_category");

			int video_id = logscan.logToDb.insertVedio(video_name,
					video_source, actors, video_category, connection);
			map.put("video_id", String.valueOf(video_id));
			map.put("from_recommend", area.getString("from_recommend"));
			map.put("search_time", area.getString("time"));

			list.add(map);

		}

	}

	public void parseKeyWordSearch(JSONObject jsonObject,
			List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		JSONArray area_list = jsonObject.getJSONArray("search_keyword_list");
		for (int i = 0; i < area_list.size(); i++) {
			JSONObject area = area_list.getJSONObject(i);

			Map<String, String> map = new HashMap<String, String>();
			map.put("sn", logscan.t_sn.get());
			map.put("ip", logscan.t_ipaddr.get());
			map.put("regioncode", logscan.t_regioncode.get());
			map.put("keywords", area.getString("keywords"));
			map.put("from_recommend", area.getString("from_recommend"));
			map.put("count", area.getString("count"));
			map.put("error_code", area.getString("error_code"));
			map.put("search_time", area.getString("time"));

			list.add(map);

		}
	}
}
