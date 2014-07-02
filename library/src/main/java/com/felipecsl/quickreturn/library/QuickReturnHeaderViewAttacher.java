package com.felipecsl.quickreturn.library;

import android.view.View;
import android.widget.AbsListView;

import com.felipecsl.quickreturn.library.widget.ScrollListenerDelegate;

public class QuickReturnHeaderViewAttacher {

    private final CompositeOnScrollListener onScrollListener;
    private final AbsListView absListView;

    public static QuickReturnHeaderViewAttacher newInstance(AbsListView listView) {
        CompositeOnScrollListener onScrollListener = new CompositeOnScrollListener();
        QuickReturnHeaderViewAttacher quickReturnHeaderViewAttacher = new QuickReturnHeaderViewAttacher(listView, onScrollListener);
        listView.setOnScrollListener(onScrollListener);
        return quickReturnHeaderViewAttacher;
    }

    private QuickReturnHeaderViewAttacher(AbsListView listView, CompositeOnScrollListener onScrollListener) {
        this.absListView = listView;
        this.onScrollListener = onScrollListener;
    }

    public void attach(View view) {
        ScrollListenerDelegate delegate = ScrollListenerDelegate.newInstance(absListView, view);
        onScrollListener.registerOnScrollListener(delegate);
    }

}