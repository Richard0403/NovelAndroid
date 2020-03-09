package com.richard.novel.view.main.fragment;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.richard.novel.R;
import com.richard.novel.common.base.App;
import com.richard.novel.common.utils.ImageLoader;
import com.richard.novel.common.utils.UIUtil;
import com.richard.novel.http.entity.base.EventMsg;
import com.richard.novel.http.entity.book.BookShelf;
import com.richard.novel.presenter.contract.BookShelfContract;
import com.richard.novel.presenter.contract.HomeContract;
import com.richard.novel.presenter.impl.BookShelfImpl;
import com.richard.novel.view.base.BaseFragment;
import com.richard.novel.view.base.BaseMvpListFragment;
import com.richard.novel.view.home.activity.BookDetailActivity;
import com.richard.novel.view.main.activity.MainActivity;
import com.richard.novel.view.shelf.adapter.BookShelfAdapter;
import com.richard.novel.widget.EmptyView;
import com.richard.novel.widget.WhiteFooterView;
import com.richard.novel.widget.arcProgress.ArcProgress;
import com.scrollablelayout.ScrollableLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.view.View.VISIBLE;

/**
 * 书架
 */
public class FragmentShelf extends BaseMvpListFragment<BookShelfContract.Presenter> implements BookShelfContract.View{
    @BindView(R.id.clv_shelf)
    RecyclerView clv_shelf;
    @BindView(R.id.arc_progress)
    ArcProgress arc_progress;
    @BindView(R.id.iv_book_pic)
    ImageView iv_book_pic;
    @BindView(R.id.tv_percent)
    TextView tv_percent;
    @BindView(R.id.slv_content)
    ScrollableLayout slv_content;
    @BindView(R.id.empty)
    EmptyView empty;
    @BindView(R.id.rl_bottom)
    RelativeLayout rl_bottom;

    private List<BookShelf> bookShelves = new ArrayList<>();
    private BookShelfAdapter bookShelfAdapter;

    public static FragmentShelf newInstance() {
        return new FragmentShelf();
    }

    @Override
    protected View getLayout(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shelf, container, false);
    }

    @Override
    protected void initView() {
        slv_content.getHelper().setCurrentScrollableContainer(clv_shelf);
        EventBus.getDefault().register(this);
    }

    @Override
    protected BookShelfContract.Presenter bindPresenter() {
        return new BookShelfImpl();
    }

    @Override
    protected BaseQuickAdapter initAdapter() {
        bookShelfAdapter = new BookShelfAdapter(bookShelves);
        bookShelfAdapter.addFooterView(new WhiteFooterView(getContext()));
        bookShelfAdapter.setOnItemLongClickListener((adapter, view, position) -> {

            showSelect(true);
            return false;
        });
        return bookShelfAdapter;
    }

    @Override
    protected void reqList(int pageNo, int pageSize) {
        mPresenter.getShelfBooks(pageNo,pageSize);
    }

    @Override
    protected RecyclerView getRecyclerView() {
        clv_shelf.setLayoutManager(new GridLayoutManager(getContext(),4));
        return clv_shelf;
    }

    @Override
    protected void listEmpty() {
        UIUtil.showViews(empty);
    }

    @Override
    public void showError() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onShelfChanged(EventMsg msg) {
        if(msg.getCode() == EventMsg.CODE_SHELF){
            resetPageNo();
            bookShelves.clear();
            reqList(getPageNo(),getPageSize());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void setBookShelf(List<BookShelf> result) {
        if(getPageNo() == 0 && !result.isEmpty()){
            bookShelves.clear();
            BookShelf first = result.get(0);
            setFirstShelfBook(first);
        }
        if (!result.isEmpty()) {
            UIUtil.goneViews(empty);
            bookShelves.addAll(result);
        }
        setReqListSuccess(result);
    }

    @OnClick(R.id.iv_book_pic)
    protected void readBook(){
        if(bookShelves.isEmpty()){
            MainActivity.start(0,0);
        }else{
            BookDetailActivity.start(getContext(), bookShelves.get(0).getBookInfo().getId());
        }
    }

    private void checkBottomChange(int futureStatus){
        if(rl_bottom.getVisibility()!=futureStatus){
            if(futureStatus == VISIBLE){
                Animation enterAnim = AnimationUtils.loadAnimation(App.INSTANCE,R.anim.enter_bottom);
                rl_bottom.startAnimation(enterAnim);
            }else{
                Animation outAnim = AnimationUtils.loadAnimation(App.INSTANCE,R.anim.out_bottom);
                rl_bottom.startAnimation(outAnim);
            }
        }
        rl_bottom.setVisibility(futureStatus);
    }


    private void setFirstShelfBook(BookShelf bookShelf){
        ImageLoader.getInstance().displayImage(bookShelf.getBookInfo().getCover(), getContext(),iv_book_pic);
        int progress = BookShelfAdapter.getProgress(bookShelf);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            arc_progress.setProgress(progress, true);
        }else{
            arc_progress.setProgress(progress);
        }
        tv_percent.setText(progress+"%");
    }

    /**
     * 是否进行选择
     * @param isShow
     */
    public void showSelect(boolean isShow){
        bookShelfAdapter.setCheckShow(isShow);
        checkBottomChange(isShow? VISIBLE:View.GONE);
    }
    public boolean isSelectShow(){
        return bookShelfAdapter.isShowCheck();
    }


    @OnClick({R.id.tv_remove_shelf})
    protected void Onclick(View view){
        switch (view.getId()){
            case R.id.tv_remove_shelf:
                //移除
                List<BookShelf> bookShelves = bookShelfAdapter.getSelectShelf();
                String ids[] = new String[bookShelves.size()];
                for(int i=0; i<bookShelves.size();i++){
                    ids[i] = String.valueOf(bookShelves.get(i).getId());
                }
                if(ids.length>0){
                    mPresenter.removeShelf(ids);
                }
                showSelect(false);
                break;
        }
    }
}
