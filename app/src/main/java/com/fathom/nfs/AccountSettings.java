package com.fathom.nfs;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.fathom.nfs.DataModels.UserDataModel;
import com.fathom.nfs.ViewModels.UserViewModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.fathom.nfs.SignUpActivity.USER;


/**
 * A simple {@link Fragment} subclass.
 */
public class AccountSettings extends Fragment {

    private ConstraintLayout accountSettingsContent;
    private ImageView userImage;
    private TextView editProfile;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private Button editButton;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private UserDataModel user = new UserDataModel();
    private final String TAG = "Account";
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private FirebaseUser mFirebaseUser;
    private FirebaseStorage storage;
    private StorageReference storageRef;
    private StorageReference userImageRef;
    private Dialog mDialog;
    private UserViewModel mUserViewModel;



    public AccountSettings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userImage = view.findViewById(R.id.userImage);
        editProfile = view.findViewById(R.id.editAccountSettings);
        firstName = view.findViewById(R.id.firstNameAccount);
        lastName = view.findViewById(R.id.lastNameAccount);
        email = view.findViewById(R.id.emailAccount);
        password = view.findViewById(R.id.passwordAccount);
        confirmPassword = view.findViewById(R.id.confirmPasswordAccount);
        editButton = view.findViewById(R.id.edit);
        accountSettingsContent = view.findViewById(R.id.accountContent);

        mDialog = new Dialog(getContext());

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        // naming the path as email
        SharedPreferences prefs = getActivity().getSharedPreferences(USER, MODE_PRIVATE);
        String docName = prefs.getString("EMAIL", "No name");

        userImageRef = storageRef.child(docName);


        ViewCompat.setLayoutDirection(accountSettingsContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        confirmPassword.setVisibility(View.GONE);
        editButton.setVisibility(View.GONE);

        pullData();
        getImage();

        firstName.setEnabled(false);
        lastName.setEnabled(false);
        email.setEnabled(false);
        password.setEnabled(false);


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                firstName.setEnabled(true);
                lastName.setEnabled(true);
                email.setEnabled(true);
                password.setEnabled(true);
                confirmPassword.setVisibility(View.VISIBLE);
                editButton.setVisibility(View.VISIBLE);

                openDialog();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UserDataModel user = new UserDataModel();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences(USER , Context.MODE_PRIVATE).edit();
                editor.putString("Email", email.getText().toString());
                editor.putString("FirstName", firstName.getText().toString());
                editor.putString("LastName", lastName.getText().toString());
                editor.putString("Password", password.getText().toString());
                editor.apply();
                user.setFirstName(firstName.getText().toString());
                user.setLastName(lastName.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());

                if (confirmPassword.getText().toString().length() < 6) {

                    Toast.makeText(getContext(), "The password is too short", Toast.LENGTH_SHORT).show();

                    return;
                }

                else if (password.getText().toString().equals(confirmPassword.getText().toString())
                        && password.getText().toString().length() >= 6) {
                    db.collection("Users")
                            .document(user.getEmail()).set(user);

                    changePassword();

                }


                uploadUserImage();

                firstName.setEnabled(false);
                lastName.setEnabled(false);
                email.setEnabled(false);
                password.setEnabled(false);
                confirmPassword.setVisibility(View.GONE);
                editButton.setVisibility(View.GONE);

            }
        });
    }

    private void changePassword() {

        String newPassword = password.getText().toString();

        mFirebaseUser.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                            Toast.makeText(getContext(), "User password updated.", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }


    private void pullData() {

        mUserViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        mUserViewModel.initUser(getContext());

        mUserViewModel.getUser(getContext()).observe(getViewLifecycleOwner(), new Observer<UserDataModel>() {
            @Override
            public void onChanged(UserDataModel userDataModel) {
                user = userDataModel;

                firstName.setText(user.getFirstName());
                lastName.setText(user.getLastName());
                email.setText(user.getEmail());
                password.setText(user.getPassword());
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void uploadUserImage() {

        userImage.setDrawingCacheEnabled(true);
        userImage.buildDrawingCache();
        Bitmap bitmap = ((BitmapDrawable) userImage.getDrawable()).getBitmap();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = userImageRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Log.d(TAG, "User image failed to upload.");

                // Handle unsuccessful uploads
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Log.d(TAG, "User image uploaded.");

                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                // ...
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            userImage.setImageURI(imageUri);
        }

    }

    private void openDialog() {

        ImageView selectedImage;
        ImageView cancel;
        Button uploadUserPhoto;

        mDialog.setContentView(R.layout.uploade_image_dialogue);
        selectedImage = mDialog.findViewById(R.id.userImageViewer);
        cancel = mDialog.findViewById(R.id.cancelUploading);
        uploadUserPhoto = mDialog.findViewById(R.id.uploadImage);

        uploadUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();
                firstName.setEnabled(false);
                lastName.setEnabled(false);
                email.setEnabled(false);
                password.setEnabled(false);
                confirmPassword.setVisibility(View.GONE);
                editButton.setVisibility(View.GONE);

            }
        });

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();


    }


    private void getImage () {

        mUserViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        mUserViewModel.initUser(getContext());

        mUserViewModel.getUser(getContext()).observe(getViewLifecycleOwner(), new Observer<UserDataModel>() {
            @Override
            public void onChanged(UserDataModel userDataModel) {
                user = userDataModel;

                userImage.setImageBitmap(user.getUserImage());
            }
        });
    }
}
