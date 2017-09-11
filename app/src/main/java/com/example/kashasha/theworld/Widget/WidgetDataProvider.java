package com.example.kashasha.theworld.Widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.kashasha.theworld.R;
import com.example.kashasha.theworld.data.TaskContract;
import java.util.ArrayList;
import java.util.List;

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    private static final String TAG = "WidgetDataProvider";

    List<String> names = new ArrayList<>();
    List<String> capitals = new ArrayList<>();
    Cursor data;


    Context mContext = null;

    public WidgetDataProvider(Context context, Intent intent) {
        mContext = context;
    }

    @Override
    public void onCreate() {
        initData();
    }

    @Override
    public void onDataSetChanged() {
        initData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return data.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews view = new RemoteViews(mContext.getPackageName(),
                R.layout.widget);
        String name = names.get(position).toString();
        String capital = capitals.get(position).toString();
        view.setTextViewText(R.id.widget_name, name);
        view.setTextViewText(R.id.widget_capital, capital);

        return view;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 3;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    private void initData() {

        names.clear();
       capitals.clear();

        String projection[] = getProjection();
        data = mContext.getContentResolver().query(TaskContract.TaskEntry.CONTENT_URI, projection, null, null, null);
        while (data.moveToNext()) {
            String name = data.getString(data.getColumnIndex(TaskContract.TaskEntry.NAME));
            String capital = data.getString(data.getColumnIndex(TaskContract.TaskEntry.CAPITAL));
            names.add(name);
            capitals.add(capital);


        }
        data.close();
    }


    public String[] getProjection() {
        String[] projection = {
                TaskContract.TaskEntry.NAME,
                TaskContract.TaskEntry.CAPITAL,
                TaskContract.TaskEntry.FLAG,


        };
        return projection;
    }


}



