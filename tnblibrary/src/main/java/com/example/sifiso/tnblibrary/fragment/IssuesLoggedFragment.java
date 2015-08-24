package com.example.sifiso.tnblibrary.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.sifiso.tnblibrary.R;
import com.example.sifiso.tnblibrary.adapter.IssuesLoggedAdapter;
import com.example.sifiso.tnblibrary.models.ReportedissueDTO;
import com.example.sifiso.tnblibrary.models.ResponseDTO;
import com.example.sifiso.tnblibrary.viewsUtil.DividerItemDecoration;

import java.util.ArrayList;
import java.util.zip.Inflater;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link IssuesLoggedFragment.IssuesLoggedListener} interface
 * to handle interaction events.
 * Use the {@link IssuesLoggedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IssuesLoggedFragment extends Fragment implements PageFragment, SwipeRefreshLayout.OnRefreshListener {

    private RecyclerView SD_list;
    private TextView HERO_caption;
    private IssuesLoggedListener mListener;
    private ResponseDTO response;
    private IssuesLoggedAdapter loggedAdapter;
    View v;
    private Context ctx;
    private SwipeRefreshLayout refreshLayout;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment IssuesLoggedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static IssuesLoggedFragment newInstance(ArrayList<ReportedissueDTO> reportedissueList) {
        IssuesLoggedFragment fragment = new IssuesLoggedFragment();
        Bundle args = new Bundle();
        args.putSerializable("reportedIssueList", reportedissueList);
        fragment.setArguments(args);
        return fragment;
    }

    public IssuesLoggedFragment() {
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
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_issues_logged, container, false);
        ctx = getActivity().getApplicationContext();
        response = (ResponseDTO) getArguments().getSerializable("response");
        setFields();
        return v;

    }

    private void setFields() {
        LayoutInflater in = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vh = in.inflate(R.layout.hero_image, null);
        HERO_caption = (TextView) vh.findViewById(R.id.HERO_caption);
        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(this);
        //SD_list.addView(v, 0);

        setList();
    }

    private void setList() {
        SD_list = (RecyclerView) v.findViewById(R.id.SD_list);
        SD_list.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        SD_list.setItemAnimator(new DefaultItemAnimator());
        SD_list.addItemDecoration(new DividerItemDecoration(ctx, RecyclerView.HORIZONTAL));

        loggedAdapter = new IssuesLoggedAdapter(ctx, response.getReportedIssueList(), R.layout.issue_cardview, new IssuesLoggedAdapter.IssuesLoggedAdapterListener() {
            @Override
            public void onReportedIssueSelected(ReportedissueDTO issues, int index) {
                mListener.onIssueLoggedFullView(issues);
            }
        });
        SD_list.setAdapter(loggedAdapter);
        //   HERO_caption.setText(response.getReportedIssueList().size() + "");
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(ReportedissueDTO uri) {
        if (mListener != null) {
            mListener.onIssueLoggedFullView(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (IssuesLoggedListener) activity;
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

    public interface IssuesLoggedListener {
        // TODO: Update argument type and name
        public void onIssueLoggedFullView(ReportedissueDTO reportedissue);

        public void onPullRefresh();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        mListener.onPullRefresh();

    }
    public void setResponse(ResponseDTO resp){
        response = resp;
        setList();
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
}
