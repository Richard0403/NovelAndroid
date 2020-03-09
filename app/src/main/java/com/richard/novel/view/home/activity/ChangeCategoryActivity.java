package com.richard.novel.view.home.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.google.gson.Gson;
import com.richard.novel.R;
import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.http.HttpRequest;
import com.richard.novel.http.api.HomeService;
import com.richard.novel.http.entity.base.ListEntity;
import com.richard.novel.http.entity.base.ObjEntity;
import com.richard.novel.http.entity.book.BookCategory;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.view.base.BaseActivity;
import com.richard.novel.view.home.adapter.Categorydapter;
import com.richard.novel.view.main.dialog.ConfirmDialog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.disposables.Disposable;

public class ChangeCategoryActivity extends BaseActivity {
    @BindView(R.id.clv_content)
    RecyclerView clv_content;


    private List<BookCategory> categories = new ArrayList<>();
    private Categorydapter categorydapter;
    private BookInfo bookInfo;

    @Override
    protected int getLayout() {
        return R.layout.activity_change_category;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bookInfo = (BookInfo) getIntent().getSerializableExtra(AppConstant.Extra.EXTRA_BOOK_INFO);

        categorydapter = new Categorydapter(categories, bookInfo.getBookCategory().getCodeX());
        clv_content.setAdapter(categorydapter);
        categorydapter.setOnItemClickListener((adapter, view, position) -> {
            BookCategory category = categories.get(position);
            ConfirmDialog confirmDialog = new ConfirmDialog(mContext, "确认将该书分类更改为（" + category.getName() + ")", new ConfirmDialog.OnViewClick() {
                @Override
                public void onConfirm() {
                    changeCategory(bookInfo.getId(), category.getCodeX());
                }

                @Override
                public void onCancel() {

                }
            });
            confirmDialog.show();
        });
    }

    @Override
    protected void initData() {

        getCategories();
    }

    private void getCategories() {
        HttpRequest httpRequest = new HttpRequest<ListEntity<BookCategory>>() {
            @Override
            public String createJson() {
                return super.createJson();
            }
            @Override
            protected void onObserved(Disposable disposable) {
            }
            @Override
            protected void onSuccess(ListEntity<BookCategory> result) {
                super.onSuccess(result);
                categories.addAll(result.getData());
                categorydapter.notifyDataSetChanged();
            }
        };
        httpRequest.start(HomeService.class, "queryCategory",true);
    }

    public void changeCategory(long bookId, long code) {
        HttpRequest httpRequest = new HttpRequest<ObjEntity<BookInfo>>() {
            @Override
            public String createJson() {
                Map<String, Object> map = new HashMap();
                map.put(AppConstant.Parm.BOOK_ID, bookId);
                map.put(AppConstant.Parm.CATEGORY, code);
                return new Gson().toJson(map);
            }

            @Override
            protected void onSuccess(ObjEntity<BookInfo> result) {
                super.onSuccess(result);
                ToastUtil.showSingleToast("修改成功");

                Intent intent = new Intent();
                intent.putExtra(AppConstant.Extra.EXTRA_BOOK_INFO, result.getData());
                setResult(RESULT_OK, intent);
                finish();
            }

            @Override
            protected void onObserved(Disposable disposable) {
            }
        };
        httpRequest.start(HomeService.class, "changeCategory",true);
    }

    public static void startForResult(Activity activity, BookInfo bookInfo){
        Intent intent = new Intent(activity, ChangeCategoryActivity.class);
        intent.putExtra(AppConstant.Extra.EXTRA_BOOK_INFO, bookInfo);
        activity.startActivityForResult(intent, AppConstant.Req.CHANGE_CATEGORY);
    }
}
