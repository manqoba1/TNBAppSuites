package com.example.clerkapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sifiso.tnblibrary.models.IssueimageDTO;
import com.example.sifiso.tnblibrary.models.ReportedissueDTO;
import com.example.sifiso.tnblibrary.models.StatusDTO;
import com.example.sifiso.tnblibrary.models.StatusreportedissueDTO;
import com.example.sifiso.tnblibrary.util.DataUtil;
import com.example.sifiso.tnblibrary.util.Util;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A fragment representing a single ReportedIssue detail screen.
 * This fragment is either contained in a {@link ReportedIssueListActivity}
 * in two-pane mode (on tablets) or a {@link ReportedIssueDetailActivity}
 * on handsets.
 */
public class ReportedIssueDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ReportedIssueDetailFragment() {
    }

    public interface ReportedIssueDetailFragmentListener {
        public void onUpdated(boolean update);
    }

    ReportedissueDTO reportedissue;
    List<StatusDTO> statusList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments().containsKey("reportedIssue")) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            reportedissue = (ReportedissueDTO) getArguments().getSerializable("reportedIssue");
            statusList = (List<StatusDTO>) getArguments().getSerializable("statuses");
        }
    }

    View v;

    private Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflat, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflat.inflate(R.layout.fragment_reportedissue_detail, container, false);
        ctx = getActivity().getApplicationContext();
        inflater = getActivity().getLayoutInflater();
        setFields();
        if (reportedissue != null) {
            // ((TextView) rootView.findViewById(R.id.reportedissue_detail)).setText(mItem.content);

            addImageToScroller();
            setSpinner();
            int rID = ctx.getResources().getIdentifier(reportedissue.getIcon(), "drawable", ctx.getPackageName());
            iconImage.setImageResource(rID);
            iconImage.setDrawingCacheEnabled(true);
            txtIssueName.setText(reportedissue.getIssueName());
            txtStatus.setText(reportedissue.getStatusreportedissueList().get(0).getStatusName());
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), rID);
            iconImage.setDrawingCacheEnabled(false);
            Palette palette = Palette.generate(bitmap);
            txtIssueName.setTextColor(palette.getMutedColor(0x000000));
            txtStatus.setTextColor(palette.getMutedColor(0x000000));
            mCallbacks.onUpdated(false);
        }

        return v;
    }

    Spinner spStatus;
    EditText txtMessage;
    Button btnUpdate;
    TextView txtIssueName, txtStatus;
    ImageView iconImage;

    private void setFields() {
        imageContainerLayout = (LinearLayout) v.findViewById(R.id.CAM_imageContainer);
        spStatus = (Spinner) v.findViewById(R.id.spStatus);
        txtMessage = (EditText) v.findViewById(R.id.txtMessage);
        txtStatus = (TextView) v.findViewById(R.id.txtStatus);
        btnUpdate = (Button) v.findViewById(R.id.btnUpdate);
        iconImage = (ImageView) v.findViewById(R.id.iconImage);
        txtIssueName = (TextView) v.findViewById(R.id.txtIssueName);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flagDone == null) {
                    return;
                }
                if (statusID == null) {
                    return;
                }

                sendUpdate();
            }
        });
    }

    List<String> strSpinner;

    private void setSpinner() {
        strSpinner = new ArrayList<>();
        strSpinner.add("Select status");
        for (StatusDTO s : statusList) {
            strSpinner.add(s.getStatusName());
        }
        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(ctx, R.layout.xsimple_spinner_item, strSpinner);
        spStatus.setAdapter(statusAdapter);
        spStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    return;
                }
                statusID = statusList.get(position - 1).getStatusID();
                Log.d(LOG, "Status : " + statusID);
                if (position == 3) {
                    flagDone = 2;
                    Log.d(LOG, "Status1 : " + flagDone);
                    return;
                }

                flagDone = 1;
                Log.d(LOG, "Status2 : " + flagDone);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void sendUpdate() {

        StatusreportedissueDTO statusreportedissue = new StatusreportedissueDTO();
        statusreportedissue.setStatusID(statusID);
        statusreportedissue.setStatusReportedIssueID(reportedissue.getStatusreportedissueList().get(0).getStatusReportedIssueID());
        statusreportedissue.setReportedIssueID(reportedissue.getReportedIssueID());
        statusreportedissue.setFlagDone(flagDone);
        statusreportedissue.setStatusReportedDate(Util.getLongDateTime(new Date()));
        DataUtil.updateStatus(ctx, statusreportedissue, new DataUtil.DataUtilInterface() {
            @Override
            public void onResponse(JSONObject r) {
                try {
                    if (r.getInt("success") == 0) {
                        Util.showErrorToast(ctx, r.getString("message"));
                        mCallbacks.onUpdated(false);
                    }
                    mCallbacks.onUpdated(true);
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof ReportedIssueDetailFragmentListener)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (ReportedIssueDetailFragmentListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }

    ReportedIssueDetailFragmentListener sDummyCallbacks = new ReportedIssueDetailFragmentListener() {
        @Override
        public void onUpdated(boolean refNo) {

        }
    };
    private ReportedIssueDetailFragmentListener mCallbacks;


    static String LOG = ReportedIssueDetailFragment.class.getSimpleName();
    LinearLayout imageContainerLayout;
    LayoutInflater inflater;

    private Integer statusID, flagDone, reportedID;

    private void addImageToScroller() {
        Log.i(LOG, "addImageToScroller");
        imageContainerLayout.removeAllViews();
        if (reportedissue.getIssueimageList().size() == 1) {
            imageContainerLayout.removeAllViews();
        }
        if (reportedissue.getIssueimageList().size() == 0) {
            //relativeLayout2.setVisibility(View.GONE);
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

}
