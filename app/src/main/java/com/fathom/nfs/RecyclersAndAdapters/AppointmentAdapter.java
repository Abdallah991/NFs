package com.fathom.nfs.RecyclersAndAdapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.fathom.nfs.DataModels.AppointmentDataModel;
import com.fathom.nfs.R;

import java.util.ArrayList;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.AppointmentHolder> {

    private static final String TAG = "Appointment Adapter";

    // Declare variables
    private ArrayList<AppointmentDataModel> mAppointments = new ArrayList<>();
    private Context mContext;

    // Constructor
    public AppointmentAdapter(ArrayList<AppointmentDataModel> AppointmentsDetails, Context context) {
        mAppointments = AppointmentsDetails;
        mContext = context;

    }

    @NonNull
    @Override
    public AppointmentAdapter.AppointmentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // Tying the list Item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointment_list_item,parent, false);

        return new AppointmentAdapter.AppointmentHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AppointmentAdapter.AppointmentHolder holder, final int position) {

        Log.d(TAG, "OnBindViewHolder: Called.");

        // Tying the UI elements of the list item with their content
        holder.day.setText(mAppointments.get(position).getDay());
        holder.month.setText(mAppointments.get(position).getMonth());
        holder.doctorName.setText(mAppointments.get(position).getDoctorName());
        holder.specialty.setText(mAppointments.get(position).getSpecialty());
        holder.timing.setText(mAppointments.get(position).getTiming());

        // Setting the list item on click listener
        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "clicked on: " + mAppointments.get(position));
                Toast.makeText(mContext, "clicked "+ mAppointments.get(position) , Toast.LENGTH_SHORT).show();

            }
        });


    }

    // Setting the size of the list
    @Override
    public int getItemCount() {
        return mAppointments.size();
    }


    // Tying the the variables to list item UI elements
    public class AppointmentHolder extends RecyclerView.ViewHolder{
        CardView card;
        TextView day;
        TextView month;
        TextView doctorName;
        TextView specialty;
        TextView timing;

        public AppointmentHolder (View itemView){
            super(itemView);

            // binding the views with the list items
            card = itemView.findViewById(R.id.appointmentCard);
            day = itemView.findViewById(R.id.day);
            month = itemView.findViewById(R.id.month);
            doctorName = itemView.findViewById(R.id.doctorName);
            specialty = itemView.findViewById(R.id.specialty);
            timing = itemView.findViewById(R.id.timing);
        }



    }
}
