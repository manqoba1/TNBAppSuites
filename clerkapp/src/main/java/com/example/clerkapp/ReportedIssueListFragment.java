package com.example.clerkapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clerkapp.adapters.ClerkIssueAdapter;
import com.example.sifiso.tnblibrary.models.ClerkDTO;
import com.example.sifiso.tnblibrary.models.ReportedissueDTO;
import com.example.sifiso.tnblibrary.models.ResponseDTO;
import com.example.sifiso.tnblibrary.models.StatusDTO;
import com.example.sifiso.tnblibrary.util.DataUtil;
import com.example.sifiso.tnblibrary.util.Util;
import com.example.sifiso.tnblibrary.viewsUtil.DividerItemDecoration;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A list fragment representing a list of ReportedIssues. This fragment
 * also supports tablet devices by allowing list items to be given an
 * 'activated' state upon selection. This helps indicate which item is
 * currently being viewed in a {@link ReportedIssueDetailFragment}.
 * <p>
 * Activities containing this fragment MUST implement the {@link Callbacks}
 * interface.
 */
public class ReportedIssueListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    ClerkIssueAdapter adapter;
    /**
     * The serialization (saved instance state) Bundle key representing the
     * activated item position. Only used on tablets.
     */
    private static final String STATE_ACTIVATED_POSITION = "activated_position";

    /**
     * The fragment's current callback object, which is notified of list item
     * clicks.
     */
    private Callbacks mCallbacks = sDummyCallbacks;

    /**
     * The current activated item position. Only used on tablets.
     */
    private int mActivatedPosition = ListView.INVALID_POSITION;

    ClerkDTO clerk;

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        getIssueFromServer();
    }

    public void refreshListStop() {
        //do processing to get new data and set your listview's adapter, maybe  reinitialise the loaders you may be using or so

        //when your data has finished loading, cset the refresh state of the view to false
        refreshLayout.setRefreshing(false);
       /* new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);


            }
        }, 5000);*/

    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callbacks {
        /**
         * Callback for when an item has been selected.
         */
        public void onItemSelected(ReportedissueDTO reportedissue, int index, List<StatusDTO> statusList);

    }

    /**
     * A dummy implementation of the {@link Callbacks} interface that does
     * nothing. Used only when this fragment is not attached to an activity.
     */
    private static Callbacks sDummyCallbacks = new Callbacks() {
        @Override
        public void onItemSelected(ReportedissueDTO reportedissue, int index, List<StatusDTO> statusList) {

        }
    };

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */

    public ReportedIssueListFragment() {
    }

    private Context ctx;
    View v;

    private TextView clerkName;
    private ListView recyclerView;
    private AutoCompleteTextView act_search;
    private SwipeRefreshLayout refreshLayout;

    private void setFields() {
        recyclerView = (ListView) v.findViewById(R.id.FCL_list);
        clerkName = (TextView) v.findViewById(R.id.txtClerk_name);
        act_search = (AutoCompleteTextView) v.findViewById(R.id.act_search);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(com.example.sifiso.tnblibrary.R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
    }

    List<String> refTitles;
    int indexRef;

    private void setAutoTextData() {
        hideKeyboard();
        refTitles = new ArrayList<>();
        for (ReportedissueDTO r : response.getReportedIssueList()) {
            refTitles.add(r.getRefNumber().trim());
        }
        ArrayAdapter<String> riverSpinner = new ArrayAdapter<String>(ctx, R.layout.xsimple_spinner_item, refTitles);
        act_search.setAdapter(riverSpinner);
        act_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // hideKeyboard();
                indexRef = searchRiver(parent.getItemAtPosition(position).toString());
                act_search.setText(response.getReportedIssueList().get(indexRef).getRefNumber().trim());

            }
        });


    }

    boolean isFound;

    private Integer searchRiver(String text) {

        int index = 0;

        for (int i = 0; i < response.getReportedIssueList().size(); i++) {
            ReportedissueDTO searchRiver = response.getReportedIssueList().get(i);
            if (searchRiver.getRefNumber().contains(text)) {
                isFound = true;
                break;
            }
            index++;
        }
        if (isFound) {
            recyclerView.setSelection(index);
            hideKeyboard();
        } else {
            hideKeyboard();
             Util.showToast(ctx, "Issue Ref Number Not Found..");
        }
        hideKeyboard();
        return index;
    }

    void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(act_search.getWindowToken(), 0);
        //imm.hideSoftInputFromWindow(WT_sp_river.getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.list_item_nav, container, false);
        ctx = getActivity().getApplicationContext();

        setFields();

        return v;
    }

    public void setClerk(ClerkDTO c) {
        clerk = c;
        if (v != null) {
            clerkName.setText(clerk.getName() + " " + clerk.getSurname());
            getIssueFromServer();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        // TODO: replace with a real list adapter.


    }

    private void setList() {

        adapter = new ClerkIssueAdapter(ctx, R.layout.clerk_card, response.getReportedIssueList(), new ClerkIssueAdapter.ClerkIssueAdapterListener() {
            @Override
            public void onIssueSelected(ReportedissueDTO issues, int index) {
                Snackbar.make(recyclerView, issues.getIssueName(), Snackbar.LENGTH_LONG).show();
                mActivatedPosition = index;
                mCallbacks.onItemSelected(issues, index, response.getStatusList());
                setActivatedPosition(mActivatedPosition);

            }
        });
        recyclerView.setAdapter(adapter);
        setActivatedPosition(mActivatedPosition);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Restore the previously serialized activated item position.
        if (savedInstanceState != null
                && savedInstanceState.containsKey(STATE_ACTIVATED_POSITION)) {
            setActivatedPosition(savedInstanceState.getInt(STATE_ACTIVATED_POSITION));
            clerk = (ClerkDTO) savedInstanceState.getSerializable("clerk");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // Activities containing this fragment must implement its callbacks.
        if (!(activity instanceof Callbacks)) {
            throw new IllegalStateException("Activity must implement fragment's callbacks.");
        }

        mCallbacks = (Callbacks) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        // Reset the active callbacks interface to the dummy implementation.
        mCallbacks = sDummyCallbacks;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("clerk", clerk);
        if (mActivatedPosition != ListView.INVALID_POSITION) {
            // Serialize and persist the activated item position.
            outState.putInt(STATE_ACTIVATED_POSITION, mActivatedPosition);
        }
    }


    /**
     * Turns on activate-on-click mode. When this mode is on, list items will be
     * given the 'activated' state when touched.
     */
    public void setActivateOnItemClick(boolean activateOnItemClick) {
        // When setting CHOICE_MODE_SINGLE, ListView will automatically
        // give items the 'activated' state when touched.
        recyclerView.setChoiceMode(activateOnItemClick
                ? ListView.CHOICE_MODE_SINGLE
                : ListView.CHOICE_MODE_NONE);
    }

    private void setActivatedPosition(int position) {
        if (position == ListView.INVALID_POSITION) {
            recyclerView.setItemChecked(mActivatedPosition, false);
        } else {
            recyclerView.setItemChecked(position, true);

        }

        mActivatedPosition = position;
    }

    ResponseDTO response;

    public void resetList(boolean upDate) {
        this.upDate = upDate;
        if (upDate) {
            getIssueFromServer();
        }

    }

    boolean upDate;

    private void getIssueFromServer() {
        DataUtil.loadClerkData(ctx, clerk.getMunicipalityID(), clerk.getClerkID(), new DataUtil.DataUtilInterface() {
            @Override
            public void onResponse(JSONObject r) {
                DataUtil.getUserData(ctx, r, new DataUtil.JsonifyListener() {
                    @Override
                    public void onResponseJSon(ResponseDTO resp) {
                        refreshListStop();
                        response = resp;
                        setList();
                        setAutoTextData();
                        if (upDate) {
                            recyclerView.setSelection(0);
                            mCallbacks.onItemSelected(adapter.getItem(0), mActivatedPosition, response.getStatusList());
                            return;
                        }
                        recyclerView.setSelection(0);
                        mCallbacks.onItemSelected(adapter.getItem(0), mActivatedPosition, response.getStatusList());
                    }

                    @Override
                    public void onError(String error) {
                        Util.showErrorToast(ctx, error);
                        refreshListStop();
                    }
                });
            }

            @Override
            public void onError(String error) {
                Util.showErrorToast(ctx, error);

            }
        });
    }
}
