package com.fathom.nfs.RecyclersAndAdapters;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fathom.nfs.DataModels.MessageDataModel;
import com.fathom.nfs.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ChatListAdapter extends BaseAdapter {

    private DatabaseReference mDatabaseReference;
    private String displayName;
    private ArrayList<DataSnapshot> mDataSnapshotsList;
    private Activity mActivity;

    private ChildEventListener mListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            mDataSnapshotsList.add(dataSnapshot);
            notifyDataSetChanged();

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public ChatListAdapter(DatabaseReference ref, String name, Activity activity) {

        displayName = name;
        mDatabaseReference = ref.child("message");
        mDataSnapshotsList = new ArrayList<>();
        mActivity = activity;
        mDatabaseReference.addChildEventListener(mListener);
    }



    @Override
    public int getCount() {
        return mDataSnapshotsList.size();
    }

    @Override
    public MessageDataModel getItem(int i) {

        DataSnapshot snapshot = mDataSnapshotsList.get(i);

        return snapshot.getValue(MessageDataModel.class);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if (view == null) {

            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.chat_msg_row, viewGroup, false);
            final ViewHolder holder = new ViewHolder();
            holder.authorName = view.findViewById(R.id.author);
            holder.body = view.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            view.setTag(holder);

        }

        final MessageDataModel messageDataModel = getItem(i);
        final ViewHolder holder = (ViewHolder) view.getTag();

//        boolean isMe = messageDataModel.getAuthor().equals(displayName);
//
//        setChatRowAppearance(isMe, holder);
//
//        String author = messageDataModel.getAuthor();
//        String msg = messageDataModel.getMessage();
//
//        holder.authorName.setText(author);
//        holder.body.setText(msg);

        return view;
    }

    static class ViewHolder {
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;
    }

    private void setChatRowAppearance (boolean isItMe, ViewHolder holder) {

        if (isItMe) {

            holder.params.gravity = Gravity.START;
//            holder.authorName.setTextColor();
            holder.body.setBackgroundResource(R.drawable.chat_bubble_dark);

        } else {

            holder.params.gravity = Gravity.START;
//            holder.authorName.setTextColor();
            holder.body.setBackgroundResource(R.drawable.chat_bubble_light);

        }

        holder.authorName.setLayoutParams(holder.params);
        holder.body.setLayoutParams(holder.params);


    }


    public void cleanUp() {

        mDatabaseReference.removeEventListener(mListener);
    }
}
