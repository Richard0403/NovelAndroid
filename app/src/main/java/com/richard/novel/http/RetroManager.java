package com.richard.novel.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.richard.novel.common.cache.AppCache;
import com.richard.novel.common.cache.UserPrefer;
import com.richard.novel.common.constant.AppConfig;
import com.richard.novel.common.utils.DesUtil;
import com.richard.novel.common.utils.LogUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Proxy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Richard on 2016/11/5.
 */
public class RetroManager {

    private static volatile RetroManager instance = null;
    private static final String algorithm = "MD5";
    private static final String secret = "fc803792672f9c04c1f5ed3df2cce3b2";

    private static final int DEFAULT_READ_TIMEOUT = 150 * 1000;
    private static final int DEFAULT_CONNECT_TIMEOUT = 150 * 1000;
    private static final int DEFAULT_WRITE_TIMEOUT = 150 * 1000;

    private Retrofit retrofit;
    private Gson gson;
    private OkHttpClient client;

//    private Class<?> mClazz;
//
//    private String mMethodName;


    private RetroManager() {
        initGson();
        initOkHttpClient();
        initRetrofit();
    }

    public static RetroManager getInstance() {
        if (instance == null) {
            synchronized (RetroManager.class) {
                if (instance == null) {
                    instance = new RetroManager();
                }
            }
        }
        return instance;
    }

    private void initRetrofit() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(AppConfig.getBaseUrl())
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
    }

    HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {
            LogUtil.i("RequestInterceptor", "message====" + message);
        }
    });

    private void initOkHttpClient() {
        if (client == null) {
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            client = new OkHttpClient.Builder()
                    .proxy(Proxy.NO_PROXY)
                    .readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                    .connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                    .writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                    .addInterceptor(new RequestInterceptor())
                    .addInterceptor(logInterceptor)
                    .addNetworkInterceptor(new NetworkInterceptor())
                    .build();
        }
    }

    public class RequestInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();

            final long time = System.currentTimeMillis();

//            final String api = "/"+getApi();
            String api = "";
            try {
                List<String> pathSegments = new ArrayList<>();
                pathSegments.addAll(original.url().pathSegments());
                pathSegments.remove(0);
                StringBuilder stringBuilder = new StringBuilder();
                for (String path : pathSegments) {
                    stringBuilder.append("/").append(path);
                }
                api = stringBuilder.toString();
            } catch (Exception e) {
                LogUtil.i("API 解析失败");
            }

            String token = "";
            try {
                token = AppCache.getUserInfo().getToken();
            } catch (Exception e) {
                LogUtil.i("用户未登录");
            }
            String requestJson="";
            try {
                Request copy = original.newBuilder().build();
                Buffer buffer = new Buffer();
                copy.body().writeTo(buffer);
                requestJson = buffer.readUtf8();
            } catch (final Exception e) {
                LogUtil.i("RequestBody解析错误");
            }

            Request request = original.newBuilder()
                    .addHeader("api", api)
                    .addHeader("appType", "android")
                    .addHeader("jPushId", UserPrefer.getJPushId())
                    .addHeader("authorization", token)
                    .addHeader("sign", getSign(api, time, requestJson))
                    .method(original.method(), original.body())
                    .build();

            LogUtil.i("RequestInterceptor", String.format("发送请求 %s ",
                    request.body()));
            return chain.proceed(request);
        }
    }

    private String getSign(String api, long time, String requestJson) {
        try {
            String requestSign = DesUtil.encode(requestJson);
            Map<String,Object> map = new HashMap<>();
            map.put("request",requestSign);
            map.put("api", api);
            map.put("time", time);

            String signInfo = DesUtil.encode(new Gson().toJson(map));
            return signInfo;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public class NetworkInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Response response = chain.proceed(request);
            if (response.code() != 200) {
                // TODO 返回状态码不为200时处理
                LogUtil.i("response status is not 200");
            }
            return response;
        }
    }


//    private String getApi() {
//        String api = "";
//        Method[] methods = mClazz.getMethods();
//        for (Method method : methods) {
//            if (method.isAnnotationPresent(POST.class) && method.getName().equals(mMethodName)) {
//                POST post = method.getAnnotation(POST.class);
//                api = post.value();
//            }
//        }
//        return api;
//    }

    private void initGson() {
        gson = new GsonBuilder()
                .serializeNulls()
                .create();
    }

    public <T> T createService(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    private String getURLEnCode(String target) {

        if (TextUtils.isEmpty(target)) {
            return "";
        }

        try {
            return URLEncoder.encode(target, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return target;
    }
}
