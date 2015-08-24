package com.example.sifiso.tnbappsuites;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.sifiso.tnblibrary.adapter.StatusAdapter;
import com.example.sifiso.tnblibrary.models.IssueimageDTO;
import com.example.sifiso.tnblibrary.models.ReportedissueDTO;
import com.example.sifiso.tnblibrary.util.Util;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


public class FullViewIssueActivity extends ActionBarActivity {
    ImageView ALI_logo, ALI_camera;
    TextView ALI_issueName, AL_date, ALI_coordinates, AF_status, AF_ref;
    LinearLayout imageContainerLayout;
    ProgressBar progressBar2;
    LayoutInflater inflater;
    ListView AF_list;
    RelativeLayout relativeLayout2;
    private Context ctx;
    String LOG = FullViewIssueActivity.class.getSimpleName();
    ReportedissueDTO reportedissue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = getLayoutInflater();
        setContentView(R.layout.activity_full_view_issue);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ctx = getApplicationContext();
        reportedissue = (ReportedissueDTO) getIntent().getSerializableExtra("reportedIssue");
        try {
            Util.setCustomActionBar(ctx, getSupportActionBar(), "Full Detail View", ctx.getResources().getDrawable(R.mipmap.ic_launcher));
        } catch (Exception e) {
            Log.d(LOG, "{0}", e);
        }
        setFields();
    }

    private void setFields() {
        relativeLayout2 = (RelativeLayout) findViewById(R.id.relativeLayout2);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar);
        progressBar2.setVisibility(View.VISIBLE);
        ALI_logo = (ImageView) findViewById(R.id.ALI_logo);
        ALI_camera = (ImageView) findViewById(R.id.ALI_camera);
        ALI_camera.setVisibility(View.GONE);
        ALI_issueName = (TextView) findViewById(R.id.ALI_issueName);
        AL_date = (TextView) findViewById(R.id.AL_date);
        ALI_coordinates = (TextView) findViewById(R.id.ALI_coordinates);
        AF_status = (TextView) findViewById(R.id.AF_status);
        AF_ref = (TextView) findViewById(R.id.AF_ref);
        imageContainerLayout = (LinearLayout) findViewById(R.id.CAM_imageContainer);
        AF_list = (ListView) findViewById(R.id.AF_list);
        setFieldValues();

    }

    private void setFieldValues() {
        int rID = ctx.getResources().getIdentifier(reportedissue.getIcon(), "drawable", ctx.getPackageName());
        ALI_logo.setImageResource(rID);
        ALI_issueName.setText(reportedissue.getIssueName());
        AL_date.setText(reportedissue.getDateReported());
        ALI_coordinates.setText(reportedissue.getLatitude() + " " + reportedissue.getLongitude());
        AF_status.setText("Status: " + reportedissue.getStatusreportedissueList().get(0).getStatusName());
        AF_ref.setText("Reference number: " + reportedissue.getRefNumber());
        AF_list.setAdapter(new StatusAdapter(ctx, reportedissue.getStatusreportedissueList()));
        addImageToScroller();
    }

    private void addImageToScroller() {
        Log.i(LOG, "addImageToScroller");
        imageContainerLayout.removeAllViews();
        if (reportedissue.getIssueimageList().size() == 1) {
            imageContainerLayout.removeAllViews();
        }
        if (reportedissue.getIssueimageList().size() == 0) {
            relativeLayout2.setVisibility(View.GONE);
            return;
        }
        for (IssueimageDTO ii : reportedissue.getIssueimageList()) {
            View v = inflater.inflate(R.layout.scroller_image_template, null);
            ImageView img = (ImageView) v.findViewById(R.id.image);
            TextView num = (TextView) v.findViewById(R.id.number);
            num.setVisibility(View.GONE);
            final ProgressBar progressBar = (ProgressBar) v.findViewById(R.id.progressBar);
            ImageLoader.getInstance().displayImage(ii.getImageUrl(), img, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {

                }
            });
            imageContainerLayout.addView(v, 0);
        }
        //uploadPhotos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_full_view_issue, menu);
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
        if(id== android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        //  TimerUtil.killFlashTimer();
        super.onDestroy();
    }

    @Override
    public void onPause() {
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_right);
        super.onPause();
    }
}
