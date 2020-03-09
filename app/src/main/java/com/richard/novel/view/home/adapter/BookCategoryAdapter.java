package com.richard.novel.view.home.adapter;

import android.view.View;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.novel.R;
import com.richard.novel.common.utils.ImageLoader;
import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.view.home.activity.BookDetailActivity;

import java.util.List;

/**
 * By Richard on 2017/12/20.
 *  图书分类
 */
public class BookCategoryAdapter extends BaseQuickAdapter<BookCategory, BaseViewHolder> {

    public BookCategoryAdapter(List<BookCategory> data) {
        super(R.layout.item_category, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BookCategory item) {

        helper.setText(R.id.tv_category, item.getName());

        helper.getView(R.id.rl_root).setOnClickListener(v -> {
            //TODO 分类详情
//            BookDetailActivity.start(mContext, item.getId());
        });
    }
}