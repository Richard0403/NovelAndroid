package com.richard.novel.view.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.richard.novel.R;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.common.db.BookQuery;
import com.richard.novel.common.utils.ImageLoader;
import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.book.BookShelf;
import com.richard.novel.presenter.contract.BookDetailContract;
import com.richard.novel.presenter.impl.BookDetailImpl;
import com.richard.novel.reader.ReadActivity;
import com.richard.novel.view.base.BaseMVPActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class BookDetailActivity extends BaseMVPActivity<BookDetailContract.Presenter> implements BookDetailContract.View {
    @BindView(R.id.iv_book_pic)
    ImageView iv_book_pic;
    @BindView(R.id.tv_book_name)
    TextView tv_book_name;
    @BindView(R.id.tv_book_author)
    TextView tv_book_author;
    @BindView(R.id.tv_summary)
    TextView tv_summary;
    @BindView(R.id.tv_add_shelf)
    TextView tv_add_shelf;
    @BindView(R.id.tv_type)
    TextView tv_type;


    private long bookId;
    BookInfo bookInfo;
    @Override
    protected int getLayout() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bookId = getIntent().getLongExtra(AppConstant.Extra.EXTRA_BOOK_ID,0);
        setAddButton();
    }


    @Override
    protected void initData() {
        super.initData();
        mPresenter.getBookDetail(bookId);
    }

    @Override
    public void showError() {

    }

    @Override
    protected BookDetailContract.Presenter bindPresenter() {
        return new BookDetailImpl();
    }

    @Override
    public void setBookDetail(BookInfo book) {
        this.bookInfo = book;
        tv_type.setText(book.getBookCategory().getName());
        ImageLoader.getInstance().displayImage(book.getCover(),mContext,iv_book_pic);
        tv_book_name.setText(book.getName());
        tv_book_author.setText(book.getAuthor());
        tv_summary.setText(book.getSummary());
    }

    public static void start(Context context, long bookId){
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(AppConstant.Extra.EXTRA_BOOK_ID, bookId);
        context.startActivity(intent);
    }

    @OnClick({R.id.tv_add_shelf, R.id.tv_read_book, R.id.tv_type})
    protected void OnClick(View view){
        switch (view.getId()){
            case R.id.tv_read_book:
                ReadActivity.start(mContext,bookInfo,false);
                break;
            case R.id.tv_add_shelf:
                if(bookInfo!=null){
                    mPresenter.addShelf(bookInfo);
                }
                break;
            case R.id.tv_type:
                ChangeCategoryActivity.startForResult(this, bookInfo);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == AppConstant.Req.CHANGE_CATEGORY && resultCode == RESULT_OK){
            BookInfo bookInfo = (BookInfo) data.getSerializableExtra(AppConstant.Extra.EXTRA_BOOK_INFO);
            setBookDetail(bookInfo);
        }
    }

    @Override
    public void onAddShelfSuccess() {
        setAddButton();
    }

    private void setAddButton(){
        BookShelf bookShelf = BookQuery.queryBookShelf(bookId);
        tv_add_shelf.setText(bookShelf==null? "加入书架":"已加入书架");
        tv_add_shelf.setTextColor(getColor(bookShelf==null? R.color.theme_color:R.color.txt_middle_grey));
        tv_add_shelf.setClickable(bookShelf==null);
    }

}
