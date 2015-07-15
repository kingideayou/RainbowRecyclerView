package me.next.rainbowrecyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class MainActivity extends AppCompatActivity {

    private String[] dataSet;
    private RecyclerView recyclerview;
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDataSet();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerViewAdapter(MainActivity.this, dataSet, recyclerview);
        recyclerview.setAdapter(adapter);
    }

    private void initDataSet() {
        // 创建数据集
        dataSet = new String[100];
        for (int i = 0; i < dataSet.length; i++){
            dataSet[i] = "this is item " + i;
        }
    }

}
