package com.felipecsl.quickreturn.library.widget;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuickReturnAdapter extends DataSetObserver implements ListAdapter {

    private final ListAdapter wrappedAdapter;
    private final int verticalSpacing;
    private final int numColumns;

    private List<Integer> itemsVerticalOffset;

    public static ListAdapter newInstance(ListAdapter adapter, GridView gridView, int columns) {
        int verticalSpacing = gridView.getVerticalSpacing();
        List<Integer> itemsVerticalOffset = getZeroFilledList(adapter.getCount() + columns);

        QuickReturnAdapter quickReturnAdapter = new QuickReturnAdapter(adapter, verticalSpacing, columns, itemsVerticalOffset);
        adapter.registerDataSetObserver(quickReturnAdapter);

        return quickReturnAdapter;
    }

    private static List<Integer> getZeroFilledList(int size) {
        List<Integer> itemsVerticalOffset = new ArrayList<Integer>(size);
        for (int i = 0; i < size; i++) {
            itemsVerticalOffset.add(0);
        }
        return itemsVerticalOffset;
    }

    private QuickReturnAdapter(ListAdapter wrappedAdapter, int verticalSpacing, int numColumns, List<Integer> itemsVerticalOffset) {
        this.wrappedAdapter = wrappedAdapter;
        this.verticalSpacing = verticalSpacing;
        this.numColumns = numColumns;
        this.itemsVerticalOffset = itemsVerticalOffset;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return wrappedAdapter.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(final int position) {
        return wrappedAdapter.isEnabled(position);
    }

    @Override
    public void registerDataSetObserver(final DataSetObserver observer) {
        wrappedAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(final DataSetObserver observer) {
        wrappedAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public int getCount() {
        return wrappedAdapter.getCount();
    }

    @Override
    public Object getItem(final int position) {
        return wrappedAdapter.getItem(position);
    }

    @Override
    public long getItemId(final int position) {
        return wrappedAdapter.getItemId(position);
    }

    @Override
    public boolean hasStableIds() {
        return wrappedAdapter.hasStableIds();
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View itemView = wrappedAdapter.getView(position, convertView, parent);

        int finalHeight = getViewHeight(parent, itemView);

        if (position + numColumns < itemsVerticalOffset.size()) {
            itemsVerticalOffset.set(position + numColumns, itemsVerticalOffset.get(position) + finalHeight + verticalSpacing);
        }

        return itemView;
    }

    private int getViewHeight(ViewGroup parent, View v) {
        v.measure(View.MeasureSpec.makeMeasureSpec(parent.getWidth() / numColumns, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        return v.getMeasuredHeight();
    }

    @Override
    public int getItemViewType(final int position) {
        if (position < numColumns) {
            return wrappedAdapter.getViewTypeCount();
        }
        return wrappedAdapter.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return wrappedAdapter.getViewTypeCount();
    }

    @Override
    public boolean isEmpty() {
        return wrappedAdapter.isEmpty();
    }

    public int getPositionVerticalOffset(int position) {
        return itemsVerticalOffset.get(position);
    }

    public int getBottomVisibleItemOffset() {
        if (isEmpty()) {
            return 0;
        }

        itemsVerticalOffset.size();
        return Collections.max(itemsVerticalOffset);
    }

    @Override
    public void onChanged() {
        itemsVerticalOffset.clear();
    }

}
