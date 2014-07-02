package com.felipecsl.quickreturn.library.widget;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

public class QuickReturnAdapter extends DataSetObserver implements ListAdapter {

    private final ListAdapter wrappedAdapter;
    private final int verticalSpacing;
    private final int numColumns;
    private int[] itemsVerticalOffset;

    public static ListAdapter newInstance(ListAdapter adapter, GridView gridView, int columns) {
        int verticalSpacing = gridView.getVerticalSpacing();
        int[] itemsVerticalOffset = new int[adapter.getCount() + columns];
        QuickReturnAdapter quickReturnAdapter = new QuickReturnAdapter(adapter, verticalSpacing, columns, itemsVerticalOffset);
        adapter.registerDataSetObserver(quickReturnAdapter);
        return quickReturnAdapter;
    }

    private QuickReturnAdapter(ListAdapter wrappedAdapter, int verticalSpacing, int numColumns, int[] itemsVerticalOffset) {
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
        View v = wrappedAdapter.getView(position, convertView, parent);

        v.measure(View.MeasureSpec.makeMeasureSpec(parent.getWidth() / numColumns, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int finalHeight = v.getMeasuredHeight();

        if (position + numColumns < itemsVerticalOffset.length) {
            itemsVerticalOffset[position + numColumns] = itemsVerticalOffset[position] + finalHeight + verticalSpacing;
        }

        return v;
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
        return itemsVerticalOffset[position];
    }

    public int getBottomVisibleItemOffset() {
        if (isEmpty()) {
            return 0;
        }

        int maxValue = 0;
        for (int i = 0; i < itemsVerticalOffset.length; i++) {
            maxValue = Math.max(maxValue, itemsVerticalOffset[i]);
        }
        return maxValue;
    }

    @Override
    public void onChanged() {
        if (wrappedAdapter.getCount() < itemsVerticalOffset.length) {
            return;
        }

        int[] newArray = new int[wrappedAdapter.getCount() + numColumns];
        System.arraycopy(itemsVerticalOffset, 0, newArray, 0, Math.min(itemsVerticalOffset.length, newArray.length));
        itemsVerticalOffset = newArray;
    }
}
