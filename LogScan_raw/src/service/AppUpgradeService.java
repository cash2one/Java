package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.logscan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class AppUpgradeService {

	// 应用助手 AppUpgrade手机商城。
	public void parseApp(JSONObject jsonObject, List<Map<String, String>> list) {
		String date = jsonObject.getString("date");
		JSONArray download_install_list = jsonObject
				.getJSONArray("download_install_list");
		if (download_install_list != null && download_install_list.size() > 0) {
			for (int i = 0; i < download_install_list.size(); i++) {

				JSONObject download_install = download_install_list
						.getJSONObject(i);

				Map<String, String> map = new HashMap<String, String>();
				map.put("sn", logscan.t_sn.get());
				map.put("ip", logscan.t_ipaddr.get());
				map.put("regioncode", logscan.t_regioncode.get());
				map.put("record_date", date);
				map.put("date", download_install.getString("date"));

				map.put("app_package_name", download_install
						.getString("app_package_name"));
				map.put("cur_ver_name", download_install
						.getString("cur_ver_name"));
				map.put("cur_ver_code", download_install
						.getString("cur_ver_code"));
				map.put("new_install", download_install
						.getString("new_install"));
				if (download_install.getString("new_install").equals("1")) {
					map.put("pre_ver_name", null);
					map.put("pre_ver_code", null);
				} else {
					map.put("pre_ver_name", download_install
							.getString("pre_ver_name"));
					map.put("pre_ver_code", download_install
							.getString("pre_ver_code"));
				}

				map.put("download_state", download_install
						.getString("download_state"));

				if (!download_install.getString("download_state").equals("1")) {
					map.put("install_state", null);
				} else {
					map.put("install_state", download_install
							.getString("install_state"));
				}

				list.add(map);

			}
		}

	}

	public void parseAppStore_download_install(JSONObject jsonObject,
			List<Map<String, String>> list) {
		String date = jsonObject.getString("date");
		String app_version = jsonObject.getString("app_version");
		JSONArray download_install_list = jsonObject
				.getJSONArray("download_install_list");
		if (download_install_list != null && download_install_list.size() > 0) {
			for (int i = 0; i < download_install_list.size(); i++) {

				JSONObject download_install = download_install_list
						.getJSONObject(i);
				Map<String, String> map = new HashMap<String, String>();
				map.put("sn", logscan.t_sn.get());
				map.put("ip", logscan.t_ipaddr.get());
				map.put("regioncode", logscan.t_regioncode.get());
				map.put("record_date", date);
				map.put("date", download_install.getString("date"));
				map.put("app_version", app_version);
				map.put("app_id", download_install.getString("app_id"));
				map.put("app_name", download_install.getString("app_name"));
				map.put("app_package_name", download_install
						.getString("app_package_name"));
				map.put("cur_ver_name", download_install
						.getString("cur_ver_name"));
				map.put("cur_ver_code", download_install
						.getString("cur_ver_code"));
				map.put("download_state", download_install
						.getString("download_state"));

				if (!download_install.getString("download_state").equals("1")) {
					map.put("install_state", null);
				} else {
					if(download_install.getString("install_state")!= null){
						map.put("install_state", download_install.getString("install_state"));
						map.put("new_install", download_install.getString("new_install"));
						if (download_install.getString("new_install").equals("1")) {
							map.put("pre_ver_name", null);
							map.put("pre_ver_code", null);
						} else {
							map.put("pre_ver_name", download_install
									.getString("pre_ver_name"));
							map.put("pre_ver_code", download_install
									.getString("pre_ver_code"));
						}
					}
					else {
						map.put("install_state", null);
						map.put("new_install", null);
						map.put("pre_ver_name", null);
						map.put("pre_ver_code", null);
					}
					
				}

				list.add(map);

			}
		}
	}

	public void parseAppStore_app_browse(JSONObject jsonObject,
			List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		String date = jsonObject.getString("date");
		String app_version = jsonObject.getString("app_version");
		JSONArray app_browse_recoder_list = jsonObject
				.getJSONArray("app_browse_recoder_list");
		if (app_browse_recoder_list != null && app_browse_recoder_list.size() > 0) {
			for (int i = 0; i < app_browse_recoder_list.size(); i++) {
				JSONObject app_browse_recoder = app_browse_recoder_list
						.getJSONObject(i);
				Map<String, String> map = new HashMap<String, String>();
				map.put("sn", logscan.t_sn.get());
				map.put("ip", logscan.t_ipaddr.get());
				map.put("regioncode", logscan.t_regioncode.get());
				map.put("record_date", date);
				map.put("app_version", app_version);
				map.put("app_browse_count", app_browse_recoder
						.getString("browse_count"));
				map.put("app_package_name", app_browse_recoder
						.getString("app_package_name"));
				map.put("app_id", app_browse_recoder.getString("app_id"));

				list.add(map);
			}
		}

	}

	public void parseAppStore_label_browse(JSONObject jsonObject,
			List<Map<String, String>> list) {
		// TODO Auto-generated method stub
		String date = jsonObject.getString("date");
		String app_version = jsonObject.getString("app_version");
		JSONArray label_browse_recoder_list = jsonObject
				.getJSONArray("label_browse_recoder_list");
		if ( label_browse_recoder_list != null && label_browse_recoder_list.size() > 0) {
			for (int i = 0; i < label_browse_recoder_list.size(); i++) {
				JSONObject label_browse_recoder = label_browse_recoder_list
						.getJSONObject(i);
				Map<String, String> map = new HashMap<String, String>();
				map.put("sn", logscan.t_sn.get());
				map.put("ip", logscan.t_ipaddr.get());
				map.put("regioncode", logscan.t_regioncode.get());
				map.put("record_date", date);
				map.put("app_version", app_version);
				map.put("label_browse_count", label_browse_recoder
						.getString("browse_count"));
				map.put("label_name", label_browse_recoder
						.getString("label_name"));
				map.put("label_id", label_browse_recoder.getString("label_id"));

				list.add(map);
			}
		}
	}

}
