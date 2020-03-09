package com.richard.novel.view.user.adapter;

import android.graphics.Paint;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.novel.R;
import com.richard.novel.common.utils.ImageLoader;
import com.richard.novel.common.utils.StringUtils;
import com.richard.novel.http.entity.user.GiftBuyRecord;
import com.richard.novel.http.entity.user.GiftInfo;

import java.util.List;

/**
 * By Richard on 2017/12/22.
 */
public class GiftBuyRecordAdapter extends BaseQuickAdapter<GiftBuyRecord, BaseViewHolder> {

    public GiftBuyRecordAdapter(List<GiftBuyRecord> data) {
        super(R.layout.item_gift_buy_record, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final GiftBuyRecord item) {
        ImageLoader.getInstance().displayRoundImage(mContext, item.getGiftInfo().getCover(), helper.getView(R.id.iv_header));

        helper.setText(R.id.tv_name, item.getGiftInfo().getName())
                .setText(R.id.tv_deliver_name, item.getDeliverName())
                .setText(R.id.tv_deliver_phone, item.getDeliverPhone())
                .setText(R.id.tv_deliver_addr, item.getDeliverAddr());

        TextView tv_status = helper.getView(R.id.tv_status);
        if(item.getOrderStatus() == GiftBuyRecord.ORDER_STATUS_PAYED){
            tv_status.setText("填写收货地址");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.theme_color));
        }else if(item.getOrderStatus() == GiftBuyRecord.ORDER_STATUS_ADD_ADDR){
            tv_status.setText("待发货");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.txt_middle_grey));
        }else if(item.getOrderStatus() == GiftBuyRecord.ORDER_STATUS_DELIVERED){
            tv_status.setText("已发货");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.txt_middle_grey));
        }else if(item.getOrderStatus() == GiftBuyRecord.ORDER_STATUS_COMPLETE){
            tv_status.setText("已完成");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.txt_light_grey));
        }else if(item.getOrderStatus() == GiftBuyRecord.ORDER_STATUS_CANCEL){
            tv_status.setText("已取消");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.txt_light_grey));
        }else{
            tv_status.setText("");
            tv_status.setTextColor(mContext.getResources().getColor(R.color.theme_color));
        }

        helper.addOnClickListener(R.id.tv_status);
    }
}