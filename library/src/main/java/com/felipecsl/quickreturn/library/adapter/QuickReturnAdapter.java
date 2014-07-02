package com.felipecsl.quickreturn.library.adapter;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;

import java.util.List;

public class QuickReturnAdapter extends DataSetObserver implements ListAdapter {

    private final ListAdapter wrappedAdapter;
    private final int verticalSpacing;
    private final int numColumns;
    private final boolean cacheViewHeights;

    private List<Integer> itemsVerticalOffset;

    public static ListAdapter newInstance(ListAdapter adapter, GridView gridView, int columns) {
        int verticalSpacing = gridView.getVerticalSpacing();
        List<Integer> itemsVerticalOffset = new ListFiller().allZeros(adapter.getCount() + columns);

        QuickReturnAdapter quickReturnAdapter = new QuickReturnAdapter(adapter, verticalSpacing, columns, itemsVerticalOffset, true);
        adapter.registerDataSetObserver(quickReturnAdapter);

        return quickReturnAdapter;
    }

    private QuickReturnAdapter(ListAdapter wrappedAdapter, int verticalSpacing, int numColumns, List<Integer> itemsVerticalOffset, boolean cacheViewHeights) {
        this.wrappedAdapter = wrappedAdapter;
        this.verticalSpacing = verticalSpacing;
        this.numColumns = numColumns;
        this.itemsVerticalOffset = itemsVerticalOffset;
        this.cacheViewHeights = cacheViewHeights;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return wrappedAdapter.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(int position) {
        return wrappedAdapter.isEnabled(position);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        wrappedAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        wrappedAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public int getCount() {
        return wrappedAdapter.getCount();
    }

    @Override
    public Object getItem(int position) {
        return wrappedAdapter.getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return wrappedAdapter.getItemId(position);
    }

    @Override
    public boolean hasStableIds() {
        return wrappedAdapter.hasStableIds();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = wrappedAdapter.getView(position, convertView, parent);

        if (validPosition(position) && valueNotCached(position)) {
            updateNextItemInCurentColumn(position, parent, itemView);
        }

        return itemView;
    }

    private boolean validPosition(int position) {
        return position + numColumns < itemsVerticalOffset.size();
    }

    private boolean valueNotCached(int position) {
        return cacheViewHeights && itemsVerticalOffset.get(position + numColumns) == 0;
    }

    /**
     * Updates the next item in column that belongs to the current item (for position = 0, it will update index 3 with the values of 0, etc.)
     */
    private void updateNextItemInCurentColumn(int position, ViewGroup parent, View itemView) {
        int finalHeight = getViewHeight(parent, itemView);
        itemsVerticalOffset.set(position + numColumns, itemsVerticalOffset.get(position) + finalHeight + verticalSpacing);
    }

    private int getViewHeight(ViewGroup parent, View view) {
        int columnWidth = parent.getWidth() / numColumns; // TODO: Do we need to take into account horizontal spacing here?
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(columnWidth, View.MeasureSpec.AT_MOST);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec); // TODO: Maybe get the view height in the constructor to simplify this.
        return view.getMeasuredHeight();
    }

    @Override
    public int getItemViewType(int position) {
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

    @Override
    public void onChanged() {
        itemsVerticalOffset.clear();
    }

}
