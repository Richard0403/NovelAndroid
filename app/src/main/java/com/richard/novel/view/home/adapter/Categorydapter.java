package com.richard.novel.view.home.adapter;

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
 * 分类
 */
public class Categorydapter extends BaseQuickAdapter<BookCategory, BaseViewHolder> {
    private long mSelectCode;

    public Categorydapter(List<BookCategory> data, long selectCode) {
        super(R.layout.item_book_category_select, data);
        mSelectCode = selectCode;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BookCategory item) {
        helper.setText(R.id.tv_name, item.getName());
        helper.setVisible(R.id.v_select, item.getCodeX() == mSelectCode);

        helper.setTextColor(R.id.tv_name,
                item.getCodeX() == mSelectCode? mContext.getColor(R.color.theme_color):mContext.getColor(R.color.txt_middle_grey));
    }
}