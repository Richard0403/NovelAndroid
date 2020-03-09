package com.richard.novel.view.home.adapter;

import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.novel.R;
import com.richard.novel.common.utils.ImageLoader;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.view.home.activity.BookDetailActivity;

import java.util.List;

/**
 * By Richard on 2017/12/20.
 * 横版图书列表
 */
public class BookCommonAdapter extends BaseQuickAdapter<BookInfo, BaseViewHolder> {
    private boolean isShowReaderNum;

    public BookCommonAdapter(List<BookInfo> data) {
        super(R.layout.item_book_common_list, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final BookInfo item) {
        ImageLoader.getInstance().displayImage(item.getCover(),mContext, (ImageView) helper.getView(R.id.iv_pic));

        helper.setText(R.id.tv_book_name, item.getName())
                .setText(R.id.tv_book_info, item.getSummary());

        if(isShowReaderNum){
            helper.setText(R.id.tv_read_num, item.getReadTimes()+"人在读");
            helper.setVisible(R.id.tv_read_num, true);
        }else{
            helper.setGone(R.id.tv_read_num, false);
        }

        helper.getView(R.id.rl_root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookDetailActivity.start(mContext, item.getId());
            }
        });
        helper.setText(R.id.tv_book_author, item.getAuthor());
    }

    public void setIsShowReaderNum(boolean isShowReaderNum){
        this.isShowReaderNum = isShowReaderNum;
        notifyDataSetChanged();
    }
}