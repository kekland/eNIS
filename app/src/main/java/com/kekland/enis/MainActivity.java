package com.kekland.enis;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.kekland.enis.NIS.NISApi;
import com.kekland.enis.Utilities.MasterListener;
import com.kekland.enis.Utilities.ProjectUtilities;
import com.kekland.enis.Utilities.SmoothActionBarDrawerToggle;

import java.lang.ref.WeakReference;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

/**
 * Created by Gulnar on 01.10.2017.
 */

public class MainActivity extends AppCompatActivity {

    SmoothActionBarDrawerToggle toggle;
    int selectedID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseAnalytics analytics = FirebaseAnalytics.getInstance(this);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.contentToolbar);
        toggle = new SmoothActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawerOpen, R.string.drawerClosed);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView view = (NavigationView)findViewById(R.id.navigation_view);


        view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                itemSelected(item);
                return true;
            }
        });
    }

    public boolean itemSelected(final MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawerLayout);
        drawer.closeDrawer(GravityCompat.START);

        final AppBarLayout appBar = (AppBarLayout)findViewById(R.id.appBar);
        appBar.setExpanded(true);

        toggle.runWhenIdle(new Runnable() {
            @Override
            public void run() {
                int id = item.getItemId();
                String title = item.getTitle().toString();

                FloatingActionButton fab = findViewById(R.id.gradesUpdateFAB);
                if(id == selectedID) {
                    return;
                }
                selectedID = id;

                ((TextView)findViewById(R.id.nameSelectedTab)).setText(title);

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                if(selectedID == R.id.menuMarks) {
                    fab.show();
                }
                else {
                    fab.hide();
                }

                if(selectedID == R.id.menuMarks) {
                    GradesFragment fragment = new GradesFragment();
                    fragment.listener = new MasterListener() {
                        @Override
                        public void ShowSnackbar(String message) {
                            Snackbar.make(findViewById(R.id.mainCoordinator), message,
                                    Snackbar.LENGTH_SHORT).show();
                        }
                    };
                    fragment.update = fab;
                    transaction.replace(R.id.content, fragment);
                }
                else if(selectedID == R.id.menuSettings) {
                    Intent intent = new Intent(MainActivity.this, DebugActivity.class);
                    startActivity(intent);
                }
                transaction.commit();
            }
        });
        return true;
    }
}
