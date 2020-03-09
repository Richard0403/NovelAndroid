package com.richard.novel.common.constant;

/**
 * By Richard on 2018/5/11.
 */

public class AppConstant {
    public class Req {
        public static final int TAG_SELECT = 0x0001;
        public static final int PIC_SELECT = 0x0002;
        public static final int TAG_CREATE = 0x0003;
        public static final int CHANGE_CATEGORY = 0x0004;
    }


    public class Extra{
        public static final String EXTRA_TAG = "EXTRA_TAG";
        public static final String EXTRA_DIARY_ID = "EXTRA_DIARY_ID";
        public static final String EXTRA_BOOK_ID = "EXTRA_BOOK_ID";
        public static final String EXTRA_BOOK_INFO = "EXTRA_BOOK_INFO";
        public static final String EXTRA_BOOK_CATEGORY = "EXTRA_BOOK_CATEGORY";
        public static final String EXTRA_ACCOUNT_TYPE = "EXTRA_ACCOUNT_TYPE";
    }

    public class Parm {
        public static final String ID = "id";
        public static final String BOOK_ID = "bookId";
        public static final String PAGE_NO = "pageNo";
        public static final String PAGE_SIZE = "pageSize";
        public static final String SHELF_ID = "shelfId";
        public static final String CHAPTER_POS = "chapterPos";
        public static final String PAGE_POS = "pagePos";
        public static final String KEY_WORDS = "keywords";
        public static final String SHELF_IDS = "shelfIds";
        public static final String CMT_TYPE = "commentType";
        public static final String RESOURCE_ID = "resourceId";
        public static final String RESOURCE_TYPE = "resourceType";
        public static final String TO_USER_ID = "toUserId";
        public static final String TO_CMT_ID = "toCommentId";
        public static final String CONTENT = "content";
        public static final String NAME = "name";
        public static final String HEADER = "header";
        public static final String SEX = "sex";
        public static final String CATEGORY = "category";
        public static final String READ_RECORDS = "readRecords";
        public static final String TIME = "time";
        public static final String TYPE = "type";
        public static final String INVITE_CODE = "inviteCode";
        public static final String PHONE = "phone";
        public static final String ADDR = "addr";
    }

    public static final String[] randomColor = {"#ff0000","#eb4310","#f6941d","#fbb417","#ffff00","#99cc33","#3f9337","#219167","#239676","#24998d","#1f9baa","#0080ff","#3366cc","#333399","#003366","#800080","#a1488e","#c71585","#bd2158"};

    public static final String FORMAT_TIME = "HH:mm";
    public static final String FORMAT_FILE_DATE = "yyyy-MM-dd";
    public static final String FORMAT_BOOK_DATE = "yyyy-MM-dd'T'HH:mm:ss";
}
