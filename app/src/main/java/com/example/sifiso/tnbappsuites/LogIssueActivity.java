package com.example.sifiso.tnbappsuites;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sifiso.tnblibrary.models.CommunitymemberDTO;
import com.example.sifiso.tnblibrary.models.IssueimageDTO;
import com.example.sifiso.tnblibrary.models.IssuesDTO;
import com.example.sifiso.tnblibrary.models.ReportedissueDTO;
import com.example.sifiso.tnblibrary.util.CloudinaryUtil;
import com.example.sifiso.tnblibrary.util.DataUtil;
import com.example.sifiso.tnblibrary.util.ImageUtil;
import com.example.sifiso.tnblibrary.util.SharedUtil;
import com.example.sifiso.tnblibrary.util.Util;
import com.google.android.gms.location.LocationRequest;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class LogIssueActivity extends ActionBarActivity implements LocationListener {
    private ImageView ALI_logo, ALI_camera;
    private EditText ALI_message;
    private Button ALI_btnSave;
    private TextView ALI_coordinates, ALI_issueName;
    private Context ctx;
    LinearLayout imageContainerLayout;

    LocationRequest mLocationRequest;

    LayoutInflater inflater;
    File photoFile, currentThumbFile;
    Uri thumbUri, fileUri;
    Bitmap bitmapForScreen;
    String mCurrentPhotoPath;
    private CommunitymemberDTO communityMember;
    private IssuesDTO issues;
    public static final int CAPTURE_IMAGE = 1001;
    static final float ACCURACY_THRESHOLD = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG, "onCreate FIRED");
        ctx = getApplicationContext();
        inflater = getLayoutInflater();
        setContentView(R.layout.activity_log_issue);
        issues = (IssuesDTO) getIntent().getSerializableExtra("issue");
        communityMember = SharedUtil.getCommunityMember(ctx);
        try {
            Util.setCustomActionBar(ctx, getSupportActionBar(), "Login Screen", ctx.getResources().getDrawable(R.mipmap.ic_launcher));
        } catch (Exception e) {
            Log.d(LOG, "{0}", e);
        }
        setField();
    }


    private File createImageFile() throws IOException {
        //creating image file names

        String imageFileName = "pic" + System.currentTimeMillis();

        imageFileName = imageFileName + issues.getName() + "_" + issues.getIssuesID();
        File root;
        if (Util.hasStorage(true)) {
            Log.i(LOG, "fetch file from getExternalStoragePublicDirectory");
            root = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES);
        } else {
            Log.i(LOG, "fetch file from getDataDirectory");
            root = Environment.getDataDirectory();
        }
        File pics = new File(root, "tnb_app");
        if (!pics.exists()) {
            pics.mkdir();
        }
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                pics
        );

        //Saving file:path for use with Action_VIEW intents
        mCurrentPhotoPath = "file: " + image.getAbsolutePath();
        return image;
    }

    List<String> currentSessionPhotos = new ArrayList<String>();

    @Override
    public void onSaveInstanceState(Bundle b) {
        Log.e(LOG, "onSaveInstanceState FIRED");

        if (currentThumbFile != null) {
            b.putString("thumbPath", currentThumbFile.getAbsolutePath());
        }
        if (photoFile != null) {
            b.putString("photoFile", photoFile.getAbsolutePath());
        }

        if (location != null) {
            b.putDouble("latitude", location.getLatitude());
            b.putDouble("longitude", location.getLongitude());
            b.putFloat("accuracy", location.getAccuracy());
        }
        super.onSaveInstanceState(b);
    }

    @Override
    public void onPause() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        super.onPause();
        startScan();
    }

    class PhotoTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            Log.w(LOG, "starting PhotoTask doInBackground, file length: " + photoFile.length());

            ExifInterface exif = null;
            if (photoFile == null || photoFile.length() == 0) {
                Log.e(LOG, "photoFile is null, length = 0, CHEERS!");
                return 99;
            }
            fileUri = Uri.fromFile(photoFile);
            if (fileUri != null) {
                try {
                    exif = new ExifInterface(photoFile.getAbsolutePath());
                    String orient = exif.getAttribute(ExifInterface.TAG_ORIENTATION);
                    Log.i(LOG, "ORIENTATION: " + orient);
                    float rotate = 0f;
                    if (orient.equalsIgnoreCase("6")) {
                        rotate = 90f;
                        Log.i(LOG, "picture rotate: " + rotate);
                    }
                    try {
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Bitmap bm = BitmapFactory.decodeFile(photoFile.getAbsolutePath(), options);
                        getLog(bm, "Raw Camera - sample size = 2");
                        Matrix matrixThumbnail = new Matrix();
                        matrixThumbnail.postScale(0.4f, 0.4f);
                        Bitmap thumb = Bitmap.createBitmap
                                (bm, 0, 0, bm.getWidth(), bm.getHeight(), matrixThumbnail, true);
                        getLog(thumb, "thumb");

                        thumb = ImageUtil.drawTextToBitmap(ctx, thumb, location);
                        //appending date & gps co-ordinates to bitmap

                        currentThumbFile = ImageUtil.getFileFromBitmap(thumb, "t" + System.currentTimeMillis() + ".jpg");
                        bitmapForScreen = ImageUtil.getBitmapFromUri(ctx, Uri.fromFile(currentThumbFile));

                        thumbUri = Uri.fromFile(currentThumbFile);

                        //writing exif data
                        Util.writeLocationToExif(currentThumbFile.getAbsolutePath(), location);
                        boolean del = photoFile.delete();
                        Log.i(LOG, "Thumbnail file length: " + currentThumbFile.length() +
                                " main image file deleted: " + del);
                    } catch (Exception e) {
                        Log.e(LOG, "unable to process bitmap", e);
                        return 9;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return 1;
                }
            }
            return 0;
        }

        @Override
        protected void onPostExecute(Integer result) {
            if (result > 0) {
                Util.showErrorToast(ctx, getString(R.string.camera_error));
                return;
            }

            try {
                currentSessionPhotos.add(Uri.fromFile(currentThumbFile).toString());
                addImageToScroller();

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    private void addImageToScroller() {
        Log.i(LOG, "addImageToScroller");
        if (currentSessionPhotos.size() == 1) {
            imageContainerLayout.removeAllViews();
        }
        if (currentSessionPhotos.size() == 3) {
            // currentSessionPhotos.remove(4);
            Util.showToast(ctx, "You can only take three images");
            ALI_camera.setVisibility(View.GONE);
        }
        View v = inflater.inflate(R.layout.scroller_image_template, null);
        ImageView img = (ImageView) v.findViewById(R.id.image);
        TextView num = (TextView) v.findViewById(R.id.number);
        num.setText(" " + currentSessionPhotos.size());
        Uri uri = Uri.fromFile(currentThumbFile);
        ImageLoader.getInstance().displayImage(uri.toString(), img);
        imageContainerLayout.addView(v, 0);
        //uploadPhotos();
    }

    private Integer reportedIssueID;

    private void logIssueToServer() {
        ReportedissueDTO dto = new ReportedissueDTO();
        dto.setIssuesID(issues.getIssuesID());
        dto.setCommunitymemberID(communityMember.getCommunityMemberID());
        dto.setMunicipalityID(communityMember.getMunicipalityID());
        dto.setReviews(ALI_message.getText().toString());
        dto.setLatitude(location.getLatitude());
        dto.setLongitude(location.getLongitude());
        dto.setDateReported(new Date().toString());
        DataUtil.reportIssue(ctx, dto, new DataUtil.DataUtilInterface() {
            @Override
            public void onResponse(JSONObject r) {
                try {
                    if (r.getInt("success") == 0) {
                        Util.showErrorToast(ctx, r.getString("message"));
                    }
                    reportedIssueID = r.getInt("reportedIssueID");
                    uploadPhotos();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void uploadPhotos() {
        Log.w(LOG, "attempting to addTeamMemberEvaluation");
        for (int i = 0; i < currentSessionPhotos.size(); i++) {
            CloudinaryUtil.uploadImagesToCDN(ctx, new File(currentSessionPhotos.get(i)), new CloudinaryUtil.CloudinaryUtilListner() {
                @Override
                public void onSuccessUpload(Map uploadResult) {
                    String imageurl = (String) uploadResult.get("url");
                    addImageIssue(reportedIssueID, imageurl);
                }

                @Override
                public void onError() {

                }

                @Override
                public void onProgress(Integer upload) {

                }
            });
        }
    }

    private void addImageIssue(Integer id, String urlPath) {
        IssueimageDTO dto = new IssueimageDTO();
        dto.setReportedIssueID(id);
        dto.setImageUrl(urlPath);
        DataUtil.addIssueImages(ctx, dto, new DataUtil.DataUtilInterface() {
            @Override
            public void onResponse(JSONObject r) {
                try {
                    if (r.getInt("success") == 0) {
                        Util.showErrorToast(ctx, r.getString("message"));
                    }
                    Util.showToast(ctx, r.getString("message"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    @Override
    public void onActivityResult(final int requestCode, final int resultCode,
                                 final Intent data) {
        Log.e(LOG, "onActivityResult requestCode: " + requestCode + "resultCode" + requestCode);
        switch (requestCode) {
            case CAPTURE_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    if (photoFile != null) {
                        Log.e(LOG, "photo file length: " + photoFile.length());
                        new PhotoTask().execute();
                    }
                }
                break;

        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Intent is handled by camera activity
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e(LOG, "FAILED to createImageFile");
                Util.showErrorToast(ctx, getString(R.string.file_error));
                return;
            }
            if (photoFile != null) {
                Log.w(LOG, "start picture intent (dispatchTakePictureIntent)");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, CAPTURE_IMAGE);
            }

        }

    }

    private interface CacheListener {
        public void onCachingDone();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e(LOG, "onRestoreInstanceState" + savedInstanceState);

        String path = savedInstanceState.getString("photoFile");
        if (path != null) {
            photoFile = new File(path);
        }

        double lat = savedInstanceState.getDouble("latitude");
        double lng = savedInstanceState.getDouble("longitude");
        float acc = savedInstanceState.getFloat("accuracy");
        location = new Location(LocationManager.GPS_PROVIDER);
        location.setLatitude(lat);
        location.setLongitude(lng);
        location.setAccuracy(acc);
        Log.w(LOG, "location accuracy saved: " + acc);
        super.onRestoreInstanceState(savedInstanceState);
    }

    private void getLog(Bitmap bm, String which) {
        if (bm == null) return;
        Log.e(LOG, which + " - bitmap: width: "
                + bm.getWidth() + " height: "
                + bm.getHeight() + " rowBytes: "
                + bm.getRowBytes());
    }

    private void setField() {
        ALI_logo = (ImageView) findViewById(R.id.ALI_logo);
        int id = ctx.getResources().getIdentifier(issues.getIcon(), "drawable", ctx.getPackageName());
        ALI_logo.setImageResource(id);
        imageContainerLayout = (LinearLayout) findViewById(R.id.CAM_imageContainer);
        ALI_issueName = (TextView) findViewById(R.id.ALI_issueName);
        ALI_issueName.setText(issues.getName());
        ALI_message = (EditText) findViewById(R.id.ALI_message);
        ALI_coordinates = (TextView) findViewById(R.id.ALI_coordinates);
        ALI_btnSave = (Button) findViewById(R.id.ALI_btnSave);
        ALI_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIssueToServer();
            }
        });
        ALI_camera = (ImageView) findViewById(R.id.ALI_camera);
        ALI_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });
    }

    @Override
    public void onResume() {
        Log.d(LOG, "onResume FIRED");
        super.onResume();
        if (locationManager == null) {
            startScan();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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

    Location location;
    LocationManager locationManager;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 2;
    private static final long MIN_TIME_BW_UPDATES = 1;
    public boolean isGPSEnabled = false;
    public boolean isNetworkEnabled = false;
    public boolean canGetLocation = false;

    private Location getLocation() {
        onLocationChanged(location);
        return location;
    }

    public void startScan() {
        getLocation();
    }

    @Override
    public void onLocationChanged(Location location) {
        locationManager = (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
        isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        Log.v(LOG + " gps", "=" + isGPSEnabled);

        isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.v(LOG + " network", "=" + isNetworkEnabled);

        if (isGPSEnabled == false && isNetworkEnabled == false) {
            Log.d(LOG, "is not connected");
            showSettingDialog();
        } else {
            canGetLocation = true;
            if (isNetworkEnabled) {
                location = null;
                locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER, MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                Log.d(LOG, "Network");
                if (locationManager != null) {
                    location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        setGPSLocation(location);
                    }
                }
            }

            if (isGPSEnabled) {
                location = null;
                if (location == null) {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d(LOG, "GPs");
                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            setGPSLocation(location);
                        }
                    }
                }
            }

        }
    }

    public void showSettingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LogIssueActivity.this);

        builder.setTitle("GPS settings");
        builder.setMessage("GPS is not enabled. Do you want to go to settings menu, to search for location?");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void setGPSLocation(Location loc) {

        this.location = loc;

        Log.w(LOG, "### Passing " + loc.getAccuracy());
        ALI_coordinates.setText("Lat : " + loc.getLatitude() + " Lng : " + loc.getLongitude());
        if (loc.getAccuracy() <= ACCURACY_THRESHOLD) {

            location = loc;
            ALI_coordinates.setText("Lat : " + loc.getLatitude() + " Lng : " + loc.getLongitude());
            stopScan();

            //finish();
            Log.e(LOG, "+++ best accuracy found: " + location.getAccuracy());
        }
    }

    public void stopScan() {

        if (locationManager != null) {
            locationManager.removeUpdates(LogIssueActivity.this);
        }

    }

    static final String LOG = LogIssueActivity.class.getSimpleName();


    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
