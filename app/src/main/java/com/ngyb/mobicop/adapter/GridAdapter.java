package com.ngyb.mobicop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngyb.mobicop.R;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/3 16:14
 */
public class GridAdapter extends BaseAdapter {
    private Context context;
    private String[] title = new String[]{"常用工具", "进程管理", "手机杀毒", "功能设置"};
    private String[] des = new String[]{"工具大全", "管理运行进程", "病毒无处藏身", "占位"};
    private int[] drawables = new int[]{R.drawable.cygj, R.drawable.jcgl, R.drawable.sjsd, R.drawable.srlj};

    public GridAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public String getItem(int position) {
        return title[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_grid_home, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.ivImage.setBackgroundResource(drawables[position]);
        viewHolder.tvTitle.setText(title[position]);
        viewHolder.tvDes.setText(des[position]);
        return convertView;
    }

    class ViewHolder {
        ImageView ivImage;
        TextView tvTitle;
        TextView tvDes;

        public ViewHolder(View view) {
            ivImage = view.findViewById(R.id.iv_image);
            tvTitle = view.findViewById(R.id.tv_title);
            tvDes = view.findViewById(R.id.tv_des);
        }
    }
}
