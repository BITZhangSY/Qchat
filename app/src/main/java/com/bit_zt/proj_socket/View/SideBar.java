package com.bit_zt.proj_socket.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.bit_zt.proj_socket.R;

/**
 * Created by bit_zt on 15/11/11.
 */
public class SideBar extends View {

    public static String[] sideContentLetter = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#" };

    private boolean mIsPress;// 是否按压状态
    private int choose = -1;// 选中标识
    private Paint paint = new Paint();// 画笔，提供绘制单元的属性信息集合

    private TextView mTextDialog;

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    public void setTextView(TextView mTextDialog) {
        this.mTextDialog = mTextDialog;
    }


    public SideBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context) {
        super(context);
    }


    /*
    * 重写该方法，绘制控件样式
    * */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //返回view宽高，单位为px
        int height = getHeight();
        int width = getWidth();

        int singleHeight = height / sideContentLetter.length;// 获得每个字符所占的高度

        if(mIsPress){        // 处于按压态
            for(int i = 0; i < sideContentLetter.length; i++){
                paint.setColor(Color.parseColor("black"));// 每个单元的颜色
                paint.setTypeface(Typeface.DEFAULT_BOLD);// 单元字体为粗体
                paint.setAntiAlias(true);
                paint.setTextSize(45);// 字体大小
                // 如果该单元是被按压的单元
                if(i == choose){
                    paint.setColor(Color.parseColor("white"));
                    paint.setFakeBoldText(true);
                }
                // (xPos,yPos)表示绘制文本的原点, paint为画笔属性集合
                float xPos = width / 2 - paint.measureText(sideContentLetter[i]) / 2;
                float yPos = singleHeight * i + singleHeight;
                canvas.drawText(sideContentLetter[i], xPos, yPos, paint);
            }
        }else {             // 处于正常态
            for (int i = 0; i < sideContentLetter.length; i++) {
                paint.setColor(Color.parseColor("#666666"));
                paint.setTypeface(Typeface.DEFAULT);
                paint.setAntiAlias(true);
                paint.setTextSize(35);
                // x坐标等于中间-字符串宽度的一半.
                float xPos = width / 2 - paint.measureText(sideContentLetter[i]) / 2;
                float yPos = singleHeight * i + singleHeight;
                canvas.drawText(sideContentLetter[i], xPos, yPos, paint);
            }
        }
        paint.reset();// 重置画笔
    }

    // 将屏幕捕捉到的动作事件向下传递到目标view
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {

        int action = event.getAction();
        float y = event.getY();
        int oldChoose = choose;
        int indexOfLetterArray_actionHappen = (int) (y / getHeight() * sideContentLetter.length);// 动作发生的区域的字符所对应的数组中的位置

        OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;

        switch (action){
            case MotionEvent.ACTION_UP:// 用户抬起了手指
                choose = -1;// 没有选中的单元
                mIsPress = false;

                // 清楚现有view并重新绘制
                invalidate();
                if(mTextDialog != null){
                    mTextDialog.setVisibility(INVISIBLE);
                }
                break;
            default:
                //不能设置背景，会覆盖掉listview中项的灰色，造成控件没有叠加显示的错觉！！
                //setBackgroundResource(R.drawable.background_sidebar);
                if(oldChoose != indexOfLetterArray_actionHappen){
                    if(indexOfLetterArray_actionHappen > 0 &&
                            indexOfLetterArray_actionHappen < sideContentLetter.length){
                        if(listener != null){
                            listener.onTouchingLetterChanged(
                                    sideContentLetter[indexOfLetterArray_actionHappen]);// 调用实现的方法，传入点击的字符
                        }
                        if(mTextDialog != null){
                            mTextDialog.setText(sideContentLetter[indexOfLetterArray_actionHappen]);
                            mTextDialog.setVisibility(VISIBLE);
                        }

                        choose = indexOfLetterArray_actionHappen;
                        mIsPress = true;
                        invalidate();
                    }
                }
        }

        return true;
    }


    /*
    *  暴露给外部，传入实现的监听接口
    * */
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener) {
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }

    /*
        *  暴露给外部实现的接口
        * */
    public interface OnTouchingLetterChangedListener{
        public void onTouchingLetterChanged(String letter);
    }
}
