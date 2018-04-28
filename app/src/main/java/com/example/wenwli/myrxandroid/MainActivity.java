package com.example.wenwli.myrxandroid;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mAppRecyclerView;

    private List<AppInfo> mAppInfos = new ArrayList<>();
    private AppAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdapter = new AppAdapter(mAppInfos);

        mAppRecyclerView = findViewById(R.id.app_list);
        mAppRecyclerView.setHasFixedSize(true);
        mAppRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // TODO: Why can't create ViewHolder without set the LayoutManager
        mAppRecyclerView.setAdapter(mAdapter);

        loadApp();
    }

    private void loadApp() {
        final PackageManager pm = getPackageManager();

        Observable.create(new Observable.OnSubscribe<ApplicationInfo>() {
            @Override
            public void call(Subscriber<? super ApplicationInfo> subscriber) {
                List<ApplicationInfo> infos = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
                for (ApplicationInfo info : infos) {
                    subscriber.onNext(info);
                }
                subscriber.onCompleted();
            }
        }).filter(new Func1<ApplicationInfo, Boolean>() {
            @Override
            public Boolean call(ApplicationInfo applicationInfo) {
                return (applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) <= 0;
            }
        }).map(new Func1<ApplicationInfo, AppInfo>() {
            @Override
            public AppInfo call(ApplicationInfo applicationInfo) {
                AppInfo info = new AppInfo();
                info.setAppName(applicationInfo.loadLabel(pm).toString());
                info.setAppIcon(applicationInfo.loadIcon(pm));
                return info;
            }
        }).subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Observer<AppInfo>() {
            @Override
            public void onCompleted() {
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(AppInfo appInfo) {
                mAppInfos.add(appInfo);
            }
        });
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
            View view = getLayoutInflater().inflate(R.layout.item_app_list, parent, false);
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
