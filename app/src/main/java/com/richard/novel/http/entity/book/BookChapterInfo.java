package com.richard.novel.http.entity.book;

import java.io.Serializable;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.converter.PropertyConverter;

/**
 * 章节信息
 */
@Entity
public class BookChapterInfo implements Serializable {

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

    //本地变量
    public long start;
    public long end;

    public BookChapterInfo(){

    }


    public long getBoxId() {
        return boxId;
    }

    public void setBoxId(long boxId) {
        this.boxId = boxId;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
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

    public static class Converter implements PropertyConverter<BookInfo, Long> {
        @Override
        public BookInfo convertToEntityProperty(Long databaseValue) {
            BookInfo bookInfo = new BookInfo();
            bookInfo.setId(databaseValue);
            return bookInfo;
        }
        @Override
        public Long convertToDatabaseValue(BookInfo entityProperty) {
            return entityProperty == null ? null : entityProperty.getId();
        }
    }
}
