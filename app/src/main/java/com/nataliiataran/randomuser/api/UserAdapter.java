package com.nataliiataran.randomuser.api;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.nataliiataran.randomuser.R;

import java.util.Collections;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {
    public interface OnItemClickListener {
        void onItemClick(Result item);
    }
    private List<Result> mlist = Collections.emptyList();
    private final OnItemClickListener listener;

    public UserAdapter(OnItemClickListener listener) {
        this.listener = listener;
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_card, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        holder.bind(mlist.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }

    public void setData(List<Result> mlist) {
        this.mlist = mlist;
        notifyDataSetChanged();
    }

    class UserViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivAvatar;
        private TextView tvName;
        private TextView tvLastName;
        private TextView tvCity;

        public UserViewHolder(View itemView) {
            super(itemView);
            ivAvatar = itemView.findViewById(R.id.ivAvatar);
            tvName = itemView.findViewById(R.id.tvFirstName);
            tvLastName = itemView.findViewById(R.id.tvLastName);
            tvCity = itemView.findViewById(R.id.tvCity);
        }

        public void bind(final Result result, final OnItemClickListener listener) {
            tvName.setText(result.getName().getFirst());
            tvLastName.setText(result.getName().getLast());
            tvCity.setText(result.getLocation().getCity());
            Glide.with(ivAvatar).load(result.getPicture().getMedium()).into(ivAvatar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(result);
                }
            });
        }

    }
}
