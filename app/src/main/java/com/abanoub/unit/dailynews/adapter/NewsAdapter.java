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

import java.text.ParseException;
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
        SimpleDateFormat sdf;
        Date date = null;
        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
        try {
            date = sdf.parse(stringDate);
            sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a", Locale.getDefault());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return sdf.format(date);
    }
}
