package com.oliverdwang.synoptica2;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {

    private RecyclerView logListRecycler;
    private RecyclerView.LayoutManager mLayoutManager;
    private logAdapter mAdapter;
    private List<logEntry> logList = new ArrayList<>();

    private Button refresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        //Toolbar toolbar = findViewById(R.id.toolbar2);
        //setSupportActionBar(toolbar);

        logListRecycler = findViewById(R.id.list);
        logListRecycler.setHasFixedSize(true);

        refresh = findViewById(R.id.button_refresh);
        refresh.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logDatabaseHelper dbHelper = logDatabaseHelper.getInstance(getApplicationContext());
                logList.clear();
                logList = dbHelper.getAllLogs();
                for(int i = 0; i < logList.size(); i++) {
                    Log.v("logList", logList.get(i).toString());
                }
                dbHelper.close();

                mAdapter.notifyDataSetChanged();
            }
        });

        mAdapter = new logAdapter(logList);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        logListRecycler.setLayoutManager(mLayoutManager);
        logListRecycler.setItemAnimator(new DefaultItemAnimator());
        logListRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        logListRecycler.setAdapter(mAdapter);

        logDatabaseHelper dbHelper = logDatabaseHelper.getInstance(this);
        logList.clear();
        logList = dbHelper.getAllLogs();
        for(int i = 0; i < logList.size(); i++) {
            Log.v("logList", logList.get(i).toString());
        }
        dbHelper.close();

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_history) {
            Intent toMain = new Intent(HistoryActivity.this, HistoryActivity.class);
            HistoryActivity.this.startActivity(toMain);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
