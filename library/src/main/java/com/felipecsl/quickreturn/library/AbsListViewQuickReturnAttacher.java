package com.felipecsl.quickreturn.library;

import android.view.View;
import android.widget.AbsListView;

import com.felipecsl.quickreturn.library.widget.AbsListViewScrollTarget;

public class AbsListViewQuickReturnAttacher extends QuickReturnAttacher {

    private final CompositeAbsListViewOnScrollListener onScrollListener = new CompositeAbsListViewOnScrollListener();
    private final AbsListView absListView;

    public AbsListViewQuickReturnAttacher(final AbsListView listView) {
        this.absListView = listView;
        absListView.setOnScrollListener(onScrollListener);
    }

    public AbsListViewScrollTarget addTargetView(final View view) {
        final AbsListViewScrollTarget targetView = new AbsListViewScrollTarget(absListView, view);
        onScrollListener.registerOnScrollListener(targetView);

        return targetView;
    }

}