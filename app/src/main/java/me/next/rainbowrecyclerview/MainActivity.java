package me.next.rainbowrecyclerview;

import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;


public class MainActivity extends AppCompatActivity {

    private String[] dataSet;
    private RecyclerView recyclerview;
    private RecyclerViewAdapter adapter;
    private int[] colors = new int[] { 0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000 };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDataSet();

        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerview = (RecyclerView) findViewById(R.id.recyclerview);
        adapter = new RecyclerViewAdapter(dataSet, recyclerview);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);

        recyclerview.setAdapter(adapter);

        recyclerview.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                TextView textView = (TextView) linearLayoutManager.findViewByPosition(
                        linearLayoutManager.findFirstVisibleItemPosition());
                int viewHeight = textView.getHeight();

                int firstVisiblePosition = linearLayoutManager.findFirstVisibleItemPosition();
                int lastVisiblePosition = linearLayoutManager.findLastVisibleItemPosition();

                float percent = (float) myPercent(viewHeight + ViewHelper.getY(textView), viewHeight);
                adapter.setPercent(percent, firstVisiblePosition, lastVisiblePosition);
                Log.d("Rainbow", "percent : " + percent);

            }
        });

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

    private int interpCircleColor(int colors[], float unit) {
        if (unit <= 0) {
            return colors[0];
        }
        if (unit >= 1) {
            return colors[colors.length - 1];
        }

        float p = unit * (colors.length - 1);
        int i = (int) p;
        p -= i;

        // now p is just the fractional part [0...1) and i is the index
        int c0 = colors[i];
        int c1 = colors[i + 1];
        int a = ave(Color.alpha(c0), Color.alpha(c1), p);
        int r = ave(Color.red(c0), Color.red(c1), p);
        int g = ave(Color.green(c0), Color.green(c1), p);
        int b = ave(Color.blue(c0), Color.blue(c1), p);

        return Color.argb(a, r, g, b);
    }

    private int ave(int s, int d, float p) {
        return s + Math.round(p * (d - s));
    }

    public static double myPercent(float y, int z) {
        double baiy = y * 1.0;
        double baiz = z * 1.0;
        return baiy / baiz;
    }

}
