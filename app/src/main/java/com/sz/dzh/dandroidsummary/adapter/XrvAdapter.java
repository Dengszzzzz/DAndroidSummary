package com.sz.dzh.dandroidsummary.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sz.dzh.dandroidsummary.R;
import com.sz.dzh.dandroidsummary.bean.RxOperatorBean;

import java.util.List;

/**
 * Created by administrator on 2018/10/12.
 */
public class XrvAdapter extends RecyclerView.Adapter<XrvAdapter.ViewHolder> {

    private Context mContext;
    private List<RxOperatorBean> mList;

    public XrvAdapter(Context context,@Nullable List<RxOperatorBean> data) {
        mContext = context;
        mList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_select,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        holder.nameTv.setText(mList.get(i).getName());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
        }
    }


}
