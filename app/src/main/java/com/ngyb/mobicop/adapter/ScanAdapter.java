package com.ngyb.mobicop.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngyb.mobicop.R;
import com.ngyb.mobicop.bean.VirusInfoBean;

import java.util.List;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/9 16:37
 */
public class ScanAdapter extends BaseAdapter {
    private Context context;
    private List<VirusInfoBean> appInfoList;

    public ScanAdapter(Context context, List<VirusInfoBean> appInfoList) {
        this.context = context;
        this.appInfoList = appInfoList;
    }

    @Override
    public int getCount() {
        if (appInfoList != null && appInfoList.size() > 0) {
            return appInfoList.size();
        }
        return 0;
    }

    @Override
    public VirusInfoBean getItem(int position) {
        return appInfoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_anit_virus, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        VirusInfoBean bean = getItem(position);
        viewHolder.ivImage.setBackgroundDrawable(bean.drawable);
        viewHolder.tvAppName.setText(bean.name);
        if (bean.isVirus) {
            viewHolder.tvIsVirus.setText("病毒");
            viewHolder.tvIsVirus.setTextColor(Color.RED);
        } else {
            viewHolder.tvIsVirus.setText("安全");
            viewHolder.tvIsVirus.setTextColor(Color.GREEN);
        }
        return convertView;
    }

    class ViewHolder {

        private final TextView tvIsVirus;
        private final TextView tvAppName;
        private final ImageView ivImage;

        ViewHolder(View view) {
            ivImage = view.findViewById(R.id.iv_image);
            tvAppName = view.findViewById(R.id.tv_app_name);
            tvIsVirus = view.findViewById(R.id.tv_isvirus);
        }
    }
}
