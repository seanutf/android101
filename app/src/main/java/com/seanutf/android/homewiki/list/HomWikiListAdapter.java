package com.seanutf.android.homewiki.list;

import android.app.Activity;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seanutf.android.base.BaseApp;
import com.seanutf.android.base.homwiki.data.WikiData;
import com.seanutf.android.base.media.load.ImageLoader;
import com.seanutf.android.commonutil.util.CollectionUtils;
import com.seanutf.android.databinding.ItemWikiListBinding;

import java.util.List;

public class HomWikiListAdapter extends RecyclerView.Adapter {

    List<WikiData> list;

    public void setData(List<WikiData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWikiListBinding vbItem = ItemWikiListBinding.inflate(((Activity) parent.getContext()).getLayoutInflater(), parent, false);
        return new HomeWikiListViewHolder(vbItem);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (CollectionUtils.isNotEmptyList(list)) {
            WikiData data = list.get(position);
            if (data != null) {
                ((HomeWikiListViewHolder) holder).vbItem.tvTitle.setText(data.title);
                ((HomeWikiListViewHolder) holder).vbItem.tvContent.setText(data.content);
                ImageLoader.loadImage(BaseApp.instance, data.imgUrl, ((HomeWikiListViewHolder) holder).vbItem.ivContent);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (CollectionUtils.isNotEmptyList(list)) {
            return list.size();
        }
        return 0;
    }
}
