package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.logscan;

import com.alibaba.fastjson.JSONObject;

public class TvShowComeService {

	public void parseTvShowCome(JSONObject jsonObject,
			List<Map<String, String>> list) {
		// TODO Auto-generated method stub
			String date = jsonObject.getString("date");
			Map<String, String> map = new HashMap<String, String>();
			map.put("sn", logscan.t_sn.get());
			map.put("ip", logscan.t_ipaddr.get());
			map.put("regioncode", logscan.t_regioncode.get());
			map.put("record_date", date);
			map.put("is_dongle_ready",  jsonObject.getString("is_dongle_ready"));
			map.put("is_setted",  jsonObject.getString("is_setted"));
			map.put("use_total_count", jsonObject.getString("use_total_count"));
			map.put("chl_updown_num",  jsonObject.getString("chl_updown_num"));
			map.put("chl_updown_effective_num",  jsonObject.getString("chl_updown_effective_num"));
			map.put("voice_change_chl_num",  jsonObject.getString("voice_change_chl_num"));
			map.put("voice_change_chl_effective_num",  jsonObject.getString("voice_change_chl_effective_num"));
			list.add(map);

		

	}
}
