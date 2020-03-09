package com.richard.novel.reader;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.novel.R;
import com.richard.novel.http.entity.book.BookChapterInfo;
import com.richard.novel.reader.page.PageStyle;

import java.util.List;

/**
 * Created by newbiechen on 17-5-19.
 */

public class PageStyleAdapter extends BaseQuickAdapter<Drawable, BaseViewHolder> {
    private int currentChecked;

    public PageStyleAdapter(List<Drawable> data) {
        super(R.layout.item_read_bg, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Drawable item) {
        View mReadBg = helper.getView(R.id.read_bg_view);
        ImageView mIvChecked = helper.getView(R.id.read_bg_iv_checked);

        mReadBg.setBackground(item);
        mIvChecked.setVisibility(View.GONE);

        if (currentChecked == getData().indexOf(item)){
            mIvChecked.setVisibility(View.VISIBLE);
        }
    }


    public void setPageStyleChecked(PageStyle pageStyle){
        currentChecked = pageStyle.ordinal();
        notifyDataSetChanged();
    }

//    @Override
//    protected void onItemClick(View v, int pos) {
//        super.onItemClick(v, pos);
//        currentChecked = pos;
//        notifyDataSetChanged();
//    }


}
