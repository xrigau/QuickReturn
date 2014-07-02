package com.felipecsl.quickreturn.library.widget;

import android.view.View;
import android.widget.AbsListView;

public class AbsListViewScrollTarget implements AbsListView.OnScrollListener {

    private final AbsListView listView;
    private final View targetView;

    private final QuickReturnTargetView quickReturnTargetView;

    public AbsListViewScrollTarget(final AbsListView listView, final View targetView) {
        this.listView = listView;
        this.targetView = targetView;
        this.quickReturnTargetView = new QuickReturnTargetView(targetView);
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        if (invalidState()) {
            return;
        }

        final int maxVerticalOffset = ((QuickReturnAdapter) listView.getAdapter()).getBottomVisibleItemOffset();
        final int listViewHeight = listView.getHeight();

        boolean isKnownOffsetIsBiggerThanList = maxVerticalOffset > listViewHeight;

        int delta = maxVerticalOffset - listViewHeight;
        int min1 = isKnownOffsetIsBiggerThanList ? delta : listViewHeight;

        int computedScrollY = getComputedScrollY();
//        final int rawY = -Math.min(min1, computedScrollY);
        int rawY = -getComputedScrollY();
        android.util.Log.w("YO", "rawY: " + rawY + "\t\t min1: " + min1 + "\t\t computedScrollY: " + computedScrollY);

        final int translationY = quickReturnTargetView.determineState(rawY, targetView.getHeight());

        quickReturnTargetView.translateTo(translationY);
    }

    private boolean invalidState() {
        return listView.getAdapter() == null || targetView == null;
    }

    protected int getComputedScrollY() {
        if (listView.getChildCount() == 0 || listView.getAdapter() == null) {
            return 0;
        }

        int pos = listView.getFirstVisiblePosition();
        final View view = listView.getChildAt(0);
        return ((QuickReturnAdapter) listView.getAdapter()).getPositionVerticalOffset(pos) - view.getTop() + listView.getPaddingTop();
    }
}
