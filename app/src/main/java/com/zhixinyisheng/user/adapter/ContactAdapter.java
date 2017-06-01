package com.zhixinyisheng.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.utils.EaseUserUtils;
import com.zhixinyisheng.user.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 焕焕 on 2017/4/18.
 */

public class ContactAdapter extends BaseExpandableListAdapter {
    private List<String> group = new ArrayList<>();
    private List<List<EaseUser>> list_child = new ArrayList<>();     //子列表
    private Context context;
    public OnArrowChangeListener onArrowChangeListener;
    public OnItemClickListener onItemClickListener;
    public OnItemLongClickListener onItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnArrowChangeListener(OnArrowChangeListener onArrowChangeListener) {
        this.onArrowChangeListener = onArrowChangeListener;
    }

    public ContactAdapter(Context context, List<String> group) {
        this.group = group;
        this.context = context;
    }

    public List<List<EaseUser>> getList_child() {
        return list_child;
    }

    public void setList_child(List<List<EaseUser>> list_child) {
        this.list_child = list_child;
        notifyDataSetChanged();
    }


    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list_child.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list_child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(
                R.layout.item_friends_group, null);
        TextView tv_area = (TextView) convertView.findViewById(R.id.tv_area);
        ImageView iv_arrow = (ImageView) convertView.findViewById(R.id.iv_jiantou);
        tv_area.setText(group.get(groupPosition));
        if (null!=onArrowChangeListener){
            onArrowChangeListener.onChanged(iv_arrow,groupPosition);
        }
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.ease_row_contact, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        EaseUser easeUser = list_child.get(groupPosition).get(childPosition);
        String username = easeUser.getUsername();
        EaseUserUtils.setUserNick(username, vh.tv);
        EaseUserUtils.setUserAvatar(context, username, vh.iv);
        vh.ll_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(groupPosition,childPosition);
                }
            }
        });
        vh.ll_contact.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(groupPosition,childPosition);
                }
                return false;
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    public interface OnArrowChangeListener{
        void onChanged(ImageView view,int groupPosition);
    }
    public interface OnItemClickListener{
        void onItemClick(int groupPosition, int childPosition);
    }
    public interface OnItemLongClickListener{
        void onItemLongClick(int groupPosition, int childPosition);
    }

    public class ViewHolder {
        ImageView iv;
        TextView tv;
        LinearLayout ll_contact;
        public ViewHolder(View v) {
            iv = (ImageView) v.findViewById(R.id.avatar);
            tv = (TextView) v.findViewById(R.id.name);
            ll_contact = (LinearLayout) v.findViewById(R.id.ll_contact);
        }
    }
}
