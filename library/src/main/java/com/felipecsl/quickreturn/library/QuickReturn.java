package com.felipecsl.quickreturn.library;

import android.view.View;
import android.widget.GridView;
import android.widget.ListAdapter;

import com.felipecsl.quickreturn.library.widget.QuickReturnAdapter;

public class QuickReturn {

    private final ListAdapter adapter;
    private final GridView gridView;
    private final int columns;

    public static QuickReturn with(ListAdapter adapter, GridView gridView, int columns) {
        return new QuickReturn(adapter, gridView, columns);
    }

    QuickReturn(ListAdapter adapter, GridView gridView, int columns) {
        this.adapter = adapter;
        this.gridView = gridView;
        this.columns = columns;
    }

    public QuickReturn addHeader(View view) {
        gridView.setAdapter(QuickReturnAdapter.newInstance(adapter, gridView, columns));
        QuickReturnAttacher quickReturnAttacher = QuickReturnAttacher.forView(gridView);
        quickReturnAttacher.addTargetView(view);
        return this;
    }
}
