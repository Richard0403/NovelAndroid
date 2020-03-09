package com.richard.novel.reader.page;


import com.richard.novel.common.db.BookQuery;
import com.richard.novel.http.entity.book.BookChapterContent;
import com.richard.novel.http.entity.book.BookChapterInfo;
import com.richard.novel.http.entity.book.BookInfo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by newbiechen on 17-5-29.
 * 网络页面加载器
 */

public class NetPageLoader extends PageLoader {
    private static final String TAG = "PageFactory";

    public NetPageLoader(PageView pageView, BookInfo collBook) {
        super(pageView, collBook);
    }

    private List<BookChapterInfo> convertTxtChapter(List<BookChapterInfo> bookChapters) {
//        List<BookChapterInfo> txtChapters = new ArrayList<>(bookChapters.size());
//        for (BookChapterInfo bean : bookChapters) {
//            BookChapterInfo chapter = new BookChapterInfo();
//            chapter.bookId = bean.getBookInfo().getId();
//            chapter.title = bean.getTitle();
//            chapter.link = bean.getTitle();
//            txtChapters.add(chapter);
//        }
        return bookChapters;
    }

    @Override
    public void refreshChapterList(List<BookChapterInfo> chapterList) {

        refreshChapter(chapterList);

//        QueryBuilder<BookChapterInfo> builder = BoxStoreHelper.getInstance().getBox(BookChapterInfo.class).query();
//        builder.equal(BookChapterInfo_.id, mCollBook.getId());
//        List<BookChapterInfo> chapterInfoList = builder.build().find();
//
//        if(chapterInfoList!=null && !chapterInfoList.isEmpty()){
//            refreshChapter(chapterInfoList);
//        }else{
//            //TODO 网络获取目录
//            NetQueries.queryChapterList(mCollBook.getId(), new ResultListener<List<BookChapterInfo>>() {
//                @Override
//                public void onSuccess(List<BookChapterInfo> bookChapterInfos) {
//                    refreshChapter(chapterInfoList);
//                }
//                @Override
//                public void onFail() {
//
//                }
//            });
//        }
    }


    private void refreshChapter(List<BookChapterInfo> chapterInfoList){
        // 将 BookChapter 转换成当前可用的 Chapter
        mChapterList = convertTxtChapter(chapterInfoList);
        isChapterListPrepare = true;

        // 目录加载完成，执行回调操作。
        if (mPageChangeListener != null) {
            mPageChangeListener.onCategoryFinish(mChapterList);
        }

        // 如果章节未打开
        if (!isChapterOpen()) {
            // 打开章节
            openChapter();
        }
    }

    @Override
    protected BufferedReader getChapterReader(BookChapterInfo chapter) throws Exception {
        //读取内容
        BookChapterContent chapterContent = BookQuery.queryChapterContent(chapter.getId());
        if(chapterContent == null){
            return null;
        }
        String content = chapterContent.getContent();

        byte[] by = content.getBytes();
        InputStreamReader isr = new InputStreamReader(new ByteArrayInputStream(by));
        BufferedReader reader = new BufferedReader(isr);
        return reader;
    }

    @Override
    protected boolean hasChapterData(BookChapterInfo chapter) {
        BookChapterContent chapterContent = BookQuery.queryChapterContent(chapter.getId());
        return chapterContent != null;
    }

    // 装载上一章节的内容
    @Override
    boolean parsePrevChapter() {
        boolean isRight = super.parsePrevChapter();

        if (mStatus == STATUS_FINISH) {
            loadPrevChapter();
        } else if (mStatus == STATUS_LOADING) {
            loadCurrentChapter();
        }
        return isRight;
    }

    // 装载当前章内容。
    @Override
    boolean parseCurChapter() {
        boolean isRight = super.parseCurChapter();

        if (mStatus == STATUS_LOADING) {
            loadCurrentChapter();
        }
        return isRight;
    }

    // 装载下一章节的内容
    @Override
    boolean parseNextChapter() {
        boolean isRight = super.parseNextChapter();

        if (mStatus == STATUS_FINISH) {
            loadNextChapter();
        } else if (mStatus == STATUS_LOADING) {
            loadCurrentChapter();
        }

        return isRight;
    }

    /**
     * 加载当前页的前面两个章节
     */
    private void loadPrevChapter() {
        if (mPageChangeListener != null) {
            int end = mCurChapterPos;
            int begin = end - 2;
            if (begin < 0) {
                begin = 0;
            }

            requestChapters(begin, end);
        }
    }

    /**
     * 加载前一页，当前页，后一页。
     */
    private void loadCurrentChapter() {
        if (mPageChangeListener != null) {
            int begin = mCurChapterPos;
            int end = mCurChapterPos;

            // 是否当前不是最后一章
            if (end < mChapterList.size()) {
                end = end + 1;
                if (end >= mChapterList.size()) {
                    end = mChapterList.size() - 1;
                }
            }

            // 如果当前不是第一章
            if (begin != 0) {
                begin = begin - 1;
                if (begin < 0) {
                    begin = 0;
                }
            }

            requestChapters(begin, end);
        }
    }

    /**
     * 加载当前页的后两个章节
     */
    private void loadNextChapter() {
        if (mPageChangeListener != null) {

            // 提示加载后两章
            int begin = mCurChapterPos + 1;
            int end = begin + 1;

            // 判断是否大于最后一章
            if (begin >= mChapterList.size()) {
                // 如果下一章超出目录了，就没有必要加载了
                return;
            }

            if (end > mChapterList.size()) {
                end = mChapterList.size() - 1;
            }

            requestChapters(begin, end);
        }
    }

    private void requestChapters(int start, int end) {
        // 检验输入值
        if (start < 0) {
            start = 0;
        }

        if (end >= mChapterList.size()) {
            end = mChapterList.size() - 1;
        }


        List<BookChapterInfo> chapters = new ArrayList<>();

        // 过滤，哪些数据已经加载了
        for (int i = start; i <= end; ++i) {
            BookChapterInfo BookChapterInfo = mChapterList.get(i);
            if (!hasChapterData(BookChapterInfo)) {
                chapters.add(BookChapterInfo);
            }
        }

        if (!chapters.isEmpty()) {
            mPageChangeListener.requestChapters(chapters);
        }
    }

    @Override
    public void saveRecord() {
        super.saveRecord();
//        if (mCollBook != null && isChapterListPrepare) {
//            //TODO 表示当前CollBook已经阅读
//            mCollBook.setIsUpdate(false);
//            mCollBook.setLastRead(StringUtils.
//                    dateConvert(System.currentTimeMillis(), Constant.FORMAT_BOOK_DATE));
//            //直接更新
//            BookRepository.getInstance()
//                    .saveCollBook(mCollBook);
//        }
    }
}

