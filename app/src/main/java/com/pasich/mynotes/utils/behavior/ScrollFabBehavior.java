package com.pasich.mynotes.utils.behavior;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

public class ScrollFabBehavior extends AppBarLayout.ScrollingViewBehavior {


    /**
     * Наразі основне завдання, знайти метод який повідомляє про завершення та початок скролу
     * і реалізувати схов та показ іконки в актион бар
     *
     * @param context
     * @param attrs
     */


    public ScrollFabBehavior(Context context, AttributeSet attrs) {
        super();
    }

    public ScrollFabBehavior() {
        super();
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, View child, View directTargetChild, View target, int nestedScrollAxes) {

        Log.wtf("pasic", "onStartNestedScroll:");
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {

        Log.wtf("pasic", "onDependentViewChanged:");
        return super.layoutDependsOn(parent, child, dependency);
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {

        Log.wtf("pasic", "onDependentViewChanged:");
        return super.onDependentViewChanged(parent, child, dependency);
    }
}
