package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.logscan;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TvUpgradeService {

	public void parseTvUpgradeV1(JSONObject data_json,
			List<Map<String, String>> dataList) throws Exception {
		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("sn", logscan.t_sn.get());
		dataMap.put("ip", logscan.t_ipaddr.get());
		dataMap.put("regioncode", logscan.t_regioncode.get());
		dataMap
				.put("upgrade_pack_md5", data_json
						.getString("upgrade_pack_md5"));
		dataMap.put("cur_version", data_json.getString("cur_version"));
		dataMap.put("upgrade_pack_id", data_json.getString("upgrade_pack_id"));
		dataMap.put("upgrade_type", data_json.getString("upgrade_type"));
		dataMap.put("status", data_json.getString("status"));
		dataMap.put("date", data_json.getString("date"));
		dataMap.put("pre_version", data_json.getString("pre_version"));
		dataMap.put("phase", data_json.getString("phase"));
		dataMap.put("softid", data_json.getString("softid"));

		dataList.add(dataMap);

	}

	// lvchenxin 升级过程
	public void parseTvUpgradeVProcess(JSONObject jsonObject,
			List<Map<String, String>> list) {
		Map<String, String> entranceMap = new HashMap<String, String>();

		getShine(entranceMap);

		JSONArray jsonArray = jsonObject.getJSONArray("upgrade_process_list");

		for (int i = 0; i < jsonArray.size(); i++) {

			Map<String, String> dataMap = getNullMap();
			dataMap.put("sn", logscan.t_sn.get());
			dataMap.put("ip", logscan.t_ipaddr.get());
			dataMap.put("regioncode", logscan.t_regioncode.get());
			dataMap.put("date", jsonObject.getString("date"));
			dataMap.put("softid", jsonObject.getString("softid"));
			dataMap.put("local_version", jsonObject.getString("local_version"));
			dataMap.put("local_build", jsonObject.getString("local_build"));

			JSONObject upgrade_process_list = jsonArray.getJSONObject(i);
		

			String entrance = upgrade_process_list.getString("entrance");
		
				dataMap.put("entrance", entranceMap.get(entrance));
				dataMap.put("package_id", upgrade_process_list.getString("package_id"));
		

			String actionsString = upgrade_process_list.getString("action");
			String action[] = actionsString.split(",");
			for (int j = 0; j < action.length; j++) {
				action[j] =action[j].trim();
			}
		
			boolean popup_notify = false;
			boolean check_new_version = false;
			boolean download_start = false;
			boolean upgrade_start = false;
			for (int j = 0; j < action.length; j++) {
				if (action[j].equals("popup_notify")) {
					dataMap.put("popup_notify", String.valueOf(0));
					popup_notify = true;
				}

				if (popup_notify) {
					if (action[j].equals("start_app_from_notify"))
						dataMap.put("popup_notify", String.valueOf(1));
					if (action[j].equals("close_notify"))
						dataMap.put("popup_notify", String.valueOf(2));
					if (action[j].equals("remind_next_time"))
						dataMap.put("popup_notify", String.valueOf(3));
					if (action[j].equals("notify_auto_close"))
						dataMap.put("popup_notify", String.valueOf(4));
				}

				if (action[j].equals("check_new_version")) {
					dataMap.put("check_new_version", String.valueOf(0));
					check_new_version = true;

				}
				if (check_new_version) {
					if (action[j].equals("find_new_version"))
						dataMap.put("check_new_version", String.valueOf(1));
					if (action[j].equals("no_new_version"))
						dataMap.put("check_new_version", String.valueOf(2));
					if (action[j].equals("check_new_version_failed"))
						dataMap.put("check_new_version", String.valueOf(3));
				}

				if (action[j].equals("download_start")) {
					dataMap.put("download", String.valueOf(0));
					download_start = true;

				}
				if (download_start) {
					dataMap.put("package_id", upgrade_process_list     //在开始下载之后才有package_id
							.getString("package_id"));
					if (action[j].equals("download_success"))
						dataMap.put("download", String.valueOf(1));
					if (action[j].equals("download_failed"))
						dataMap.put("download", String.valueOf(2));
				}

				if (action[j].equals("upgrade_start")) {
					dataMap.put("upgrade", String.valueOf(0));
					upgrade_start = true;

				}
				if (upgrade_start) {
					if (action[j].equals("upgrade_success"))
						dataMap.put("upgrade", String.valueOf(1));
					if (action[j].equals("upgrade_failed"))
						dataMap.put("upgrade", String.valueOf(2));
				}
			}
			list.add(dataMap);
		}
	}

	// 升级事件

	public void parseTvUpgradeVEvent(JSONObject jsonObject,
			List<Map<String, String>> t_Upgradeevent_request_new_versionList,
			List<Map<String, String>> t_Upgradeevent_memory_space_infoList,
			List<Map<String, String>> t_Upgradeevent_download_failedList,
			List<Map<String, String>> t_Upgradeevent_upgrade_failedList) {
		// TODO Auto-generated method stub
		JSONArray jsonArray = jsonObject.getJSONArray("upgrade_event_list");

		Map<String, String> entranceMap = new HashMap<String, String>();
		getShine(entranceMap);

		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject upgrade_event_list = jsonArray.getJSONObject(i);
			
			for (int j = 0; j < Integer.parseInt(upgrade_event_list.getString("count")); j++) {
				Map<String, String> dataMap = new HashMap<String, String>();
				dataMap.put("sn", logscan.t_sn.get());
				dataMap.put("ip", logscan.t_ipaddr.get());
				dataMap.put("regioncode", logscan.t_regioncode.get());
				dataMap.put("date", jsonObject.getString("date"));
				dataMap.put("softid", jsonObject.getString("softid"));
				dataMap.put("local_version", jsonObject.getString("local_version"));
				dataMap.put("local_build", jsonObject.getString("local_build"));

				
				dataMap.put("package_id", upgrade_event_list
						.getString("package_id"));
				
				
				
				
					dataMap.put("entrance", entranceMap
							.get(upgrade_event_list.getString("entrance")));
				
				
				

				if (upgrade_event_list.getString("event").equals(
						"request_new_version")) {
					dataMap.put("upgrade_app_version_code", upgrade_event_list.getJSONObject("event_data")
							.getString("upgrade_app_version_code"));
					dataMap.put("upgrade_request_result", upgrade_event_list.getJSONObject("event_data")
							.getString("upgrade_request_result"));
					t_Upgradeevent_request_new_versionList.add(dataMap);

				} else if (upgrade_event_list.getString("event").equals(
						"memory_space_info")) {
					if(upgrade_event_list.getJSONObject("event_data")
							.getString("memory_status").equals("enough"))
					dataMap.put("memory_status", "0");
					
					if(upgrade_event_list.getJSONObject("event_data")
							.getString("memory_status").equals("no_enough"))
					dataMap.put("memory_status", "1");
					
					t_Upgradeevent_memory_space_infoList.add(dataMap);
				} else if (upgrade_event_list.getString("event").equals(
						"download_failed")) {
					dataMap.put("download_failed_error_type", upgrade_event_list.getJSONObject("event_data")
							.getString("download_failed_error_type"));
					dataMap.put("download_failed_error_code", upgrade_event_list.getJSONObject("event_data")
							.getString("download_failed_error_code"));
					dataMap.put("download_progress", upgrade_event_list.getJSONObject("event_data")
							.getString("download_progress"));
					dataMap.put("download_size", upgrade_event_list.getJSONObject("event_data")
							.getString("download_size"));
					t_Upgradeevent_download_failedList.add(dataMap);
				} else if (upgrade_event_list.getString("event").equals(
						"upgrade_failed")) {
					dataMap.put("package_path", upgrade_event_list.getJSONObject("event_data")
							.getString("package_path"));
					dataMap.put("upgrade_failed_error_type", upgrade_event_list.getJSONObject("event_data")
							.getString("upgrade_failed_error_type"));
					dataMap.put("upgrade_failed_error_code", upgrade_event_list.getJSONObject("event_data")
							.getString("upgrade_failed_error_code"));
					t_Upgradeevent_upgrade_failedList.add(dataMap);
				}
			}
			

		}

	}

	// usb 升级事件
	public void parseTvUpgradeVUsb(JSONObject jsonObject,
			List<Map<String, String>> list) {
		// TODO Auto-generated method stub

		JSONArray jsonArray = jsonObject.getJSONArray("usb_upgrade_event_list");

		for (int i = 0; i < jsonArray.size(); i++) {

			Map<String, String> dataMap = new HashMap<String, String>();
			dataMap.put("sn", logscan.t_sn.get());
			dataMap.put("ip", logscan.t_ipaddr.get());
			dataMap.put("regioncode", logscan.t_regioncode.get());
			dataMap.put("date", jsonObject.getString("date"));
			dataMap.put("softid", jsonObject.getString("softid"));
			dataMap.put("local_version", jsonObject.getString("local_version"));
			dataMap.put("local_build", jsonObject.getString("local_build"));

			JSONObject usb_upgrade_event_list = jsonArray.getJSONObject(i);

			if(usb_upgrade_event_list.getString("entrance").equals("system_usb")){
				dataMap.put("entrance", "0");
			}
			else if (usb_upgrade_event_list.getString("entrance").equals("factory_usb")) {
				dataMap.put("entrance", "1");
			}
			else {
				dataMap.put("entrance", null);
			}

			if (usb_upgrade_event_list.getString("upgrade_result").equals(
					"success") ) {
				dataMap.put("upgrade_result", "success");
				dataMap.put("version_after_upgrade", usb_upgrade_event_list.getJSONObject("event_data")
						.getString("version_after_upgrade"));
				dataMap.put("build_after_upgrade", usb_upgrade_event_list.getJSONObject("event_data")
						.getString("build_after_upgrade"));
				dataMap.put("soft_id_after_upgrade", usb_upgrade_event_list.getJSONObject("event_data")
						.getString("soft_id_after_upgrade"));
				dataMap.put("upgrade_failed_error_code", null);
				list.add(dataMap);

			} else if (usb_upgrade_event_list.getString("upgrade_result")
					.equals("failed")) {
			
				dataMap.put("upgrade_result", "failed");
				dataMap.put("version_after_upgrade", null);
				dataMap.put("build_after_upgrade", null);
				dataMap.put("soft_id_after_upgrade", null);
				dataMap.put("upgrade_failed_error_code", usb_upgrade_event_list.getJSONObject("event_data")
						.getString("upgrade_failed_error_code"));
				list.add(dataMap);
			}

		}
	}

	// 映射
	public void getShine(Map<String, String> entranceMap) {

		entranceMap.put("auto", "0");
		entranceMap.put("notify", "1");
		entranceMap.put("systemsetting", "2");
		entranceMap.put("huba", "3");
		entranceMap.put("message_center", "4");
		entranceMap.put("usb", "5");
		entranceMap.put("NULL", null);


	}

	// 升级过程空list
	public Map<String, String> getNullMap() {

		Map<String, String> map = new HashMap<String, String>();
		map.put("sn", null);
		map.put("ip", null);
		map.put("regioncode", null);
		map.put("date", null);
		map.put("softid", null);
		map.put("local_version", null);
		map.put("local_build", null);
		map.put("package_id", null);
		map.put("entrance", null);
		map.put("popup_notify", null);
		map.put("check_new_version", null);
		map.put("download", null);
		map.put("upgrade", null);
		return map;
	}

}
