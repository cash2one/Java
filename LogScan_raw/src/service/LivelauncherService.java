package service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.logscan;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class LivelauncherService {

	public  void parseLivelauncherV1(JSONObject jsonObject,
			List<Map<String, String>> list) {
		String date = jsonObject.getString("date");
		String version_name = jsonObject.getString("version_name");
		String version_code = jsonObject.getString("version_code");
		JSONArray area_list = jsonObject.getJSONArray("area_list");
		
		
		for (int i = 0; i < area_list.size(); i++) {
			
			JSONObject area = area_list.getJSONObject(i);
			String timeList[] = area.getString("area_click_duration").split(";");
			
			for (String string : timeList) {
				Map<String, String>  map = new HashMap<String, String>();
				map.put("sn",logscan.t_sn.get());
				map.put("ip", logscan.t_ipaddr.get());
				map.put("regioncode", logscan.t_regioncode.get());
				map.put("date", date);
				map.put("version_name", version_name);
				map.put("version_code", version_code);
				
				map.put("area_name", area.getString("area_name"));
				
				map.put("area_click_duration_start", string.substring(0,string.indexOf(",")));
				map.put("area_click_duration_end", string.substring(string.indexOf(",")+1,string.length()));
				
				
				map.put("package_name", area.getString("package_name"));
				list.add(map);
			}
		}
	}

	public void parseLivelauncherV2(JSONObject jsonObject,
			List<Map<String, String>> list,Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		String date = jsonObject.getString("date");
		String version_name = jsonObject.getString("version_name");
		String version_code = jsonObject.getString("version_code");
		JSONArray area_list = jsonObject.getJSONArray("area_list");
		
		for (int i = 0; i < area_list.size(); i++) {
			
			JSONObject area = area_list.getJSONObject(i);
			JSONArray  area_content_list = area.getJSONArray("area_content_list");
			for (int j = 0; j < area_content_list.size(); j++) {
				
				JSONObject area_content = area_content_list.getJSONObject(j);
				Map<String, String> map = getNullMap();
				map.put("sn",logscan.t_sn.get());
				map.put("ip", logscan.t_ipaddr.get());
				map.put("regioncode", logscan.t_regioncode.get());
				map.put("record_date", date);
				map.put("version_name", version_name);
				map.put("version_code", version_code);
				
				String  area_name = area.getString("area_name");
				map.put("area_name",area_name );
				map.put("package_name", area.getString("package_name"));
				map.put("area_click_duration_start", area_content.getString("start_time"));
				map.put("area_click_duration_end", area_content.getString("end_time"));
				if (area_name.equals("tv_view") || area_name.equals("tv_custom_view")){
					String	channel =  area_content.getString("channel");
					String  program =  area_content.getString("program");
					int content_id = logscan.logToDb.insertTvView(channel,
							program, connection);
					map.put("content_id", String.valueOf(content_id));
				}
				
				if (area_name.equals("recommend_video_view") ){

					String video_name = area_content.getString("name");
					String video_source = area_content.getString("source");
					String actors = area_content.getString("actors");
					String video_category = area_content.getString("category");
					int video_id = logscan.logToDb.insertVedio(video_name,
							video_source, actors, video_category, connection);
					map.put("content_id",String.valueOf(video_id));
				}
				if (area_name.equals("video_type_view") ){
					
					String entrance_name = area_content.getString("name");
					String entrance_source = area_content.getString("source");
					int content_id = logscan.logToDb.insertVideoTypeView(entrance_name,
							entrance_source, connection);
					map.put("content_id", String.valueOf(content_id));
				}
				if (area_name.equals("recommend_app_view") ){
				
					String app_name = area_content.getString("app_name");
					String app_package_name = area_content.getString("package_name");
					int content_id = logscan.logToDb.insertAppView(app_name,
							app_package_name, connection);
					map.put("content_id", String.valueOf(content_id));
				}

				
				list.add(map);
			}
		}
	}

	public Map<String, String> getNullMap() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("sn", null);
		map.put("ip", null);
		map.put("regioncode", null);
		map.put("record_date", null);
		map.put("area_name", null);
		map.put("area_click_duration_start", null);
		map.put("area_click_duration_end", null);
		map.put("package_name", null);
		map.put("channel", null);
		map.put("program", null);
		map.put("entrance_name", null);
		map.put("vedio_id", null);
		map.put("entrance_source", null);
		map.put("app_name", null);
		map.put("app_package_name", null);
		return map;
	}
}
