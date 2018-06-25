package com.abanoub.unit.dailynews.UI;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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

import com.abanoub.unit.dailynews.R;
import com.abanoub.unit.dailynews.adapter.NewsAdapter;
import com.abanoub.unit.dailynews.loader.NewsLoader;
import com.abanoub.unit.dailynews.model.DailyNews;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<List<DailyNews>> {

    private String query;

    private ProgressBar progressBar;

    private NewsAdapter adapter;

    private static final String NEWS_FEED_URL =
            "http://content.guardianapis.com/search";
    //?q=debate&tag=politics/politics&api-key=2339781e-91cc-4bc2-b4cf-6bdac3c2e5f0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ListView listView = findViewById(R.id.news_list);
        adapter = new NewsAdapter(this, new ArrayList<DailyNews>());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DailyNews dailyNews = adapter.getItem(i);

                Uri uri = Uri.parse(dailyNews.getmNewsUrl());

                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(1, null, this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public Loader<List<DailyNews>> onCreateLoader(int i, Bundle bundle) {
        Uri uri = Uri.parse(NEWS_FEED_URL);
        Uri.Builder builder = uri.buildUpon();

        builder.appendQueryParameter("q", query);
        builder.appendQueryParameter("api-key", "2339781e-91cc-4bc2-b4cf-6bdac3c2e5f0");
        return new NewsLoader(this, builder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<DailyNews>> loader, List<DailyNews> dailyNews) {
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        adapter.clear();

        if (dailyNews != null && !dailyNews.isEmpty()){
            adapter.addAll(dailyNews);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<DailyNews>> loader) {
        adapter.clear();
    }
}
