package com.richard.novel.http.api;

import com.richard.novel.http.entity.base.BaseEntity;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.book.BookChapterInfo;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.book.BookShelf;
import com.richard.novel.http.entity.home.BannerInfo;
import com.richard.novel.http.entity.home.Version;
import com.richard.novel.http.entity.user.Comment;
import com.richard.novel.http.entity.user.CommentItem;
import com.richard.novel.http.entity.user.Praise;
import com.richard.novel.http.entity.user.UserInfo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * By Richard on 2017/12/26.
 */

public interface HomeService {
    @POST(ApiConfig.SING_IN)
    Observable<ObjEntity<UserInfo>> signIn(@Body RequestBody requestBody);

    @POST(ApiConfig.VERSION)
    Observable<ObjEntity<Version>> getVersion(@Body RequestBody requestBody);

    @Multipart
    @POST(ApiConfig.UPLOAD_IMAGE)
    Observable<ObjEntity<String>> uploadImage(@Part List<MultipartBody.Part> files);

    @POST(ApiConfig.QUERY_CATEGORY)
    Observable<ListEntity<BookCategory>> queryCategory(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_SINGLE_CATEGORY)
    Observable<ObjEntity<BookCategory>> getSingleCategory(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_HOT_BOOKS)
    Observable<ListEntity<BookInfo>> queryHotBooks(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_NEW_BOOKS)
    Observable<ListEntity<BookInfo>> queryNewBooks(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_BOOK_DETAIL)
    Observable<ObjEntity<BookInfo>> queryBookDetail(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_CHAPTER_LIST)
    Observable<ListEntity<BookChapterInfo>> queryChapterList(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_CHAPTER_CONTENT)
    Single<ObjEntity<BookChapterInfo>> getChapterInfoPackage(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_BANNERS)
    Observable<ListEntity<BannerInfo>> getBanners(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_SHELF_BOOKS)
    Observable<ListEntity<BookShelf>> getShelfBooks(@Body RequestBody requestBody);

    @POST(ApiConfig.ADD_SHELF_BOOKS)
    Observable<BaseEntity> addShelfBook(@Body RequestBody requestBody);

    @POST(ApiConfig.UPDATE_SHELF_BOOK)
    Observable<BaseEntity> updateShelfBook(@Body RequestBody requestBody);

    @POST(ApiConfig.SEARCH_BOOK)
    Observable<ListEntity<BookInfo>> searchBook(@Body RequestBody requestBody);

    @POST(ApiConfig.REMOVE_SHELF)
    Observable<BaseEntity> removeShelf(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_COMMENTS)
    Observable<ListEntity<CommentItem>> queryComments(@Body RequestBody requestBody);

    @POST(ApiConfig.COMMENT_ADD)
    Observable<ObjEntity<Comment>> addCmt(@Body RequestBody requestBody);

    @POST(ApiConfig.COMMENT_DELETE)
    Observable<BaseEntity> deleteCmt(@Body RequestBody requestBody);

    @POST(ApiConfig.CHANGE_CATEGORY)
    Observable<ObjEntity<BookInfo>> changeCategory(@Body RequestBody requestBody);

    @POST(ApiConfig.ADD_PRAISE)
    Observable<ObjEntity<Praise>> addPraise(@Body RequestBody requestBody);
}
