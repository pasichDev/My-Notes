package io.github.scrollmanageotherview;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_MOVE;
import static android.view.MotionEvent.ACTION_UP;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;


public class ScrollManageView extends ScrollView {

    /**
     * Constant that determines the animation time if not set by the user
     */
    private final int DURATION_TRANSLATE = 300;
    /**
     * A constant that specifies the prefix by which the shift length should be multiplied
     */
    private final float PREFIX = 1.10F;

    private View mDependence;
    private int orientationHideDependence;
    private boolean mHideActionPanel = false;
    private int durationAnimate = DURATION_TRANSLATE;
    private int dependenceResourceId;
    private float startDependenceX;
    private float startDependenceY;
    private int displayWidth;
    private int displayHeight;
    private int actionFlag = 0;

    public ScrollManageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public ScrollManageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.ScrollManageView);
        dependenceResourceId = t.getResourceId(R.styleable.ScrollManageView_id_dependence, 0);
        durationAnimate = t.getInteger(R.styleable.ScrollManageView_durationAnimate, DURATION_TRANSLATE);
        orientationHideDependence = t.getInteger(R.styleable.ScrollManageView_orientationHideDependence, OrientationHideDependence.HORIZONTAL);
        t.recycle();

        displayWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
        displayHeight = Resources.getSystem().getDisplayMetrics().heightPixels;
    }


    private void updateDependenceParam() {
        startDependenceX = mDependence.getX();
        startDependenceY = mDependence.getY();
    }


    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (dependenceResourceId != 0) {
            mDependence = getRootView().findViewById(dependenceResourceId);
            mDependence.getViewTreeObserver().addOnGlobalLayoutListener(this::updateDependenceParam);

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
            if (!mHideActionPanel && actionFlag >= 14) hideView();
        }


        if (action == ACTION_UP || action == ACTION_CANCEL) {
            actionFlag = 0;
            if (mHideActionPanel) showView();
        }

    }


    private void hideView() {

        if (orientationHideDependence == OrientationHideDependence.HORIZONTAL)
            mDependence.animate().x(getTranslateDependenceX()).setDuration(durationAnimate).start();
        else
            mDependence.animate().y(getTranslateDependenceY()).setDuration(durationAnimate).start();

        mHideActionPanel = true;

    }

    private void showView() {
        if (orientationHideDependence == OrientationHideDependence.HORIZONTAL)
            mDependence.animate().x(startDependenceX).setDuration(durationAnimate).start();
        else mDependence.animate().y(startDependenceY).setDuration(durationAnimate).start();

        mHideActionPanel = false;
    }


    private float getTranslateDependenceX() {
        if (getLocationDependence() == 1)
            return (startDependenceX + ((displayWidth - mDependence.getWidth()) * PREFIX));
        else return (startDependenceX - ((displayWidth - mDependence.getWidth()) * PREFIX));
    }

    private float getTranslateDependenceY() {
        if (getLocationDependence() == 1)
            return (startDependenceY + ((displayHeight - mDependence.getHeight()) * PREFIX));
        else return (startDependenceY - ((displayHeight - mDependence.getHeight()) * PREFIX));
    }


    /**
     * Method that determines in which direction to move the dependency
     * 1 - right, bottom
     * 0 - left, top
     *
     * @return - the side in which we move the object
     */

    private int getLocationDependence() {
        int widthDisplayCenter = displayWidth / 2;
        int heightDisplayCenter = displayHeight / 2;

        if (orientationHideDependence == 0) {
            if (startDependenceX > widthDisplayCenter) return 1;
            else return 0;
        } else {
            if (startDependenceY > heightDisplayCenter) return 1;
            else return 0;
        }
    }

    /**
     * Method for setting animation duration
     *
     * @param duration - duration (int < 10000)
     */
    public void setDurationAnimate(int duration) {
        this.durationAnimate = duration > 10000 ? 9999 : duration;
    }

    /**
     * Method that allows you to set the type of sliding
     *
     * @return true - horizontal scroll / false vertical scroll
     */

    public int getOrientationHideDependence() {
        return orientationHideDependence;
    }

    /**
     * Method that allows you to set the type of sliding horizontal (0) / vertical (1)
     *
     * @param orientationHideDependence - parameter
     */
    public void setOrientationHideDependence(int orientationHideDependence) {
        this.orientationHideDependence = orientationHideDependence > 1 ? OrientationHideDependence.HORIZONTAL : orientationHideDependence;
    }
}
