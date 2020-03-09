package com.richard.novel.view.home.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.richard.novel.R;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.presenter.contract.ClassifyContract;
import com.richard.novel.presenter.impl.ClassifyContractImpl;
import com.richard.novel.view.base.BaseMvpListFragment;
import com.richard.novel.view.home.adapter.BookCommonAdapter;
import com.richard.novel.view.home.adapter.BookNewAdapter;
import com.richard.novel.widget.EmptyView;
import com.scrollablelayout.ScrollableLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created Richard
 * Date 2018/6/9.
 */
public class BookClassifyFragment extends BaseMvpListFragment<ClassifyContract.Presenter> implements ClassifyContract.View {

    @BindView(R.id.slv_content)
    ScrollableLayout slv_content;
    @BindView(R.id.clv_new)
    RecyclerView clv_new;
    @BindView(R.id.clv_hot)
    RecyclerView clv_hot;
    @BindView(R.id.empty)
    EmptyView empty;

    public static final String ARGS_CODE = "ARGS_CODE";
    private Long code;

    private List<BookInfo> hotBooks = new ArrayList<>();

    private List<BookInfo> newBooks = new ArrayList<>();
    private BookNewAdapter newAdapter;


    @Override
    protected View getLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book_classify, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        code = getArguments().getLong(ARGS_CODE);
    }

    @Override
    protected ClassifyContract.Presenter bindPresenter() {
        return new ClassifyContractImpl(this);
    }
    @Override
    protected void initView() {
        slv_content.getHelper().setCurrentScrollableContainer(clv_hot);

        newAdapter = new BookNewAdapter(newBooks);
        clv_new.setAdapter(newAdapter);
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.loadNewBooks(0,20, code);
    }



    @Override
    protected BaseQuickAdapter initAdapter() {
        BookCommonAdapter commonAdapter = new BookCommonAdapter(hotBooks);
        return commonAdapter;
    }

    @Override
    protected RecyclerView getRecyclerView() {
        return clv_hot;
    }

    @Override
    protected void listEmpty() {
        empty.setVisibility(View.VISIBLE);
    }

    @Override
    protected void reqList(final int pageNo, final int pageSize) {
        mPresenter.loadHotBooks(pageNo,pageSize,code);
    }




    public static BookClassifyFragment newInstance(long code) {
        Bundle args = new Bundle();
        args.putLong(ARGS_CODE, code);
        BookClassifyFragment fragment = new BookClassifyFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void showError() {

    }

    @Override
    public void setHotBooks(List<BookInfo> hotBookResult) {
        if(getPageNo() == 0){
            hotBooks.clear();
        }
        if (!hotBookResult.isEmpty()) {
            hotBooks.addAll(hotBookResult);
            empty.setVisibility(View.GONE);
        }
        setReqListSuccess(hotBookResult);
    }

    @Override
    public void setNewBooks(List<BookInfo> hotBooks) {
        newBooks.clear();
        newBooks.addAll(hotBooks);
        newAdapter.notifyDataSetChanged();
    }
}
