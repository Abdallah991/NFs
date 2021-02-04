package com.fathom.nfs.ViewModels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.fathom.nfs.DataModels.UserDataModel;
import com.fathom.nfs.Repositories.UserRepository;


public class UserViewModel extends ViewModel {

    /**
     * @class user View model
     * @desription  setting user as live data
     * @date 4 feb 2021
     */
    private MutableLiveData<UserDataModel> mUser;
    private UserRepository mRepository;
    private String TAG = "MVVM";
    private Context mContext;

    public void selectUser (UserDataModel user) {

        mUser.setValue(user);
    }

    // get user data from repo
    public void initUser(Context context){

        Log.d(TAG," init in ViewModel called.");
        if (mUser != null){
            Log.d(TAG,"Mutable data is already loaded from the Repo.");
            return;
        }

        Log.d(TAG," Mutable data is empty and going to be loaded");

        mContext = context;
        mRepository = UserRepository.getInstance();
        mUser = mRepository.getUser(mContext);
    }

    // get user method
    public MutableLiveData<UserDataModel> getUser( Context context) {
        mContext = context;
        return mUser;
    }
}
