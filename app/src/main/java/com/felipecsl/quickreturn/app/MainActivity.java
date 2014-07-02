package com.felipecsl.quickreturn.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.felipecsl.quickreturn.library.QuickReturnAttacher;
import com.felipecsl.quickreturn.library.widget.QuickReturnAdapter;

public class MainActivity extends ActionBarActivity {

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_grid);
        initialize();
    }

    private void initialize() {
        AbsListView viewGroup = (AbsListView) findViewById(R.id.listView);
        TextView topTextView = (TextView) findViewById(R.id.quickReturnTopTarget);

        adapter = new ArrayAdapter<String>(this, R.layout.list_item);
        addMoreItems(100);

        viewGroup.setAdapter(new QuickReturnAdapter(adapter));

        final QuickReturnAttacher quickReturnAttacher = QuickReturnAttacher.forView(viewGroup);
        quickReturnAttacher.addTargetView(topTextView);
    }

    private void addMoreItems(final int amount) {
        for (int i = 0; i < amount; i++) {
            adapter.add("Item " + String.valueOf(i));
        }
    }

}
