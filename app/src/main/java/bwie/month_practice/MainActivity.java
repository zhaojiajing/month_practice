package bwie.month_practice;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import com.google.gson.Gson;
import java.io.File;
import java.io.IOException;
import java.util.List;
import butterknife.Bind;
import bwie.month_practice.adapter.LiveRadioAdapter;
import bwie.month_practice.bean.LiveRadio;
import bwie.mylibrary.BaseActivity;
import bwie.mylibrary.OkHttpUtils;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends BaseActivity implements View.OnClickListener {


    @Bind(R.id.mrecycleView)
    RecyclerView mrecycleView;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.button2)
    Button button2;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String json = (String) msg.obj;
            LiveRadio liveRadio = new Gson().fromJson(json, LiveRadio.class);
            List<LiveRadio.DataBean> data = liveRadio.getData();
            mrecycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
            mrecycleView.setAdapter(new LiveRadioAdapter(MainActivity.this, data));
            mrecycleView.setOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    //RecyclerView的SCROLL_STATE_IDLE*******
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        /**
                         *在滑动的空闲状态时 计算缓存大小
                         */
                        count();
                    }

                }
            });
        }
    };

    @Override
    public int bindlayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void initdata() {

    }

    @Override
    public void loaddata() {
        //请求数据
        String url = "http://www.yulin520.com/a2a/impressApi/news/mergeList?sign=C7548DE604BCB8A17592EFB9006F9265&pageSize=10&gender=2&ts=1871746850&page=1";
        OkHttpUtils.get(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json = response.body().string();
                handler.obtainMessage(1, json).sendToTarget();
            }
        });
        //计算缓存大小
        // count();
    }

    private void count() {
        File cacheDir = getCacheDir();
        String path = cacheDir.getPath() + "/image_manager_disk_cache";
        File file = new File(path);

        File[] files = file.listFiles();
        long sumsize = 0;
        for (File f : files) {
            sumsize += f.length();
        }
        button2.setText("清除缓存\n" + Formatter.formatFileSize(this, sumsize));

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button://定位
                startActivity(new Intent(this, MapActivity.class));
                break;
            case R.id.button2://清缓存
                // clear();
                clearwj(getCacheDir());
                count();
                break;
        }
    }

    private void clearwj(File file) {
        //列出所以文件
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                clearwj(f);
            } else {
                f.delete();
            }
        }

    }

    private void clear() {
        String path = getCacheDir().getPath() + "/image_manager_disk_cache";
        File file = new File(path);
        File[] files = file.listFiles();
        for (File f : files) {
            f.delete();
        }

    }
}
