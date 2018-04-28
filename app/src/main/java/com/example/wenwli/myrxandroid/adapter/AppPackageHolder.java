package com.example.wenwli.myrxandroid.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wenwli.myrxandroid.R;
import com.example.wenwli.myrxandroid.model.AppInfo;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppPackageHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.app_image_view)
    ImageView mAppIconImageView;
    @BindView(R.id.app_text_view)
    TextView mAppNameTextView;

    public AppPackageHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(AppInfo info) {
        mAppIconImageView.setImageDrawable(info.getAppIcon());
        mAppNameTextView.setText(info.getAppName());
    }
}
