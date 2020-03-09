package com.richard.novel.view.home.adapter;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.novel.R;
import com.richard.novel.common.utils.ImageLoader;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.view.home.activity.BookDetailActivity;

import java.util.List;

/**
 * By Richard on 2017/12/20.
 * 竖版 图书适配器
 */
public class BookNewAdapter extends BaseQuickAdapter<BookInfo, BaseViewHolder> {


    public BookNewAdapter(List<BookInfo> data) {
        super(R.layout.item_book_new, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BookInfo item) {
        ImageLoader.getInstance().displayRoundImage(mContext, item.getCover(), helper.getView(R.id.iv_book_pic));
        helper.setText(R.id.tv_book_name, item.getName());

        helper.getView(R.id.ll_root).setOnClickListener(v -> {
            BookDetailActivity.start(mContext, item.getId());
        });

    }
}