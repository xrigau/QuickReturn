package com.felipecsl.quickreturn.library;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.felipecsl.quickreturn.library.widget.AbsListViewScrollTarget;

public abstract class QuickReturnAttacher {

    public static QuickReturnAttacher forView(ViewGroup viewGroup) {
        return new AbsListViewQuickReturnAttacher((AbsListView) viewGroup);
    }

    public abstract AbsListViewScrollTarget addTargetView(final View view);
}
