package com.richard.novel.view.user.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.richard.novel.R;
import com.richard.novel.common.utils.FormatUtils;
import com.richard.novel.common.utils.StringUtils;
import com.richard.novel.http.entity.user.UserAccountRecord;

import java.util.List;

/**
 * By Richard on 2017/12/22.
 */
public class UserAccountRecordAdapter extends BaseQuickAdapter<UserAccountRecord, BaseViewHolder> {
    private int accountType;

    public UserAccountRecordAdapter(List<UserAccountRecord> data, int accoutType) {
        super(R.layout.item_user_account_record, data);
        this.accountType = accoutType;
    }

    @Override
    protected void convert(final BaseViewHolder helper, final UserAccountRecord item) {
        helper.setText(R.id.tv_tip, item.getTitle())
                .setText(R.id.tv_date, FormatUtils.getFormatDateTime("HH:mm", item.getCreateTime()))
                .setText(R.id.tv_header_date, FormatUtils.getFormatDateTime("MM月dd日", item.getCreateTime()));
        int amount  = item.getAmountChange();
        int balance = item.getAmount();

        helper.setText(R.id.tv_change, amount>0? "+"+ StringUtils.parseMoney(amount): ""+StringUtils.parseMoney(amount));


        //        ll_header v_line tv_header_date
        if(getData().indexOf(item) == 0){
            helper.setGone(R.id.ll_header,true);
            helper.setGone(R.id.v_line, false);
        }else{
            String curDate = FormatUtils.getFormatDateTime("yyyyMMDD",  item.getCreateTime());
            String beforeDate = FormatUtils.getFormatDateTime("yyyyMMDD",  getData().get(getData().indexOf(item)-1).getCreateTime());
            if(curDate.equals(beforeDate)){
                helper.setGone(R.id.ll_header,false);
                helper.setGone(R.id.v_line, false);
            }else{
                helper.setGone(R.id.ll_header,true);
                helper.setGone(R.id.v_line, true);
            }
        }

    }
}