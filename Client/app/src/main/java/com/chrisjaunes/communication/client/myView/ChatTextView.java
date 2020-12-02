package com.chrisjaunes.communication.client.myView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.chrisjaunes.communication.client.R;
import com.chrisjaunes.communication.client.account.ChatTextStyle;

public class ChatTextView extends View {
    // textwidth
    public final static int textWidth = 600;
    public final static int padding = 15;
    public final static int borderWidth = 15;
    public final static int line_space = 3;
    //三角信息
    public final static int triangleWidth = 30;
    public final static int triangleHeight = 40;

    private int width;
    private int height;
    //圆角半径
    private float radius;
    //左边
    private Boolean is_left;
    // 内容
    private String text;
    //字体大小
    private int text_size;
    // 外面边框画笔
    private Paint paintOut;
    // 里面画笔
    private Paint paintIn;
    // 文字画笔
    private Paint paintText;

    int bubble_color;
    int border_color;
    int text_color;

    int signleLineHeight;

    public ChatTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Log.d("ChatTextView","create view");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ChatTextView);
        radius = typedArray.getFloat(R.styleable.ChatTextView_border_radius,0);
        is_left = typedArray.getBoolean(R.styleable.ChatTextView_is_left,true);

        text = typedArray.getString(R.styleable.ChatTextView_mtext);

        text_size = typedArray.getDimensionPixelSize(R.styleable.ChatTextView_mtextSize,5);
        bubble_color = typedArray.getColor(R.styleable.ChatTextView_mbackground_color,Color.BLUE);
        border_color = typedArray.getColor(R.styleable.ChatTextView_mborder_color,Color.GREEN);
        text_color = typedArray.getColor(R.styleable.ChatTextView_mtext_color,Color.BLACK);

        typedArray.recycle();
        paintOut = new Paint();

        //设置抗锯齿
        paintOut.setAntiAlias(true);
        //设置填充
        paintOut.setStyle(Paint.Style.FILL_AND_STROKE);
        //设置防抖动
        paintOut.setDither(true);

        paintIn = new Paint();

        //设置抗锯齿
        paintIn.setAntiAlias(true);
        //设置填充
        paintIn.setStyle(Paint.Style.FILL_AND_STROKE);
        //设置防抖动
        paintIn.setDither(true);

        paintText = new Paint();

        paintText.setTextSize(text_size);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if( text == null ){
            setMeasuredDimension(0,0);
            return;
        }
        Log.d("ChatTextView","now text: " + text);
        //获得当前view的宽高限制的类型
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        width = calcWidth();
        height = calcHeight();
        Log.d("ChatTextView","measure: width" + width + " height: " + height);
        setMeasuredDimension(width,height);
    }

    @Override
    protected  void onDraw(Canvas canvas){
        paintIn.setColor(bubble_color);
        paintText.setColor(text_color);
        paintOut.setColor(border_color);
        if( text == null ) {
            super.onDraw(canvas);
            return;
        }
        Log.d("ChatTextView","onDraw");
        Log.d("ChatTextView","text: " + text);
        Log.d("ChatTextView","draw: width" + width + " height: " + height);
        RectF rectF = new RectF(0,0,width,height);
        if( is_left){
            // 画外矩形
            rectF = new RectF(triangleWidth, 0, width, height);
            canvas.drawRoundRect(rectF,radius,radius,paintOut);
            //画三角形
            Path path = new Path();
            path.moveTo(triangleWidth,height/2 - triangleHeight/2);
            path.lineTo(0,height/2);
            path.lineTo(triangleWidth,height/2 + triangleHeight/2);
            path.close();
            canvas.drawPath(path,paintOut);

            //画内矩形
            rectF = new RectF(triangleWidth + borderWidth,borderWidth,width-borderWidth,height-borderWidth);
        }
        else{
            //画外面矩形
            rectF = new RectF(0, 0, width - triangleWidth, height);
            canvas.drawRoundRect(rectF,radius,radius,paintOut);
            // 画三角形
            Path path = new Path();
            path.moveTo(width-triangleWidth,height/2 - triangleHeight/2);
            path.lineTo(width,height/2);
            path.lineTo(width-triangleWidth,height/2 + triangleHeight/2);
            path.close();
            canvas.drawPath(path,paintOut);

            //画内矩形
            rectF = new RectF(borderWidth,borderWidth,width-triangleWidth-borderWidth,height-borderWidth);
        }
        canvas.drawRect(rectF,paintIn);
//        canvas.drawRoundRect(rectF,radius,radius,paintIn);
        drawText(canvas);
        super.onDraw(canvas);
    }

    private void drawText(Canvas canvas){
        Log.d("ChatTextView"," drawText: " + text);
        Log.d("ChatTextView","width: " + width + " height: " + height);
        char[] textChars= text.toCharArray();
        float ww=0.0f;
        float startL=0.0f;
        float startT=0.0f;
        int limt;
        if(is_left){
            startL += triangleWidth + borderWidth + padding;
            limt = width - borderWidth - padding;
        }
        else{
            startL += borderWidth + padding;
            limt = width - triangleWidth - borderWidth - padding;
        }
        startT+= borderWidth + signleLineHeight ;
        StringBuilder sb=new StringBuilder();

        for(int i=0;i<textChars.length;i++){
            float v = paintText.measureText(textChars[i] + "");
            if(ww+v<=limt){
                sb.append(textChars[i]);
                ww+=v;
            }
            else{
                canvas.drawText(sb.toString(),startL,startT,paintText);
                startT+=signleLineHeight+line_space;
                sb=new StringBuilder();
                ww=0.0f;
                ww+=v;
                sb.append(textChars[i]);
            }
        }

        if(sb.toString().length()>0){
            canvas.drawText(sb.toString(),startL,startT,paintText);
        }

    }

    private int calcWidth(){
        double textLen = paintText.measureText(text);
        if( textLen < textWidth ){
            return (int)(textLen + 2*padding + borderWidth*2 + triangleWidth);
        }
        else{
            return (int)(textWidth + 2*padding + borderWidth*2 + triangleWidth);
        }
    }

    private int calcHeight(){
        Paint.FontMetricsInt textFm = paintText.getFontMetricsInt();
        signleLineHeight = Math.abs(textFm.top-textFm.bottom);

        double textLen = paintText.measureText(text);
        int cols = (int)(textLen/textWidth) + 1 ;

        return (signleLineHeight + line_space) * cols + padding*2 + borderWidth * 2;
    }

    // 给外面接口，更改text
    public void setMyText(String text){
        this.text = text;
        invalidate();
    }

    // 更改颜色
    public void setMyColor(int text_color,int border_color,int background_color){
        this.text_color = text_color;
        this.border_color = border_color;
        this.bubble_color = background_color;
        invalidate();
    }
    // 更改颜色
    public void setMyColor(ChatTextStyle chatTextStyle){
        this.text_color = Color.parseColor(chatTextStyle.font_color);
        this.border_color = Color.parseColor(chatTextStyle.border_color);
        this.bubble_color = Color.parseColor(chatTextStyle.bubble_color);
        Log.d("TalkAdapterX", "" + this.text_color);
        invalidate();
    }
}
