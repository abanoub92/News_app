package com.abanoub.unit.dailynews.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.abanoub.unit.dailynews.R;
import com.abanoub.unit.dailynews.model.DailyNews;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends ArrayAdapter<DailyNews> {


    public NewsAdapter(@NonNull Context context, @NonNull List<DailyNews> objects) {
        super(context, R.layout.list_item, objects);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rootView = convertView;

        if (rootView == null){
            rootView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        DailyNews dailyNews = getItem(position);

        TextView section_name = rootView.findViewById(R.id.section_name);
        section_name.setText(dailyNews.getmSectionName());

        TextView title = rootView.findViewById(R.id.section_title);
        title.setText(dailyNews.getmTitle());

        TextView date = rootView.findViewById(R.id.section_date);
        String stringDate = dailyNews.getmPublishedDate();
        date.setText(formatDate(stringDate));

        return rootView;
    }


    private String formatDate (String stringDate){
        long longDate = Long.getLong(stringDate);
        Date date = new Date(longDate);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault());
        return format.format(date);
    }
}
