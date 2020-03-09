package com.richard.novel.http.entity.book;

import com.richard.novel.common.db.BookQuery;
import com.richard.novel.common.db.BoxStoreHelper;

import java.io.Serializable;
import java.util.List;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.converter.PropertyConverter;

/**
 * 章节内容
 */
@Entity
public class BookChapterContent implements Serializable {

    @Id
    private long boxId;

    private long id;
    @Convert(converter = Converter.class, dbType = Long.class)
    private BookInfo bookInfo;

    private int chapterNo;

    public String title;

    private int wordNum;

    private String content;

    private int status;

    public BookChapterContent(){

    }


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

    public int getChapterNo() {
        return chapterNo;
    }

    public void setChapterNo(int chapterNo) {
        this.chapterNo = chapterNo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWordNum() {
        return wordNum;
    }

    public void setWordNum(int wordNum) {
        this.wordNum = wordNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void parse(BookChapterInfo content) {
        this.boxId = content.getBoxId();
        this.id = content.getId();
        this.bookInfo = content.getBookInfo();
        this.chapterNo = content.getChapterNo();
        this.title = content.getTitle();
        this.wordNum = content.getWordNum();
        this.content = content.getContent();
        this.status = content.getStatus();
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
