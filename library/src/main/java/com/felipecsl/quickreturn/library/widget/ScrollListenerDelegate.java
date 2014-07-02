package com.felipecsl.quickreturn.library.widget;

import android.view.View;
import android.widget.AbsListView;

import com.felipecsl.quickreturn.library.adapter.QuickReturnAdapter;

public class ScrollListenerDelegate implements AbsListView.OnScrollListener {

    private final AbsListView listView;
    private final View targetView;

    private QuickReturnViewTranslater quickReturnViewTranslater;

    public static ScrollListenerDelegate newInstance(AbsListView listView, View targetView) {
        return new ScrollListenerDelegate(listView, targetView, new QuickReturnViewTranslater(targetView));
    }

    private ScrollListenerDelegate(AbsListView listView, View targetView, QuickReturnViewTranslater quickReturnViewTranslater) {
        this.listView = listView;
        this.targetView = targetView;
        this.quickReturnViewTranslater = quickReturnViewTranslater;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (invalidState()) {
            return;
        }

        int rawY = -getComputedScrollY();
        int translationY = quickReturnViewTranslater.determineState(rawY, targetView.getHeight());

        quickReturnViewTranslater.translateTo(translationY);
    }

    private boolean invalidState() {
        return listView.getAdapter() == null;
    }

    private int getComputedScrollY() {
        if (listView.getChildCount() == 0) {
            return 0;
        }

        int position = listView.getFirstVisiblePosition();
        View firstView = listView.getChildAt(0);
        int itemVerticalOffset = ((QuickReturnAdapter) listView.getAdapter()).getPositionVerticalOffset(position);

        return itemVerticalOffset - firstView.getTop() + listView.getPaddingTop();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        // no-op
    }
}
