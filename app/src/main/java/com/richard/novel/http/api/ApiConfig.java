package com.richard.novel.http.api;

public class ApiConfig {

    public static final boolean IS_DEBUG = true;
    /*登录注册*/
    public static final String SING_IN = "auth/register";
    /*上传文件*/
    public static final String UPLOAD_IMAGE = "upload/uploadFile";
    /*获取所有分类*/
    public static final String QUERY_CATEGORY = "book/getCategory";
    /*获取单个分类信息*/
    public static final String QUERY_SINGLE_CATEGORY = "book/getSingleCategory";
    /*热门书籍*/
    public static final String QUERY_HOT_BOOKS = "book/getHotBooks";
    /*最新书籍*/
    public static final String QUERY_NEW_BOOKS = "book/getNewBooks";
    /*书籍详情*/
    public static final String QUERY_BOOK_DETAIL = "book/getBookDetail";
    /*获取书籍的目录*/
    public static final String QUERY_CHAPTER_LIST = "book/getChapterList";
    /*获取目录内容*/
    public static final String QUERY_CHAPTER_CONTENT = "book/getChapterContent";
    /*分类获取banner*/
    public static final String QUERY_BANNERS = "banner/queryBanner";
    /*获取书架书籍*/
    public static final String QUERY_SHELF_BOOKS = "shelf/getShelfBooks";
    /*添加书架*/
    public static final String ADD_SHELF_BOOKS = "shelf/addBookShelf";
    /*更新书架*/
    public static final String UPDATE_SHELF_BOOK = "shelf/updateBookShelf";
    /*书籍搜索*/
    public static final String SEARCH_BOOK = "book/searchBook";
    /*移除书架*/
    public static final String REMOVE_SHELF = "shelf/removeBookShelf";
    /*查询评论*/
    public static final String QUERY_COMMENTS = "comment/getComment";
    /*添加评论*/
    public static final String COMMENT_ADD = "comment/addComment";
    /*删除评论*/
    public static final String COMMENT_DELETE = "comment/delComment";
    /*更新用户信息*/
    public static final String UPDATE_USER = "user/updateUser";
    /*版本号控制*/
    public static final String VERSION = "version/getVersion";
    /*修改书籍分类*/
    public static final String CHANGE_CATEGORY = "book/changeBookCategory";
    /*点赞/取消点赞*/
    public static final String ADD_PRAISE = "praise/addPraise";
    /*提交阅读时间*/
    public static final String UPLOAD_READ_TIME = "userdata/uploadReadTime";
    /*查询某一天的阅读时间*/
    public static final String QUERY_READ_TIME = "userdata/getDayReadTime";
    /*查询指定账户信息*/
    public static final String QUERY_USER_ACCOUNT = "user/getUserAccount";
    /*查询指定账户流水记录*/
    public static final String QUERY_USER_ACCOUNT_RECORD = "user/getAccountRecord";
    /*查询邀请记录*/
    public static final String QUERY_INVITE_RECORD = "user/getInviteRecord";
    /*查询邀请总数*/
    public static final String QUERY_INVITE_COUNT = "user/getInviteCount";
    /*填写邀请码*/
    public static final String WRITE_INVITE_CODE = "user/writeInviteCode";
    /*查询礼物列表*/
    public static final String QUERY_GIFT_LIST = "gift/getGiftList";
    /*兑换礼物*/
    public static final String EXCHANGE_GIFT = "gift/exchangeGift";
    /*查询已购礼物列表*/
    public static final String QUERY_GIFT_BUY_RECORD = "gift/getGiftBuyRecord";
    /* 填写收货地址*/
    public static final String GIFT_ADD_ADDR = "gift/addGiftAddr";
}
