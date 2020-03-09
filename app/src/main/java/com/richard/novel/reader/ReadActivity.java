package com.richard.novel.reader;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.richard.novel.R;
import com.richard.novel.common.db.BoxStoreHelper;
import com.richard.novel.common.utils.BrightnessUtils;
import com.richard.novel.common.utils.LogUtil;
import com.richard.novel.common.utils.ScreenUtils;
import com.richard.novel.common.utils.StringUtils;
import com.richard.novel.common.utils.SystemBarUtils;
import com.richard.novel.common.utils.TimerUtil;
import com.richard.novel.common.utils.ToastUtil;
import com.richard.novel.http.entity.book.BookChapterInfo;
import com.richard.novel.http.entity.book.BookInfo;
import com.richard.novel.http.entity.record.TimeRecord;
import com.richard.novel.presenter.contract.ReadContract;
import com.richard.novel.presenter.impl.ReadContractImpl;
import com.richard.novel.reader.page.PageLoader;
import com.richard.novel.reader.page.PageView;
import com.richard.novel.view.base.BaseMVPActivity;
import com.richard.novel.view.home.activity.BookDetailActivity;
import com.richard.novel.view.home.dialog.DownloadDialog;
import com.yuyh.library.imgsel.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.support.v4.view.ViewCompat.LAYER_TYPE_SOFTWARE;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by newbiechen on 17-5-16.
 */

public class ReadActivity extends BaseMVPActivity<ReadContract.Presenter>
        implements ReadContract.View {
    private static final String TAG = "ReadActivity";
    public static final int REQUEST_MORE_SETTING = 1;
    public static final String EXTRA_COLL_BOOK = "extra_coll_book";
    public static final String EXTRA_IS_COLLECTED = "extra_is_collected";

    // 注册 Brightness 的 uri
    private final Uri BRIGHTNESS_MODE_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS_MODE);
    private final Uri BRIGHTNESS_URI =
            Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS);
    private final Uri BRIGHTNESS_ADJ_URI =
            Settings.System.getUriFor("screen_auto_brightness_adj");

    private static final int WHAT_CATEGORY = 1;
    private static final int WHAT_CHAPTER = 2;


    @BindView(R.id.read_dl_slide)
    DrawerLayout mDlSlide;
    /*************top_menu_view*******************/
    @BindView(R.id.read_abl_top_menu)
    AppBarLayout mAblTopMenu;
    @BindView(R.id.read_tv_community)
    TextView mTvCommunity;
    @BindView(R.id.read_tv_brief)
    TextView mTvBrief;
    /***************content_view******************/
    @BindView(R.id.read_pv_page)
    PageView mPvPage;
    /***************bottom_menu_view***************************/
    @BindView(R.id.read_tv_page_tip)
    TextView mTvPageTip;

    @BindView(R.id.read_ll_bottom_menu)
    LinearLayout mLlBottomMenu;
    @BindView(R.id.read_tv_pre_chapter)
    TextView mTvPreChapter;
    @BindView(R.id.read_sb_chapter_progress)
    SeekBar mSbChapterProgress;
    @BindView(R.id.read_tv_next_chapter)
    TextView mTvNextChapter;
    @BindView(R.id.read_tv_category)
    ImageView mTvCategory;
    @BindView(R.id.read_tv_night_mode)
    ImageView mTvNightMode;
    /*    @BindView(R.id.read_tv_download)
        TextView mTvDownload;*/
    @BindView(R.id.read_tv_setting)
    ImageView mTvSetting;
    @BindView(R.id.read_tv_download)
    ImageView mTvDownload;
    /***************left slide*******************************/
    @BindView(R.id.read_iv_category)
    RecyclerView mLvCategory;
    /*****************view******************/
    private ReadSettingDialog mSettingDialog;
    private PageLoader mPageLoader;
    private Animation mTopInAnim;
    private Animation mTopOutAnim;
    private Animation mBottomInAnim;
    private Animation mBottomOutAnim;
    private ChapterAdapter chapterAdapter;
    List<BookChapterInfo> chapterList = new ArrayList<>();
    private BookInfo mCollBook;

    //倒计时器
    private CountDownTimer mTimer;//超过时间停止计时
    private CountDownTimer mRecordTimer;//固定时间上传
    private int countDown = 3*60*1000+1200;//5分钟提交一次

    //控制屏幕常亮
    private PowerManager.WakeLock mWakeLock;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {
                case WHAT_CATEGORY:
                    mLvCategory.scrollToPosition(mPageLoader.getChapterPos());
                    break;
                case WHAT_CHAPTER:
                    mPageLoader.openChapter();
                    break;
            }
        }
    };
    // 接收电池信息和时间更新的广播
    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                int level = intent.getIntExtra("level", 0);
                mPageLoader.updateBattery(level);
            }
            // 监听分钟的变化
            else if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
                mPageLoader.updateTime();
            }
        }
    };

    // 亮度调节监听
    // 由于亮度调节没有 Broadcast 而是直接修改 ContentProvider 的。所以需要创建一个 Observer 来监听 ContentProvider 的变化情况。
    private ContentObserver mBrightObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            super.onChange(selfChange);

            // 判断当前是否跟随屏幕亮度，如果不是则返回
            if (selfChange || !mSettingDialog.isBrightFollowSystem()) return;

            // 如果系统亮度改变，则修改当前 Activity 亮度
            if (BRIGHTNESS_MODE_URI.equals(uri)) {
                Log.d(TAG, "亮度模式改变");
            } else if (BRIGHTNESS_URI.equals(uri) && !BrightnessUtils.isAutoBrightness(ReadActivity.this)) {
                Log.d(TAG, "亮度模式为手动模式 值改变");
                BrightnessUtils.setBrightness(ReadActivity.this, BrightnessUtils.getScreenBrightness(ReadActivity.this));
            } else if (BRIGHTNESS_ADJ_URI.equals(uri) && BrightnessUtils.isAutoBrightness(ReadActivity.this)) {
                Log.d(TAG, "亮度模式为自动模式 值改变");
                BrightnessUtils.setDefaultBrightness(ReadActivity.this);
            } else {
                Log.d(TAG, "亮度调整 其他");
            }
        }
    };

    /***************params*****************/
    private boolean isCollected = false; // isFromSDCard
    private boolean isNightMode = false;
    private boolean isFullScreen = false;
    private boolean isRegistered = false;

    private long mBookId;

    public static void start(Context context, BookInfo collBook, boolean isCollected) {
        context.startActivity(new Intent(context, ReadActivity.class)
                .putExtra(EXTRA_IS_COLLECTED, isCollected)
                .putExtra(EXTRA_COLL_BOOK, collBook));
    }

    @Override
    protected ReadContract.Presenter bindPresenter() {
        return new ReadContractImpl();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_read;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mCollBook = (BookInfo) getIntent().getSerializableExtra(EXTRA_COLL_BOOK);
        isCollected = getIntent().getBooleanExtra(EXTRA_IS_COLLECTED, false);

        TimerUtil.startTimer();
        startCountDown();
        startRecordTimer();
    }

    /**
     * 停止时间倒计时
     */
    private void startCountDown(){
        if(mTimer == null){
            mTimer = new CountDownTimer(countDown, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    LogUtil.i("CountDownTimer", "==="+millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    LogUtil.i("CountDownTimer", "倒计时结束");
                    TimerUtil.pauseTimer();
                }
            };
        }
        TimerUtil.continueTimer();
        mTimer.cancel();
        mTimer.start();
    }

    /**
     * 保存时间倒计时
     */
    private void startRecordTimer(){
        mRecordTimer = new CountDownTimer(countDown, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                LogUtil.i(TAG, "固定时间保存");
                saveReadTime();
                mRecordTimer.start();
            }
        };
        mRecordTimer.start();
    }

    private void stopTimer(){
        LogUtil.i(TAG, "结束并保存");
        saveReadTime();

        mTimer.cancel();
        mRecordTimer.cancel();
        TimerUtil.stopTimer();
    }

    private void saveReadTime(){
        if(TimerUtil.getCountDown()>0){
            TimeRecord record = new TimeRecord(mBookId, TimerUtil.getCountDown(), System.currentTimeMillis(), false);
            BoxStoreHelper.getInstance().put(TimeRecord.class, record);
            mPresenter.uploadReadTime();
            TimerUtil.resetCount();
        }
    }


    @Override
    protected void initData() {
        super.initData();
        isNightMode = ReadSettingManager.isNightMode();
        isFullScreen = ReadSettingManager.isFullScreen();
        mBookId = mCollBook.getId();

        setUpToolbar();
        initWidget();
        initClick();
        processLogic();
    }

    protected void setUpToolbar() {
//        Toolbar mToolbar = ButterKnife.findById(this, R.id.toolbar);
//        if (mToolbar != null){
//            supportActionBar(mToolbar);
//        }
//        //设置标题
//        toolbar.setTitle(mCollBook.getName());
//        //半透明化StatusBar
//        SystemBarUtils.transparentStatusBar(this);
    }

    protected void initWidget() {

        // 如果 API < 18 取消硬件加速
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2
                && Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            mPvPage.setLayerType(LAYER_TYPE_SOFTWARE, null);
        }

        //获取页面加载器
        mPageLoader = mPvPage.getPageLoader(mCollBook);
        //禁止滑动展示DrawerLayout
        mDlSlide.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //侧边打开后，返回键能够起作用
        mDlSlide.setFocusableInTouchMode(false);
        mSettingDialog = new ReadSettingDialog(this, mPageLoader);

        setUpAdapter();

        //夜间模式按钮的状态
        toggleNightMode();

        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        registerReceiver(mReceiver, intentFilter);

        //设置当前Activity的Brightness
        if (ReadSettingManager.isBrightnessAuto()) {
            BrightnessUtils.setDefaultBrightness(this);
        } else {
            BrightnessUtils.setBrightness(this, ReadSettingManager.getBrightness());
        }

        //初始化屏幕常亮类
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "keep bright");

        //隐藏StatusBar
        mPvPage.post(
                () -> hideSystemBar()
        );

        //初始化TopMenu
        initTopMenu();

        //初始化BottomMenu
        initBottomMenu();
    }

    private void initTopMenu() {
        if (Build.VERSION.SDK_INT >= 19) {
            mAblTopMenu.setPadding(0, ScreenUtils.getStatusBarHeight(mContext), 0, 0);
        }
    }

    private void initBottomMenu() {
        //判断是否全屏
        if (ReadSettingManager.isFullScreen()) {
            //还需要设置mBottomMenu的底部高度
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLlBottomMenu.getLayoutParams();
            params.bottomMargin = ScreenUtils.getNavigationBarHeight();
            mLlBottomMenu.setLayoutParams(params);
        } else {
            //设置mBottomMenu的底部距离
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) mLlBottomMenu.getLayoutParams();
            params.bottomMargin = 0;
            mLlBottomMenu.setLayoutParams(params);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged: " + mAblTopMenu.getMeasuredHeight());
    }

    private void toggleNightMode() {
        if (isNightMode) {
//            mTvNightMode.setText(getString(R.string.nb_mode_morning));
            Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_read_menu_morning);
            mTvNightMode.setImageDrawable(drawable);
        } else {
//            mTvNightMode.setText(getString(R.string.nb_mode_night));
            Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.ic_read_menu_night);
            mTvNightMode.setImageDrawable(drawable);
        }
    }

    private void setUpAdapter() {
        chapterAdapter = new ChapterAdapter(chapterList);
        mLvCategory.setAdapter(chapterAdapter);
    }

    // 注册亮度观察者
    private void registerBrightObserver() {
        try {
            if (mBrightObserver != null) {
                if (!isRegistered) {
                    final ContentResolver cr = getContentResolver();
                    cr.unregisterContentObserver(mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_MODE_URI, false, mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_URI, false, mBrightObserver);
                    cr.registerContentObserver(BRIGHTNESS_ADJ_URI, false, mBrightObserver);
                    isRegistered = true;
                }
            }
        } catch (Throwable throwable) {
            LogUtils.e(TAG, "register mBrightObserver error! " + throwable);
        }
    }

    //解注册
    private void unregisterBrightObserver() {
        try {
            if (mBrightObserver != null) {
                if (isRegistered) {
                    getContentResolver().unregisterContentObserver(mBrightObserver);
                    isRegistered = false;
                }
            }
        } catch (Throwable throwable) {
            LogUtils.e(TAG, "unregister BrightnessObserver error! " + throwable);
        }
    }

    protected void initClick() {
        mPageLoader.setOnPageChangeListener(
                new PageLoader.OnPageChangeListener() {

                    @Override
                    public void onChapterChange(int pos) {
                        chapterAdapter.setChapter(pos);
                    }

                    @Override
                    public void requestChapters(List<BookChapterInfo> requestChapters) {
                        mPresenter.loadChapter(mBookId, requestChapters);
                        mHandler.sendEmptyMessage(WHAT_CATEGORY);
                        //隐藏提示
                        mTvPageTip.setVisibility(GONE);
                    }

                    @Override
                    public void onCategoryFinish(List<BookChapterInfo> chapters) {
                        for (BookChapterInfo chapter : chapters) {
                            chapter.setTitle(StringUtils.convertCC(chapter.getTitle(), mPvPage.getContext()));
                        }
                        chapterList.addAll(chapters);
                        chapterAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onPageCountChange(int count) {
                        mSbChapterProgress.setMax(Math.max(0, count - 1));
                        mSbChapterProgress.setProgress(0);
                        // 如果处于错误状态，那么就冻结使用
                        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING
                                || mPageLoader.getPageStatus() == PageLoader.STATUS_ERROR) {
                            mSbChapterProgress.setEnabled(false);
                        }
                        else {
                            mSbChapterProgress.setEnabled(true);
                        }
                    }

                    @Override
                    public void onPageChange(int pos) {
                        startCountDown();

                        mSbChapterProgress.post(
                                () -> mSbChapterProgress.setProgress(pos)
                        );
                    }
                }
        );

        mSbChapterProgress.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (mLlBottomMenu.getVisibility() == VISIBLE) {
                            //显示标题
                            mTvPageTip.setText((progress + 1) + "/" + (mSbChapterProgress.getMax() + 1));
                            mTvPageTip.setVisibility(VISIBLE);
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        //进行切换
                        int pagePos = mSbChapterProgress.getProgress();
                        if (pagePos != mPageLoader.getPagePos()) {
                            mPageLoader.skipToPage(pagePos);
                        }
                        //隐藏提示
                        mTvPageTip.setVisibility(GONE);
                    }
                }
        );

        mPvPage.setTouchListener(new PageView.TouchListener() {
            @Override
            public boolean onTouch() {
                return !hideReadMenu();
            }

            @Override
            public void center() {
                toggleMenu(true);
            }

            @Override
            public void prePage() {
            }

            @Override
            public void nextPage() {
            }

            @Override
            public void cancel() {
            }
        });

        chapterAdapter.setOnItemClickListener((adapter, view, position) -> {
                    mDlSlide.closeDrawer(Gravity.START);
                    mPageLoader.skipToChapter(position);
                }
        );

        mTvCategory.setOnClickListener(
                (v) -> {
                    //移动到指定位置
                    if (chapterAdapter.getData().size() > 0) {
                        mLvCategory.scrollToPosition(mPageLoader.getChapterPos());
                    }
                    //切换菜单
                    toggleMenu(true);
                    //打开侧滑动栏
                    mDlSlide.openDrawer(Gravity.START);
                }
        );
        mTvSetting.setOnClickListener(
                (v) -> {
                    toggleMenu(false);
                    mSettingDialog.show();
                }
        );

        mTvDownload.setOnClickListener(v -> {
            DownloadDialog downloadDialog = new DownloadDialog(mContext, chapterList, new DownloadDialog.OnViewClick() {
                @Override
                public void onConfirm() {
                    ToastUtil.showSingleToast("缓存成功");
                    chapterAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancle() {
                    chapterAdapter.notifyDataSetChanged();
                    ToastUtil.showSingleToast("缓存停止");
                }
            });
            downloadDialog.show();
        });

        mTvPreChapter.setOnClickListener(
                (v) -> {
                    if (mPageLoader.skipPreChapter()) {
                        chapterAdapter.setChapter(mPageLoader.getChapterPos());
                    }
                }
        );

        mTvNextChapter.setOnClickListener(
                (v) -> {
                    if (mPageLoader.skipNextChapter()) {
                        chapterAdapter.setChapter(mPageLoader.getChapterPos());
                    }
                }
        );

        mTvNightMode.setOnClickListener(
                (v) -> {
                    if (isNightMode) {
                        isNightMode = false;
                    } else {
                        isNightMode = true;
                    }
                    mPageLoader.setNightMode(isNightMode);
                    toggleNightMode();
                }
        );

        mTvBrief.setOnClickListener(
                (v) -> BookDetailActivity.start(this, mBookId)
        );

        mTvCommunity.setOnClickListener(
                (v) -> {
                    //TODO 简介
//                    Intent intent = new Intent(this, CommunityActivity.class);
//                    startActivity(intent);
                }
        );

        mSettingDialog.setOnDismissListener(
                dialog -> hideSystemBar()
        );
    }

    /**
     * 隐藏阅读界面的菜单显示
     *
     * @return 是否隐藏成功
     */
    private boolean hideReadMenu() {
        hideSystemBar();
        if (mAblTopMenu.getVisibility() == VISIBLE) {
            toggleMenu(true);
            return true;
        } else if (mSettingDialog.isShowing()) {
            mSettingDialog.dismiss();
            return true;
        }
        return false;
    }

    private void showSystemBar() {
        //显示
        SystemBarUtils.showUnStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.showUnStableNavBar(this);
        }
    }

    private void hideSystemBar() {
        //隐藏
        SystemBarUtils.hideStableStatusBar(this);
        if (isFullScreen) {
            SystemBarUtils.hideStableNavBar(this);
        }
    }

    /**
     * 切换菜单栏的可视状态
     * 默认是隐藏的
     */
    private void toggleMenu(boolean hideStatusBar) {
        initMenuAnim();

        if (mAblTopMenu.getVisibility() == View.VISIBLE) {
            //关闭
            mAblTopMenu.startAnimation(mTopOutAnim);
            mLlBottomMenu.startAnimation(mBottomOutAnim);
            mAblTopMenu.setVisibility(GONE);
            mLlBottomMenu.setVisibility(GONE);
            mTvPageTip.setVisibility(GONE);

            if (hideStatusBar) {
                hideSystemBar();
            }
        } else {
            mAblTopMenu.setVisibility(View.VISIBLE);
            mLlBottomMenu.setVisibility(View.VISIBLE);
            mAblTopMenu.startAnimation(mTopInAnim);
            mLlBottomMenu.startAnimation(mBottomInAnim);

            showSystemBar();
        }
    }

    //初始化菜单动画
    private void initMenuAnim() {
        if (mTopInAnim != null) return;

        mTopInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_in);
        mTopOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_top_out);
        mBottomInAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_in);
        mBottomOutAnim = AnimationUtils.loadAnimation(this, R.anim.slide_bottom_out);
        //退出的速度要快
        mTopOutAnim.setDuration(200);
        mBottomOutAnim.setDuration(200);
    }

    protected void processLogic() {
        // 如果是已经收藏的，那么就从数据库中获取目录
        if (isCollected) {

        } else {
            // 从网络中获取目录
            mPresenter.loadCategory(mBookId);
        }
    }

    /***************************view************************************/
    @Override
    public void showError() {

    }

    @Override
    public void finishChapter() {
        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
            mHandler.sendEmptyMessage(WHAT_CHAPTER);
        }
        // 当完成章节的时候，刷新列表
        chapterAdapter.notifyDataSetChanged();
    }

    @Override
    public void errorChapter() {
        if (mPageLoader.getPageStatus() == PageLoader.STATUS_LOADING) {
            mPageLoader.chapterError();
        }
    }

    @Override
    public void onBackPressed() {
        if (mAblTopMenu.getVisibility() == View.VISIBLE) {
            // 非全屏下才收缩，全屏下直接退出
            if (!ReadSettingManager.isFullScreen()) {
                toggleMenu(true);
                return;
            }
        } else if (mSettingDialog.isShowing()) {
            mSettingDialog.dismiss();
            return;
        } else if (mDlSlide.isDrawerOpen(Gravity.START)) {
            mDlSlide.closeDrawer(Gravity.START);
            return;
        }

        //上传进度
        mPresenter.updateShelf(mBookId, mPageLoader.getChapterPos(), mPageLoader.getPagePos());
        exit();
//        if (!mCollBook.isLocal() && !isCollected) {
//            AlertDialog alertDialog = new AlertDialog.Builder(this)
//                    .setTitle("加入书架")
//                    .setMessage("喜欢本书就加入书架吧")
//                    .setPositiveButton("确定", (dialog, which) -> {
//                        //设置为已收藏
//                        isCollected = true;
//                        if(BookQuery.queryBook(mCollBook.getId()) == null){
//                            //设置添加时间
//                            mCollBook.setLastReadTime(System.currentTimeMillis());
//                            mCollBook.setLocal(true);
//                            BoxStoreHelper.getInstance().put(BookInfo.class, mCollBook);
//                        }
//                        exit();
//                    })
//                    .setNegativeButton("取消", (dialog, which) -> {
//                        exit();
//                    }).create();
//            alertDialog.show();
//        } else {
//            exit();
//        }
    }

    // 退出
    private void exit() {
        // 返回给BookDetail。
//        Intent result = new Intent();
//        result.putExtra(BookDetailActivity.RESULT_IS_COLLECTED, isCollected);
//        setResult(Activity.RESULT_OK, result);
        // 退出
        super.onBackPressed();
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerBrightObserver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWakeLock.acquire();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWakeLock.release();
        mPageLoader.saveRecord();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterBrightObserver();
    }


    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);

        mHandler.removeMessages(WHAT_CATEGORY);
        mHandler.removeMessages(WHAT_CHAPTER);

        mPageLoader.closeBook();
        mPageLoader = null;

        stopTimer();

        super.onDestroy();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isVolumeTurnPage = ReadSettingManager
                .isVolumeTurnPage();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToPrePage();
                }
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (isVolumeTurnPage) {
                    return mPageLoader.skipToNextPage();
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        SystemBarUtils.hideStableStatusBar(this);
        if (requestCode == REQUEST_MORE_SETTING) {
            boolean fullScreen = ReadSettingManager.isFullScreen();
            if (isFullScreen != fullScreen) {
                isFullScreen = fullScreen;
                // 刷新BottomMenu
                initBottomMenu();
            }

            // 设置显示状态
            if (isFullScreen) {
                SystemBarUtils.hideStableNavBar(this);
            } else {
                SystemBarUtils.showStableNavBar(this);
            }
        }
    }

    @Override
    public void showCategory(List<BookChapterInfo> bookChapterList) {
//        mPageLoader.getCollBook().setBookChapters(bookChapters);
        mPageLoader.refreshChapterList(bookChapterList);
//
//        // 如果是目录更新的情况，那么就需要存储更新数据
//        if (mCollBook.isUpdate() && isCollected) {
//            BookRepository.getInstance()
//                    .saveBookChaptersWithAsync(bookChapters);
//        }
    }
}
