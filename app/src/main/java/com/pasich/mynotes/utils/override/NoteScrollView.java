package com.pasich.mynotes.utils.override;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.pasich.mynotes.R;

public class NoteScrollView extends ScrollView {

    private final int durationAnimation = 200;
    private final float prefixWidth = 1.25F;
    private View mDependence;
    private boolean mHideActionPanel = false;
    private int dependenceResourceId;


    public NoteScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public NoteScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.NoteScrollView);
        dependenceResourceId = t.getResourceId(R.styleable.NoteScrollView_id_dependence, 0);
        t.recycle();
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (dependenceResourceId != 0) {
            mDependence = getRootView().findViewById(dependenceResourceId);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDependence = null;
        dependenceResourceId = 0;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mDependence != null) {
            commOnTouchEvent(ev);
        }

        return super.onTouchEvent(ev);
    }


    public void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        if (action == ACTION_DOWN || action == ACTION_MOVE) {
            if (!mHideActionPanel) hideView();
        }
        if (action == ACTION_UP || action == ACTION_CANCEL) {
            if (mHideActionPanel) showView();
        }

    }


    private void hideView() {
        mDependence.animate().x((mDependence.getX() + (mDependence.getWidth() * prefixWidth))).setDuration(durationAnimation).start();
        mHideActionPanel = true;

    }

    private void showView() {
        mDependence.animate().x((mDependence.getX() - mDependence.getWidth() * prefixWidth)).setDuration(durationAnimation).start();
        mHideActionPanel = false;
    }


}
