package com.example.newstutby;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rssfeedtutby.DB.DBHelper;
import com.example.rssfeedtutby.adapters.NewsAdapter;
import com.example.rssfeedtutby.fragments.ListNews;
import com.example.rssfeedtutby.fragments.dialogs.StartDialog;
import com.example.rssfeedtutby.models.Channel;
import com.example.rssfeedtutby.models.DBNew;
import com.example.rssfeedtutby.models.Item;
import com.example.rssfeedtutby.models.RSSfeed;
import com.j256.ormlite.dao.Dao;

import net.hockeyapp.android.CrashManager;
import net.hockeyapp.android.UpdateManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<DBNew>> {

    private LoaderManager loaderManager;
    private Loader<List<DBNew>> loader;
    private SharedPreferences sPref;
    private TutByService tutByService;
    private StartDialog startDialog;
    private NewsAdapter newsAdapter;
    private List<DBNew> dbNews;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sPref = getSharedPreferences(Constants.SHARED_PREFERENCE_NAME, Context.MODE_PRIVATE);

        CrashManager.register(this);
        UpdateManager.register(this);

        loaderManager = getLoaderManager();

        loader = loaderManager.initLoader(Constants.LOADER_NEWS_ID, null, this);

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.createNonStrict())
                .build();

        tutByService = retrofit.create(TutByService.class);

        dbNews = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, dbNews);

        refreshData();

        ListNews listNewsFragment = new ListNews();
        listNewsFragment.setNewsAdapter(newsAdapter);
        replaceFragment(listNewsFragment);
    }

    public void replaceFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerFragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void refreshData() {
        Call<RSSfeed> call = tutByService.getAllLastNews();
        call.enqueue(new Callback<RSSfeed>() {
            @Override
            public void onResponse(Call<RSSfeed> call, Response<RSSfeed> response) {
                Channel channel = response.body().getChannel();
                if (channel.getLastBuildDate().equals(sPref.getString(Constants.SPREF_LAST_BUILD_DATE, ""))) {

                    if (startDialog != null) {
                        startDialog.dismiss();
                    }

                    if (dbNews.size() == 0) {
                        getDataFromDB();
                    }

                    Toast.makeText(MainActivity.this, getString(R.string.no_new_news), Toast.LENGTH_SHORT).show();
                } else {
                    sPref.edit().putString(Constants.SPREF_LAST_BUILD_DATE, channel.getLastBuildDate()).commit();
                    ArrayList<Item> items = channel.getChannel();
                    ((NewsLoader) loader).setItems(items);
                    loader.forceLoad();
                }
            }

            @Override
            public void onFailure(Call<RSSfeed> call, Throwable t) {
                t.printStackTrace();

                if (dbNews.size() == 0) {
                    getDataFromDB();
                }

                if (startDialog != null) {
                    startDialog.dismiss();
                }

                Toast.makeText(MainActivity.this, getString(R.string.message_fail_refresh), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getDataFromDB() {
        DBHelper dbHelper = new DBHelper(MainActivity.this);
        try {
            Dao<DBNew, Integer> dao = dbHelper.getDao();
            List<DBNew> news = dao.queryForAll();
            if (news.size() == 0) {
                return;
            } else {
                dbNews.addAll(news);
                newsAdapter.notifyDataSetChanged();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh_news) {
            refreshData();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<List<DBNew>> onCreateLoader(int id, Bundle args) {
        startDialog = new StartDialog();
        startDialog.show(getSupportFragmentManager(), "startDialog");
        Loader<List<DBNew>> loader = null;
        if (id == Constants.LOADER_NEWS_ID) {
            loader = new NewsLoader(MainActivity.this);
            Log.d(Constants.MYLOG, "onCreateLoader");
        }
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<DBNew>> loader, List<DBNew> data) {
        Log.d(Constants.MYLOG, "onLoadFinished");
        dbNews.clear();
        dbNews.addAll(data);
        newsAdapter.notifyDataSetChanged();
        Log.d("MyLogSize", dbNews.size() + "");
        if (startDialog != null) {
            startDialog.dismiss();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<DBNew>> loader) {
        Log.d(Constants.MYLOG, "onLoaderReset");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UpdateManager.unregister();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount() == 0) {
            super.onBackPressed();
        }
    }
}
