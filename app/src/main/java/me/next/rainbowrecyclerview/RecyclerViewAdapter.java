package me.next.rainbowrecyclerview;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by NeXT on 15/7/13.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private float percent;
    private String[] dataSet;
    private RecyclerView recyclerView;
    private int startPosition;
    private int endPosition;

    private int[] colors = new int[] { 0xFFFF0000, 0xFFFF00FF, 0xFF0000FF, 0xFF00FFFF, 0xFF00FF00, 0xFFFFFF00, 0xFFFF0000 };

    public RecyclerViewAdapter(String[] dataSet, RecyclerView recyclerView) {
        this.dataSet = dataSet;
        this.recyclerView = recyclerView;
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.d("RainbowDemo", "dx : " + dx);
//                Log.d("RainbowDemo", "dy : " + dy);
            }
        });
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.item_list, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(dataSet[position]);
        if (position >= startPosition && position <= endPosition) {
            holder.textView.setTextColor(interpCircleColor(colors, percent, position - startPosition));
        }
//        recyclerView.getLayoutManager().getDecoratedTop(recyclerView.getChildAt(position));
//        Log.d("RainbowDemo", "item y : " + ViewHelper.getY(recyclerView.getLayoutManager().findViewByPosition(position)));
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
        if (unit <= 0) {
            return colors[0];
        }
        if (unit >= 1) {
            return colors[colors.length - 1];
        }

        int i = position % (colors.length - 1);

        int c0 = colors[i];
        int c1 = colors[i + 1];
        int a = ave(Color.alpha(c0), Color.alpha(c1), unit);
        int r = ave(Color.red(c0), Color.red(c1), unit);
        int g = ave(Color.green(c0), Color.green(c1), unit);
        int b = ave(Color.blue(c0), Color.blue(c1), unit);

        return Color.argb(a, r, g, b);
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
}
