package com.zhixinyisheng.user.ui.IM.ui.friend;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.easeui.domain.EaseUser;
import com.zhixinyisheng.user.R;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


@SuppressLint("DefaultLocale")
public class FriendsAdapter extends BaseAdapter implements SectionIndexer {
    private Context ct;
    private List<EaseUser> data;

    public FriendsAdapter(Context ct, List<EaseUser> datas) {
        this.ct = ct;
        this.data = datas;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     *
     * @param list
     */
    public void updateListView(List<EaseUser> list) {
        this.data = list;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(ct).inflate(
                    R.layout.ease_row_contact, null);
            holder = new ViewHolder();
            holder.avatar = (ImageView) convertView.findViewById(com.hyphenate.easeui.R.id.avatar);
            holder.name = (TextView) convertView.findViewById(com.hyphenate.easeui.R.id.name);
            holder.alpha = (TextView) convertView.findViewById(com.hyphenate.easeui.R.id.header);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        EaseUser friend = data.get(position);
        final String name = friend.getNick();
        final String avatar = friend.getAvatar();

        Glide.with(ct).load(avatar)
                .placeholder(com.hyphenate.easeui.R.drawable.ic_launcher2)//占位图
                .error(com.hyphenate.easeui.R.drawable.ic_launcher2)//加载错误图
                .bitmapTransform(new CropCircleTransformation(ct))//裁剪圆形
                .into(holder.avatar);
        holder.name.setText(name);

        // 根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);
        // 如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            holder.alpha.setVisibility(View.VISIBLE);
            holder.alpha.setText(friend.getSortLetters());
        } else {
            holder.alpha.setVisibility(View.GONE);
        }



        return convertView;
    }

    static class ViewHolder {
        TextView alpha;// 首字母提示
        ImageView avatar;
        TextView name;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return data.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    @SuppressLint("DefaultLocale")
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = data.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section){
                return i;
            }
        }

        return -1;
    }

    @Override
    public Object[] getSections() {
        return null;
    }

}