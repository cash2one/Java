package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import service.ActivityService;
import service.AdService;
import service.AppStoreService;
import service.AppUpgradeService;
import service.DurationService;
import service.ErrorService;
import service.SearchService;
import service.TvShowComeService;
import service.TvSourceService;
import service.LivelauncherService;
import service.TvSettingsService;
import service.TvUpgradeService;
import service.TvWatchService;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lumaqq.IPSeeker;

import dao.LogToDb;

public class logscan {

	public static Logger logger = Logger.getLogger(logscan.class);
	private static ThreadLocal<Connection> conn = new ThreadLocal<Connection>();
	static Properties p = new Properties();
	// static public String ipaddr;
	// static public String sn;
	// static public String regioncode;
	final static String DOMAIN_DURATION_SWITCH = "duration_switch";
	final static String DOMAIN_ERROR = "error";
	final static String DOMAIN_ACTIVITY_START = "activity_start";
	final static String DOMAIN_APP_STORE = "app_store";
	final static String DOMAIN_APP_STORE5 = "app_store5.0";
	final static String DOMAIN_AD = "ad";
	final static String DOMAIN_TV_UPGRADE = "tv_update";

	// lvchenxin
	final static String DOMAIN_LIVELAUNCHER = "livelauncher";
	final static String DOMAIN_APPASSISTANT_DOWNLOAD_INSTALL = "appassistant_download_install";
	final static String DOMAIN_APPUPGRADE_DOWNLOAD_INSTALL = "appupgrade_download_install";
	final static String DOMAIN_MOBILE_APPSTORE_DOWNLOAD_INSTALL = "mobile_appstore_download_install";
	final static String DOMAIN_TVSOURCE = "tv_source";
	final static String DOMAIN_TVSETTINGS = "tv_settings";
	final static String DOMAIN_TVSHOWCOME = "tv_showcome";
	final static String DOMAIN_TVWATCH = "tv_watch";
	final static String DOMAIN_SEARCHACTION = "search_action";
	
	private static DurationService durationService = new DurationService();
	private static ErrorService errorService = new ErrorService();
	private static ActivityService activityService = new ActivityService();
	private static AppStoreService appStoreService = new AppStoreService();
	private static AdService adService = new AdService();
	private static TvUpgradeService tvUpgradeService = new TvUpgradeService();
	private static TvSourceService tvSourceService = new TvSourceService();
	private static TvSettingsService tvSettingsService = new TvSettingsService(); 
	private static TvShowComeService tvShowComeService = new TvShowComeService();
	private static TvWatchService tvWatchService = new TvWatchService();
	private static SearchService searchService = new SearchService();
	
	
	
	// lvchenxin
	private static LivelauncherService livelauncherService = new LivelauncherService();
    private static AppUpgradeService   appUpgradeService = new AppUpgradeService();
	
	public static ThreadLocal<String> t_sn = new ThreadLocal<String>();
	public static ThreadLocal<String> t_ipaddr = new ThreadLocal<String>();
	public static ThreadLocal<String> t_regioncode = new ThreadLocal<String>();

	public static ThreadLocal<List<Map<String, String>>> t_durationList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_errorList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_activityList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_appBrowseList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_labelBrowseList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_downloadStateList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_adList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_tvUpgradeList = new ThreadLocal<List<Map<String, String>>>();


	// lvchenxin
	public static ThreadLocal<List<Map<String, String>>> t_livelauncherList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_UpgradeprocessList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_Upgradeevent_request_new_versionList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_Upgradeevent_memory_space_infoList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_Upgradeevent_download_failedList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_Upgradeevent_upgrade_failedList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_UpgradeusbeventList = new ThreadLocal<List<Map<String, String>>>();
	
	//应用升级
	public static ThreadLocal<List<Map<String, String>>> t_appassistantList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_appupgradeList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_mobile_appstoreList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_app_store_download_installList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_app_store_app_browseList = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_app_store_label_browseList = new ThreadLocal<List<Map<String, String>>>();

	//第二轮数据埋桩
	public static ThreadLocal<List<Map<String, String>>> t_tvsource = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_tvsettings = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_tvshowcome = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_tvwatch = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_livelauncher = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_clicksearch = new ThreadLocal<List<Map<String, String>>>();
	public static ThreadLocal<List<Map<String, String>>> t_keywordsearch = new ThreadLocal<List<Map<String, String>>>();

	
	
	

	final static ExecutorService mThreadPoll = Executors.newFixedThreadPool(20);
	public static LogToDb logToDb = new LogToDb();
	private static int batchCount = 10000;

	// private static Statement stat;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//参数为文件名
			if (args.length<1) {
				logger.error("参数异常！");
			}
			else {
				loadProperties();
				//parseFile(p.getProperty("path"));
				//parseFile("terminal.log.2014-07-11.log");
				//mThreadPoll.submit(new Runnable_impl("terminal.log.2014-07-11.log"));
				//mThreadPoll.submit(new Runnable_impl("terminal.log.2014-07-12.log"));
				//mThreadPoll.submit(new Runnable_impl("terminal.log"));
				//mThreadPoll.submit(new Runnable_impl("terminal (2).log"));
				//String logPathes[]=args[0].split(",");
				String logPathes[]=args[0].split(",");
				for (String logPath : logPathes) {
					mThreadPoll.submit(new Runnable_impl(logPath));
				}
			}
			mThreadPoll.shutdown();
			//mThreadPoll.submit(parseThread);
			//errorScan();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("main",e);
		}
	}

	public static void loadProperties() {
		try {
			FileInputStream ferr = new FileInputStream("logscan.properties");
			p.load(ferr);
			ferr.close();
		} catch (Exception e) {
			// TODO: handle exception
			logger.error("loadProperties", e);
		}
	}

	public static void parseFile(String filename) throws Exception {
		File file = new File(filename);
		// FileReader fileReader = new FileReader(file);
		FileInputStream fin = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fin,
				"UTF-8"));
		String str;
		IPSeeker ipSeeker = IPSeeker.getInstance("QQWry.Dat");
		t_regioncode.set(ipSeeker.getCountry("1.1.1.1"));
		// StringBuffer content=new StringBuffer();
		int count = 0;
		long starttime = System.currentTimeMillis();
		while ((str = br.readLine()) != null) {
			// content.append(str); 
			// String content=readFile(p.getProperty("path")+filename);
			try {
				JSONObject log = JSON.parseObject(str);
				t_sn.set(log.getString("sn"));
				t_ipaddr.set(log.getString("ipaddr"));
				String region = log.getString("region");
				if (region == null || region.isEmpty()) {
					region = ipSeeker.getCountry(t_ipaddr.get());
				}
				t_regioncode.set(region);
				// System.out.println("sn======="+sn);
				// System.out.println("ipaddr======"+ipaddr);
				JSONObject loginfo = JSONObject.parseObject(log.get("loginfo")
						.toString());
				JSONArray uploaddatas = loginfo.getJSONArray("uploaddata");

				for (int i = 0; i < uploaddatas.size(); i++) {
					JSONObject uploaddata = uploaddatas.getJSONObject(i);
					if ((DOMAIN_DURATION_SWITCH.equals(uploaddata
							.getString("domain")))
							&& "1.0".equals(uploaddata.getString("version"))) {
						durationService.parseDurationV1(uploaddata
								.getJSONObject("data"), t_durationList.get());
						if (t_durationList.get().size() > 0
								&& t_durationList.get().size() >= batchCount) {
							logToDb.insertDuration(t_durationList.get(), conn
									.get());
							t_durationList.get().clear();
						}
					} else if ((DOMAIN_ERROR.equals(uploaddata
							.getString("domain")))
							&& ("1.0".equals(uploaddata.getString("version")) || "2.0".equals(uploaddata.getString("version").trim()))) {
						errorService.parseErrorV1(uploaddata
								.getJSONObject("data"), t_errorList.get());
						if (t_errorList.get().size() > 0
								&& t_errorList.get().size() >= batchCount) {
							logToDb.insertError(t_errorList.get(), conn.get());
							t_errorList.get().clear();
						}
					} else if ((DOMAIN_ACTIVITY_START.equals(uploaddata
							.getString("domain")))
							&& "1.0".equals(uploaddata.getString("version"))) {
						activityService.parseActivityV1(uploaddata
								.getJSONObject("data"), t_activityList.get());
						if (t_activityList.get().size() > 0
								&& t_activityList.get().size() >= batchCount) {
							logToDb.insertActivity(t_activityList.get(), conn
									.get());
							t_activityList.get().clear();
						}
					} else if ((DOMAIN_APP_STORE.equals(uploaddata
							.getString("domain")))
							&& "V1.0".equals(uploaddata.getString("version"))) {
						appStoreService.parseAppstoreV1(uploaddata
								.getJSONObject("data"), t_appBrowseList.get(),
								t_labelBrowseList.get(), t_downloadStateList
										.get());
						if (t_appBrowseList.get().size() > 0
								&& t_appBrowseList.get().size() >= batchCount) {
							logToDb.insertAppBrowse(t_appBrowseList.get(), conn
									.get());
							t_appBrowseList.get().clear();
						}
						if (t_labelBrowseList.get().size() > 0
								&& t_labelBrowseList.get().size() >= batchCount) {
							logToDb.insertLabelBrowse(t_labelBrowseList.get(),
									conn.get());
							t_labelBrowseList.get().clear();
						}
						if (t_downloadStateList.get().size() > 0
								&& t_downloadStateList.get().size() >= batchCount) {
							logToDb.insertDownloadState(t_downloadStateList
									.get(), conn.get());
							t_downloadStateList.get().clear();
						}
					} else if ((DOMAIN_AD
							.equals(uploaddata.getString("domain")))
							&& "1.0".equals(uploaddata.getString("version"))) {
						adService.parseAdV1(uploaddata.getJSONObject("data"),
								t_adList.get());
						if (t_adList.get().size() > 0
								&& t_adList.get().size() >= batchCount) {
							logToDb.insertAd(t_adList.get(), conn.get());
							t_adList.get().clear();
						}
					} else if ((DOMAIN_TV_UPGRADE.equals(uploaddata
							.getString("domain")))
							&& "1.0".equals(uploaddata.getString("version"))) {
						tvUpgradeService.parseTvUpgradeV1(uploaddata
								.getJSONObject("data"), t_tvUpgradeList.get());
						if (t_tvUpgradeList.get().size() > 0
								&& t_tvUpgradeList.get().size() >= batchCount) {
							logToDb.insertTvUpgrade(t_tvUpgradeList.get(), conn
									.get());
							t_tvUpgradeList.get().clear();
						}
					}
					// lvchenxin
					else if (DOMAIN_LIVELAUNCHER.equals(uploaddata
							.getString("domain"))
							&& "1.0".equals(uploaddata.getString("version"))) {

						livelauncherService.parseLivelauncherV1(uploaddata
								.getJSONObject("data"), t_livelauncherList.get());

						if (t_livelauncherList.get().size() > 0
								&& t_livelauncherList.get().size() >= batchCount) {
							logToDb.insertLiveLauncher(
									t_livelauncherList.get(), conn.get());
							t_livelauncherList.get().clear();

						}
					}
					
					
					else if ((DOMAIN_TV_UPGRADE.equals(uploaddata
							.getString("domain")))
							&& "2.0".equals(uploaddata.getString("version"))) {
						tvUpgradeService.parseTvUpgradeVProcess(uploaddata
								.getJSONObject("data"), t_UpgradeprocessList.get());
						
						tvUpgradeService.parseTvUpgradeVEvent(uploaddata
								.getJSONObject("data"), t_Upgradeevent_request_new_versionList.get(),t_Upgradeevent_memory_space_infoList.get(),t_Upgradeevent_download_failedList.get(),t_Upgradeevent_upgrade_failedList.get());
						
						tvUpgradeService.parseTvUpgradeVUsb(uploaddata
								.getJSONObject("data"), t_UpgradeusbeventList.get());
						
						if (t_UpgradeprocessList.get().size() > 0
								&& t_UpgradeprocessList.get().size() >= batchCount) {
							logToDb.insertTvUpgradeProcess(t_UpgradeprocessList.get(), conn
									.get());
							t_UpgradeprocessList.get().clear();
						}
						
						if (t_Upgradeevent_request_new_versionList.get().size() > 0
								&& t_Upgradeevent_request_new_versionList.get().size() >= batchCount) {
							logToDb.insertTvUpgradeevent_request_new_versionList(t_Upgradeevent_request_new_versionList.get(), conn
									.get());
							t_Upgradeevent_request_new_versionList.get().clear();
						}
						if (t_Upgradeevent_memory_space_infoList.get().size() > 0
								&& t_Upgradeevent_memory_space_infoList.get().size() >= batchCount) {
							logToDb.insertTvUpgradeevent_memory_space_infoList(t_Upgradeevent_memory_space_infoList.get(), conn
									.get());
							t_Upgradeevent_memory_space_infoList.get().clear();
						}
						if (t_Upgradeevent_download_failedList.get().size() > 0
								&& t_Upgradeevent_download_failedList.get().size() >= batchCount) {
							logToDb.insertTvUpgradeevent_download_failedList(t_Upgradeevent_download_failedList.get(), conn
									.get());
							t_Upgradeevent_download_failedList.get().clear();
						}
						if (t_Upgradeevent_upgrade_failedList.get().size() > 0
								&& t_Upgradeevent_upgrade_failedList.get().size() >= batchCount) {
							logToDb.insertTvUpgradeevent_upgrade_failedList(t_Upgradeevent_upgrade_failedList.get(), conn
									.get());
							t_Upgradeevent_upgrade_failedList.get().clear();
						}
						
						
						if (t_UpgradeusbeventList.get().size() > 0
								&& t_UpgradeusbeventList.get().size() >= batchCount) {
							logToDb.insertTvUpgradusbevent(t_UpgradeusbeventList.get(), conn
									.get());
							t_UpgradeusbeventList.get().clear();
						}
					
					}
					else if (DOMAIN_APPASSISTANT_DOWNLOAD_INSTALL.equals(uploaddata
							.getString("domain"))
							&& "1.0".equals(uploaddata.getString("version"))&&"1".equals(uploaddata.getString("client_id"))) {

						appUpgradeService.parseApp(uploaddata
								.getJSONObject("data"), t_appassistantList.get());

						if (t_appassistantList.get().size() > 0
								&& t_appassistantList.get().size() >= batchCount) {
							logToDb.insertApp(
									t_appassistantList.get(), conn.get(),"terminal_app_appassistant");
							t_appassistantList.get().clear();

						}
					}
					
					else if (DOMAIN_APPUPGRADE_DOWNLOAD_INSTALL.equals(uploaddata
							.getString("domain"))
							&& "1.0".equals(uploaddata.getString("version"))&&"2".equals(uploaddata.getString("client_id"))) {
						appUpgradeService.parseApp(uploaddata
								.getJSONObject("data"), t_appupgradeList.get());

						if (t_appupgradeList.get().size() > 0
								&& t_appupgradeList.get().size() >= batchCount) {
							logToDb.insertApp(
									t_appupgradeList.get(), conn.get(),"terminal_app_appupgrade");
							t_appupgradeList.get().clear();

						}
					}
					else if (DOMAIN_MOBILE_APPSTORE_DOWNLOAD_INSTALL.equals(uploaddata
							.getString("domain"))
							&& "1.0".equals(uploaddata.getString("version"))&&"3".equals(uploaddata.getString("client_id"))) {

						appUpgradeService.parseApp(uploaddata
								.getJSONObject("data"), t_mobile_appstoreList.get());

						if (t_mobile_appstoreList.get().size() > 0
								&& t_mobile_appstoreList.get().size() >= batchCount) {
							logToDb.insertApp(
									t_mobile_appstoreList.get(), conn.get(),"terminal_app_mobile_appstore");
							t_mobile_appstoreList.get().clear();

						}
					}
					else if (DOMAIN_APP_STORE5.equals(uploaddata
							.getString("domain"))
							&& "V1.0".equals(uploaddata.getString("version"))&&"0".equals(uploaddata.getString("client_id"))) {

						appUpgradeService.parseAppStore_download_install(uploaddata
								.getJSONObject("data"), t_app_store_download_installList.get());
						appUpgradeService.parseAppStore_app_browse(uploaddata
								.getJSONObject("data"), t_app_store_app_browseList.get());
						appUpgradeService.parseAppStore_label_browse(uploaddata
								.getJSONObject("data"), t_app_store_label_browseList.get());

						if (t_app_store_download_installList.get().size() > 0
								&& t_app_store_download_installList.get().size() >= batchCount) {
							logToDb.insertAppStore_download_install(
									t_app_store_download_installList.get(), conn.get());
							t_app_store_download_installList.get().clear();

						}
						if (t_app_store_app_browseList.get().size() > 0
								&& t_app_store_app_browseList.get().size() >= batchCount) {
							logToDb.insertAppStore_app_browse(
									t_app_store_app_browseList.get(), conn.get());
							t_app_store_app_browseList.get().clear();

						}
						if (t_app_store_label_browseList.get().size() > 0
								&& t_app_store_label_browseList.get().size() >= batchCount) {
							logToDb.insertAppStore_label_browse(
									t_app_store_label_browseList.get(), conn.get());
							t_app_store_label_browseList.get().clear();

						}
					}
					
					
					else if (DOMAIN_TVSOURCE.equals(uploaddata
							.getString("domain"))
							&& "1.0".equals(uploaddata.getString("version"))) {
						
					    tvSourceService.parseTvSource(uploaddata
								.getJSONObject("data"), t_tvsource.get());
					
					    if (t_tvsource.get().size() > 0
								&& t_tvsource.get().size() >= batchCount) {
							logToDb.insertTvSource(
									t_tvsource.get(), conn.get());
							t_tvsource.get().clear();

						}
					}
					
					else if (DOMAIN_TVSETTINGS.equals(uploaddata
							.getString("domain"))
							&& "1.0".equals(uploaddata.getString("version"))) {
					    tvSettingsService.parseTvSettings(uploaddata
								.getJSONObject("data"), t_tvsettings.get());
					
					    if (t_tvsettings.get().size() > 0
								&& t_tvsettings.get().size() >= batchCount) {
							logToDb.insertTvSettings(
									t_tvsettings.get(), conn.get());
							t_tvsettings.get().clear();

						}
					}
					
					
					else if (DOMAIN_TVSHOWCOME.equals(uploaddata
							.getString("domain"))
							&& "1.0".equals(uploaddata.getString("version"))) {
					    tvShowComeService.parseTvShowCome(uploaddata
								.getJSONObject("data"), t_tvshowcome.get());
					
					    
					    if (t_tvshowcome.get().size() > 0
								&& t_tvshowcome.get().size() >= batchCount) {
							logToDb.insertTvShowCome(
									t_tvshowcome.get(), conn.get());
							t_tvshowcome.get().clear();

						}
					}
					
					
					else if (DOMAIN_TVWATCH.equals(uploaddata
							.getString("domain"))
							&& "1.0".equals(uploaddata.getString("version"))) {
					    tvWatchService.parseTvShowCome(uploaddata
								.getJSONObject("data"), t_tvwatch.get());
					
					    if (t_tvwatch.get().size() > 0
								&& t_tvwatch.get().size() >= batchCount) {
					    	for(Map<String, String> map :t_tvwatch.get()){
					    		System.out.println(map.get("program_name"));
					    	}
							logToDb.insertTvWatch(
									t_tvwatch.get(), conn.get());
							t_tvwatch.get().clear();

						}
					}
					
					else if (DOMAIN_SEARCHACTION.equals(uploaddata
							.getString("domain"))
							&& "1.0".equals(uploaddata.getString("version"))) {
					    searchService.parseClickSearch(uploaddata
								.getJSONObject("data"), t_clicksearch.get(),conn
								.get());
					    searchService.parseKeyWordSearch(uploaddata
								.getJSONObject("data"), t_keywordsearch.get());
					
					    if (t_clicksearch.get().size() > 0
								&& t_clicksearch.get().size() >= batchCount) {
							logToDb.insertClickSearch(
									t_clicksearch.get(), conn.get());
							t_clicksearch.get().clear();

						}
					    if (t_keywordsearch.get().size() > 0
								&& t_keywordsearch.get().size() >= batchCount) {
							logToDb.insertKeywordSearch(
									t_keywordsearch.get(), conn.get());
							t_keywordsearch.get().clear();

						}
					}
					
					
					else if (DOMAIN_LIVELAUNCHER.equals(uploaddata
							.getString("domain"))
							&& "2.0".equals(uploaddata.getString("version"))) {
					    livelauncherService.parseLivelauncherV2(uploaddata
								.getJSONObject("data"), t_livelauncher.get(),conn
								.get());
					
					    if (t_livelauncher.get().size() > 0
								&& t_livelauncher.get().size() >= batchCount) {
							logToDb.insertLiveLauncherV2(
									t_livelauncher.get(), conn.get());
							t_livelauncher.get().clear();

						}
					}
					
				}
			} catch (Exception e) {
				// TODO: handle exception
				logger.error("error", e);
			}
			count++;
			logger.info("扫描文件：" + filename + " line " + count);

		}
		logToDb.insertDuration(t_durationList.get(), conn.get());
		t_durationList.get().clear();
		logToDb.insertError(t_errorList.get(), conn.get());
		t_errorList.get().clear();
		logToDb.insertActivity(t_activityList.get(), conn.get());
		t_activityList.get().clear();
		logToDb.insertAppBrowse(t_appBrowseList.get(), conn.get());
		t_appBrowseList.get().clear();
		logToDb.insertLabelBrowse(t_labelBrowseList.get(), conn.get());
		t_labelBrowseList.get().clear();
		logToDb.insertDownloadState(t_downloadStateList.get(), conn.get());
		t_downloadStateList.get().clear();
		logToDb.insertAd(t_adList.get(), conn.get());
		t_adList.get().clear();
	
		logToDb.insertTvUpgrade(t_tvUpgradeList.get(), conn.get());
		t_tvUpgradeList.get().clear();

		
		
		
		// lvchenxin
		
		logToDb.insertLiveLauncher(
				t_livelauncherList.get(), conn.get());
		t_livelauncherList.get().clear();
		
		logToDb.insertTvUpgradeProcess(t_UpgradeprocessList.get(), conn.get());
		t_UpgradeprocessList.get().clear();
		
		logToDb.insertTvUpgradeevent_request_new_versionList(t_Upgradeevent_request_new_versionList.get(), conn.get());
		t_Upgradeevent_request_new_versionList.get().clear();
		logToDb.insertTvUpgradeevent_memory_space_infoList(t_Upgradeevent_memory_space_infoList.get(), conn.get());
		t_Upgradeevent_memory_space_infoList.get().clear();
		logToDb.insertTvUpgradeevent_download_failedList(t_Upgradeevent_download_failedList.get(), conn.get());
		t_Upgradeevent_download_failedList.get().clear();
		logToDb.insertTvUpgradeevent_upgrade_failedList(t_Upgradeevent_upgrade_failedList.get(), conn.get());
		t_Upgradeevent_upgrade_failedList.get().clear();
	
		logToDb.insertTvUpgradusbevent(t_UpgradeusbeventList.get(), conn.get());
		t_UpgradeusbeventList.get().clear();
		//应用升级
		logToDb.insertApp(t_appassistantList.get(), conn.get(),"terminal_app_appassistant");
		t_appassistantList.get().clear();
		logToDb.insertApp(t_appupgradeList.get(), conn.get(),"terminal_app_appupgrade");
		t_appupgradeList.get().clear();
		logToDb.insertApp(t_mobile_appstoreList.get(), conn.get(),"terminal_app_mobile_appstore");
		t_mobile_appstoreList.get().clear();
		for(Map<String, String> map : t_app_store_download_installList.get()){
			String s = map.get("app_package_name");
			if(s.length() > 50){
				System.out.println(s);
			}
		}
		logToDb.insertAppStore_download_install(t_app_store_download_installList.get(), conn.get());
		t_app_store_download_installList.get().clear();
		logToDb.insertAppStore_app_browse(t_app_store_app_browseList.get(), conn.get());
		t_app_store_app_browseList.get().clear();
		logToDb.insertAppStore_label_browse(t_app_store_label_browseList.get(), conn.get());
		t_app_store_label_browseList.get().clear();
		
		//第二轮数据埋桩
		logToDb.insertTvSource(t_tvsource.get(), conn.get());
		for(Map<String, String> map : t_tvsource.get()){
			String s = map.get("duration");
			if(Integer.parseInt(s) > 65535 ){
				System.out.println(s);
			}
		}
		t_tvsource.get().clear();
		logToDb.insertTvSettings(t_tvsettings.get(), conn.get());
		t_tvsettings.get().clear();
		logToDb.insertTvShowCome(t_tvshowcome.get(), conn.get());
		for(Map<String, String> map : t_tvshowcome.get()){
			String s = map.get("use_total_count");
			int i = Integer.parseInt(s);
			if(i > 32767){
				System.out.println(i);
			}
		}
		t_tvshowcome.get().clear();
		for(Map<String, String> map :t_tvwatch.get()){
			String s = map.get("program_name");
			if(s.length() > 20){
				System.out.println(s);
			}
    	}
		logToDb.insertTvWatch(t_tvwatch.get(), conn.get());
		t_tvwatch.get().clear();
		logToDb.insertClickSearch(t_clicksearch.get(), conn.get());
		t_clicksearch.get().clear();
		logToDb.insertKeywordSearch(t_keywordsearch.get(), conn.get());
		t_keywordsearch.get().clear();
		logToDb.insertLiveLauncherV2(t_livelauncher.get(), conn.get());
		t_livelauncher.get().clear();
		
		br.close();
		fin.close();
		// return content.toString();
		long endtime = System.currentTimeMillis();
		System.out.println(endtime - starttime);
		//logger.info("扫描完成，共扫描" + count + "条数据，用时"+ (int) ((endtime - starttime) / (1000 * 60)) + "分！");

	}

	public static void contentMysql() throws Exception {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		String url = p.getProperty("url");
		String username = p.getProperty("username");
		String password = p.getProperty("password");
		conn.set(DriverManager.getConnection(url, username, password));
	}

	// public static void errorScan() throws Exception
	// {
	// String sql="SELECT * FROM "+
	// "(SELECT *,SUM(COUNT) AS counts FROM terminal_error_log " +
	// "GROUP BY platform,series,TYPE,stacktrace_md5,VERSION ) a "+
	// "WHERE counts > "+p.getProperty("warningnum");
	// Statement stat_select=conn.createStatement();
	//
	//
	// ResultSet rs=stat_select.executeQuery(sql);
	// while (rs.next()) {
	// StringBuffer resultSB=new StringBuffer();
	// resultSB.append("错误数量："+rs.getString("counts")+"\n");
	// resultSB.append("电视信息：\n");
	// resultSB.append("平台："+rs.getString("platform")+"\n");
	// resultSB.append("系列："+rs.getString("series")+"\n");
	// resultSB.append("软件物料号："+rs.getString("softwareID")+"\n");
	// resultSB.append("类型："+rs.getString("type")+"\n");
	// resultSB.append("版本号："+rs.getString("version")+"\n");
	//
	// resultSB.append("软件信息:\n");
	// resultSB.append("包名："+rs.getString("packagename")+"\n");
	// resultSB.append("版本号："+rs.getString("versioncode")+"\n");
	// resultSB.append("版本名称："+rs.getString("versionname")+"\n");
	// resultSB.append("错误栈：\n"+rs.getString("stacktrace")+"\n");
	// SendMail sendMail=new SendMail("终端错误自动检测","zhangxinjian@konka.com",
	// "错误报告",resultSB.toString(),1);
	//
	// sendMail.setEmailFrom(p.getProperty("emailFrom"));
	// sendMail.setFromPwd(p.getProperty("fromPwd"));
	// sendMail.setSmtpHost(p.getProperty("smtpHost"));
	//
	// sendMail.send();
	// }
	//
	//
	// }

}

class Runnable_impl implements Runnable {

	private String logPath;

	Runnable_impl(String path) {
		logPath = path;
	}

	public void run() {
		// TODO Auto-generated method stub
		// System.out.println("logpath==="+logPath);
		try {
			logscan.t_durationList.set(new ArrayList<Map<String, String>>());
			logscan.t_activityList.set(new ArrayList<Map<String, String>>());
			logscan.t_errorList.set(new ArrayList<Map<String, String>>());
			logscan.t_appBrowseList.set(new ArrayList<Map<String, String>>());
			logscan.t_labelBrowseList.set(new ArrayList<Map<String, String>>());
			logscan.t_downloadStateList
					.set(new ArrayList<Map<String, String>>());
			logscan.t_adList.set(new ArrayList<Map<String, String>>());
			logscan.t_tvUpgradeList.set(new ArrayList<Map<String, String>>());

			// lvchenxin
			logscan.t_livelauncherList
					.set(new ArrayList<Map<String, String>>());
			logscan.t_UpgradeprocessList
			.set(new ArrayList<Map<String, String>>());
			
			logscan.t_Upgradeevent_request_new_versionList
			.set(new ArrayList<Map<String, String>>());
			logscan.t_Upgradeevent_memory_space_infoList
			.set(new ArrayList<Map<String, String>>());
			logscan.t_Upgradeevent_download_failedList
			.set(new ArrayList<Map<String, String>>());
			logscan.t_Upgradeevent_upgrade_failedList
			.set(new ArrayList<Map<String, String>>());
			
			logscan.t_UpgradeusbeventList
			.set(new ArrayList<Map<String, String>>());
			//
			logscan.t_appassistantList.set(new ArrayList<Map<String, String>>());
			logscan.t_appupgradeList.set(new ArrayList<Map<String, String>>());
			logscan.t_mobile_appstoreList.set(new ArrayList<Map<String, String>>());
			logscan.t_app_store_download_installList.set(new ArrayList<Map<String, String>>());
			logscan.t_app_store_app_browseList.set(new ArrayList<Map<String, String>>());
			logscan.t_app_store_label_browseList.set(new ArrayList<Map<String, String>>());
			
			logscan.t_tvsource.set(new ArrayList<Map<String, String>>());
			logscan.t_tvsettings.set(new ArrayList<Map<String, String>>());
			logscan.t_tvshowcome.set(new ArrayList<Map<String, String>>());
			logscan.t_tvwatch.set(new ArrayList<Map<String, String>>());
			logscan.t_livelauncher.set(new ArrayList<Map<String, String>>());
			logscan.t_clicksearch.set(new ArrayList<Map<String, String>>());
			logscan.t_keywordsearch.set(new ArrayList<Map<String, String>>());
			
			logscan.contentMysql();
			logscan.parseFile(logPath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logscan.logger.error("Runnable_impl", e);
		}
	}
}
