package com.felipecsl.quickreturn.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.felipecsl.quickreturn.library.QuickReturn;

public class MainActivity extends ActionBarActivity {

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);
        initialize();
    }

    private void initialize() {
        GridView gridView = (GridView) findViewById(R.id.listView);
        TextView topTextView = (TextView) findViewById(R.id.quickReturnTopTarget);

        adapter = new ArrayAdapter<String>(this, R.layout.list_item);
        addMoreItems(100);

        int columns = gridView.getResources().getInteger(R.integer.grid_column_count);
        QuickReturn.with(adapter, gridView, columns).addHeader(topTextView);
    }

    private void addMoreItems(final int amount) {
        for (int i = 0; i < amount; i++) {
            adapter.add("Item " + String.valueOf(i));
        }
    }

}
