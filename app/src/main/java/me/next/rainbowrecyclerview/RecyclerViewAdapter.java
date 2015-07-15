package me.next.rainbowrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nineoldandroids.view.ViewHelper;


/**
 * Created by NeXT on 15/7/13.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private float percent;
    private int startPosition;
    private int endPosition;
    private String[] dataSet;

    private Context context;
    private LinearLayoutManager layoutManager;

    private int[] colors = new int[] { 0xFF1565C0, 0xFF00E5FF, 0xFF69F0AE, 0xFF76FF03, 0xFFFDD835, 0xFFFFA000, 0xFFEF6C00};

    public RecyclerViewAdapter(Context context, String[] dataSet, RecyclerView recyclerView) {
        this.context = context;
        this.dataSet = dataSet;
        layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
                int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

                View view = layoutManager.findViewByPosition(firstVisiblePosition);
                int viewHeight = view.getHeight();

                float percent = (float) getPercentFromValue(viewHeight + ViewHelper.getY(view), viewHeight);
                setPercent(percent, firstVisiblePosition, lastVisiblePosition);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(
                context).inflate(R.layout.item_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(dataSet[position]);
        holder.textView.setTextColor(getColor(position));

        if (position >= startPosition && position <= endPosition) {
            holder.textView.setTextColor(interpCircleColor(colors, percent, position - startPosition));
        }
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text_view);
        }
    }

    private int interpCircleColor(int colors[], float unit, int position) {

        int i = position % (colors.length - 1);

        int c0 = colors[i];
        int c1 = colors[i + 1];
        int a = ave(Color.alpha(c0), Color.alpha(c1), unit);
        int r = ave(Color.red(c0), Color.red(c1), unit);
        int g = ave(Color.green(c0), Color.green(c1), unit);
        int b = ave(Color.blue(c0), Color.blue(c1), unit);

        return Color.argb(a, r, g, b);
    }

    private int getColor(int position) {
        return colors[position % colors.length];
    }

    private int ave(int s, int d, float p) {
        return s + Math.round(p * (d - s));
    }

    public void setPercent(float percent, int startPosition, int endPosition) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.percent = percent;
        notifyDataSetChanged();
    }

    public static double getPercentFromValue(float y, int z) {
        double valueFirst = y * 1.0;
        double valueSecond = z * 1.0;
        return valueFirst / valueSecond;
    }

}
