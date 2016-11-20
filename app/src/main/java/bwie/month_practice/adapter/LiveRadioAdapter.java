package bwie.month_practice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import bwie.month_practice.MainActivity;
import bwie.month_practice.R;
import bwie.month_practice.bean.LiveRadio;
import bwie.mylibrary.BaseViewHolder;
import bwie.mylibrary.GlideUtils;

/**
 * Created by zjj on 2016/11/19.
 */
public class LiveRadioAdapter extends RecyclerView.Adapter<LiveRadioAdapter.MyViewHolder> {
    private final Context context;
    private final List<LiveRadio.DataBean> data;


    public LiveRadioAdapter(Context context, List<LiveRadio.DataBean> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_item, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.setData(context, data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends BaseViewHolder<LiveRadio.DataBean> {
        private final ImageView img_pic;
        private final TextView tv_age;
        private final TextView tv_zy;
        private final TextView tv_introduction;

        public MyViewHolder(View itemView) {
            super(itemView);
            img_pic = (ImageView) itemView.findViewById(R.id.img_pic);
            tv_age = (TextView) itemView.findViewById(R.id.tv_age);
            tv_zy = (TextView) itemView.findViewById(R.id.tv_zy);
            tv_introduction = (TextView) itemView.findViewById(R.id.tv_introduction);
        }

        @Override
        public void setData(Context context, LiveRadio.DataBean dataBean) {
            GlideUtils.loadpics(context, dataBean.getUserImg(), img_pic);
            tv_age.setText(dataBean.getUserAge() + "");
            tv_zy.setText(dataBean.getOccupation());
            tv_introduction.setText(dataBean.getIntroduction());
        }
    }

}
