package com.richard.novel.common.db;

import com.richard.novel.http.entity.book.BookChapterContent;
import com.richard.novel.http.entity.book.BookChapterContent_;
import com.richard.novel.http.entity.book.BookChapterInfo;
import com.richard.novel.http.entity.book.BookChapterInfo_;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.book.BookInfo_;
import com.richard.novel.http.entity.book.BookShelf;
import com.richard.novel.http.entity.book.BookShelf_;

import java.util.List;

import io.objectbox.query.QueryBuilder;

/**
 * Created by XiaoU on 2018/10/12.
 */

public class BookQuery {
    public static BookInfo queryBook(long bookId){
        QueryBuilder<BookInfo> builder = BoxStoreHelper.getInstance().getBox(BookInfo.class).query();
        builder.equal(BookInfo_.id, bookId);
        return builder.build().findFirst();
    }

    public static BookChapterInfo queryChapter(long chapterId){
        QueryBuilder<BookChapterInfo> builder = BoxStoreHelper.getInstance().getBox(BookChapterInfo.class).query();
        builder.equal(BookChapterInfo_.id, chapterId);
        return builder.build().findFirst();
    }

    public static List<BookChapterInfo> queryChapterList(long bookId){
        QueryBuilder<BookChapterInfo> builder = BoxStoreHelper.getInstance().getBox(BookChapterInfo.class).query();
        builder.equal(BookChapterInfo_.bookInfo, bookId);
        return builder.build().find();
    }

    public static BookChapterContent queryChapterContent(long chapterId){
        QueryBuilder<BookChapterContent> builder = BoxStoreHelper.getInstance().getBox(BookChapterContent.class).query();
        builder.equal(BookChapterContent_.id, chapterId);
        return builder.build().findFirst();
    }

    public static BookShelf queryBookShelf(long bookId){
        QueryBuilder<BookShelf> builder = BoxStoreHelper.getInstance().getBox(BookShelf.class).query();
        builder.equal(BookShelf_.bookInfo, bookId);
        return builder.build().findFirst();
    }
    public static BookShelf queryBookShelfById(long shelfId){
        QueryBuilder<BookShelf> builder = BoxStoreHelper.getInstance().getBox(BookShelf.class).query();
        builder.equal(BookShelf_.id, shelfId);
        return builder.build().findFirst();
    }
}
