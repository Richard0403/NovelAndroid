package com.richard.novel.view.user.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.novel.R;
import com.richard.novel.common.utils.FormatUtils;
import com.richard.novel.common.utils.ImageLoader;
import com.richard.novel.http.entity.user.InviteRecord;
import com.richard.novel.http.entity.user.UserAccountRecord;

import java.util.List;

/**
 * By Richard on 2017/12/22.
 */
public class InviteRecordAdapter extends BaseQuickAdapter<InviteRecord, BaseViewHolder> {

    public InviteRecordAdapter(List<InviteRecord> data) {
        super(R.layout.item_invite_record, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final InviteRecord item) {
        ImageLoader.getInstance().displayCricleImage(mContext, item.getToUser().getHeader(), helper.getView(R.id.iv_header));

        helper.setText(R.id.tv_name, item.getToUser().getName())
                .setText(R.id.tv_date, FormatUtils.getFormatDateTime("yyyy-MM-dd HH:mm", item.getCreateTime()));

        int amount  = item.getAmount();
        helper.setText(R.id.tv_change, amount>0? "+"+amount: ""+amount);
    }
}