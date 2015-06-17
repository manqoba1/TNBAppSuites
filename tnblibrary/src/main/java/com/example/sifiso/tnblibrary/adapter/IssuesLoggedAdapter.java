package com.example.sifiso.tnblibrary.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sifiso.tnblibrary.R;
import com.example.sifiso.tnblibrary.models.IssuesDTO;
import com.example.sifiso.tnblibrary.models.ReportedissueDTO;

import java.util.List;

/**
 * Created by sifiso on 2015-06-12.
 */
public class IssuesLoggedAdapter extends RecyclerView.Adapter<IssuesLoggedAdapter.Holder> {
    private Context mContext;
    private List<ReportedissueDTO> mList;
    private int rowLayout;
    private IssuesLoggedAdapterListener listener;

    public IssuesLoggedAdapter(Context mContext, List<ReportedissueDTO> mList, int rowLayout, IssuesLoggedAdapterListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.rowLayout = rowLayout;
        this.listener = listener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_cardview, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder h, final int position) {
        final ReportedissueDTO is = mList.get(position);

        int rID = mContext.getResources().getIdentifier(is.getIcon(), "drawable", mContext.getPackageName());
        h.IC_image.setImageResource(rID);
        h.IC_issueName.setText(is.getIssueName());
        h.IC_refNo.setText(is.getRefNumber());
        h.IC_status.setText(is.getStatusreportedissueList().get(0).getStatusName());
        h.IC_date.setText(is.getDateReported());
        h.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReportedIssueSelected(is, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface IssuesLoggedAdapterListener {
        public void onReportedIssueSelected(ReportedissueDTO issues, int index);

    }

    public class Holder extends RecyclerView.ViewHolder {
        protected ImageView IC_image;
        protected TextView IC_issueName, IC_refNo, IC_status, IC_date;
        protected CardView cardView;

        public Holder(View itemView) {
            super(itemView);
            IC_issueName = (TextView) itemView.findViewById(R.id.IC_issueName);
            IC_refNo = (TextView) itemView.findViewById(R.id.IC_refNo);
            IC_status = (TextView) itemView.findViewById(R.id.IC_status);
            IC_date = (TextView) itemView.findViewById(R.id.IC_date);
            IC_image = (ImageView) itemView.findViewById(R.id.IC_image);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
