package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.logscan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class AppStoreService {

	public void parseAppstoreV1(JSONObject data_json,List<Map<String, String>> appBrowseList,
			List<Map<String, String>> labelBrowseList,List<Map<String, String>> downloadStateList) throws Exception 
	{
		String client_id=data_json.getString("client_id");
		String app_version=data_json.getString("app_version");
		String date=data_json.getString("date");
		
		
		JSONArray app_browse_recoders=data_json.getJSONArray("app_browse_recoder_list");
		if (app_browse_recoders!=null) {
			for (int i = 0; i < app_browse_recoders.size(); i++) {
				JSONObject app_browse_recoder=app_browse_recoders.getJSONObject(i);

				Map<String, String> app_browseMap=new HashMap<String, String>();
				app_browseMap.put("sn",logscan.t_sn.get());
				app_browseMap.put("ip", logscan.t_ipaddr.get());
				app_browseMap.put("regioncode", logscan.t_regioncode.get());
				app_browseMap.put("client_id", client_id);
				app_browseMap.put("app_version", app_version);
				app_browseMap.put("date", date);
				app_browseMap.put("app_package_name", app_browse_recoder.getString("app_package_name"));
				app_browseMap.put("app_id", app_browse_recoder.getString("app_id"));
				app_browseMap.put("browse_count", app_browse_recoder.getString("browse_count"));
				
				appBrowseList.add(app_browseMap);
			}
		}

		JSONArray label_browse_recoders=data_json.getJSONArray("label_browse_recoder_list");
		if (label_browse_recoders!=null) {
			for (int i = 0; i < label_browse_recoders.size(); i++) {
				JSONObject label_browse_recoder=label_browse_recoders.getJSONObject(i);
				
				Map<String, String> label_browseMap=new HashMap<String, String>();
				label_browseMap.put("sn",logscan.t_sn.get());
				label_browseMap.put("ip", logscan.t_ipaddr.get());
				label_browseMap.put("regioncode", logscan.t_regioncode.get());
				label_browseMap.put("client_id", client_id);
				label_browseMap.put("app_version", app_version);
				label_browseMap.put("date", date);
				label_browseMap.put("label_name", label_browse_recoder.getString("label_name"));
				label_browseMap.put("label_id", label_browse_recoder.getString("label_id"));
				label_browseMap.put("browse_count", label_browse_recoder.getString("browse_count"));

				labelBrowseList.add(label_browseMap);
			}
		}
		
		JSONArray download_states=data_json.getJSONArray("download_state_list");
		if (download_states!=null) {
			for (int i = 0; i < download_states.size(); i++) {
				JSONObject download_state=download_states.getJSONObject(i);
				
				Map<String, String> download_stateMap=new HashMap<String, String>();
				download_stateMap.put("sn",logscan.t_sn.get());
				download_stateMap.put("ip", logscan.t_ipaddr.get());
				download_stateMap.put("regioncode", logscan.t_regioncode.get());
				download_stateMap.put("client_id", client_id);
				download_stateMap.put("app_version", app_version);
				download_stateMap.put("date", download_state.getString("date"));
				download_stateMap.put("d_app_id", download_state.getString("app_id"));
				download_stateMap.put("d_app_name", download_state.getString("app_name"));
				download_stateMap.put("d_app_package_name", download_state.getString("app_packagename"));
				download_stateMap.put("d_version_code", download_state.getString("version_code"));
				download_stateMap.put("d_version_name", download_state.getString("version_name"));
				download_stateMap.put("upgrade", download_state.getString("upgrade"));
				download_stateMap.put("finish_state", download_state.getString("finish_state"));

				downloadStateList.add(download_stateMap);
			}
		}
		
	}
}
