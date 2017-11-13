package com.example.dell.mydemostreamred5;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.dell.mydemostreamred5.ui.adapter.SectionPagerAdapter;
import com.example.dell.mydemostreamred5.ui.fragment.BackFragment;
import com.red5pro.streaming.R5Connection;
import com.red5pro.streaming.R5StreamProtocol;
import com.red5pro.streaming.config.R5Configuration;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public DrawerLayout drawerLayout;
    private TextView tittle;
    public static R5Configuration r5Configuration;
    public static BackFragment currentFragment;
    public static R5Connection connection;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tittle= (TextView) findViewById(R.id.title);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        r5Configuration = new R5Configuration(R5StreamProtocol.RTSP, "192.168.13.94", 8081, "live", 1.0f);
        r5Configuration.setLicenseKey("3DDG-2E22-VSLZ-O3AZ");
        r5Configuration.setBundleID(getPackageName());
        r5Configuration.setPort(8081);
        r5Configuration.setHost("192.168.13.11");
        r5Configuration.setStreamName("113");
         connection = new R5Connection(r5Configuration);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.


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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            return rootView;
        }
    }

    public void callFragment(Fragment fragment) {
        if (fragment instanceof BackFragment) {
            currentFragment = (BackFragment) fragment;
        } else {
            currentFragment = null;
        }
         FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment).addToBackStack(null);
        transaction.commit();
    }
    public void drawerAction(View v) {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        if ((drawerLayout.isDrawerOpen(GravityCompat.START) )) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

}
