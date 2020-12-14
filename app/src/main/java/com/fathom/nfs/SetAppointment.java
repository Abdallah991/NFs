package com.fathom.nfs;



import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.exceptions.OutOfDateRangeException;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;
import com.fathom.nfs.DataModels.AppointmentDataModel;
import com.fathom.nfs.DataModels.MessageDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import java.util.Date;

import static com.fathom.nfs.DoctorsDetails.appointmentSpeciality;
import static com.fathom.nfs.DoctorsDetails.doctorEmailId;
import static com.fathom.nfs.DoctorsDetails.doctorFullName;
import static com.fathom.nfs.SignUpActivity.USER;


/**
 * A simple {@link Fragment} subclass.
 */
public class SetAppointment extends Fragment {


    private LinearLayout setAppointmentContent;
    private CalendarView mCalendarView;
    private Calendar calendar;
    private AppointmentDataModel appointment = new AppointmentDataModel();
    private MessageDataModel message = new MessageDataModel();
    private Spinner startTime;
    private Spinner startAmPm;
    private Spinner endTime;
    private Spinner endAmPm;
    private Button bookAppointment;
    private ImageView backButton;
    private TextView doctorNameInAppointment;
    private TextView specialtyInAppointment;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String day;
    private String month;
    private String doctorName;
    private String doctorEmail;
    private String speciality;
    // confirmation dialog
    private Dialog mDialog;


    public SetAppointment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_set_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        setAppointmentContent = view.findViewById(R.id.setAppointmentContent);
        calendar = Calendar.getInstance();
        mCalendarView = view.findViewById(R.id.calendarView);
        startTime = view.findViewById(R.id.startTime);
        startAmPm = view.findViewById(R.id.startAMPM);
        endTime = view.findViewById(R.id.endTime);
        endAmPm = view.findViewById(R.id.endAMPM);
        bookAppointment = view.findViewById(R.id.appointmentBooking);
        backButton = view.findViewById(R.id.backButtonToDoctorDetailed);
        doctorNameInAppointment = view.findViewById(R.id.doctorNameInAppointment);
        specialtyInAppointment = view.findViewById(R.id.specialtyInAppointment);

        mDialog = new Dialog(getContext());
        // static variable to show what is the name of the doctor
        doctorName = doctorFullName;
        speciality = appointmentSpeciality;
        doctorNameInAppointment.setText(doctorName);
        specialtyInAppointment.setText(speciality);
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item, getResources().getStringArray(R.array.timings));
        ArrayAdapter<String> AmPmAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.spinner_item, getResources().getStringArray(R.array.AMPM));
        timeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        AmPmAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);


        startTime.setAdapter(timeAdapter);
        endTime.setAdapter(timeAdapter);

        startAmPm.setAdapter(AmPmAdapter);
        endAmPm.setAdapter(AmPmAdapter);

        Date date = new Date();
        try {
            mCalendarView.setDate(calendar.getTime());
        } catch (OutOfDateRangeException e) {
            e.printStackTrace();
        }

        showAppointmentsForTheDay(calendar.getTime());

//        Toast.makeText(getContext(),"Calender is "+ calendar.getTime()   , Toast.LENGTH_SHORT).show();

        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

//                showAppointmentsForTheDay(eventDay.getCalendar().getTime());
                int dayOfMonth = eventDay.getCalendar().get(Calendar.DAY_OF_MONTH);
                int monthOfYear = (eventDay.getCalendar().get(Calendar.MONTH)+1);
                day = Integer.toString(dayOfMonth);
                switch (monthOfYear) {
                    case 1:
                        month = "JAN";
                        break;
                    case 2:
                        month = "FEB";
                        break;
                    case 3:
                        month = "MAR";
                        break;
                    case 4:
                        month = "APR";
                        break;
                    case 5:
                        month = "MAY";
                        break;
                    case 6:
                        month = "JUN";
                        break;
                    case 7:
                        month = "JUL";
                        break;
                    case 8:
                        month = "AUG";
                        break;
                    case 9:
                        month = "SEP";
                        break;
                    case 10:
                        month = "OCT";
                        break;
                    case 11:
                        month = "NOV";
                        break;
                    case 12:
                        month = "DEC";
                        break;
                }
//                Toast.makeText(getContext(),"date is " +day +" / "+ month  , Toast.LENGTH_SHORT).show();

            }
        });




        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavController navController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);
                navController.navigateUp();
            }
        });

        ViewCompat.setLayoutDirection(setAppointmentContent, ViewCompat.LAYOUT_DIRECTION_LTR);


        bookAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences preferences = getActivity().getSharedPreferences(USER, 0);
                String firstName = preferences.getString("FirstName", "");
                String lastName = preferences.getString("LastName", "");
                String email = preferences.getString("Email", "");

//                Toast.makeText(getContext(),
//                        "Appointment is " +startTime.getSelectedItem().toString()+ " "+ startAmPm.getSelectedItem().toString() +
//                        " to "+ endTime.getSelectedItem().toString()+ " "+ endAmPm.getSelectedItem().toString(),
//                        Toast.LENGTH_SHORT).show();

                appointment.setDoctorName(doctorFullName);
                appointment.setTo(doctorEmailId);
                message.setSubject("Hello, Appointment from "+email);
                message.setText("Hello there you appointment is from " +startTime.getSelectedItem().toString()+ " "+ startAmPm.getSelectedItem().toString() +
                        " to "+ endTime.getSelectedItem().toString()+ " "+ endAmPm.getSelectedItem().toString() );
                appointment.setDay(day);
                appointment.setMonth(month);
                appointment.setTiming(startTime.getSelectedItem().toString()+startAmPm.getSelectedItem().toString()+ " to "
                        +endTime.getSelectedItem().toString()+endAmPm.getSelectedItem().toString());
                appointment.setUserEmail(email);
                appointment.setUserName(firstName +" "+ lastName);
                appointment.setSpecialty(speciality);
                appointment.setMessage(message);

                setAppointment();

            }
        });
    }


    private String showAppointmentsForTheDay(Date date) {

        String dayOfWeek = "null";

        switch (date.getDay()) {
            case 0:
                dayOfWeek = "Sunday";
                break;
            case 1:
                dayOfWeek = "Monday";
                break;
            case 2:
                dayOfWeek = "Tuesday";
                break;
            case 3:
                dayOfWeek = "Wednesday";
                break;
            case 4:
                dayOfWeek = "Thursday";
                break;
            case 5:
                dayOfWeek = "Friday";
                break;
            case 6:
                dayOfWeek = "Saturday";


        }
        return dayOfWeek;



    }

    private void setAppointment() {

        Button confirm;
        Button cancel;
        TextView appointmentTiming;

        mDialog.setContentView(R.layout.confirm_appointment_dialog);
        confirm = mDialog.findViewById(R.id.confirmAppointment);
        cancel = mDialog.findViewById(R.id.cancelAppointment);
        appointmentTiming = mDialog.findViewById(R.id.appointmentTime);
        appointmentTiming.setText("Appointment at "+day+ "/"+month +" "+ startTime.getSelectedItem().toString()+startAmPm.getSelectedItem().toString()+ " to "
                +endTime.getSelectedItem().toString()+endAmPm.getSelectedItem().toString()+ " with "+ doctorName);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore.getInstance().
                        collection("Appointments").add(appointment).
                        addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentReference> task) {
                                Log.d("Appointment", "Appointment set Success");
                                mDialog.dismiss();
                                Toast.makeText(getContext(), "Appointment will be validated by the doctor", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("Appointment", "Appointment set Failure");

                    }
                });
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDialog.dismiss();


            }
        });

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.show();


    }


}
