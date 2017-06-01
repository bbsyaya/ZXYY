package com.zhixinyisheng.user.adapter.doctor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.zhixinyisheng.user.R;
import com.zhixinyisheng.user.util.DensityUtils;
import com.zhixinyisheng.user.util.GlideUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.finalteam.galleryfinal.model.PhotoInfo;

import static com.and.yzy.frame.application.BaseApplication.mContext;

/**
 * author & yuanyx
 * create at  2016/6/6 18:07
 */
public class ChooseIconAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<PhotoInfo> data;
    private Object defaultItem = new Object();
    private ImageView defaultView;
    private Context context;

    private List<String> strings;

    public List<String> getStrings() {
        return strings;
    }

    public void setString(String string) {
        if (strings == null) {
            strings = new ArrayList<>();
        }
        strings.add(string);
    }

    public ChooseIconAdapter(Context context) {
        super();
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public List<PhotoInfo> getData() {
        if (this.data == null) {
            this.data = new ArrayList<>();
        }
        return this.data;
    }

    public void setData(List<PhotoInfo> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(PhotoInfo str) {
        if (data == null) {
            this.data = new ArrayList<>();
        }
        data.add(str);
        notifyDataSetChanged();
    }
    public void deleteData(int pos){
        data.remove(pos);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if (data == null) {
            return 1;
        }
        if (data.size()<9){
            return data.size() + 1;
        }else{
            return 9;
        }

    }

    @Override
    public Object getItem(int position) {
        if (data == null || position >= data.size()) {
            return defaultItem;
        }
        return data.get(position);
    }

    public boolean isDefaultItem(Object item) {
        return item.equals(defaultItem);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (data == null || position >= data.size()) {
            return getDefaultView();
        }
        ViewHolder viewHolder;
        if (convertView == null || convertView == getDefaultView()) {
            convertView = inflater.inflate(R.layout.item_image, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        PhotoInfo info = (PhotoInfo) getItem(position);
        String str = info.getPhotoPath();
        if ( info.getHeight()!=0){
            GlideUtil.loadlocalImage(mContext, new File(str), viewHolder.iv);
        }else{
            GlideUtil.loadlocalImage(mContext, str, viewHolder.iv);
        }


        return convertView;
    }

    public ImageView getDefaultView() {
        if (defaultView == null) {
            defaultView = new ImageView(inflater.getContext());
            LayoutParams params = new LayoutParams(DensityUtils.dp2px(110),
                    DensityUtils.dp2px(110));
            defaultView.setLayoutParams(params);
            defaultView.setImageResource(R.mipmap.ic_jubao_add);
        }
        return defaultView;
    }

    static class ViewHolder {

        @Bind(R.id.iv)
        ImageView iv;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
