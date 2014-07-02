package com.felipecsl.quickreturn.library.widget;

import android.view.View;
import android.widget.AbsListView;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.WrapperListAdapter;

public class AbsListViewScrollTarget extends QuickReturnTargetView implements AbsListView.OnScrollListener {

    private final AbsListView listView;

    public AbsListViewScrollTarget(final AbsListView listView, final View targetView) {
        super(targetView);

        this.listView = listView;
        final QuickReturnAdapter adapter = getAdapter();

        if (adapter == null) {
            throw new UnsupportedOperationException("You need to set the listView adapter before adding a targetView");
        }

        adapter.setVerticalSpacing(((GridView) listView).getVerticalSpacing());
    }

    private QuickReturnAdapter getAdapter() {
        ListAdapter adapter = listView.getAdapter();

        if (adapter instanceof WrapperListAdapter) {
            adapter = ((WrapperListAdapter) adapter).getWrappedAdapter();
        }

        if (!(adapter instanceof QuickReturnAdapter)) {
            throw new UnsupportedOperationException("Your QuickReturn ListView adapter must be an instance of QuickReturnAdapter.");
        }

        return (QuickReturnAdapter) adapter;
    }

    @Override
    public void onScrollStateChanged(final AbsListView view, final int scrollState) {
    }

    @Override
    public void onScroll(final AbsListView view, final int firstVisibleItem, final int visibleItemCount, final int totalItemCount) {
        if (listView.getAdapter() == null || quickReturnView == null) {
            return;
        }

        final int maxVerticalOffset = getAdapter().getMaxVerticalOffset();
        final int listViewHeight = listView.getHeight();
        final int rawY = -Math.min(maxVerticalOffset > listViewHeight
                ? maxVerticalOffset - listViewHeight
                : listViewHeight, getComputedScrollY());

        final int translationY = currentTransition.determineState(rawY, quickReturnView.getHeight());

        translateTo(translationY);
    }

    protected int getComputedScrollY() {
        if (listView.getChildCount() == 0 || listView.getAdapter() == null) {
            return 0;
        }

        int pos = listView.getFirstVisiblePosition();
        final View view = listView.getChildAt(0);
        return getAdapter().getPositionVerticalOffset(pos) - view.getTop();
    }
}
