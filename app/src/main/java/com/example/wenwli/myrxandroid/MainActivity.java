package com.example.wenwli.myrxandroid;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mAppRecyclerView;
    private AppAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<AppInfo> appInfos = new ArrayList<>();

        AppInfo info = new AppInfo();
        info.setAppIcon(null);
        info.setAppName("QQ");

        AppInfo info1 = new AppInfo();
        info.setAppIcon(null);
        info.setAppName("Wechat");

        appInfos.add(info);
        appInfos.add(info1);

        mAppRecyclerView = findViewById(R.id.app_list);
        mAdapter = new AppAdapter(appInfos);
        mAppRecyclerView.setAdapter(mAdapter);
    }

    private class AppHolder extends RecyclerView.ViewHolder {

        private ImageView mAppIconImageView;
        private TextView mAppNameTextView;

        public AppHolder(View itemView) {
            super(itemView);
            mAppIconImageView = itemView.findViewById(R.id.app_image_view);
            mAppNameTextView = itemView.findViewById(R.id.app_text_view);
        }

        public void bind(AppInfo info) {
            mAppIconImageView.setImageDrawable(info.getAppIcon());
            mAppNameTextView.setText(info.getAppName());
        }
    }

    private class AppAdapter extends RecyclerView.Adapter<AppHolder> {

        private List<AppInfo> mAppInfos;

        public AppAdapter(List<AppInfo> appInfos) {
            mAppInfos = appInfos;
        }

        @NonNull
        @Override
        public AppHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_app_list, parent);
            return new AppHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull AppHolder holder, int position) {
            AppInfo info = mAppInfos.get(position);
            holder.bind(info);
        }

        @Override
        public int getItemCount() {
            return mAppInfos.size();
        }
    }
}
