package com.richard.novel.http.api;

import com.richard.novel.http.entity.base.BaseEntity;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.book.BookChapterInfo;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.book.BookShelf;
import com.richard.novel.http.entity.home.BannerInfo;
import com.richard.novel.http.entity.user.Comment;
import com.richard.novel.http.entity.user.CommentItem;
import com.richard.novel.http.entity.user.GiftBuyRecord;
import com.richard.novel.http.entity.user.GiftInfo;
import com.richard.novel.http.entity.user.InviteRecord;
import com.richard.novel.http.entity.user.ReadTimeSummary;
import com.richard.novel.http.entity.user.UserAccount;
import com.richard.novel.http.entity.user.UserAccountRecord;
import com.richard.novel.http.entity.user.UserInfo;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

import java.util.List;

/**
 * By Richard on 2017/12/26.
 */

public interface UserService {
    @POST(ApiConfig.UPDATE_USER)
    Observable<ObjEntity<UserInfo.UserBean>> updateUser(@Body RequestBody requestBody);

    @POST(ApiConfig.UPLOAD_READ_TIME)
    Observable<BaseEntity> uploadReadTime(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_READ_TIME)
    Observable<ObjEntity<ReadTimeSummary>> queryReadTime(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_USER_ACCOUNT)
    Observable<ObjEntity<UserAccount>> queryUserAccount(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_USER_ACCOUNT_RECORD)
    Observable<ListEntity<UserAccountRecord>> queryUserAccountRecord(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_INVITE_RECORD)
    Observable<ListEntity<InviteRecord>> getInviteRecord(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_INVITE_COUNT)
    Observable<ObjEntity<Integer>> getInviteCount(@Body RequestBody requestBody);

    @POST(ApiConfig.WRITE_INVITE_CODE)
    Observable<BaseEntity> writeInviteCount(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_GIFT_LIST)
    Observable<ListEntity<GiftInfo>> queryGiftList(@Body RequestBody requestBody);

    @POST(ApiConfig.EXCHANGE_GIFT)
    Observable<BaseEntity> exchangeGift(@Body RequestBody requestBody);

    @POST(ApiConfig.QUERY_GIFT_BUY_RECORD)
    Observable<ListEntity<GiftBuyRecord>> queryGiftBuyRecord(@Body RequestBody requestBody);

    @POST(ApiConfig.GIFT_ADD_ADDR)
    Observable<BaseEntity> addGiftAddr(@Body RequestBody requestBody);
}
