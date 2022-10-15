package com.pasich.mynotes.utils.override;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

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
    private float startDependenceX;

    private int actionFlag = 0;

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
            mDependence.getViewTreeObserver().addOnGlobalLayoutListener(() -> startDependenceX = mDependence.getX());

        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDependence = null;
        dependenceResourceId = 0;
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mDependence != null) {
            commOnTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void commOnTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        actionFlag = actionFlag + action;
        if (action == ACTION_DOWN || action == ACTION_MOVE) {
            if (!mHideActionPanel && actionFlag >= 12) hideView();
        }


        if (action == ACTION_UP || action == ACTION_CANCEL) {
            actionFlag = 0;
            if (mHideActionPanel) showView();
        }

    }


    private void hideView() {
        mDependence.animate().x((startDependenceX + (mDependence.getWidth() * prefixWidth))).setDuration(durationAnimation).start();
        mHideActionPanel = true;

    }

    private void showView() {
        mDependence.animate().x((startDependenceX)).setDuration(durationAnimation).start();
        mHideActionPanel = false;
    }


}
