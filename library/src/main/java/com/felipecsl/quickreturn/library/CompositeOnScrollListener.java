package com.felipecsl.quickreturn.library;

import android.widget.AbsListView;

import java.util.ArrayList;

public class CompositeOnScrollListener extends ArrayList<AbsListView.OnScrollListener> implements AbsListView.OnScrollListener {

    public void registerOnScrollListener(AbsListView.OnScrollListener listener) {
        add(listener);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        for (AbsListView.OnScrollListener listener : this) {
            listener.onScrollStateChanged(view, scrollState);
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        for (AbsListView.OnScrollListener listener : this) {
            listener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
        }
    }
}
