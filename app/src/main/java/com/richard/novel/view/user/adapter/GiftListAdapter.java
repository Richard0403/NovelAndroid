package com.richard.novel.view.user.adapter;

import android.graphics.Paint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.novel.R;
import com.richard.novel.common.utils.FormatUtils;
import com.richard.novel.common.utils.ImageLoader;
import com.richard.novel.common.utils.StringUtils;
import com.richard.novel.http.entity.user.GiftInfo;
import com.richard.novel.http.entity.user.InviteRecord;

import java.util.List;

/**
 * By Richard on 2017/12/22.
 */
public class GiftListAdapter extends BaseQuickAdapter<GiftInfo, BaseViewHolder> {

    public GiftListAdapter(List<GiftInfo> data) {
        super(R.layout.item_gift_info, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GiftInfo item) {
        ImageLoader.getInstance().displayRoundImage(mContext, item.getCover(), helper.getView(R.id.iv_header));

        helper.setText(R.id.tv_name, item.getName())
                .setText(R.id.tv_price, item.getPrice()/100+"积分")
                .setText(R.id.tv_buy_num, "已有"+item.getOrderNum()+"人兑换");

        TextView originPrice = helper.getView(R.id.tv_origin_price);
        originPrice.setText("￥" + StringUtils.parseMoney(item.getMarketPrice()));
        originPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        helper.addOnClickListener(R.id.tv_exchange);
    }
}