package com.richard.novel.common.db;

import com.richard.novel.http.entity.book.BookChapterContent;
import com.richard.novel.http.entity.book.BookChapterContent_;
import com.richard.novel.http.entity.book.BookChapterInfo;
import com.richard.novel.http.entity.book.BookChapterInfo_;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.book.BookInfo_;
import com.richard.novel.http.entity.book.BookShelf;
import com.richard.novel.http.entity.book.BookShelf_;
import com.richard.novel.http.entity.record.TimeRecord;
import com.richard.novel.http.entity.record.TimeRecord_;

import java.util.List;

import io.objectbox.query.QueryBuilder;

/**
 * Created by XiaoU on 2018/10/12.
 */

public class TimeRecordQuery {
    public static List<TimeRecord> queryNotUploadRecord(){
        QueryBuilder<TimeRecord> builder = BoxStoreHelper.getInstance().getBox(TimeRecord.class).query();
        builder.equal(TimeRecord_.isUploaded, false);
        return builder.build().find();
    }

}
