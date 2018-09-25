package com.example.appiness.sampleapp.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appiness.sampleapp.R;
import com.example.appiness.sampleapp.realm.UserObject;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserVH>
{
    private Context context;
    private List<UserObject> userObjectList;

    public UserListAdapter(Context context, List<UserObject> userObjectList) {
        this.context = context;
        this.userObjectList = userObjectList;
    }

    @NonNull
    @Override
    public UserVH onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        return new UserVH(LayoutInflater.from(context).inflate(R.layout.item_user,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull UserVH userVH, int position) {

        UserObject userObject = userObjectList.get(position);

        userVH.name.setText(userObject.getName());
        userVH.dob.setText(userObject.getDob());
        userVH.desc.setText(userObject.getAbout());

    }

    @Override
    public int getItemCount() {
        return userObjectList.size();
    }

    class UserVH extends RecyclerView.ViewHolder {

        private TextView name,dob,desc;

        UserVH(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.tv_user_name);
            dob = itemView.findViewById(R.id.tv_user_dob);
            desc = itemView.findViewById(R.id.tv_user_desc);
        }
    }

}
