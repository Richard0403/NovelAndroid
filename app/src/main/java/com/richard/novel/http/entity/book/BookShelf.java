package com.richard.novel.http.entity.book;



import com.richard.novel.common.db.BookQuery;

import java.io.Serializable;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.converter.PropertyConverter;

import static io.objectbox.model.PropertyType.Relation;

/**
 * 书架
 */
@Entity
public class BookShelf implements Serializable {
    @Id
    private long boxId;

    private long id;

    @Convert(converter = Converter.class, dbType = Long.class)
    private BookInfo bookInfo;

    private int chapterPos = 0;

    private int pagePos = 0;

    private long lastReadTime;

    public long getBoxId() {
        return boxId;
    }

    public void setBoxId(long boxId) {
        this.boxId = boxId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BookInfo getBookInfo() {
        return bookInfo;
    }

    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    public int getChapterPos() {
        return chapterPos;
    }

    public void setChapterPos(int chapterPos) {
        this.chapterPos = chapterPos;
    }

    public int getPagePos() {
        return pagePos;
    }

    public void setPagePos(int pagePos) {
        this.pagePos = pagePos;
    }

    public long getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(long lastReadTime) {
        this.lastReadTime = lastReadTime;
    }

    public static class Converter implements PropertyConverter<BookInfo, Long> {
        @Override
        public BookInfo convertToEntityProperty(Long databaseValue) {
            BookInfo bookInfo = BookQuery.queryBook(databaseValue);
            if(bookInfo==null){
                bookInfo = new BookInfo();
                bookInfo.setId(databaseValue);
            }
            return bookInfo;
        }
        @Override
        public Long convertToDatabaseValue(BookInfo entityProperty) {
            return entityProperty == null ? null : entityProperty.getId();
        }
    }
}
