package com.example.clerkapp.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import de.hdodenhof.circleimageview.CircleImageView;

import com.example.sifiso.tnblibrary.R;
import com.example.sifiso.tnblibrary.models.ReportedissueDTO;
import com.example.sifiso.tnblibrary.util.Util;

import java.util.List;

/**
 * Created by sifiso on 2015-08-11.
 */
public class ClerkIssueAdapter extends ArrayAdapter<ReportedissueDTO> {
    private Context ctx;
    private List<ReportedissueDTO> mList;
    private int rowLayout;
    private ClerkIssueAdapterListener mListener;
    LayoutInflater mInflater;

    public ClerkIssueAdapter(Context context, int resource, List<ReportedissueDTO> list, ClerkIssueAdapterListener listener) {
        super(context, resource, list);
        rowLayout = resource;
        mList = list;
        mListener = listener;
        ctx = context;
        this.mInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public long getItemId(int position) {
        return mList.size();
    }

    @Override
    public ReportedissueDTO getItem(int position) {
        return mList.get(position);
    }

    @Override
    public View getView(final int position, View itemView, ViewGroup parent) {
        Holder h;
        if (itemView == null) {
            itemView = mInflater.inflate(rowLayout, null);
            h = new Holder();
            h.card_view = (CardView) itemView.findViewById(R.id.card_view);
            h.PSN_imagex = (CircleImageView) itemView.findViewById(R.id.PSN_imagex);
            h.PSN_txtDate = (TextView) itemView.findViewById(R.id.PSN_txtDate);
            h.PSN_txtStatus = (TextView) itemView.findViewById(R.id.PSN_txtStatus);
            h.PSN_txtName = (TextView) itemView.findViewById(R.id.PSN_txtName);
            h.txtRefNo = (TextView) itemView.findViewById(R.id.txtRefNo);
            itemView.setTag(h);
        } else {
            h = (Holder) itemView.getTag();
        }
        final ReportedissueDTO dto = mList.get(position);
        h.PSN_txtDate.setText(dto.getDateReported());
        int rID = ctx.getResources().getIdentifier(dto.getIcon(), "drawable", ctx.getPackageName());
        h.PSN_imagex.setImageResource(rID);
        if (dto.getStatusreportedissueList().get(0).getFlagDone() == 4) {
            h.PSN_txtStatus.setTextColor(ctx.getResources().getColor(R.color.green));
        } else if (dto.getStatusreportedissueList().get(0).getFlagDone() == 3) {
            h.PSN_txtStatus.setTextColor(ctx.getResources().getColor(R.color.orange));
        }

        h.PSN_txtStatus.setText(dto.getStatusreportedissueList().get(0).getStatusName());
        h.PSN_txtName.setText(dto.getIssueName());
        h.txtRefNo.setText("Ref: " + dto.getRefNumber());
        h.card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onIssueSelected(dto, position);
            }
        });
        return (itemView);
    }


    public class Holder {
        protected CardView card_view;
        protected CircleImageView PSN_imagex;
        protected TextView PSN_txtName, PSN_txtStatus, PSN_txtDate, txtRefNo;
    }

    public interface ClerkIssueAdapterListener {
        public void onIssueSelected(ReportedissueDTO issues, int index);

    }
}
