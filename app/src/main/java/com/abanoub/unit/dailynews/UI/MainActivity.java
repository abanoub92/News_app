package com.abanoub.unit.dailynews.UI;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abanoub.unit.dailynews.R;
import com.abanoub.unit.dailynews.adapter.NewsAdapter;
import com.abanoub.unit.dailynews.loader.NewsLoader;
import com.abanoub.unit.dailynews.model.DailyNews;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<List<DailyNews>> {

    TextView empty_text;

    boolean isConnect;

    ProgressBar progressBar;

    /**
     * global container the query of selection news type
     */
    private String query;

    /**
     * global adapter container for list view
     */
    private NewsAdapter adapter;

    /**
     * global url to get data of news
     */
    private static final String NEWS_FEED_URL = "http://content.guardianapis.com/search";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** connect the xml toolbar and add to design */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /** connect the xml drawer layout
         * it's the activity_main master view group */
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        /** responsible of getting the section type by
         * clicking on one of many section in navigation list */
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        empty_text = findViewById(R.id.empty_text);
        progressBar = findViewById(R.id.progress_bar);

        /** check the network state if there's an internet connection */
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo info = cm.getActiveNetworkInfo();
        isConnect = info != null && info.isConnectedOrConnecting();

        /** connect listView variable with xml ListView */
        ListView listView = findViewById(R.id.news_list);
        listView.setEmptyView(empty_text);

        /** create an object from custom adapter */
        adapter = new NewsAdapter(this, new ArrayList<DailyNews>());

        /** add the adapter to list view */
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                /** getting position of list row which clicked */
                DailyNews dailyNews = adapter.getItem(i);

                /** convert news feed url to uri for use it in search */
                Uri uri = Uri.parse(dailyNews.getmNewsUrl());

                /** open the browser and searching for this uri */
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });


        /** responsible of initialize the loader and fetch the data*/
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        /** getting the clicked section to looking of equal with news type */
        if (id == R.id.nav_news) {
            query = getString(R.string.section_global);
        } else if (id == R.id.nav_science) {
            query = getString(R.string.section_science);
        } else if (id == R.id.nav_cities) {
            query = getString(R.string.section_cities);
        } else if (id == R.id.nav_global_development) {
            query = getString(R.string.section_global_development);
        } else if (id == R.id.nav_world_cup) {
            query = getString(R.string.section_world_cup);
        } else if (id == R.id.nav_football) {
            query = getString(R.string.section_football);
        } else if (id == R.id.nav_tech) {
            query = getString(R.string.section_tech);
        } else if (id == R.id.nav_business) {
            query = getString(R.string.section_business);
        } else if (id == R.id.nav_environment) {
            query = getString(R.string.section_environment);
        }


        /** after getting the right section make reload to connection
         * to get the right news equals with selected section */
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.restartLoader(1, null, this);


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public Loader<List<DailyNews>> onCreateLoader(int i, Bundle bundle) {
        /** convert the string url to uri can edit on it
         * uri allows us to add and modify the using url */
        Uri uri = Uri.parse(NEWS_FEED_URL);
        Uri.Builder builder = uri.buildUpon();

        builder.appendQueryParameter("q", query);
        builder.appendQueryParameter("order-by", "newest");
        builder.appendQueryParameter("api-key", "");
        return new NewsLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<DailyNews>> loader, List<DailyNews> dailyNews) {
        if (isConnect) {
            /** connect with xml progress bar it's run while the data fetching
             * and hide when data is download */
            progressBar.setVisibility(View.GONE);
            empty_text.setText(getString(R.string.empty_list));

            /** clear the adapter for new data */
            adapter.clear();

            /** check the list is empty
             * if not empty add the data to adapter */
            if (dailyNews != null && !dailyNews.isEmpty()) {
                adapter.addAll(dailyNews);
            }
        }else {
            /** in case of no internet connection */
            progressBar.setVisibility(View.GONE);
            empty_text.setText(getString(R.string.no_connection));
        }
    }

    /**
     * when the loader resets clear the adapter
     */
    @Override
    public void onLoaderReset(Loader<List<DailyNews>> loader) {
        adapter.clear();
    }
}
