package com.example.notepadmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.GroupViewHolder> {

    private Context context;
    private List<Group> groupList;
    private OnItemClickListener onItemClickListener;

    public GroupAdapter(Context context, List<Group> groupList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.groupList = groupList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public GroupViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_item, parent, false);
        return new GroupViewHolder(view);
    }
    // group_item kırmızı yanıyor bunu hallet gemini

    @Override
    public void onBindViewHolder(@NonNull GroupViewHolder holder, int position) {
        Group group = groupList.get(position);
        holder.groupName.setText(group.getGroupName());
        holder.itemView.setOnClickListener(v -> onItemClickListener.onItemClick(group));
    }

    @Override
    public int getItemCount() {
        return groupList.size();
    }

    public static class GroupViewHolder extends RecyclerView.ViewHolder {

        TextView groupName;

        public GroupViewHolder(View itemView) {
            super(itemView);
            groupName = itemView.findViewById(R.id.tvGroupName);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Group group);
    }
}
