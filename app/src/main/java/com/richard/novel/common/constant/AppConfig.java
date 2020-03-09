package com.richard.novel.common.constant;


import com.richard.novel.common.base.App;
import com.richard.novel.common.cache.SysPrefer;
import com.richard.novel.common.utils.FileUtil;

import java.io.File;

public class AppConfig {

    public static final String APP_DB_NAME = "xiaou.db";
    public static final String APP_DB_PWD = "xiaou";

    public static String getBaseUrl() {
        if (SysPrefer.IS_DEBUG) {
            return TEST_SERVER;
        }
        return BASE_URL;
    }

    /**
     * 内部测试服务器地址
     */
    private static final String TEST_SERVER = "http://xxx.xxxx.xx/";
    private static final String LOCAL_SERVER = "http://192.168.0.136:8080/";


    /**
     * 正式线上服务器地址
     */
    private static final String BASE_URL = "http://xx.xxx.xxx/";

    /**
     * 与微信相关的常量
     */
    public class WECHAT {
        /**
         * 微信开放平台所分配的appId，从微信开放平台获取。
         *
         *  https://open.weixin.qq.com/cgi-bin/showdocument?action=dir_list&t=resource/res_list&verify=1&id=1417751808&token=&lang=zh_CN
         */
        public static final String WECHAT_ID = "wx53c97077d15830e2";
        //旧版ID
//        public static final String WECHAT_ID = "wx776e967dde85843c";
        /**
         * 微信开放平台所分配的appId，从微信开放平台获取。
         */
        public static final String WECHAT_KEY = "20240691aef59bbd4b5a01cbaf2f9149";
    }
    public class QQ{
        public static final String QQ_ID = "101450625";
        public static final String SERVICE_QQ = "933513679";
    }

    public static String PATH_DATA = FileUtil.createRootPath(App.getInstance()) + "/cache";

    public static String PATH_TXT = PATH_DATA + "/book/";

    public static String PATH_PIC = PATH_DATA + "/pic";

    //BookCachePath (因为getCachePath引用了Context，所以必须是静态变量，不能够是静态常量)
    public static String BOOK_CACHE_PATH = PATH_DATA+ File.separator
            + "book_cache"+ File.separator ;
    //文件阅读记录保存的路径
    public static String BOOK_RECORD_PATH = PATH_DATA + File.separator
            + "book_record" + File.separator;



    /**
     * 请求头数据传输格式
     */
    public static String MEDIA_TYPE_FORMAT_JSON = "application/json; charset=utf-8";
    public static String MEDIA_TYPE_FORMAT_IMG = "image/* ; charset=utf-8";
    public static String MEDIA_TYPE_FORMAT_MP3 = "audio/mp3; charset=utf-8";

}
