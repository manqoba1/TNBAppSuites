package com.example.clerkapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.example.sifiso.tnblibrary.models.ClerkDTO;
import com.example.sifiso.tnblibrary.models.ReportedissueDTO;
import com.example.sifiso.tnblibrary.models.ResponseDTO;
import com.example.sifiso.tnblibrary.models.StatusDTO;
import com.example.sifiso.tnblibrary.util.DataUtil;
import com.example.sifiso.tnblibrary.util.Util;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;


/**
 * An activity representing a list of ReportedIssues. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ReportedIssueDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ReportedIssueListFragment} and the item details
 * (if present) is a {@link ReportedIssueDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ReportedIssueListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ReportedIssueListActivity extends FragmentActivity
        implements ReportedIssueListFragment.Callbacks, ReportedIssueDetailFragment.ReportedIssueDetailFragmentListener {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    ClerkDTO clerk;
    private Context ctx;
    ReportedIssueListFragment reportedIssueListFragment;
    static String LOG = ReportedIssueListActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportedissue_list);
        ctx = getApplicationContext();
        clerk = (ClerkDTO) getIntent().getSerializableExtra("clerk");
        Log.i(LOG, new Gson().toJson(clerk));
        if (findViewById(R.id.reportedissue_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            reportedIssueListFragment = ((ReportedIssueListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.reportedissue_list));
            reportedIssueListFragment.setActivateOnItemClick(true);
            Bundle arg = new Bundle();
            arg.putSerializable("clerk", clerk);
//            reportedIssueListFragment.setArguments(arg);
            reportedIssueListFragment.setClerk(clerk);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link ReportedIssueListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */


    @Override
    public void onItemSelected(ReportedissueDTO reportedissue, int index, List<StatusDTO> statusList) {
        //1 Toast.makeText(ctx, reportedissue.getIssueName(), Toast.LENGTH_LONG).show();
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Toast.makeText(ctx, reportedissue.getIssueName(), Toast.LENGTH_LONG).show();
            Bundle arguments = new Bundle();
            arguments.putSerializable("reportedIssue", reportedissue);
            arguments.putInt("index", index);
            arguments.putSerializable("statuses", (Serializable) statusList);
            arguments.putSerializable("clerk", clerk);
            ReportedIssueDetailFragment fragment = new ReportedIssueDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.reportedissue_detail_container, fragment)
                    .commit();

        } else {
            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, ReportedIssueDetailActivity.class);
            detailIntent.putExtra("reportedIssue", reportedissue);
            detailIntent.putExtra("index", index);
            detailIntent.putExtra("statuses", (Serializable) statusList);
            detailIntent.putExtra("clerk", clerk);
            startActivity(detailIntent);
        }
    }


    @Override
    public void onUpdated(boolean update) {
        reportedIssueListFragment.resetList(update);
    }
}
