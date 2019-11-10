package com.ngyb.mobicop.adapter;

import android.content.Context;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.ngyb.mobicop.R;
import com.ngyb.utils.bean.ProcessBean;

import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * 作者：南宫燚滨
 * 描述：
 * 邮箱：nangongyibin@gmail.com
 * 日期：2019/11/10 14:48
 */
public class ProcessManagerAdapter extends BaseAdapter implements StickyListHeadersAdapter {
    private Context context;
    private boolean showSys;
    private List<ProcessBean> processInfoList;
    private List<ProcessBean> customerList;
    private List<ProcessBean> systemList;

    public ProcessManagerAdapter(Context context, List<ProcessBean> processInfoList, List<ProcessBean> customerList, List<ProcessBean> systemList) {
        this.context = context;
        this.processInfoList = processInfoList;
        this.customerList = customerList;
        this.systemList = systemList;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        TitleViewHold titleViewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_process_text, null);
            titleViewHold = new TitleViewHold(convertView);
            convertView.setTag(titleViewHold);
        } else {
            titleViewHold = (TitleViewHold) convertView.getTag();
        }
        if (!processInfoList.get(position).isSys()) {
            titleViewHold.tvProcessTitle.setText("用户进程(" + customerList.size() + ")");
        } else {
            titleViewHold.tvProcessTitle.setText("系统进程(" + systemList.size() + ")");
        }
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        if (!processInfoList.get(position).isSys()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getCount() {
        if (showSys) {
            return processInfoList.size();
        } else {
            return customerList.size();
        }
    }

    @Override
    public ProcessBean getItem(int position) {
        if (showSys) {
            return processInfoList.get(position);
        } else {
            return customerList.get(position);
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_process, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ProcessBean processBean = getItem(position);
        viewHolder.ivIcon.setBackgroundDrawable(processBean.getDrawable());
        viewHolder.tvName.setText(processBean.getName());
        String strSize = Formatter.formatFileSize(context, processBean.getSize());
        viewHolder.tvSize.setText(strSize);
        String packageName = context.getPackageName();
        if (packageName.equals(processBean.getPackageName())) {
            viewHolder.cbBox.setVisibility(View.GONE);
        } else {
            viewHolder.cbBox.setVisibility(View.VISIBLE);
        }
        viewHolder.cbBox.setChecked(processBean.isCheck());
        return convertView;
    }

    public void setShowSys(Boolean showSys) {
        this.showSys = showSys;
    }

    class ViewHolder {

        private final ImageView ivIcon;
        private final TextView tvName;
        private final TextView tvSize;
        private final CheckBox cbBox;

        ViewHolder(View view) {
            ivIcon = view.findViewById(R.id.iv_icon);
            tvName = view.findViewById(R.id.tv_name);
            tvSize = view.findViewById(R.id.tv_size);
            cbBox = view.findViewById(R.id.cb_box);
        }
    }

    class TitleViewHold {

        private final TextView tvProcessTitle;

        TitleViewHold(View view) {
            tvProcessTitle = view.findViewById(R.id.tv_process_title);
        }
    }
}
