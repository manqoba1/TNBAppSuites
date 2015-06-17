package com.example.sifiso.tnbappsuites;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.sifiso.tnblibrary.fragment.DashboardFragment;
import com.example.sifiso.tnblibrary.fragment.IssuesLoggedFragment;
import com.example.sifiso.tnblibrary.fragment.PageFragment;
import com.example.sifiso.tnblibrary.models.CommunitymemberDTO;
import com.example.sifiso.tnblibrary.models.IssuesDTO;
import com.example.sifiso.tnblibrary.models.ReportedissueDTO;
import com.example.sifiso.tnblibrary.models.ResponseDTO;
import com.example.sifiso.tnblibrary.toolbox.WebCheck;
import com.example.sifiso.tnblibrary.toolbox.WebCheckResult;
import com.example.sifiso.tnblibrary.util.CachedUtil;
import com.example.sifiso.tnblibrary.util.DataUtil;
import com.example.sifiso.tnblibrary.util.SharedUtil;
import com.example.sifiso.tnblibrary.util.Util;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainPagerActivity extends ActionBarActivity implements DashboardFragment.DashboardFragmentListener, IssuesLoggedFragment.IssuesLoggedListener {
    static final String LOG = MainPagerActivity.class.getSimpleName();
    Context ctx;
    ProgressBar progressBar;
    List<PageFragment> pageFragmentList;
    ViewPager mPager;
    Menu mMenu;
    PagerAdapter adapter;
    private ResponseDTO response;
    private DashboardFragment dashboardFragment;
    private IssuesLoggedFragment loggedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pager);
        ctx = getApplicationContext();
        Log.d(LOG, "activity started");
        communityMember = SharedUtil.getCommunityMember(ctx);
        Util.setCustomActionBar(ctx, getSupportActionBar(),
                "Report Issue", ctx.getResources().getDrawable(R.mipmap.ic_launcher));
        setField();
    }

    private void setField() {
        mPager = (ViewPager) findViewById(R.id.SITE_pager);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        // mPager.setOffscreenPageLimit(NUM_ITEMS - 1);

        PagerTitleStrip strip = (PagerTitleStrip) findViewById(R.id.pager_title_strip);
        //strip.setVisibility(View.GONE);

    }

    private void buildPages() {

        pageFragmentList = new ArrayList<PageFragment>();
        dashboardFragment = new DashboardFragment();
        loggedFragment = new IssuesLoggedFragment();
        Bundle data = new Bundle();
        data.putSerializable("response", response);


        dashboardFragment.setArguments(data);
        loggedFragment.setArguments(data);

        pageFragmentList.add(dashboardFragment);
        pageFragmentList.add(loggedFragment);

        initializeAdapter();


    }

    CommunitymemberDTO communityMember;

    private void loadDashboardData() {
        DataUtil.loadUserData(ctx, 1, new DataUtil.DataUtilInterface() {
            @Override
            public void onResponse(JSONObject r) {
                DataUtil.getUserData(ctx, r, new DataUtil.JsonifyListener() {
                    @Override
                    public void onResponseJSon(ResponseDTO resp) {
                        response = resp;
                        cacheDataLocal(resp);
                    }

                    @Override
                    public void onError(String error) {
                        Util.showErrorToast(ctx, error);
                    }
                });
            }

            @Override
            public void onError(String error) {
                Util.showErrorToast(ctx, error);
            }
        });
    }

    private void cacheDataLocal(ResponseDTO resp) {
        CachedUtil.cacheData(ctx, resp, CachedUtil.CACHE_DASH_BOARD_DATA, new CachedUtil.CacheUtilListener() {
            @Override
            public void onFileDataDeserialized(ResponseDTO response) {

            }

            @Override
            public void onDataCached(ResponseDTO resp) {
                response = resp;
                Log.e(LOG, new Gson().toJson(resp));
                //  buildPages();
            }

            @Override
            public void onError() {

            }
        });
    }

    private void initializeAdapter() {
        try {
            adapter = new PagerAdapter(getSupportFragmentManager());
            mPager.setAdapter(adapter);
            mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageSelected(int arg0) {
                    PageFragment pf = pageFragmentList.get(arg0);
                    if (pf instanceof DashboardFragment) {

                    }

                }

                @Override
                public void onPageScrolled(int arg0, float arg1, int arg2) {

                }

                @Override
                public void onPageScrollStateChanged(int arg0) {
                }
            });
            //ZoomOutPageTransformerImpl z = new ZoomOutPageTransformerImpl();
            // mPager.setPageTransformer(true, z);
        } catch (Exception e) {
            Log.e(LOG, "-- Some shit happened, probably IllegalState of some kind ...{0}", e);
        }
    }

    private void getLocalStoredData() {
        CachedUtil.getCachedData(ctx, CachedUtil.CACHE_DASH_BOARD_DATA, new CachedUtil.CacheUtilListener() {
            @Override
            public void onFileDataDeserialized(ResponseDTO resp) {
                response = resp;
                buildPages();
                WebCheckResult wr = WebCheck.checkNetworkAvailability(ctx);
                if (wr.isMobileConnected() || wr.isWifiConnected()) {
                    loadDashboardData();
                }
            }

            @Override
            public void onDataCached(ResponseDTO response) {

            }

            @Override
            public void onError() {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getLocalStoredData();
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
            SharedUtil.logoutCommunityMember(ctx);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onIssueSelected(IssuesDTO issues) {
        Intent i = new Intent(MainPagerActivity.this, LogIssueActivity.class);
        i.putExtra("issue", issues);
        startActivity(i);
    }

    @Override
    public void onIssueLoggedFullView(ReportedissueDTO reportedissue) {

    }

    private class PagerAdapter extends FragmentStatePagerAdapter implements PageFragment {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {

            return (Fragment) pageFragmentList.get(i);
        }

        @Override
        public int getCount() {
            return pageFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String title = "Title";

            switch (position) {
                case 0:
                    title = "Report Issue";
                    break;
                case 1:
                    title = "Reported Issues";
                    break;

                default:
                    break;
            }
            return title;
        }

        @Override
        public void animateCounts() {

        }
    }

}
