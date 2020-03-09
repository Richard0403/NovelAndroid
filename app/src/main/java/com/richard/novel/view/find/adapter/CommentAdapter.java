package com.richard.novel.view.find.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.richard.novel.R;
import com.richard.novel.common.cache.AppCache;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.common.utils.*;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.entity.base.BaseEntity;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.user.Comment;
import com.richard.novel.http.entity.user.CommentItem;
import com.richard.novel.http.entity.user.Praise;
import com.richard.novel.http.entity.user.UserInfo;
import com.richard.novel.view.find.dialog.EditDialog;
import com.richard.novel.view.main.dialog.ConfirmDialog;
import com.richard.novel.widget.commentText.CommentListTextView;
import io.reactivex.disposables.Disposable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * By Richard on 2017/12/20.
 * 发现页评论
 */
public class CommentAdapter extends BaseQuickAdapter<CommentItem, BaseViewHolder> {
    EditDialog cmtDialog;


    public CommentAdapter(List<CommentItem> data) {
        super(R.layout.item_comment, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final CommentItem item) {
        ImageLoader.getInstance().displayCricleImage(mContext,item.getComment().getUser().getHeader(), helper.getView(R.id.iv_pic));

        helper.setText(R.id.iv_name, item.getComment().getUser().getName())
                .setText(R.id.tv_comment, item.getComment().getContent())
                .setText(R.id.tv_date, FormatUtils.getFormatDateTime("MM月dd日 HH:mm", item.getComment().getCreateTime()))
                .setText(R.id.tv_comment_count, String.valueOf(item.getChildCmt().size()))
                .setVisible(R.id.tv_delete, item.getComment().getUser().equals(AppCache.getUserInfo().getUser())
                            || UserInfo.USER_RULE_ADMIN.equals(AppCache.getUserInfo().getRole()))
                .setGone(R.id.ll_child_comment, item.getChildCmt().size() != 0)
                .setImageResource(R.id.iv_like, item.getUserPraise() == null? R.mipmap.icon_commnet_like_false:R.mipmap.icon_commnet_like_true)
                .setText(R.id.tv_like_count, String.valueOf(item.getPraiseNum()));


        helper.getView(R.id.ll_comment).setOnClickListener(view -> {
            //回复
            cmtDialog = new EditDialog(mContext,
                    "@"+item.getComment().getUser().getName()+":",
                    1,
                    content -> {
                        sendCmt(content,
                                item.getComment().getUser().getId(),
                                item.getComment().getId(),
                                item);
                    });
            cmtDialog.show();
        });
        helper.getView(R.id.ll_like).setOnClickListener(view -> {
            //点赞
            addPraise(item);
        });

        helper.getView(R.id.tv_delete).setOnClickListener(view -> {
            //删除
            deleteCmt(item.getComment(), false, getData().indexOf(item));
        });

        CommentListTextView cltv_comment = helper.getView(R.id.cltv_comment);
        setCommentData(cltv_comment, item);
    }


    private void setCommentData(CommentListTextView cltv_comment, final CommentItem item){
        cltv_comment.setNameColor (mContext.getResources().getColor(R.color.theme_color));
        cltv_comment.setCommentColor (mContext.getResources().getColor(R.color.txt_middle_grey));
        cltv_comment.setTalkStr (" @ ");
        cltv_comment.setTalkColor (mContext.getResources().getColor(R.color.txt_middle_grey));

        List<CommentListTextView.CommentInfo> mCommentInfos = new ArrayList<>();
        for(Comment childBean : item.getChildCmt()){
            CommentListTextView.CommentInfo commentInfo = new CommentListTextView.CommentInfo();
            commentInfo.setID(childBean.getId()+"")
                    .setNickname(childBean.getUser().getName())
                    .setComment(childBean.getContent())
                    .setUserId(childBean.getUser().getId()+"");
            if(childBean.getToUser()!=null && !childBean.getToUser().equals(childBean.getUser())){
                commentInfo.setTonickname(childBean.getToUser().getName());
                commentInfo.setToUserId(childBean.getToUser().getId()+"");
            }else{
                commentInfo.setTonickname("");
                commentInfo.setToUserId("");
            }
            mCommentInfos.add(commentInfo);
        }
        cltv_comment.setData(mCommentInfos);
        cltv_comment.setonCommentListener(new CommentListTextView.onCommentListener() {
            @Override
            public void onNickNameClick(int position, CommentListTextView.CommentInfo mInfo) {
                String userId = mInfo.getUserId();
                //TODO 个人中心
            }

            @Override
            public void onToNickNameClick(int position, CommentListTextView.CommentInfo mInfo) {
                String userId = mInfo.getToUserId();
                //TODO 个人中心
            }

            @Override
            public void onCommentItemClick(int position, CommentListTextView.CommentInfo mInfo) {
                //TODO 回复评论
//                itemListener.onChildCommentClick(item, position);
                if(AppCache.getUserInfo().getUser().equals(item.getChildCmt().get(position).getUser())){
                    deleteCmt(item.getChildCmt().get(position), true, getData().indexOf(item));
                }else{
                    cmtDialog = new EditDialog(mContext,
                            "@"+item.getChildCmt().get(position).getUser().getName()+":",
                            1,
                            content -> {
                                sendCmt(content,
                                        item.getChildCmt().get(position).getUser().getId(),
                                        item.getComment().getId(),
                                        item);
                            });
                    cmtDialog.show();
                }
            }
            @Override
            public void onCommentItemLongClick(int position, CommentListTextView.CommentInfo mInfo) {
                //TODO 长按评论
//                itemListener.onChileCommentLongClick(item, position);
                if(UserInfo.USER_RULE_ADMIN.equals(AppCache.getUserInfo().getRole())){
                    deleteCmt(item.getChildCmt().get(position), true,  getData().indexOf(item));
                }
            }
            @Override
            public void onOtherClick() {

            }

        });
    }

    private void addPraise(CommentItem item) {
        HttpRequest httpRequest = new HttpRequest<ObjEntity<Praise>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.RESOURCE_ID, item.getComment().getId());
                map.put(AppConstant.Parm.RESOURCE_TYPE,0);
                map.put(AppConstant.Parm.TO_USER_ID, item.getComment().getUser().getId());
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ObjEntity<Praise> result) {
                super.onSuccess(result);
                getData().get(getData().indexOf(item)).setUserPraise(result.getData());
                if(result.getData()!=null){
                    getData().get(getData().indexOf(item)).setPraiseNum(item.getPraiseNum()+1);
                }else{
                    getData().get(getData().indexOf(item)).setPraiseNum(item.getPraiseNum()-1);
                }
                notifyDataSetChanged();
                ToastUtil.showSingleToast(result.getMsg());
            }
            @Override
            protected void onObserved(Disposable disposable) {
            }

        };
        httpRequest.start(HomeService.class, "addPraise");
    }


    public void sendCmt(String content, Long toUserId, Long toCommentId, CommentItem commentItem) {

        HttpRequest httpRequest = new HttpRequest<ObjEntity<Comment>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap<>();
                map.put(AppConstant.Parm.CMT_TYPE, "words");
                map.put(AppConstant.Parm.RESOURCE_ID, "");
                map.put(AppConstant.Parm.TO_USER_ID, toUserId);
                map.put(AppConstant.Parm.TO_CMT_ID,toCommentId);
                map.put(AppConstant.Parm.CONTENT,content);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ObjEntity<Comment> result) {
                super.onSuccess(result);
                ToastUtil.showSingleToast("发送成功");
                getData().get(getData().indexOf(commentItem)).getChildCmt().add(result.getData());
                notifyDataSetChanged();

                if(cmtDialog!=null && cmtDialog.isShowing()){
                    cmtDialog.dismiss();
                }
            }
            @Override
            protected void onObserved(Disposable disposable) {
            }

        };
        httpRequest.start(HomeService.class, "addCmt",true);
    }

    /**
     * @param comment 当前要删除的评论
     * @param isChildCmt 是否是子评论
     * @param position 当前父评论的位置索引
     */
    public void deleteCmt(Comment comment, boolean isChildCmt, int position) {
        ConfirmDialog confirmDialog  = new ConfirmDialog(mContext, "删除该条信息？", new ConfirmDialog.OnViewClick() {
            @Override
            public void onConfirm() {
                HttpRequest httpRequest = new HttpRequest<BaseEntity>() {
                    @Override
                    public String createJson() {
                        Map<String, Object> map = new HashMap<>();
                        map.put(AppConstant.Parm.ID, comment.getId());
                        return new Gson().toJson(map);
                    }

                    @Override
                    protected void onSuccess(BaseEntity result) {
                        super.onSuccess(result);
                        if(!isChildCmt){
                            getData().remove(position);
                        }else{
                            getData().get(position).getChildCmt().remove(comment);
                        }
                        notifyDataSetChanged();
                    }
                    @Override
                    protected void onObserved(Disposable disposable) {
                    }
                };
                httpRequest.start(HomeService.class, "deleteCmt",true);
            }
            @Override
            public void onCancel() {

            }
        });
        confirmDialog.show();

    }

}