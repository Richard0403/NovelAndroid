package com.richard.novel.widget;

/**
 * Created by XiaoU on 2018/12/5.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.richard.novel.common.constant.AppConstant;
import com.richard.novel.common.utils.LogUtil;
import com.richard.novel.common.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * User: mengqingdong
 * Date: 2018/5/21
 * Time: 14:42
 * Description: 小球碰撞
 */

public class BallsCollisionView extends View {
    /**
     * 小球半径 px
     */
    private float radius = 100.0f;
    /**
     * 小球数量
     */
    private int ballCount = 3;
    /**
     * 小球集合
     */
    List<Ball> ballList = new ArrayList<>();
    /** 小球数据 */
    List<BallData> ballDatas = new ArrayList<>();

    public BallsCollisionView(Context context) {
        this(context, null);
    }

    public BallsCollisionView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BallsCollisionView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBallData(List<BallData> ballData) {
        try {
            this.ballList.clear();
            this.ballDatas.clear();
            this.ballDatas.addAll(ballData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initBall(float width, float height) throws Exception{
        ballList = createBalls(width, height,ballDatas);
    }

    /**
     * 初始化碰撞小球，默认随机位置，初始化时小球不可以重叠
     * 如果想生成不同样式的小球需要重写此方法
     *  @param width  view width
     * @param height view height
     * @param ballData
     */
    public List<Ball> createBalls(float width, float height, List<BallData> ballData) throws Exception {
        ballCount = ballData.size();
        radius = ScreenUtils.dpToPx(40);

        float minX = radius;
        float maxX = width - radius;
        float minY = radius;
        float maxY = height - radius;
        List<Ball> balls = new ArrayList<>();

        do {
            float centerX = getRandomIntByRange((int) minX, (int) maxX);
            float centerY = getRandomIntByRange((int) minY, (int) maxY);
            if (!ballIntersectList(radius, centerX, centerY, balls)) {
                BallData data = ballData.remove(0);

                Random rand =new Random();
                Paint paint = createBallPaint();
                paint.setColor(Color.parseColor(AppConstant.randomColor[rand.nextInt(AppConstant.randomColor.length)]));

                Ball ball = new Ball(radius, centerX, centerY, paint, data);
                balls.add(ball);
            }
        } while (balls.size() < ballCount);
        return balls;
    }

    private Paint createBallPaint() {
        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#55943a21"));
        return paint;
    }

    /**
     * 判断当前要生成的小球是否与其他球相交
     *
     * @param radius   要生成小球的半径
     * @param centerX  要生成小球圆心 x 坐标
     * @param centerY  要生成小球圆心 y 坐标
     * @param ballList 被比较小球集合
     * @return 有一个相交返回true
     */
    private boolean ballIntersectList(float radius, float centerX, float centerY, List<Ball> ballList) {
        for (Ball temp : ballList) {
            if (ballIntersect(radius, centerX, centerY, temp)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取指定范围内整数 rangeStart要小于rangeEnd
     *
     * @param rangeStart 整数开始值
     * @param rangeEnd   整数结束值
     */
    private int getRandomIntByRange(int rangeStart, int rangeEnd) throws Exception {
        if (rangeStart >= rangeEnd) {
            throw new Exception("rangeStart should less then rangeEnd");
        }
        Random random = new Random();
        return random.nextInt(rangeEnd) % (rangeEnd - rangeStart + 1) + rangeStart;
    }

    /**
     * 判断2个小球是否相交
     *
     * @param ball    第一个小球
     * @param radius  第二个小球的半径
     * @param centerX 第二个小球圆心 x 坐标
     * @param centerY 第二个小球圆心 y 坐标
     * @return 相交返回true
     */
    private boolean ballIntersect(float radius, float centerX, float centerY, Ball ball) {
        double dx = centerX - ball.centerX;
        double dy = centerY - ball.centerY;
        double dist = Math.sqrt(dx * dx + dy * dy);
        return ball.radius + radius > dist;
    }

    /**
     * 判断2个小球是否相交
     *
     * @param ball1 第一个小球
     * @param ball2 第二个小球
     * @return 相交返回true
     */
    private boolean ballIntersect(Ball ball1, Ball ball2) {
        double dx = ball2.centerX - ball1.centerX;
        double dy = ball2.centerY - ball1.centerY;
        double dist = Math.sqrt(dx * dx + dy * dy);
        return ball1.radius + ball2.radius > dist;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (ballList.size() == 0) {
            try {
                initBall(getWidth(), getHeight());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        hitBall();
        for (Ball ball : ballList) {
            ball.calculateNextPositionOfBall();
            ball.calculateBallIsOutOfBounds(getWidth(), getHeight());
        }
        for (Ball ball : ballList) {
            ball.draw(canvas);
        }
        postInvalidate();
    }

    /**
     * 一个球与当前球碰撞
     */
    public void hitBall() {
        int num = ballList.size();
        for (int i = 0; i < num; i++) {
            Ball ball1 = ballList.get(i);
            for (int j = i + 1; j < num; j++) {
                Ball ball2 = ballList.get(j);
                double dx = ball2.centerX - ball1.centerX;
                double dy = ball2.centerY - ball1.centerY;
                double dist = Math.sqrt(dx * dx + dy * dy);
                double misDist = ball1.radius + ball2.radius;
                if (dist < misDist) {
                    double angle = Math.atan2(dy, dx);
                    double tx = ball1.centerX + Math.cos(angle) * misDist;
                    double ty = ball1.centerY + Math.sin(angle) * misDist;
                    double ax = (tx - ball2.centerX) * ball1.spring;
                    double ay = (ty - ball2.centerY) * ball1.spring;
                    ball1.vx -= ax;
                    ball1.vy -= ay;
                    ball2.vx += ax;
                    ball2.vy += ay;
                }
            }
        }
    }

    public List<Ball> getBallList(){
        return ballList;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
            for (int i = 0; i < ballList.size(); i++) {
                Ball ball = ballList.get(i);
                //点击位置x坐标与圆心的x坐标的距离
                int distanceX = (int) Math.abs(ball.centerX - x);
                //点击位置y坐标与圆心的y坐标的距离
                int distanceY = (int) Math.abs(ball.centerY - y);
                //点击位置与圆心的直线距离
                int distanceZ = (int) Math.sqrt(Math.pow(distanceX, 2) + Math.pow(distanceY, 2));
                if (distanceZ <= ball.radius && ballClickListener!=null) {
                    LogUtil.e("ball---->", ball.ballData.getContent());
                    ballClickListener.getTaskId(ball.ballData.getType());
                }
            }
        }
        return true;
    }




    public interface BallViewListener {
        void getTaskId(int taskId);
    }
    public BallViewListener ballClickListener;

    public void setBallViewListener(BallViewListener listener) {
        ballClickListener = listener;
    }


    public class Ball {
        /**
         * x轴速度 向右为正，向左为负
         */
        public float vx = 2.0f;
        /**
         * y轴速度  向右为正，向左为负
         */
        public float vy = 2.0f;
        /**
         * 圆心x坐标
         */
        public float centerX;
        /**
         * 圆心y坐标
         */
        public float centerY;
        /**
         * 圆半径
         */
        public float radius = 150.0f;
        /**
         * 2个球碰撞的弹性
         */
        public float spring = 1.0f;
        /**
         * 球与边界碰撞的弹性
         */
        public float bounce = -1.0f;
        /**
         * 球的重力
         */
        public float gravity = 0.00f;

        /**
         * paint
         */
        public Paint paint;
        /**
         * 图片bitmap
         */
        public Bitmap bitmap;
        public RectF bitmapRectF;
        private TextPaint tPaint;
        /**
         * 小球的数据
         */
        public BallData ballData;

        public Ball(float radius, float centerX, float centerY) {
            this.radius = radius;
            this.centerX = centerX;
            this.centerY = centerY;
        }

        public Ball(float radius, float centerX, float centerY, Paint paint, BallData data) {
            this.radius = radius;
            this.centerX = centerX;
            this.centerY = centerY;
            this.paint = paint;
            this.ballData = data;
        }

        public Ball(float radius, float centerX, float centerY, Bitmap bitmap, BallData data) {
            this.radius = radius;
            this.centerX = centerX;
            this.centerY = centerY;
            this.bitmap = bitmap;
            this.ballData = data;
        }

        public Paint getPaint() {
            return paint;
        }

        public void setPaint(Paint paint) {
            this.paint = paint;
        }

        /**
         * 优先使用bitmap绘制
         */
        public void draw(Canvas canvas) {
            if(tPaint == null){
                tPaint = new TextPaint();
                tPaint.setAntiAlias(true);
                tPaint.setTextAlign(Paint.Align.CENTER);
                tPaint.setColor(Color.WHITE);
                tPaint.setStyle(Paint.Style.FILL);
                tPaint.setTextSize(ScreenUtils.dpToPx(16));
            }

            if (null == bitmap) {
                canvas.drawCircle(centerX, centerY, radius, paint);

                Rect rect = new Rect((int) (centerX - radius), (int) (centerY - radius), (int) (centerX + radius), (int) (centerY + radius));
                Paint.FontMetrics fontMetrics = tPaint.getFontMetrics();
                float top = fontMetrics.top;//为基线到字体上边框的距离,即上图中的top
                float bottom = fontMetrics.bottom;//为基线到字体下边框的距离,即上图中的bottom
                int baseLineY = (int) (rect.centerY() + top);//基线中间点的y轴计算公式
                StaticLayout layout = new StaticLayout(ballData.getContent(), tPaint, (int) (2 * radius), Layout.Alignment.ALIGN_NORMAL, 1.0F, 0.0F, true);
                canvas.save();
                canvas.translate(centerX, baseLineY);//从100，100开始画
                layout.draw(canvas);
                canvas.restore();//别忘了restore
//                canvas.drawText(ballData.getContent(), centerX, centerY, tPaint);
            } else {
                bitmapRectF = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
                canvas.drawBitmap(bitmap, null, bitmapRectF, null);
            }
        }


        /**
         * 计算小球是否越界，超出边界调整其位置并改变运动方向
         *
         * @param width  边界的宽
         * @param height 边界的高
         */
        public void calculateBallIsOutOfBounds(float width, float height) {
            float edgeDistance = centerX + radius - width;
            if (edgeDistance > 0) {
                //超出右边界
                centerX = width - radius;
                //向左移动
                vx *= bounce;
            }
            edgeDistance = this.centerX - radius;
            if (edgeDistance < 0) {
                //超出左边界
                centerX = radius;
                //向右移动
                vx *= bounce;
            }
            edgeDistance = centerY + radius - height;
            if (edgeDistance > 0) {
                //超出下边界
                centerY = height - radius;
                vy *= bounce;
            }
            edgeDistance = centerY - radius;
            if (edgeDistance < 0) {
                //超出上边界
                centerY = radius;
                vy *= bounce;
            }
        }

        /**
         * 计算小球下一步的位置
         */
        public void calculateNextPositionOfBall() {
            this.vy += gravity;
            this.centerX += vx;
            this.centerY += vy;
        }
    }

    public static class BallData {
        private int type;
        private String content;

        public BallData() {
        }

        public BallData(int type, String content) {
            this.type = type;
            this.content = content;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}