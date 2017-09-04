/*
 *  SidebarFragmentDemo 1.0
 *  Author: Lauri Mattila
 *
 *  8.3.2017
 *
 *  Purpose of this demo is to show how android widgeds allow user to accomplish
 *  Multipaged application by using fragments. Demo also include a way to save specific fragment.
 *
 *  Current version is manual, Dynamic and better one is probably incoming someday.
 *
 *
 */


package miracle.sidebarfragmentdemo;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import miracle.sidebarfragmentdemo.Fragments.PageFragment;




public class MainJava extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main_activity);

        //toolbar stuff
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //sidebar stuff and sidebartoolbar binding
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //noticeable here: setDrawerListener is depricated. Use addDrawerListener instead
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //sidebar click handler
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //adding the first fragment
        fm.beginTransaction().add(R.id.containerView, new PageFragment(), "mainFrag").commitNow();
        fm.beginTransaction().show(fm.findFragmentByTag("mainFrag")).commit();
    }



    /*
    *  onBackPressed() back pressed handles only drawer
    */

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /*
     *  Usage of this implementation might lead into high memory usage.
     *
     *  This method is mainly the purpose of this demo. Here Fragments are added for the side drawer,
     *  purposely saving one, so it wont get erased. This fragment could for example have data, that is wanted to be saved
     *  (front pages etc.)
     *
     *  Rest of the fragments/pages will be cleaned after user leaves the page.
     *
     *
     */

    //Includes series of adds and removes instead of replace.
    //This is to ensure that main page saved while viewing other pages
    //Other pages are deleted from fragmentmanager to save memory
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        //This is the fragment/page that gets saved
        if (id == R.id.nav_main) {

            if(fm.findFragmentByTag("secondaryFrag") != null) {
                fm.beginTransaction().remove(fm.findFragmentByTag("secondaryFrag")).commit();
            }
            if(fm.findFragmentByTag("tertiaryFrag") != null) {
                fm.beginTransaction().remove(fm.findFragmentByTag("tertiaryFrag")).commit();
            }

            fm.beginTransaction().show(fm.findFragmentByTag("mainFrag")).commitNow();

        }

        //these pages will be forgotten after leaving the page. Grabage collection takes care of them

        else if (id == R.id.nav_secondary) {
            //Hide saved fragment from view
            fm.beginTransaction().hide(fm.findFragmentByTag("mainFrag")).commit();

            /*
             * Remove other existing fragments, in this case there is only one possibility
             * If there is multiple fragments used, removing unnecessary fragments could be  done in a
             * "reverse order" -> Delete all but fragment wanted to save.
             * In this case we delete fragments manually
             */

            if(fm.findFragmentByTag("tertiaryFrag") != null) {
                fm.beginTransaction().remove(fm.findFragmentByTag("tertiaryFrag")).commit();
            }

            fm.beginTransaction().add(R.id.containerView, new PageFragment(), "secondaryFrag").commitNow();

        } else if (id == R.id.nav_tertiary) {
            //Hide saved fragment from view
            fm.beginTransaction().hide(fm.findFragmentByTag("mainFrag")).commit();

            if(fm.findFragmentByTag("secondaryFrag") != null) {
                fm.beginTransaction().remove(fm.findFragmentByTag("secondaryFrag")).commit();
            }

            fm.beginTransaction().add(R.id.containerView, new PageFragment(), "tertiaryFrag").commitNow();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
