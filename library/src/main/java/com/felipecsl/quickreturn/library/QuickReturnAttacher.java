package com.felipecsl.quickreturn.library;

import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.felipecsl.quickreturn.library.widget.QuickReturnTargetView;

public abstract class QuickReturnAttacher {

    public static QuickReturnAttacher forView(ViewGroup viewGroup) {
        return new AbsListViewQuickReturnAttacher((AbsListView) viewGroup);
    }

    public abstract QuickReturnTargetView addTargetView(final View view);
}
