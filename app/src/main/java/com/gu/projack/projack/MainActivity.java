package com.gu.projack.projack;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View.OnTouchListener;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab, fabAdd, fabImport;
    Animation fabOpen, fabClose,fabRotateClock, fabRotateAntiClock;
    boolean isOpen = false;


    private SimpleCursorAdapter dataAdapter;
    SwipeRefreshLayout swipreRefresh;

    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 50;
    enum Direction {LEFT, RIGHT;}

    int mSelectedItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        swipreRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipreRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipreRefresh.setRefreshing(true);
                dataAdapter.notifyDataSetChanged();

                swipreRefresh.setRefreshing(false);
            }
        });

        displayListView();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fabAdd = (FloatingActionButton) findViewById(R.id.fab_add);
        fabImport = (FloatingActionButton) findViewById(R.id.fab_import);

        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fabRotateClock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_clockwise);
        fabRotateAntiClock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_anticlockwise);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view){
                if(isOpen){
                    fabAdd.startAnimation(fabClose);
                    fabImport.startAnimation(fabClose);
                    fab.startAnimation(fabRotateAntiClock);

                    fabAdd.setClickable(false);
                    fabImport.setClickable(false);
                    isOpen = false;

                }

                else {
                    fabAdd.startAnimation(fabOpen);
                    fabImport.startAnimation(fabOpen);
                    fab.startAnimation(fabRotateClock);

                    fabAdd.setClickable(true);
                    fabImport.setClickable(true);
                    isOpen = true;
                }
            }
        });


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ///Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                      //  .setAction("Action", null).show();

                    startActivity(new Intent(MainActivity.this, Add_Project.class));

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void displayListView() {

        final SqlDbHandler db = new SqlDbHandler(this);
        Cursor cursor = db.fetchAllProjects();

        // The desired columns to be bound
        String[] columns = new String[] {
                db.KEY_PROJECT_ID,
                db.KEY_PROJECT_NAME,
                db.KEY_PROJECT_TYPE,
                db.KEY_PROJECT_DESC,
                db.KEY_PROJECT_START_DATE,
                db.KEY_PROJECT_END_DATE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[] {
                R.id.project_id,
                R.id.project_title,
                R.id.project_type,
                R.id.project_desc,
                R.id.start_date,
                R.id.end_date,
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.list_project_item,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listProjectView);
        // Assign adapter to ListView
        listView.setAdapter(dataAdapter);

        listView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {

                // Get the cursor, positioned to the corresponding row in the result set
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // Get the state's capital from this row in the database.
                String projectId =
                        cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                //Toast.makeText(getApplicationContext(),
                  //      projectId, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this, SwipeActivity.class);

               // intent.putExtra("PROJECT_ID", projectId);
                startActivity(intent);
            }
        });

        listView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        historicX = event.getX();
                        historicY = event.getY();
                        break;

                    case MotionEvent.ACTION_UP:
                        if (event.getX() - historicX < -DELTA) {
                            Toast.makeText(getApplicationContext(), "SWIPE LEFT",Toast.LENGTH_SHORT).show();
                            MainActivity.this.overridePendingTransition(R.anim.slide_in_left,
                                    R.anim.slide_out_left);
                            return true;
                        }
                        else if (event.getX() - historicX > DELTA) {
                            Toast.makeText(getApplicationContext(), "SWIPE RIGHT",Toast.LENGTH_SHORT).show();
                            MainActivity.this.overridePendingTransition(R.anim.slide_in_right,
                                    R.anim.slide_out_right);
                            return true;
                        }
                        break;

                    default:
                        return false;
                }
                return false;
            }
        });
    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
