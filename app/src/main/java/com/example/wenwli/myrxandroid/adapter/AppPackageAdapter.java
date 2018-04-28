package com.example.wenwli.myrxandroid.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.wenwli.myrxandroid.R;
import com.example.wenwli.myrxandroid.model.AppInfo;

import java.util.List;

public class AppPackageAdapter extends RecyclerView.Adapter<AppPackageHolder> {

    private List<AppInfo> mAppInfos;

    public AppPackageAdapter(List<AppInfo> appInfos) {
        mAppInfos = appInfos;
    }

    @NonNull
    @Override
    public AppPackageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_app_list, parent, false);
        return new AppPackageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppPackageHolder holder, int position) {
        AppInfo info = mAppInfos.get(position);
        holder.bind(info);
    }

    @Override
    public int getItemCount() {
        return mAppInfos.size();
    }
}
