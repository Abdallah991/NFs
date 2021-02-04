package com.fathom.nfs.Repositories;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.fathom.nfs.DataModels.AppointmentDataModel;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import static com.fathom.nfs.SignUpActivity.USER;

public class AppointmentRepository {
    /**
     * @class Appointment Repository
     * @desription  fetching appointments from backend
     * @date 4 feb 2021
     */

    private static AppointmentRepository instance;
    private String TAG = "MVVM";
    private ArrayList<AppointmentDataModel> mAppointments = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseStorage storage;
    private StorageReference storageRef;

    public static AppointmentRepository getInstance() {
        if (instance == null) {

            Log.d("MVVM"," getting static instance of the Repo.");
            instance = new AppointmentRepository();
        }

        Log.d("MVVM"," returning the existing static instance of the Repo.");

        return instance;
    }

    public MutableLiveData<List<AppointmentDataModel>> getAppointments(String email) {

        // calling the webservice task of function
        setAppointments(email);
        MutableLiveData<List<AppointmentDataModel>> data = new MutableLiveData<>();
        data.setValue(mAppointments);

        return data;
    }

    // get appointment from firebase
    private  void setAppointments (String email) {

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();

        mAppointments = new ArrayList<>();

        db.collection("Appointments").whereEqualTo("userEmail", email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                if (mAppointments.size() <= task.getResult().size()) {
                                    AppointmentDataModel appointment = document.toObject(AppointmentDataModel.class);
                                    appointment.setDocumentId(document.getId());
                                    mAppointments.add(appointment);

                                }

                                Log.d(TAG, "Task size " + task.getResult().size());

                                Log.d(TAG, "Doctor array size " + mAppointments.size());



                            }
                        } else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });

        Log.d(TAG," Loading the data is DONE");

    }

    // delete appointment in backend
    public void deleteAppointment(AppointmentDataModel appointment) {

        db.collection("Appointments").document(appointment.getDocumentId()).delete();

    }
}
