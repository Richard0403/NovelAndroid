package com.richard.novel.http.entity.book;



import java.io.Serializable;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.converter.PropertyConverter;

/**
 * 书籍信息
 */
@Entity
public class BookInfo implements Serializable {
    @Id
    private long boxId;

    private long id;
    @Convert(converter = Converter.class, dbType = Long.class)
    private BookCategory bookCategory;

    private String name;

    private String author;

    private String summary;

    private String cover;

    private int chapter_num;

    private long word_num;

    private int status;

    private String url;

    private int media_type;

    private String author_introduction;

    private String poster;

    private long readTimes;



    //用户变量
    private int chapterPos;
    private int pagePos;
    private boolean isLocal;
    private long lastReadTime;


    public long getBoxId() {
        return boxId;
    }

    public void setBoxId(long boxId) {
        this.boxId = boxId;
    }

    public long getLastReadTime() {
        return lastReadTime;
    }

    public void setLastReadTime(long lastReadTime) {
        this.lastReadTime = lastReadTime;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BookCategory getBookCategory() {
        return bookCategory;
    }

    public void setBookCategory(BookCategory bookCategory) {
        this.bookCategory = bookCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public int getChapter_num() {
        return chapter_num;
    }

    public void setChapter_num(int chapter_num) {
        this.chapter_num = chapter_num;
    }

    public long getWord_num() {
        return word_num;
    }

    public void setWord_num(long word_num) {
        this.word_num = word_num;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }

    public String getAuthor_introduction() {
        return author_introduction;
    }

    public void setAuthor_introduction(String author_introduction) {
        this.author_introduction = author_introduction;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public long getReadTimes() {
        return readTimes;
    }

    public void setReadTimes(long readTimes) {
        this.readTimes = readTimes;
    }

    public static class Converter implements PropertyConverter<BookCategory, Long> {
        @Override
        public BookCategory convertToEntityProperty(Long databaseValue) {
            BookCategory category = new BookCategory();
            category.setCodeX(databaseValue);
            return category;
        }
        @Override
        public Long convertToDatabaseValue(BookCategory entityProperty) {
            return entityProperty == null ? null : entityProperty.getCodeX();
        }
    }
}
