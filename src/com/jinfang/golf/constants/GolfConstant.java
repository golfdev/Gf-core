package com.jinfang.golf.constants;

public class GolfConstant {
    public static final String APPKEY_ANDROID_VALUE = "jf_golf_android";
    public static final String APPKEY_IOS_VALUE = "jf_golf_ios";

    public static final String IMAGE_PATH = "/opt/webapp/golf/WebContent/img";

    public static final String AUDIO_PATH = "/opt/webapp/golf/WebContent/audio";
//    public static final String HEAD_PATH = "/Users/zenglvlin/img";

    public static final String IMAGE_DOMAIN = "http://210.72.225.19:40011/img";
    public static final String AUDIO_DOMAIN = "http://210.72.225.19:40011/audio";
    
    public static final String DEFAULT_HEAD_URL = "/head/default_man.png";
    
    public static final String DEFAULT_CLUB_LOGO_URL = "/logo/default_club_log.png";
    
    public static final String DEFAULT_TEAM_LOGO_URL = "/logo/default_team_logo.png";

    
    public static final String ROOT_DOMAIN = getRootDomain();
    public static final String PUSH_DOMAIN = "210.72.225.19";
    public static final int PUSH_PORT = 40010;
    
    public static final String FOLLOW_COUNT_KEY = "follow_count_";
    public static final String FRIEND_COUNT_KEY = "friend_count_";
    public static final String FANS_COUNT_KEY = "fans_count_";
    
    public static final String LIVE_VIEW_COUNT_KEY = "live_view_count_";
    
    public static final String TEAM_APPLY_COUNT_KEY = "team_apply_count_";

    public static final String TEAM_MEMBER_COUNT_KEY = "team_member_count_";

    public static final String ORDER_WAY_LOCK = "order_way_lock_";

    private static String getRootDomain() {
    	if (System.getProperties().getProperty("os.name").toLowerCase().contains("windows")) {
    		return "127.0.0.1";
    	}
    	return "210.72.225.19";
    }

}
