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
import android.widget.LinearLayout;
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
    private Button confirmButton;
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
    private Dialog monsterDialog;
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
        confirmButton = view.findViewById(R.id.confirm);
        accountSettingsContent = view.findViewById(R.id.accountContent);

        mDialog = new Dialog(getContext());
        monsterDialog = new Dialog(getContext());

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        // naming the path as email
        SharedPreferences prefs = getActivity().getSharedPreferences(USER, MODE_PRIVATE);
        String docName = prefs.getString("EMAIL", "No name");

        userImageRef = storageRef.child(docName+"ProfileImage.jpeg");


        ViewCompat.setLayoutDirection(accountSettingsContent, ViewCompat.LAYOUT_DIRECTION_LTR);

        confirmPassword.setVisibility(View.GONE);
        confirmButton.setVisibility(View.GONE);

        pullData();
        getImage();

        firstName.setEnabled(false);
        lastName.setEnabled(false);
        email.setEnabled(false);
        password.setEnabled(false);


        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editProfile.getText().toString().equals("Edit")) {
                    firstName.setEnabled(true);
                    lastName.setEnabled(true);
                    email.setEnabled(true);
                    password.setEnabled(true);
                    confirmPassword.setVisibility(View.VISIBLE);
                    confirmButton.setVisibility(View.VISIBLE);
                    editProfile.setText("Edit photo");
                } else if (editProfile.getText().toString().equals("Edit photo")) {
                    openDialog();
                    editProfile.setText("Edit");
                }
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
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
                user.setAccountType("normal");

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
                confirmButton.setVisibility(View.GONE);

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
                            Toast.makeText(getContext(), "Your Information have been updated!", Toast.LENGTH_SHORT).show();

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

                SharedPreferences preferences = getActivity().getSharedPreferences(USER, 0);
                String emailValue = preferences.getString("Email", "");
                String firstNameValue = preferences.getString("FirstName", "");
                String lastNameValue = preferences.getString("LastName", "");
                String passwordValue = preferences.getString("Password", "");

                if(!firstNameValue.equals("")) {
                    firstName.setText(firstNameValue);
                    lastName.setText(lastNameValue);
                    email.setText(emailValue);
                    password.setText(passwordValue);
                    confirmPassword.setText(passwordValue);
                } else {
                    firstName.setText(user.getFirstName());
                    lastName.setText(user.getLastName());
                    email.setText(user.getEmail());
                    password.setText(user.getPassword());
                    confirmPassword.setText(user.getPassword());
                }
            }

        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void uploadUserImage() {

        userImageRef = storageRef.child(user.getEmail()+"ProfileImage.jpeg");
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
        
        mDialog.dismiss();

    }

    private void openDialog() {

        ImageView selectedImage;
        ImageView cancel;
        Button uploadUserPhoto;
        Button selectMonster;

        mDialog.setContentView(R.layout.uploade_image_dialogue);
        selectedImage = mDialog.findViewById(R.id.userImageViewer);
        cancel = mDialog.findViewById(R.id.cancelUploading);
        uploadUserPhoto = mDialog.findViewById(R.id.uploadImage);
        selectMonster = mDialog.findViewById(R.id.selectMonster);

        uploadUserPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        selectMonster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMonsterDialog();
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
                confirmButton.setVisibility(View.GONE);

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

    private void openMonsterDialog() {

        mDialog.dismiss();
        ImageView monster1;
        ImageView monster2;
        ImageView monster3;
        ImageView monster4;
        ImageView monster5;
        ImageView monster6;
        ImageView monster7;
        ImageView monster8;
        LinearLayout selector1;
        LinearLayout selector2;
        LinearLayout selector3;
        LinearLayout selector4;
        LinearLayout selector5;
        LinearLayout selector6;
        LinearLayout selector7;
        LinearLayout selector8;
        ImageView cancel;
        Button confirmMonster;

        monsterDialog.setContentView(R.layout.monsters_dialog);
        monster1 = monsterDialog.findViewById(R.id.monster1);
        monster2 = monsterDialog.findViewById(R.id.monster2);
        monster3 = monsterDialog.findViewById(R.id.monster3);
        monster4 = monsterDialog.findViewById(R.id.monster4);
        monster5 = monsterDialog.findViewById(R.id.monster5);
        monster6 = monsterDialog.findViewById(R.id.monster6);
        monster7 = monsterDialog.findViewById(R.id.monster7);
        monster8 = monsterDialog.findViewById(R.id.monster8);
        selector1 = monsterDialog.findViewById(R.id.monster1Selector);
        selector2 = monsterDialog.findViewById(R.id.monster2Selector);
        selector3 = monsterDialog.findViewById(R.id.monster3Selector);
        selector4 = monsterDialog.findViewById(R.id.monster4Selector);
        selector5 = monsterDialog.findViewById(R.id.monster5Selector);
        selector6 = monsterDialog.findViewById(R.id.monster6Selector);
        selector7 = monsterDialog.findViewById(R.id.monster7Selector);
        selector8 = monsterDialog.findViewById(R.id.monster8Selector);
        cancel = monsterDialog.findViewById(R.id.cancelMonsterDialog);
        confirmMonster = monsterDialog.findViewById(R.id.confirmMonster);

        monster1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector1.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                selector2.setBackgroundColor(getResources().getColor(R.color.white));
                selector3.setBackgroundColor(getResources().getColor(R.color.white));
                selector4.setBackgroundColor(getResources().getColor(R.color.white));
                selector5.setBackgroundColor(getResources().getColor(R.color.white));
                selector6.setBackgroundColor(getResources().getColor(R.color.white));
                selector7.setBackgroundColor(getResources().getColor(R.color.white));
                selector8.setBackgroundColor(getResources().getColor(R.color.white));
                userImage.setImageResource(R.drawable.monster11);
            }
        });
        monster2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                selector1.setBackgroundColor(getResources().getColor(R.color.white));
                selector3.setBackgroundColor(getResources().getColor(R.color.white));
                selector4.setBackgroundColor(getResources().getColor(R.color.white));
                selector5.setBackgroundColor(getResources().getColor(R.color.white));
                selector6.setBackgroundColor(getResources().getColor(R.color.white));
                selector7.setBackgroundColor(getResources().getColor(R.color.white));
                selector8.setBackgroundColor(getResources().getColor(R.color.white));
                userImage.setImageResource(R.drawable.monster12);
            }
        });
        monster3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector3.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                selector2.setBackgroundColor(getResources().getColor(R.color.white));
                selector1.setBackgroundColor(getResources().getColor(R.color.white));
                selector4.setBackgroundColor(getResources().getColor(R.color.white));
                selector5.setBackgroundColor(getResources().getColor(R.color.white));
                selector6.setBackgroundColor(getResources().getColor(R.color.white));
                selector7.setBackgroundColor(getResources().getColor(R.color.white));
                selector8.setBackgroundColor(getResources().getColor(R.color.white));
                userImage.setImageResource(R.drawable.monster13);
            }
        });

        monster4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector4.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                selector2.setBackgroundColor(getResources().getColor(R.color.white));
                selector3.setBackgroundColor(getResources().getColor(R.color.white));
                selector1.setBackgroundColor(getResources().getColor(R.color.white));
                selector5.setBackgroundColor(getResources().getColor(R.color.white));
                selector6.setBackgroundColor(getResources().getColor(R.color.white));
                selector7.setBackgroundColor(getResources().getColor(R.color.white));
                selector8.setBackgroundColor(getResources().getColor(R.color.white));
                userImage.setImageResource(R.drawable.monster14);
            }
        });

        monster5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector5.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                selector2.setBackgroundColor(getResources().getColor(R.color.white));
                selector3.setBackgroundColor(getResources().getColor(R.color.white));
                selector4.setBackgroundColor(getResources().getColor(R.color.white));
                selector1.setBackgroundColor(getResources().getColor(R.color.white));
                selector6.setBackgroundColor(getResources().getColor(R.color.white));
                selector7.setBackgroundColor(getResources().getColor(R.color.white));
                selector8.setBackgroundColor(getResources().getColor(R.color.white));
                userImage.setImageResource(R.drawable.monster15);

            }

        });

        monster6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector6.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                selector2.setBackgroundColor(getResources().getColor(R.color.white));
                selector3.setBackgroundColor(getResources().getColor(R.color.white));
                selector4.setBackgroundColor(getResources().getColor(R.color.white));
                selector5.setBackgroundColor(getResources().getColor(R.color.white));
                selector1.setBackgroundColor(getResources().getColor(R.color.white));
                selector7.setBackgroundColor(getResources().getColor(R.color.white));
                selector8.setBackgroundColor(getResources().getColor(R.color.white));
                userImage.setImageResource(R.drawable.monster16);
            }
        });
        monster7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector7.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                selector2.setBackgroundColor(getResources().getColor(R.color.white));
                selector3.setBackgroundColor(getResources().getColor(R.color.white));
                selector4.setBackgroundColor(getResources().getColor(R.color.white));
                selector5.setBackgroundColor(getResources().getColor(R.color.white));
                selector6.setBackgroundColor(getResources().getColor(R.color.white));
                selector1.setBackgroundColor(getResources().getColor(R.color.white));
                selector8.setBackgroundColor(getResources().getColor(R.color.white));
                userImage.setImageResource(R.drawable.monster17);
            }
        });

        monster8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selector8.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                selector2.setBackgroundColor(getResources().getColor(R.color.white));
                selector3.setBackgroundColor(getResources().getColor(R.color.white));
                selector4.setBackgroundColor(getResources().getColor(R.color.white));
                selector5.setBackgroundColor(getResources().getColor(R.color.white));
                selector6.setBackgroundColor(getResources().getColor(R.color.white));
                selector7.setBackgroundColor(getResources().getColor(R.color.white));
                selector1.setBackgroundColor(getResources().getColor(R.color.white));
                userImage.setImageResource(R.drawable.monster18);
            }
        });


        confirmMonster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                openGallery();
                monsterDialog.dismiss();

            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monsterDialog.dismiss();
                firstName.setEnabled(false);
                lastName.setEnabled(false);
                email.setEnabled(false);
                password.setEnabled(false);
                confirmPassword.setVisibility(View.GONE);
                confirmButton.setVisibility(View.GONE);

            }
        });

        monsterDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        monsterDialog.show();



    }
}
