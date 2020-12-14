package com.fathom.nfs;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fathom.nfs.DataModels.MessageDataModel;
import com.fathom.nfs.RecyclersAndAdapters.ChatListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


/**
 * A simple {@link Fragment} subclass.
 */
public class Chat extends Fragment {

    private DatabaseReference mDatabaseReference;
    private EditText message;
    private ImageButton sendButton;
    private ListView chatListView;
    private String TAG = "CHAT";
    private String displayName;
    private RelativeLayout chatContent;
    private ChatListAdapter mChatListAdapter;

    public Chat() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_chat, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        message = view.findViewById(R.id.messageInput);
        sendButton = view.findViewById(R.id.sendButton);
        chatListView = view.findViewById(R.id.chat_list_view);
        chatContent = view.findViewById(R.id.chatContent);

        ViewCompat.setLayoutDirection(chatContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        // Sending a message through the keyboard
        message.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {

                sendMessage();
                return true;
            }
        });

        // Sending a message through the button
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendMessage();

            }
        });
    }

    private void sendMessage() {

        Log.d(TAG, "message sent");
        String messageContent = message.getText().toString();
        if (! messageContent.equals("")) {
            MessageDataModel msg = new MessageDataModel(messageContent, "Abdallah");
            mDatabaseReference.child("message").push().setValue(msg);
            message.setText("");

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        mChatListAdapter = new ChatListAdapter(mDatabaseReference, "Abdallah",getActivity());
        chatListView.setAdapter(mChatListAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        mChatListAdapter.cleanUp();
    }
}
