package me.next.rainbowrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    private String[] dataSet;
    private RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDataSet();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);

        recyclerview.setAdapter(new RecyclerViewAdapter(dataSet, recyclerview));

        linearLayoutManager.findFirstVisibleItemPosition();
//        recyclerview.getChildAt(linearLayoutManager.findFirstVisibleItemPosition())
    }

    private void initDataSet() {
        // 创建数据集
        dataSet = new String[100];
        for (int i = 0; i < dataSet.length; i++){
            dataSet[i] = "this is item " + i;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
