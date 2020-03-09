package com.richard.novel.view.shelf.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.novel.R;
import com.richard.novel.common.db.BookQuery;
import com.richard.novel.common.db.BoxStoreHelper;
import com.richard.novel.common.utils.ImageLoader;
import com.richard.novel.common.utils.LogUtil;
import com.richard.novel.http.entity.book.BookChapterInfo;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.book.BookShelf;
import com.richard.novel.view.home.activity.BookDetailActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * By Richard on 2017/12/20.
 * 书架
 */
public class BookShelfAdapter extends BaseQuickAdapter<BookShelf, BaseViewHolder> {
    private boolean isShowCheck;
    private List<BookShelf> selectShelf = new ArrayList<>();
    public BookShelfAdapter(List<BookShelf> data) {
        super(R.layout.item_shelf_book, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BookShelf item) {
        ImageLoader.getInstance().displayImage(item.getBookInfo().getCover(),mContext,helper.getView(R.id.iv_pic));

        helper.setText(R.id.tv_book_name, item.getBookInfo().getName());

        helper.setVisible(R.id.cb_select, isShowCheck);

        helper.setChecked(R.id.cb_select, selectShelf.contains(item));

        helper.getView(R.id.rl_root).setOnClickListener(v -> {
            if(isShowCheck){
                if(selectShelf.contains(item)){
                    selectShelf.remove(item);
                }else{
                    selectShelf.add(item);
                }
                notifyDataSetChanged();
            }else{
                BookDetailActivity.start(mContext, item.getBookInfo().getId());
            }
        });

        helper.setProgress(R.id.pb_download, getProgress(item));
    }


    public static int getProgress(BookShelf bookShelf){

        int chapterPos = bookShelf.getChapterPos();
        int pagePos = bookShelf.getPagePos();
//        long chapterCount = BoxStoreHelper.getInstance().count(BookChapterInfo.class);
        long chapterCount = bookShelf.getBookInfo().getChapter_num()+1;
        int progress = 0;
        try {
            if(chapterCount != 0){
                progress = (int) (chapterPos*100/chapterCount+1);
                List<BookChapterInfo> chapterInfos = BookQuery.queryChapterList(bookShelf.getBookInfo().getId());
                String chapterContent = BookQuery.queryChapterContent(chapterInfos.get(chapterPos).getId()).getContent();
                progress+=(pagePos*100/chapterContent.length()/chapterCount);
            }
        }catch (Exception e){
            LogUtil.e("章节无缓存");
        }
        return progress;
    }

    public void setCheckShow(boolean isShow) {
        this.isShowCheck = isShow;
        this.selectShelf.clear();
        notifyDataSetChanged();
    }

    public boolean isShowCheck() {
        return isShowCheck;
    }

    public List<BookShelf> getSelectShelf() {
        return selectShelf;
    }

}