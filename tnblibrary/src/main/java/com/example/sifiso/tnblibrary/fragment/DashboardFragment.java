package com.example.sifiso.tnblibrary.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sifiso.tnblibrary.R;
import com.example.sifiso.tnblibrary.adapter.IssueDashboardAdapter;
import com.example.sifiso.tnblibrary.models.IssuesDTO;
import com.example.sifiso.tnblibrary.models.ResponseDTO;
import com.example.sifiso.tnblibrary.viewsUtil.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link com.example.sifiso.tnblibrary.fragment.DashboardFragment.DashboardFragmentListener} interface
 * to handle interaction events.
 * Use the {@link DashboardFragment} factory method to
 * create an instance of this fragment.
 */
public class DashboardFragment extends Fragment implements PageFragment {


    private RecyclerView SD_list;
    private DashboardFragmentListener mListener;
    IssueDashboardAdapter dashboardAdapter;
    private List<IssuesDTO> issuesList;
    private ResponseDTO response;

    public DashboardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ctx = getActivity().getApplicationContext();
        response = (ResponseDTO) getArguments().getSerializable("response");
        setFields();
        return v;
    }

    View v;
    private Context ctx;

    private void setFields() {
        SD_list = (RecyclerView) v.findViewById(R.id.SD_list);
        SD_list.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        SD_list.setItemAnimator(new DefaultItemAnimator());
        SD_list.addItemDecoration(new DividerItemDecoration(ctx, RecyclerView.HORIZONTAL));
        setList();
    }

    private void setList() {
        if (issuesList == null) {
            issuesList = new ArrayList<IssuesDTO>();
        }
        dashboardAdapter = new IssueDashboardAdapter(ctx, response.getIssuesList(), R.layout.issue_item_card, new IssueDashboardAdapter.IssueAdapterListener() {
            @Override
            public void onIssueSelected(IssuesDTO issues, int index) {
                mListener.onIssueSelected(issues);
            }
        });
        SD_list.setAdapter(dashboardAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DashboardFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void animateCounts() {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface DashboardFragmentListener {
        // TODO: Update argument type and name
        public void onIssueSelected(IssuesDTO issues);
    }

}
