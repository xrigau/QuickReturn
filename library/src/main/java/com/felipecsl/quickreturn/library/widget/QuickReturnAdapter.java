package com.felipecsl.quickreturn.library.widget;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuickReturnAdapter extends DataSetObserver implements ListAdapter {

    private final ListAdapter wrappedAdapter;
    private int[] itemsVerticalOffset;
    private final int numColumns = 3; // TODO
    private int verticalSpacing;

    public QuickReturnAdapter(final ListAdapter wrappedAdapter) {
        this.wrappedAdapter = wrappedAdapter;
        itemsVerticalOffset = new int[wrappedAdapter.getCount() + numColumns];
        wrappedAdapter.registerDataSetObserver(this);
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
        View v;
        int finalHeight;
        v = wrappedAdapter.getView(position, convertView, parent);

        v.measure(View.MeasureSpec.makeMeasureSpec(parent.getWidth() / numColumns, View.MeasureSpec.AT_MOST), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        finalHeight = v.getMeasuredHeight();

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
        if (position >= itemsVerticalOffset.length) {
            return 0;
        }

        return itemsVerticalOffset[position];
    }

    public int getMaxVerticalOffset() {
        if (isEmpty()) {
            return 0;
        }

        final List<Integer> items = new ArrayList<Integer>(itemsVerticalOffset.length);
        for (final int aMItemOffsetY : itemsVerticalOffset) {
            items.add(aMItemOffsetY);
        }
        return Collections.max(items);
    }

    @Override
    public void onChanged() {
        super.onChanged();

        if (wrappedAdapter.getCount() < itemsVerticalOffset.length) {
            return;
        }

        int[] newArray = new int[wrappedAdapter.getCount() + numColumns];
        System.arraycopy(itemsVerticalOffset, 0, newArray, 0, Math.min(itemsVerticalOffset.length, newArray.length));
        itemsVerticalOffset = newArray;
    }

    public void setVerticalSpacing(final int verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }
}
