package com.example.sifiso.tnblibrary.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sifiso.tnblibrary.R;
import com.example.sifiso.tnblibrary.models.IssueimageDTO;
import com.example.sifiso.tnblibrary.models.StatusreportedissueDTO;

import java.util.List;

/**
 * Created by sifiso on 2015-06-24.
 */
public class StatusAdapter extends BaseAdapter {

    Context mCtx;
    List<StatusreportedissueDTO> mList;

    public StatusAdapter(Context mCtx, List<StatusreportedissueDTO> mList) {
        this.mCtx = mCtx;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        Holder h;
        if (v == null) {
            h = new Holder();
            LayoutInflater inflater = (LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.status_item, parent, false);
            h.ALI_status = (TextView) v.findViewById(R.id.ALI_status);
            h.ALI_logo = (ImageView) v.findViewById(R.id.ALI_logo);
            h.AL_date = (TextView) v.findViewById(R.id.AL_date);
            v.setTag(h);
        } else {
            h = (Holder) v.getTag();
        }
        StatusreportedissueDTO s = mList.get(position);
        //int rID = mCtx.getResources().getIdentifier(s.ge(), "drawable", mCtx.getPackageName());
        h.AL_date.setText(s.getStatusReportedDate());
        h.ALI_logo.setImageDrawable(mCtx.getResources().getDrawable(android.R.drawable.sym_def_app_icon));
        h.ALI_status.setText(s.getStatusName());
        return v;
    }

    public void animateView(final View view) {
        Animation a = AnimationUtils.loadAnimation(mCtx, R.anim.grow_fade_in_center);
        a.setDuration(500);
        if (view == null)
            return;
        view.startAnimation(a);
    }

    class Holder {
        TextView ALI_status, AL_date;
        ImageView ALI_logo;
    }
}
