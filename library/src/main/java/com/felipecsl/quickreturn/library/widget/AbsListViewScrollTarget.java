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

        final int maxVerticalOffset = ((QuickReturnAdapter) listView.getAdapter()).getMaxVerticalOffset();
        final int listViewHeight = listView.getHeight();
        final int rawY = -Math.min(maxVerticalOffset > listViewHeight
                ? maxVerticalOffset - listViewHeight
                : listViewHeight, getComputedScrollY());

        final int translationY = quickReturnTargetView.currentTransition.determineState(rawY, targetView.getHeight());

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
        return ((QuickReturnAdapter) listView.getAdapter()).getPositionVerticalOffset(pos) - view.getTop();
    }
}
