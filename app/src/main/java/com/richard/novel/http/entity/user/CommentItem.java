package com.richard.novel.http.entity.user;

import java.util.List;

/**
 * Created by XiaoU on 2018/11/8.
 */

public class CommentItem {
    private Comment comment;
    private List<Comment> childCmt;

    private int praiseNum;
    private Praise userPraise;

    public int getPraiseNum() {
        return praiseNum;
    }

    public void setPraiseNum(int praiseNum) {
        this.praiseNum = praiseNum;
    }

    public Praise getUserPraise() {
        return userPraise;
    }

    public void setUserPraise(Praise userPraise) {
        this.userPraise = userPraise;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<Comment> getChildCmt() {
        return childCmt;
    }

    public void setChildCmt(List<Comment> childCmt) {
        this.childCmt = childCmt;
    }
}
