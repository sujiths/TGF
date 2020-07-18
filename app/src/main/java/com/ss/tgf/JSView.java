package com.ss.tgf;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class JSView extends View
{
    private Context mContext;
    private int mHeight;
    private int mWidth;
    private float mTouchX;
    private float mTouchY;
    private int mAction;
    private Vibrator vb;

    JSView(Context c)
    {
        super(c);
        mContext = c;
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        vb = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);

        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;
    }

    void drawJoySticks(int originX, int originY, int Oradius, Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(Color.GRAY);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);
        p.setAntiAlias(true);
        canvas.drawCircle(originX,originY,Oradius , p);
    }




    void drawLeftButtons(int originX, int originY, int Oradius, int Iradius ,Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(Color.GRAY);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);
        p.setAntiAlias(true);
        canvas.drawCircle(originX,originY,Oradius , p);
        canvas.drawCircle(originX,originY + Oradius/2,Iradius, p);

        Paint tp = new Paint();
        tp.setColor(Color.GRAY);
        tp.setStrokeWidth(5);
        tp.setStyle(Paint.Style.STROKE);
        tp.setAntiAlias(true);
        tp.setTextSize(60f);
        Rect r = new Rect();
        tp.getTextBounds("D", 0, 1, r);
        float x = originX - r.width() / 2f - r.left;
        float y = (originY + Oradius/2)  + r.height() / 2f - r.bottom;
        canvas.drawText("D", x,y, tp);

        canvas.drawCircle(originX,originY - Oradius/2,Iradius, p);
        tp.getTextBounds("U", 0, 1, r);
        x = originX - r.width() / 2f - r.left;
        y = (originY - Oradius/2)  + r.height() / 2f - r.bottom;
        canvas.drawText("U", x, y, tp);

        canvas.drawCircle(originX + Oradius/2,originY,Iradius, p);
        tp.getTextBounds("R", 0, 1, r);
        x = (originX + Oradius/2) - r.width() / 2f - r.left;
        y = originY + r.height() / 2f - r.bottom;
        canvas.drawText("R", x , y, tp);

        canvas.drawCircle(originX - Oradius/2,originY,Iradius, p);
        tp.getTextBounds("L", 0, 1, r);
        x = (originX - Oradius/2) - r.width() / 2f - r.left;
        y = originY + r.height() / 2f - r.bottom;
        canvas.drawText("L", x,y, tp);

    }

    void drawRightButtons(int originX, int originY, int Oradius, int Iradius ,Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(Color.GRAY);
        p.setStrokeWidth(5);
        p.setStyle(Paint.Style.STROKE);
        p.setAntiAlias(true);
        canvas.drawCircle(originX,originY,Oradius , p);
        canvas.drawCircle(originX,originY + Oradius/2,Iradius, p);

        Paint tp = new Paint();
        tp.setColor(Color.GREEN);
        tp.setStrokeWidth(5);
        tp.setStyle(Paint.Style.STROKE);
        tp.setAntiAlias(true);
        tp.setTextSize(60f);
        Rect r = new Rect();
        tp.getTextBounds("A", 0, 1, r);
        float x = originX - r.width() / 2f - r.left;
        float y = (originY + Oradius/2)  + r.height() / 2f - r.bottom;
        canvas.drawText("A", x, y, tp);
        canvas.drawCircle(originX,originY - Oradius/2,Iradius, p);
        tp.setColor(Color.YELLOW);

        tp.getTextBounds("Y", 0, 1, r);
        x = originX - r.width() / 2f - r.left;
        y = (originY - Oradius/2)  + r.height() / 2f - r.bottom;
        canvas.drawText("Y", x, y, tp);

        canvas.drawCircle(originX + Oradius/2,originY,Iradius, p);
        tp.setColor(Color.RED);
        tp.getTextBounds("B", 0, 1, r);
        x = (originX + Oradius/2) - r.width() / 2f - r.left;
        y = originY + r.height() / 2f - r.bottom;
        canvas.drawText("B", x, y, tp);

        canvas.drawCircle(originX - Oradius/2,originY,Iradius, p);
        tp.setColor(Color.BLUE);
        tp.getTextBounds("X", 0, 1, r);
        x = (originX - Oradius/2) - r.width() / 2f - r.left;
        y = originY + r.height() / 2f - r.bottom;
        canvas.drawText("X", x, y, tp);

    }

    void drawLeftOverlay(float originX, float originY, int Oradius, Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(Color.GRAY);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setShader(new RadialGradient(mWidth/2 - mWidth/8, mHeight/2 + mHeight/6,
                Oradius, Color.BLACK, Color.DKGRAY, Shader.TileMode.MIRROR));
        // check if inside circle
        if(Math.sqrt( Math.pow(mTouchX - (mWidth/2 - mWidth/8), 2) + Math.pow(mTouchY - (mHeight/2 + mHeight/6), 2)) < Oradius) {
            if(mAction == MotionEvent.ACTION_MOVE) {
                canvas.drawCircle(originX, originY, Oradius, p);
                if (Build.VERSION.SDK_INT >= 26) {
                    vb.vibrate(VibrationEffect.createOneShot(20, 100));
                }
                else {
                    vb.vibrate(20);
                }
            }
            else
                canvas.drawCircle(mWidth / 2 - mWidth / 8, mHeight / 2 + mHeight / 6, Oradius - 2, p);
        }
        else
            canvas.drawCircle(mWidth / 2 - mWidth / 8, mHeight / 2 + mHeight / 6, Oradius - 2, p);

    }


    void drawRightOverlay(float originX, float originY, int Oradius, Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(Color.GRAY);
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setShader(new RadialGradient(mWidth/2 + mWidth/8, mHeight/2 + mHeight/6,
                Oradius, Color.BLACK, Color.DKGRAY, Shader.TileMode.MIRROR));
        // check if inside circle
        if(Math.sqrt( Math.pow(mTouchX - (mWidth/2 + mWidth/8), 2) + Math.pow(mTouchY - (mHeight/2 + mHeight/6), 2)) < Oradius) {
            if(mAction == MotionEvent.ACTION_MOVE) {
                canvas.drawCircle(originX, originY, Oradius, p);
                if (Build.VERSION.SDK_INT >= 26) {
                    vb.vibrate(VibrationEffect.createOneShot(20, 100));
                }
                else {
                    vb.vibrate(20);
                }

            }
            else
                canvas.drawCircle(mWidth / 2 + mWidth / 8, mHeight / 2 + mHeight / 6, Oradius - 2, p);
        }
        else
        {
            canvas.drawCircle(mWidth / 2 + mWidth / 8, mHeight / 2 + mHeight / 6, Oradius - 2, p);
        }
    }

    void drawBackground(Canvas canvas)
    {
        Paint p = new Paint();
        p.setColor(Color.DKGRAY);
        p.setAntiAlias(true);
        canvas.drawRect(new Rect(0,0, mWidth, mHeight), p);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawBackground(canvas);
        drawLeftButtons(mWidth/4,mHeight/2 - mHeight/4, 250, 65,canvas);
        drawRightButtons(mWidth/4 + mWidth/2,mHeight/2 - mHeight/4, 250, 65,canvas);
        drawJoySticks(mWidth/2 - mWidth/8, mHeight/2 + mHeight/6, 150, canvas);
        drawJoySticks(mWidth/2 + mWidth/8, mHeight/2 + mHeight/6, 150, canvas);

        drawLeftOverlay(mTouchX, mTouchY, 150, canvas);
        drawRightOverlay(mTouchX, mTouchY, 150, canvas);

        mTouchX = 0;
        mTouchY = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTouchX = event.getX();
        mTouchY = event.getY();
        mAction = event.getAction();
        invalidate();
        return true;
    }
}
