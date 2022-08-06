package com.pasich.mynotes.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

class VisualSoundView extends View {
    public VisualSoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Try for a width based on our minimum
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int w = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int minh = MeasureSpec.getSize(w) - getPaddingBottom() + getPaddingTop();
        int h = resolveSizeAndState(minh, heightMeasureSpec, 0);

        setMeasuredDimension(w, h);
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        @SuppressLint("DrawAllocation")
        Paint line1 = new Paint();
        line1.setStrokeWidth(5);

        line1.setColor(Color.BLACK);
        line1.setTextSize(20);
        line1.setAntiAlias(true);


        // Draw the pointer
        canvas.drawLine(100, 100, 100, 250, line1);
        canvas.drawLine(200, 100, 200, 250, line1);
        canvas.drawLine(300, 100, 300, 250, line1);

    }

}