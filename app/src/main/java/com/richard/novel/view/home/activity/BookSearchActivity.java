package com.richard.novel.view.home.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.richard.novel.R;
import com.richard.novel.common.cache.UserPrefer;
import com.richard.novel.common.utils.EmojiInputFilter;
import com.richard.novel.common.utils.LogUtil;
import com.richard.novel.common.utils.StringUtils;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.presenter.BaseContract;
import com.richard.novel.presenter.contract.BookDetailContract;
import com.richard.novel.presenter.contract.BookSearchContract;
import com.richard.novel.presenter.impl.BookSearchImpl;
import com.richard.novel.view.base.BaseMvpListActivity;
import com.richard.novel.view.home.adapter.BookCommonAdapter;
import com.richard.novel.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import co.lujun.androidtagview.TagContainerLayout;
import co.lujun.androidtagview.TagView;

public class BookSearchActivity extends BaseMvpListActivity<BookSearchContract.Presenter> implements BookSearchContract.View {
    @BindView(R.id.clv_search_result)
    RecyclerView clv_search_result;
    @BindView(R.id.et_keywords)
    EditText et_keywords;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.iv_clear)
    ImageView iv_clear;
    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.tag_content)
    TagContainerLayout tag_content;
    @BindView(R.id.rl_record)
    RelativeLayout rl_record;
    @BindView(R.id.rl_search_result)
    RelativeLayout rl_search_result;

    private List<BookInfo> searchBooks = new ArrayList<>();
    private String keywords;
    private boolean isQuerying;

    private List<String> searchRecord = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_book_search;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setupEditView();
    }

    @Override
    protected BookSearchContract.Presenter bindPresenter() {
        return new BookSearchImpl();
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return clv_search_result;
    }

    @Override
    protected BaseQuickAdapter initAdapter() {
        return new BookCommonAdapter(searchBooks);
    }

    @Override
    protected void reqList(int pageNo, int pageSize) {
        if(!StringUtils.isEmpty(keywords)){
            mPresenter.searchBook(pageNo,pageSize,keywords);
        }
    }

    @Override
    protected void listEmpty() {
        empty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {

    }

    @Override
    protected void initData() {
        super.initData();
        searchRecord.addAll(UserPrefer.getSearchRecord());
        tag_content.setTags(searchRecord);
        showRecord(true);
        tag_content.setOnTagClickListener(new TagView.OnTagClickListener() {
            @Override
            public void onTagClick(int position, String text) {
                et_keywords.setText(text);
                toSearch();
            }

            @Override
            public void onTagLongClick(int position, String text) {

            }

            @Override
            public void onTagCrossClick(int position) {

            }
        });
    }

    @Override
    public void setSearchBook(List<BookInfo> bookList) {
        isQuerying = false;
        if(getPageNo() == 0){
            searchBooks.clear();
        }
        if (!bookList.isEmpty()) {
            searchBooks.addAll(bookList);
            empty.setVisibility(View.GONE);
        }
        setReqListSuccess(bookList);
    }

    @OnClick(R.id.iv_search)
    protected void OnSearchClick(){
        toSearch();
    }
    @OnClick(R.id.iv_clear)
    protected void ClearText(){
        et_keywords.setText("");
        showRecord(true);
    }


    private void toSearch(){
        keywords = et_keywords.getText().toString();
        if(!StringUtils.isEmpty(keywords)){
            resetPageNo();
            mPresenter.searchBook(getPageNo(),getPageSize(),keywords);

            searchRecord.remove(keywords);
            searchRecord.add(0,keywords);
            UserPrefer.setSearchRecord(searchRecord);
            tag_content.setTags(searchRecord);
        }
    }

    private void showRecord(boolean isShow){
        rl_search_result.setVisibility(isShow? View.GONE:View.VISIBLE);
        if(isShow){
            rl_record.setVisibility(tag_content.getTags().isEmpty()? View.GONE:View.VISIBLE);
        }else{
            rl_record.setVisibility(View.GONE);
        }
    }


    private void setupEditView() {
        et_keywords.setOnEditorActionListener((v, actionId, event) -> {

            if(actionId == EditorInfo.IME_ACTION_SEARCH) {
                toSearch();
            }
            return false;
        });
        InputFilter[] emojiFilters = {new EmojiInputFilter()};
        et_keywords.setFilters(emojiFilters);
        et_keywords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                LogUtil.i("开始输入");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                LogUtil.i("正在输入");
                showRecord(s.length()==0);
                if(s.length()>0){
                    iv_clear.setVisibility(View.VISIBLE);
                    if(!isQuerying){
                        isQuerying = true;
                        keywords = s.toString();
                        resetPageNo();
                        mPresenter.searchBook(getPageNo(),getPageSize(),keywords);
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtil.i("输入结束");
            }
        });
    }
}
