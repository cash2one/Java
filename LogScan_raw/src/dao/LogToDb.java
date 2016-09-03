package dao;

import java.awt.print.Printable;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import filter.MD5Util;

public class LogToDb {

	public static Logger logger = Logger.getLogger(LogToDb.class);

	public void insertDuration(List<Map<String, String>> durationList,
			Connection conn) throws Exception {
		String sql_insert = "insert into terminal_duration (sn,ip,regioncode,duration,switch_on_time) "
				+ "values(?,?,?,?,?)";
		PreparedStatement stat_insert = conn.prepareStatement(sql_insert);
		try {
			for (Map<String, String> durationMap : durationList) {
				stat_insert.setString(1, durationMap.get("sn"));
				stat_insert.setString(2, durationMap.get("ip"));
				stat_insert.setString(3, durationMap.get("regioncode"));
				stat_insert.setInt(4, Integer.parseInt(durationMap
						.get("duration")));
				stat_insert.setString(5, durationMap.get("switch_on_time"));
				stat_insert.addBatch();
			}

			stat_insert.executeBatch();
		} catch (Exception e) {
			// TODO: handle exception
			logger.info("insertDuration", e);
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}
	}

	public void insertError(List<Map<String, String>> errorList, Connection conn)
			throws Exception {
		String sql_insert = "insert into terminal_error (sn,ip,regioncode,platform,tv_model,tv_software_version,softid,"
				+ "error_type,app_version_code,app_version_name,app_package_name,stack_info_md5,date,error_count) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sql_insert_stack = "INSERT INTO terminal_error_stack (stack_info_md5,stack_info) "
				+ "VALUES(?,?) ON DUPLICATE KEY UPDATE counts=counts+1";
		PreparedStatement stat_insert = conn.prepareStatement(sql_insert);
		PreparedStatement stat_insert_stack = conn
				.prepareStatement(sql_insert_stack);
		try {
			for (Map<String, String> errorMap : errorList) {
				stat_insert.setString(1, errorMap.get("sn"));
				stat_insert.setString(2, errorMap.get("ip"));
				stat_insert.setString(3, errorMap.get("regioncode"));
				stat_insert.setString(4, errorMap.get("platform"));
				stat_insert.setString(5, errorMap.get("tv_model"));
				stat_insert.setString(6, errorMap.get("tv_software_version"));
				stat_insert.setString(7, errorMap.get("softid"));
				stat_insert.setString(8, errorMap.get("error_type"));
				stat_insert.setString(9, errorMap.get("app_version_code"));
				stat_insert.setString(10, errorMap.get("app_version_name"));
				stat_insert.setString(11, errorMap.get("app_package_name"));
				stat_insert.setString(12, MD5Util.MD5Encode(errorMap
						.get("stack_info")));
				stat_insert.setString(13, errorMap.get("date"));
				stat_insert.setString(14, errorMap.get("error_count"));

				stat_insert.addBatch();

				stat_insert_stack.setString(1, MD5Util.MD5Encode(errorMap
						.get("stack_info")));
				stat_insert_stack.setString(2, errorMap.get("stack_info"));
				stat_insert_stack.addBatch();
			}

			stat_insert.executeBatch();
			stat_insert_stack.executeBatch();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("insertError", e);
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();

			stat_insert_stack.clearBatch();
			stat_insert_stack.close();
		}

	}

	public void insertActivity(List<Map<String, String>> activityList,
			Connection conn) throws Exception {
		String sql_insert = "insert into terminal_activity (sn,ip,regioncode,package_name,activity_name,app_version_name,"
				+ "app_version_code,date,time,counts,holdtime)"
				+ "values(?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stat_insert = conn.prepareStatement(sql_insert);
		try {
			for (Map<String, String> activityMap : activityList) {
				stat_insert.setString(1, activityMap.get("sn"));
				stat_insert.setString(2, activityMap.get("ip"));
				stat_insert.setString(3, activityMap.get("regioncode"));
				stat_insert.setString(4, activityMap.get("package_name"));
				stat_insert.setString(5, activityMap.get("activity_name"));
				stat_insert.setString(6, activityMap.get("app_version_name"));
				stat_insert.setString(7, activityMap.get("app_version_code"));
				stat_insert.setString(8, activityMap.get("date"));
				stat_insert
						.setInt(9, Integer.parseInt(activityMap.get("time")));
				stat_insert.setInt(10, Integer.parseInt(activityMap
						.get("counts")));
				stat_insert.setInt(11, Integer.parseInt(activityMap
						.get("holdtime")));

				stat_insert.addBatch();
			}

			stat_insert.executeBatch();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("insertActivity", e);
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}
	}

	public void insertAppBrowse(List<Map<String, String>> appBrowseList,
			Connection conn) throws Exception {
		String sql_insert = "insert into terminal_app_browse (sn,ip,regioncode,client_id,app_version,"
				+ "date,app_package_name,app_id,browse_count) "
				+ "values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement stat_insert = conn.prepareStatement(sql_insert);
		try {
			for (Map<String, String> appBrowseMap : appBrowseList) {
				stat_insert.setString(1, appBrowseMap.get("sn"));
				stat_insert.setString(2, appBrowseMap.get("ip"));
				stat_insert.setString(3, appBrowseMap.get("regioncode"));
				stat_insert.setString(4, appBrowseMap.get("client_id"));
				stat_insert.setString(5, appBrowseMap.get("app_version"));
				stat_insert.setString(6, appBrowseMap.get("date"));
				stat_insert.setString(7, appBrowseMap.get("app_package_name"));
				stat_insert.setInt(8, Integer.parseInt(appBrowseMap
						.get("app_id")));
				stat_insert.setString(9, appBrowseMap.get("browse_count"));
				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("insertAppBrowse", e);
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}

	}

	public void insertLabelBrowse(List<Map<String, String>> labelBrowseList,
			Connection conn) throws Exception {
		String sql_insert = "insert into terminal_label_browse (sn,ip,regioncode,client_id,app_version,"
				+ "date,label_name,label_id,browse_count) "
				+ "values(?,?,?,?,?,?,?,?,?)";
		PreparedStatement stat_insert = conn.prepareStatement(sql_insert);
		try {
			for (Map<String, String> labelBrowseMap : labelBrowseList) {
				stat_insert.setString(1, labelBrowseMap.get("sn"));
				stat_insert.setString(2, labelBrowseMap.get("ip"));
				stat_insert.setString(3, labelBrowseMap.get("regioncode"));
				stat_insert.setString(4, labelBrowseMap.get("client_id"));
				stat_insert.setString(5, labelBrowseMap.get("app_version"));
				stat_insert.setString(6, labelBrowseMap.get("date"));
				stat_insert.setString(7, labelBrowseMap.get("label_name"));
				stat_insert.setInt(8, Integer.parseInt(labelBrowseMap
						.get("label_id")));
				stat_insert.setString(9, labelBrowseMap.get("browse_count"));
				stat_insert.addBatch();
			}

			stat_insert.executeBatch();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("insertLabelBrowse", e);
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}

	}

	public void insertDownloadState(
			List<Map<String, String>> downloadStateList, Connection conn)
			throws Exception {
		String sql_insert = "insert into terminal_download_state (sn,ip,regioncode,client_id,app_version,"
				+ "date,d_app_id,d_app_name,d_app_package_name,d_version_code,d_version_name,upgrade,finish_state) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stat_insert = conn.prepareStatement(sql_insert);
		try {
			for (Map<String, String> labelBrowseMap : downloadStateList) {
				stat_insert.setString(1, labelBrowseMap.get("sn"));
				stat_insert.setString(2, labelBrowseMap.get("ip"));
				stat_insert.setString(3, labelBrowseMap.get("regioncode"));
				stat_insert.setString(4, labelBrowseMap.get("client_id"));
				stat_insert.setString(5, labelBrowseMap.get("app_version"));
				stat_insert.setString(6, labelBrowseMap.get("date"));
				stat_insert.setInt(7, Integer.parseInt(labelBrowseMap
						.get("d_app_id")));
				stat_insert.setString(8, labelBrowseMap.get("d_app_name"));
				stat_insert.setString(9, labelBrowseMap
						.get("d_app_package_name"));
				stat_insert.setString(10, labelBrowseMap.get("d_version_code"));
				stat_insert.setString(11, labelBrowseMap.get("d_version_name"));
				stat_insert.setInt(12, Integer.parseInt(labelBrowseMap
						.get("upgrade")));
				stat_insert.setString(13, labelBrowseMap.get("finish_state"));
				stat_insert.addBatch();
			}

			stat_insert.executeBatch();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("insertDownloadState", e);
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}
	}

	public void insertAd(List<Map<String, String>> adList, Connection conn)
			throws Exception {
		String sql_insert = "insert into terminal_ad (sn,ip,regioncode,click_count,show_count,ad_id,date) "
				+ "values(?,?,?,?,?,?,?)";
		PreparedStatement stat_insert = conn.prepareStatement(sql_insert);
		try {
			for (Map<String, String> adMap : adList) {
				stat_insert.setString(1, adMap.get("sn"));
				stat_insert.setString(2, adMap.get("ip"));
				stat_insert.setString(3, adMap.get("regioncode"));
				stat_insert.setInt(4, Integer
						.parseInt(adMap.get("click_count")));
				stat_insert
						.setInt(5, Integer.parseInt(adMap.get("show_count")));
				stat_insert.setInt(6, Integer.parseInt(adMap.get("ad_id")));
				stat_insert.setString(7, adMap.get("date"));

				stat_insert.addBatch();
			}

			stat_insert.executeBatch();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("insertAd", e);
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}
	}

	public void insertTvUpgrade(List<Map<String, String>> dataList,
			Connection conn) throws Exception {
		String sql_insert = "insert into terminal_tv_upgrade (sn,ip,regioncode,softid,"
				+ "upgrade_pack_md5,cur_version,upgrade_pack_id,upgrade_type,status,pre_version,phase,date) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stat_insert = conn.prepareStatement(sql_insert);
		try {
			for (Map<String, String> dataMap : dataList) {
				stat_insert.setString(1, dataMap.get("sn"));
				stat_insert.setString(2, dataMap.get("ip"));
				stat_insert.setString(3, dataMap.get("regioncode"));
				stat_insert.setString(4, dataMap.get("softid"));
				stat_insert.setString(5, dataMap.get("upgrade_pack_md5"));
				stat_insert.setString(6, dataMap.get("cur_version"));
				stat_insert.setInt(7, Integer.parseInt(dataMap
						.get("upgrade_pack_id")));
				stat_insert.setString(8, dataMap.get("upgrade_type"));
				stat_insert.setString(9, dataMap.get("status"));
				stat_insert.setString(10, dataMap.get("pre_version"));
				stat_insert.setString(11, dataMap.get("phase"));
				stat_insert.setString(12, dataMap.get("date"));

				stat_insert.addBatch();
			}

			stat_insert.executeBatch();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("insertTvUpgrade", e);
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}
	}

	// lvchenxin
	public void insertLiveLauncher(List<Map<String, String>> list,
			Connection connection) throws SQLException {
		String sql_insert = "insert into terminal_livelauncher (sn,ip,regioncode,date,"
				+ "version_name,version_code,area_name,area_click_duration_start,area_click_duration_end,package_name) "
				+ "values(?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);
		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("date"));
				stat_insert.setString(5, map.get("version_name"));
				stat_insert.setString(6, map.get("version_code"));
				stat_insert.setString(7, map.get("area_name"));
				stat_insert.setString(8, map.get("area_click_duration_start"));
				stat_insert.setString(9, map.get("area_click_duration_end"));
				stat_insert.setString(10, map.get("package_name"));

				stat_insert.addBatch();
			}
			stat_insert.executeBatch();

		} catch (SQLException e) {
			logger.error("insertLiveLauncher", e);
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}

	}

	// 升级过程
	public void insertTvUpgradeProcess(List<Map<String, String>> list,
			Connection connection) throws SQLException {
		String sql_insert = "insert into terminal_upgrade_process(sn,ip,regioncode,date,softid,local_version,local_build,package_id,entrance,popup_notify,check_new_version,download,upgrade) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);

		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("date"));
				stat_insert.setString(5, map.get("softid"));
				stat_insert.setString(6, map.get("local_version"));
				stat_insert.setString(7, map.get("local_build"));
				stat_insert.setString(8, map.get("package_id"));
				stat_insert.setString(9, map.get("entrance"));
				stat_insert.setString(10, map.get("popup_notify"));
				stat_insert.setString(11, map.get("check_new_version"));
				stat_insert.setString(12, map.get("download"));
				stat_insert.setString(13, map.get("upgrade"));

				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();

		}

	}

	// 升级事件
	public void insertTvUpgradeevent_request_new_versionList(
			List<Map<String, String>> list, Connection connection)
			throws SQLException {
		String sql_insert = "insert into terminal_upgrade_event_request_new_version(sn,ip,regioncode,date,softid,local_version,local_build,package_id,entrance,upgrade_app_version_code,upgrade_request_result) values(?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);

		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("date"));
				stat_insert.setString(5, map.get("softid"));
				stat_insert.setString(6, map.get("local_version"));
				stat_insert.setString(7, map.get("local_build"));
				stat_insert.setString(8, map.get("package_id"));
				stat_insert.setString(9, map.get("entrance"));
				stat_insert.setString(10, map.get("upgrade_app_version_code"));
				stat_insert.setString(11, map.get("upgrade_request_result"));

				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();

		}
	}

	public void insertTvUpgradeevent_memory_space_infoList(
			List<Map<String, String>> list, Connection connection)
			throws SQLException {
		String sql_insert = "insert into terminal_upgrade_event_memory_space_info(sn,ip,regioncode,date,softid,local_version,local_build,package_id,entrance,memory_status) values(?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);

		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("date"));
				stat_insert.setString(5, map.get("softid"));
				stat_insert.setString(6, map.get("local_version"));
				stat_insert.setString(7, map.get("local_build"));
				stat_insert.setString(8, map.get("package_id"));
				stat_insert.setString(9, map.get("entrance"));
				stat_insert.setString(10, map.get("memory_status"));

				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();

		}

	}

	public void insertTvUpgradeevent_download_failedList(
			List<Map<String, String>> list, Connection connection)
			throws SQLException {
		String sql_insert = "insert into terminal_upgrade_event_download_failed(sn,ip,regioncode,date,softid,local_version,local_build,package_id,entrance,download_failed_error_type,download_failed_error_code,download_progress,download_size) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);

		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("date"));
				stat_insert.setString(5, map.get("softid"));
				stat_insert.setString(6, map.get("local_version"));
				stat_insert.setString(7, map.get("local_build"));
				stat_insert.setString(8, map.get("package_id"));
				stat_insert.setString(9, map.get("entrance"));
				stat_insert
						.setString(10, map.get("download_failed_error_type"));
				stat_insert
						.setString(11, map.get("download_failed_error_code"));
				stat_insert.setString(12, map.get("download_progress"));
				stat_insert.setString(13, map.get("download_size"));

				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();

		}

	}

	public void insertTvUpgradeevent_upgrade_failedList(
			List<Map<String, String>> list, Connection connection)
			throws SQLException {
		String sql_insert = "insert into terminal_upgrade_event_upgrade_failed(sn,ip,regioncode,date,softid,local_version,local_build,package_id,entrance,package_path,upgrade_failed_error_type,upgrade_failed_error_code) values(?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);

		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("date"));
				stat_insert.setString(5, map.get("softid"));
				stat_insert.setString(6, map.get("local_version"));
				stat_insert.setString(7, map.get("local_build"));
				stat_insert.setString(8, map.get("package_id"));
				stat_insert.setString(9, map.get("entrance"));
				stat_insert.setString(10, map.get("package_path"));
				stat_insert.setString(11, map.get("upgrade_failed_error_type"));
				stat_insert.setString(12, map.get("upgrade_failed_error_code"));

				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();

		}
	}

	// usb升级事件
	public void insertTvUpgradusbevent(List<Map<String, String>> list,
			Connection connection) throws SQLException {
		String sql_insert = "insert into terminal_usb_upgrade_event(sn,ip,regioncode,date,softid,local_version,local_build,upgrade_result,entrance,version_after_upgrade,build_after_upgrade,soft_id_after_upgrade,upgrade_failed_error_code) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);

		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("date"));
				stat_insert.setString(5, map.get("softid"));
				stat_insert.setString(6, map.get("local_version"));
				stat_insert.setString(7, map.get("local_build"));
				stat_insert.setString(8, map.get("upgrade_result"));
				stat_insert.setString(9, map.get("entrance"));
				stat_insert.setString(10, map.get("version_after_upgrade"));
				stat_insert.setString(11, map.get("build_after_upgrade"));
				stat_insert.setString(12, map.get("soft_id_after_upgrade"));
				stat_insert.setString(13, map.get("upgrade_failed_error_code"));

				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();

		}
	}

	// 应用升级 应用助手 AppUpgrade手机商城。
	public void insertApp(List<Map<String, String>> list,
			Connection connection, String table) throws SQLException {

		String sql_insert = "insert into "
				+ table
				+ "(sn,ip,regioncode,record_date,date,app_package_name,cur_ver_name,cur_ver_code,new_install,pre_ver_name,pre_ver_code,download_state,install_state) values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);
		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("record_date"));
				stat_insert.setString(5, map.get("date"));
				stat_insert.setString(6, map.get("app_package_name"));
				stat_insert.setString(7, map.get("cur_ver_name"));
				stat_insert.setString(8, map.get("cur_ver_code"));
				stat_insert.setString(9, map.get("new_install"));
				stat_insert.setString(10, map.get("pre_ver_name"));
				stat_insert.setString(11, map.get("pre_ver_code"));
				stat_insert.setString(12, map.get("download_state"));
				stat_insert.setString(13, map.get("install_state"));

				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();

		}

	}

	public void insertAppStore_download_install(List<Map<String, String>> list,
			Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		String sql_insert = "insert into terminal_app_appstore_download_install(sn,ip,regioncode,record_date,app_version,date,app_id,app_name,app_package_name,cur_ver_name,cur_ver_code,new_install,pre_ver_name,pre_ver_code,download_state,install_state) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);
		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("record_date"));
				stat_insert.setString(5, map.get("app_version"));
				stat_insert.setString(6, map.get("date"));
				stat_insert.setString(7, map.get("app_id"));
				stat_insert.setString(8, map.get("app_name"));
				stat_insert.setString(9, map.get("app_package_name"));
				stat_insert.setString(10, map.get("cur_ver_name"));
				stat_insert.setString(11, map.get("cur_ver_code"));
				stat_insert.setString(12, map.get("new_install"));
				stat_insert.setString(13, map.get("pre_ver_name"));
				stat_insert.setString(14, map.get("pre_ver_code"));
				stat_insert.setString(15, map.get("download_state"));
				stat_insert.setString(16, map.get("install_state"));

				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();

		}
	}

	public void insertAppStore_app_browse(List<Map<String, String>> list,
			Connection connection) throws SQLException {
		String sql_insert = "insert into terminal_app_appstore_app_browse(sn,ip,regioncode,record_date,app_version,app_browse_count,app_package_name,app_id) values(?,?,?,?,?,?,?,?)";
		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);
		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("record_date"));
				stat_insert.setString(5, map.get("app_version"));
				stat_insert.setString(6, map.get("app_browse_count"));
				stat_insert.setString(7, map.get("app_package_name"));
				stat_insert.setString(8, map.get("app_id"));

				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();

		}

	}

	public void insertAppStore_label_browse(List<Map<String, String>> list,
			Connection connection) throws SQLException {
		String sql_insert = "insert into terminal_app_appstore_label_browse(sn,ip,regioncode,record_date,app_version,label_browse_count,label_name,label_id) values(?,?,?,?,?,?,?,?)";
		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);
		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("record_date"));
				stat_insert.setString(5, map.get("app_version"));
				stat_insert.setString(6, map.get("label_browse_count"));
				stat_insert.setString(7, map.get("label_name"));
				stat_insert.setString(8, map.get("label_id"));

				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();

		}
	}

	public void insertTvSource(List<Map<String, String>> list,
			Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		String sql_insert = "INSERT INTO `terminal_tv_source`(sn,ip,regioncode,record_date,NAME,TIME,COUNT,duration,use_date) VALUES(?,?,?,?,?,?,?,?,?)";
		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);
		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("record_date"));
				stat_insert.setString(5, map.get("name"));
				stat_insert.setString(6, map.get("time"));
				stat_insert.setString(7, map.get("count"));
				stat_insert.setString(8, map.get("duration"));
				stat_insert.setString(9, map.get("use_date"));
				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}
	}

	public void insertTvSettings(List<Map<String, String>> list,
			Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		String sql_insert = "INSERT INTO `terminal_tv_settings`(sn,ip,regioncode,record_date,`OPTION`,suboption,VALUE,use_time) VALUES(?,?,?,?,?,?,?,?)" ;
		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);
		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("record_date"));
				stat_insert.setString(5, map.get("option"));
				stat_insert.setString(6, map.get("suboption"));
				stat_insert.setString(7, map.get("value"));
				stat_insert.setString(8, map.get("time"));
				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}
	}

	public void insertTvShowCome(List<Map<String, String>> list,
			Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		String sql_insert = "INSERT INTO `terminal_tv_showcome`(sn,ip,regioncode,record_date,is_dongle_ready,is_setted,use_total_count,chl_updown_num,chl_updown_effective_num,voice_change_chl_num,voice_change_chl_effective_num) VALUES(?,?,?,?,?,?,?,?,?,?,?)" ;
		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);
		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("record_date"));
				stat_insert.setString(5, map.get("is_dongle_ready"));
				stat_insert.setString(6, map.get("is_setted"));
				stat_insert.setString(7, map.get("use_total_count"));
				stat_insert.setString(8, map.get("chl_updown_num"));
				stat_insert.setString(9, map.get("chl_updown_effective_num"));
				stat_insert.setString(10, map.get("voice_change_chl_num"));
				stat_insert.setString(11, map.get("voice_change_chl_effective_num"));
				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}
	}

	public void insertTvWatch(List<Map<String, String>> list,
			Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		String sql_insert = "INSERT INTO `terminal_tv_watch`(sn,ip,regioncode,record_date,source,chanel,program_name,start_time,end_time) VALUES(?,?,?,?,?,?,?,?,?)" ;
		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);
		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("record_date"));
				stat_insert.setString(5, map.get("source"));
				stat_insert.setString(6, map.get("chanel"));
				stat_insert.setString(7, map.get("program_name"));
				stat_insert.setString(8, map.get("start_time"));
				stat_insert.setString(9, map.get("end_time"));
				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}
	}

	public void insertClickSearch(List<Map<String, String>> list,
			Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		String sql_insert = "INSERT INTO `terminal_search_click_action`(sn,ip,regioncode,video_id,from_recommend,search_time) VALUES(?,?,?,?,?,?)" ;
		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);
		try {
			for (Map<String, String> map : list) {
				String search_time = map.get("search_time");
				if (search_time.length() != 13) {
					continue;
				}
				search_time = search_time.substring(0, 10);
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("video_id"));
				stat_insert.setString(5, map.get("from_recommend"));
				stat_insert.setString(6, search_time);
				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}
		
		
	}
	public void insertKeywordSearch(List<Map<String, String>> list,
			Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		String sql_insert = "	INSERT INTO `terminal_search_keyword_action`(sn,ip,regioncode,keywords,keyword_type,from_recommend,COUNT,error_code,search_time) VALUES(?,?,?,?,?,?,?,?,?)" ;
		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);
		try {
			for (Map<String, String> map : list) {
				String search_time = map.get("search_time");
				if (search_time.length() != 13) {
					continue;
				}
				search_time = search_time.substring(0, 10);
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("keywords"));
				stat_insert.setString(5, map.get("keyword_type"));
				stat_insert.setString(6, map.get("from_recommend"));
				stat_insert.setString(7, map.get("count"));
				stat_insert.setString(8, map.get("error_code"));
				stat_insert.setString(9, search_time);
				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}
	}

	/*public int insertVedio(String videoName, String videoSource,
			String[] actors, String[] videoCategory, Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		int vedioid;
		List<Integer> cIdList = new ArrayList<Integer>();
		List<Integer> aIdList = new ArrayList<Integer>();
		String sql_query ;
		String sql_insert ;
		ResultSet rs;
		PreparedStatement stat;
		
		sql_query = "SELECT * FROM terminal_vedio WHERE name = ? AND source = ?";
		stat = connection.prepareStatement(sql_query);
		stat.setString(1, videoName);
		stat.setString(2, videoSource);
		rs = stat.executeQuery();
		if(rs.next())
			return rs.getInt(1);
		else {
			sql_insert = "insert into terminal_vedio(NAME,source) values(?,?) ";
			stat = connection.prepareStatement(sql_insert);
			stat.setString(1, videoName);
			stat.setString(2, videoSource);
			stat.executeUpdate();
			
			sql_query = "select LAST_INSERT_ID()";
			stat = connection.prepareStatement(sql_query);
			rs = stat.executeQuery();
			rs.next();
			vedioid = rs.getInt(1);
			
			for (String string : videoCategory) {
				sql_query = "SELECT *  FROM terminal_categoryinfo WHERE category = ?" ;
				stat = connection.prepareStatement(sql_query);
				stat.setString(1, string);
				rs = stat.executeQuery();
				
				if(rs.next())
					cIdList.add(rs.getInt(1));
				else {
					sql_insert = "insert into terminal_categoryinfo(category) values(?) ";
					stat = connection.prepareStatement(sql_insert);
					stat.setString(1, string);
					stat.executeUpdate();
					
					sql_query = "select LAST_INSERT_ID()";
					stat = connection.prepareStatement(sql_insert);
					rs = stat.executeQuery(sql_query);
					rs.next();
					
					cIdList.add(rs.getInt(1));
				}
				
			}
			
			
			for (String string : actors) {
				sql_query = "SELECT *  FROM terminal_actorinfo WHERE actor = ?" ;
				stat = connection.prepareStatement(sql_query);
				stat.setString(1, string);
				rs = stat.executeQuery();
				
				if(rs.next())
					aIdList.add(rs.getInt(1));
				else {
					sql_insert = "insert into terminal_actorinfo(actor) values(?) ";
					stat = connection.prepareStatement(sql_insert);
					stat.setString(1, string);
					stat.executeUpdate();
					
					sql_query = "select LAST_INSERT_ID()";
					stat = connection.prepareStatement(sql_query);
					rs = stat.executeQuery();
					rs.next();
					
					aIdList.add(rs.getInt(1));
				}
				
			}
			
			for (Integer integer : aIdList) {
				sql_insert = "insert into terminal_vedio_r_actor(vedioid,actorid) values(?,?)";
				stat = connection.prepareStatement(sql_insert);
				stat.setInt(1, vedioid);
				stat.setInt(2, integer);
				stat.executeUpdate();
			}
			
			for (Integer integer : cIdList) {
				sql_insert = "insert into terminal_vedio_r_category(vedioid,categoryid) values(?,?)";
				stat = connection.prepareStatement(sql_insert);
				stat.setInt(1, vedioid);
				stat.setInt(2, integer);
				stat.executeUpdate();
			}
			
			return vedioid;

		}
		
	}
*/
	
	public int insertVedio(String videoName, String videoSource,
			String actors, String videoCategory, Connection connection) throws SQLException {
			String sql_query ;
			String sql_insert ;
			ResultSet rs;
			PreparedStatement stat;	
			sql_query = "SELECT * FROM terminal_vedio WHERE name = ? AND source = ? and actors = ? and categorys = ?";
			stat = connection.prepareStatement(sql_query);
			stat.setString(1, videoName);
			stat.setString(2, videoSource);
			stat.setString(3, actors);
			stat.setString(4, videoCategory);
			rs = stat.executeQuery();
			if(rs.next())
				return rs.getInt(1);
			else {
				sql_insert = "insert into terminal_vedio(NAME,source,actors,categorys) values(?,?,?,?) ";
				stat = connection.prepareStatement(sql_insert);
				stat.setString(1, videoName);
				stat.setString(2, videoSource);
				stat.setString(3, actors);
				stat.setString(4, videoCategory);
				stat.executeUpdate();
				
				sql_query = "SELECT * FROM terminal_vedio WHERE name = ? AND source = ? and actors = ? and categorys = ?";
				stat = connection.prepareStatement(sql_query);
				stat.setString(1, videoName);
				stat.setString(2, videoSource);
				stat.setString(3, actors);
				stat.setString(4, videoCategory);
				rs = stat.executeQuery();
				rs.next();
				return rs.getInt(1);
			}
			
	}

	public int insertTvView(String channel, String program,
			Connection connection) throws SQLException {
		String sql_query ;
		String sql_insert ;
		ResultSet rs;
		PreparedStatement stat;	
		sql_query = "SELECT * FROM terminal_livelauncher_tv_view  WHERE channel = ? AND program = ?";
		stat = connection.prepareStatement(sql_query);
		stat.setString(1, channel);
		stat.setString(2, program);
		rs = stat.executeQuery();
		if(rs.next())
			return rs.getInt(1);
		else {
			sql_insert = "insert into terminal_livelauncher_tv_view(channel,program) values(?,?) ";
			stat = connection.prepareStatement(sql_insert);
			stat.setString(1, channel);
			stat.setString(2, program);
			stat.executeUpdate();
			
			sql_query = "SELECT * FROM terminal_livelauncher_tv_view  WHERE channel = ? AND program = ?";
			stat = connection.prepareStatement(sql_query);
			stat.setString(1, channel);
			stat.setString(2, program);
			rs = stat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
		
	}

	public int insertVideoTypeView(String entranceName, String entranceSource,
			Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		String sql_query ;
		String sql_insert ;
		ResultSet rs;
		PreparedStatement stat;	
		sql_query = "SELECT * FROM terminal_livelauncher_video_type_view  WHERE name = ? AND source = ?";
		stat = connection.prepareStatement(sql_query);
		stat.setString(1, entranceName);
		stat.setString(2, entranceSource);
		rs = stat.executeQuery();
		if(rs.next())
			return rs.getInt(1);
		else {
			sql_insert = "insert into terminal_livelauncher_video_type_view(name,source) values(?,?) ";
			stat = connection.prepareStatement(sql_insert);
			stat.setString(1, entranceName);
			stat.setString(2, entranceSource);
			stat.executeUpdate();
			
			sql_query = "SELECT * FROM terminal_livelauncher_video_type_view  WHERE name = ? AND source = ?";
			stat = connection.prepareStatement(sql_query);
			stat.setString(1, entranceName);
			stat.setString(2, entranceSource);
			rs = stat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}
	}

	public int insertAppView(String appName, String appPackageName,
			Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		String sql_query ;
		String sql_insert ;
		ResultSet rs;
		PreparedStatement stat;	
		sql_query = "SELECT * FROM terminal_livelauncher_app_view  WHERE app_name = ? AND package_name = ?";
		stat = connection.prepareStatement(sql_query);
		stat.setString(1, appName);
		stat.setString(2, appPackageName);
		rs = stat.executeQuery();
		if(rs.next())
			return rs.getInt(1);
		else {
			sql_insert = "insert into terminal_livelauncher_app_view(app_name,package_name) values(?,?) ";
			stat = connection.prepareStatement(sql_insert);
			stat.setString(1, appName);
			stat.setString(2, appPackageName);
			stat.executeUpdate();
			
			sql_query = "SELECT * FROM terminal_livelauncher_app_view  WHERE app_name = ? AND package_name = ?";
			stat = connection.prepareStatement(sql_query);
			stat.setString(1, appName);
			stat.setString(2, appPackageName);
			rs = stat.executeQuery();
			rs.next();
			return rs.getInt(1);
		}	}
	
	
	public void insertLiveLauncherV2(List<Map<String, String>> list,
			Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		
		String sql_insert = "INSERT INTO `terminal_livelauncher`(sn,ip,regioncode,DATE,version_name,version_code,area_name,area_click_duration_start,area_click_duration_end,package_name,content_id) VALUES(?,?,?,?,?,?,?,?,?,?,?)" ;
		PreparedStatement stat_insert = connection.prepareStatement(sql_insert);
		try {
			for (Map<String, String> map : list) {
				stat_insert.setString(1, map.get("sn"));
				stat_insert.setString(2, map.get("ip"));
				stat_insert.setString(3, map.get("regioncode"));
				stat_insert.setString(4, map.get("record_date"));
				stat_insert.setString(5, map.get("version_name"));
				stat_insert.setString(6, map.get("version_code"));
				stat_insert.setString(7, map.get("area_name"));
				stat_insert.setString(8, map.get("area_click_duration_start"));
				stat_insert.setString(9, map.get("area_click_duration_end"));
				stat_insert.setString(10, map.get("package_name"));
				stat_insert.setString(11, map.get("content_id"));
				stat_insert.addBatch();
			}
			stat_insert.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stat_insert.clearBatch();
			stat_insert.close();
		}
	}


	
	

}
