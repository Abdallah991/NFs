package com.fathom.nfs.Repositories;

import android.content.res.Resources;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.fathom.nfs.DataModels.HelpLinesDataModel;
import com.fathom.nfs.R;
import java.util.ArrayList;
import java.util.List;

/**
 * @class Helpline Repository
 * @desription  fetching helplines
 * @date 4 feb 2021
 */

public class HelpLineRepository {

    // Creating one instance
    private static HelpLineRepository instance;

    private ArrayList<HelpLinesDataModel> mHelpLineItems = new ArrayList<>();


    public static HelpLineRepository getInstance() {
        if (instance == null) {

            Log.d("MVVM"," getting static instance of the Repo.");
            instance = new HelpLineRepository();
        }

        Log.d("MVVM"," returning the existing static instance of the Repo.");

        return instance;
    }

    public MutableLiveData<List<HelpLinesDataModel>> getHelpLiens () {

        // calling the webservice task of function
        setHelpLineItems();
        MutableLiveData<List<HelpLinesDataModel>> data = new MutableLiveData<>();
        data.setValue(mHelpLineItems);

        return data;
    }


    // Getting live data from webservice
    private  void setHelpLineItems () {

        Log.d("MVVM"," Loading the data is going to start");



        if (mHelpLineItems.isEmpty()) {
        mHelpLineItems.add (
                new HelpLinesDataModel(R.drawable.abuse, R.drawable.abuse_image, "It is a lot more than just being physical. \n" +
                        "•\tControl abuse\n" +
                        "•\tMental abuse\n" +
                        "•\tEmotional abuse\n" +
                        "•\tVerbal abuse\n" +
                        "\n" +
                        "Abuse does not just occur from your significant other, family members (extended and not extended), friends and strangers. \n" +
                        "\n" +
                        "Domestic Abuse from a significant other (spouse, fiancée & boyfriend/girlfriend): \n" +
                        "\n" +
                        "These traits puts women at risk of ‘Separation Assault’. Over 70% of women who are injured in domestic violence cases were injured after separation from their abuser. Danger zone peaks within first 2-3 weeks.\n" +
                        "\n" +
                        "9 warning signs of an abuser\n" +
                        "1.\tQuick involvement in a relationship \n" +
                        "2.\tExtreme jealousy \n" +
                        "3.\tControlling behaviour \n" +
                        "4.\tThreats of violence \n" +
                        "5.\tAbrupt mood changes \n" +
                        "6.\tVerbal abuse \n" +
                        "7.\tBreaking objects \n" +
                        "8.\tUse of force during an argument \n" +
                        "9.\tHistory of past battering \n" +
                        "\n" +
                        "FIVE different types of abuse \n" +
                        "•\tPhysical Abuse: Any intentional use of physical force with the intent to control a partner through fear or injury.\n" +
                        "•\tEmotional/Verbal Abuse: An attempt to control a partner through the manipulation of their self-esteem, sense of personal security, relationships with others, and/or their perception of reality. Often it results in the victim feeling worthless and responsible for the abuse.\n" +
                        "•\tSexual Abuse: Any behaviors that impact a person’s ability to control their sexual activity or the circumstances in which sexual activity occurs\n" +
                        "•\tDigital Abuse: This is a form of emotional/verbal abuse that uses technology or social media to intimidate, harass, bully, stalk or threaten a current or ex-partner.\n" +
                        "•\tFinancial Abuse: The use of finances or access to finances to control a partner. It’s one of the powerful forms of abuse, and common method of entrapping a partner in the relationship. It’s often given as the reason that victims of abuser stayed in or returned to an abusive relationship.\n"
                        , "+97366710901")
        );
//        mHelpLineItems.add (
//                new HelpLinesDataModel(R.drawable.depression, R.drawable.depression_image,"Depression helpline", "+4355664427")
//        );

        Log.d("MVVM"," Loading the data is DONE");

        }
    }
}
