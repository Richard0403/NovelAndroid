package com.richard.novel.http;

import com.google.gson.Gson;
import com.richard.novel.common.constant.AppConfig;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.entity.book.BookChapterInfo;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by XiaoU on 2018/10/12.
 */

public class SingleRequest {
    public static Single<BookChapterInfo> getChapterInfo(Map param){
        HomeService service = RetroManager.getInstance().createService(HomeService.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse(AppConfig.MEDIA_TYPE_FORMAT_JSON), new Gson().toJson(param));
        return (service.getChapterInfoPackage(requestBody)
                .map(bean -> bean.getData()));
    }
}
