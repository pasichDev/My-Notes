package com.pasich.mynotes.utils.override;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.pasich.mynotes.R;

public class SearchViewNote extends SearchView {

    public SearchViewNote(Context context) {
        super(context);

    }

    public SearchViewNote(Context context, AttributeSet attrs) {
        super(context, attrs);
        customPanelButtons();
    }

    public SearchViewNote(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        customPanelButtons();
    }

    public SearchViewNote(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        customPanelButtons();
    }


    /**
     * A method that adds two buttons to an existing VIew to sort and format the list
     */

    private void customPanelButtons() {
        LinearLayout mSearchView = (LinearLayout) this.getChildAt(0);
        View mSearchViewLayout = View.inflate(getContext(), R.layout.search_view_buttons, null);
        mSearchView.addView(mSearchViewLayout);

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.wtf("pasic", "onTouchEvent: " + event);
        return super.onTouchEvent(event);
    }
}
