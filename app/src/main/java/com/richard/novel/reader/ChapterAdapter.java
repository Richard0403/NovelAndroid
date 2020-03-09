package com.richard.novel.reader;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.novel.R;
import com.richard.novel.common.db.BookQuery;
import com.richard.novel.http.entity.book.BookChapterContent;
import com.richard.novel.http.entity.book.BookChapterInfo;

import java.util.List;

/**
 * Created by newbiechen on 17-6-5.
 */

public class ChapterAdapter extends BaseQuickAdapter<BookChapterInfo, BaseViewHolder> {

    private int currentSelected = 0;

    public ChapterAdapter(List<BookChapterInfo> data) {
        super(R.layout.item_chapter, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BookChapterInfo item) {
        TextView mTvChapter = helper.getView(R.id.category_tv_chapter);

        //首先判断是否该章已下载
        Drawable drawable = null;

        //TODO:目录显示设计的有点不好，需要靠成员变量是否为null来判断。
        //如果没有链接地址表示是本地文件
        if (item.getBookInfo().isLocal()){
            drawable = ContextCompat.getDrawable(mContext,R.drawable.selector_category_load);
        }
        else {
            BookChapterContent chapterContent = BookQuery.queryChapterContent(item.getId());
            if (chapterContent!=null){
                drawable = ContextCompat.getDrawable(mContext,R.drawable.selector_category_load);
            }else {
                drawable = ContextCompat.getDrawable(mContext, R.drawable.selector_category_unload);
            }
        }

        mTvChapter.setSelected(false);
        mTvChapter.setTextColor(ContextCompat.getColor(mContext,R.color.nb_text_default));
        mTvChapter.setCompoundDrawablesWithIntrinsicBounds(drawable,null,null,null);
        mTvChapter.setText(item.getTitle());

        if (getData().indexOf(item) == currentSelected){
            mTvChapter.setTextColor(ContextCompat.getColor(mContext,R.color.light_red));
            mTvChapter.setSelected(true);
        }
    }

    public void setChapter(int pos){
        currentSelected = pos;
        notifyDataSetChanged();
    }

}
