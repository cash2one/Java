package service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import main.logscan;

import com.alibaba.fastjson.JSONObject;


public class DurationService {


	public void parseDurationV1(JSONObject data_json,List<Map<String, String>> durationList) throws Exception 
	{
		String duration=data_json.getString("duration");
		String switch_on_time=data_json.getString("switch_on_time");
		Map<String, String> durationMap=new HashMap<String, String>();
		durationMap.put("sn",logscan.t_sn.get());
		durationMap.put("ip", logscan.t_ipaddr.get());
		durationMap.put("regioncode", logscan.t_regioncode.get());
		durationMap.put("duration", duration);
		durationMap.put("switch_on_time", switch_on_time);
		durationList.add(durationMap);

	}
}
