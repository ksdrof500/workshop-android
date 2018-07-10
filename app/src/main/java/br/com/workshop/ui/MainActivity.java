package br.com.workshop.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import br.com.workshop.MainApplication;
import br.com.workshop.R;
import br.com.workshop.dummy.DummyContent;
import br.com.workshop.model.DataBase;
import br.com.workshop.model.Talks;
import br.com.workshop.model.TalksSQL;
import br.com.workshop.network.APIManager;
import br.com.workshop.network.AppService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        EventsFragment.OnListFragmentInteractionListener {

    private int selectedItem;

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
        navigateTo(R.id.nav_home);
        APIManager.getAppService().getTalks().enqueue(mtalks);
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            try {
                PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                Toast.makeText(this, getString(R.string.version,pInfo.versionName), Snackbar.LENGTH_LONG).show();
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
         }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        return navigateTo(item.getItemId());
    }

    public boolean navigateTo(int id) {
        if (id != selectedItem) {

            if (id == R.id.nav_home) {

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_fragment, new EventsFragment())
                        .commit();

            } else if (id == R.id.nav_share) {

                Intent intent = new Intent(this, WebViewActivity.class);
                startActivity(intent);

            } else if (id == R.id.nav_send) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
            }
        }

        selectedItem = id;
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Callback<List<Talks>> mtalks = new Callback<List<Talks>>() {
        @Override
        public void onResponse(Call<List<Talks>> call, Response<List<Talks>> response) {
            if(response.isSuccessful()) {
                SQLiteDatabase db = new DataBase(MainActivity.this).getWritableDatabase();

                for (Talks talks : response.body()) {

                    ContentValues values = new ContentValues();
                    values.put(TalksSQL.TalksEntry.COLUMN_NAME, talks.name);
                    values.put(TalksSQL.TalksEntry.COLUMN_DESCRIPTION, talks.description);
                    values.put(TalksSQL.TalksEntry.COLUMN_IMAGE, talks.image);

                    db.insertWithOnConflict(TalksSQL.TalksEntry.TALKS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
            }
        }

        @Override
        public void onFailure(Call<List<Talks>> call, Throwable t) {

        }
    };

    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        startActivity(new Intent(this, DetailActivity.class));
    }
}
