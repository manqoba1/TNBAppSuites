package com.example.sifiso.tnblibrary.adapter;

import android.content.Context;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sifiso.tnblibrary.R;
import com.example.sifiso.tnblibrary.models.IssuesDTO;

import java.util.List;

/**
 * Created by sifiso on 2015-05-19.
 */
public class IssueDashboardAdapter extends RecyclerView.Adapter<IssueDashboardAdapter.Holder> {

    private Context mContext;
    private List<IssuesDTO> mList;
    private int rowLayout;
    private IssueAdapterListener listener;

    public IssueDashboardAdapter(Context mContext, List<IssuesDTO> mList, int rowLayout, IssueAdapterListener listener) {
        this.mContext = mContext;
        this.mList = mList;
        this.rowLayout = rowLayout;
        this.listener = listener;
    }


    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.issue_item_card, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder h, final int position) {
        final IssuesDTO issues = mList.get(position);

        int rID = mContext.getResources().getIdentifier(issues.getIcon(), "drawable", mContext.getPackageName());

        h.INSC_image.setImageResource(rID);

        h.IIC_text.setText(issues.getName());

        h.INSC_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onIssueSelected(issues, position);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    public interface IssueAdapterListener {
        public void onIssueSelected(IssuesDTO issues, int index);

    }

    public class Holder extends RecyclerView.ViewHolder {
        protected ImageView INSC_image;
        protected TextView IIC_text;
        protected CardView cardView;

        public Holder(View itemView) {
            super(itemView);
            IIC_text = (TextView) itemView.findViewById(R.id.IIC_text);
            INSC_image = (ImageView) itemView.findViewById(R.id.INSC_image);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
        }
    }
}
