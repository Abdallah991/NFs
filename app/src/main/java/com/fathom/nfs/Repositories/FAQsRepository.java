package com.fathom.nfs.Repositories;

import androidx.lifecycle.MutableLiveData;

import com.fathom.nfs.DataModels.CategoryDataModel;
import com.fathom.nfs.DataModels.FAQsDataModel;
import com.fathom.nfs.DataModels.QuestionDataModel;
import com.fathom.nfs.R;

import java.util.ArrayList;
import java.util.List;

public class FAQsRepository {
    // Creating one instance

    private static FAQsRepository instance;
    private ArrayList<FAQsDataModel> mFAQs = new ArrayList<>();


    public static FAQsRepository getInstance() {

        if (instance == null) {

            instance = new FAQsRepository();
        }


        return instance;
    }

    public MutableLiveData<List<FAQsDataModel>> getFAQs() {

        // calling the webservice task of function
        setFAQs();
        MutableLiveData<List<FAQsDataModel>> data = new MutableLiveData<>();
        data.setValue(mFAQs);

        return data;
    }


    // Getting live data from webservice
    private  void setFAQs () {

        QuestionDataModel question1 = new QuestionDataModel(
                "Who are you?", "I am Narjes a trained psychiatrist with over 12 years experience in the field.");
        QuestionDataModel question2 = new QuestionDataModel(
                "How are you?", "I am Norah a trained psychiatrist with over 12 years experience in the field.");
        QuestionDataModel question3 = new QuestionDataModel(
                "Where are you from?", "I am Ahmed a trained psychiatrist with over 12 years experience in the field.");

        ArrayList<QuestionDataModel> faqs1 = new ArrayList<>();
        ArrayList<QuestionDataModel> faqs2 = new ArrayList<>();
        ArrayList<QuestionDataModel> faqs3 = new ArrayList<>();
        faqs1.add(question1);
        faqs1.add(question2);
        faqs1.add(question3);
        faqs2.add(question2);
        faqs2.add(question3);
        faqs2.add(question1);
        faqs3.add(question3);
        faqs3.add(question1);
        faqs3.add(question2);


        CategoryDataModel category1 = new CategoryDataModel(R.drawable.psychaitry,
                "Psychiatry",
                "Psychiatry is the medical specialty devoted to the diagnosis, prevention, and treatment of mental disorders.",
                R.drawable.psychiatry_q);

        CategoryDataModel category2 = new CategoryDataModel(R.drawable.psychology,
                "Psychology",
                "Psychology is the science of behavior and mind which includes the study of conscious and unconscious phenomena.",
                R.drawable.psychology_q);

        CategoryDataModel category3 = new CategoryDataModel(R.drawable.behavioral_therapy,
                "Behavioral Therapy",
                "Behavior therapy is a broad term referring to clinical psychotherapy that uses techniques derived from behaviorism",
                R.drawable.behaviour_therapy_q);

        String FAQ1 = "Psychiatry";
        String FAQ2 = "Psychology";
        String FAQ3 = "Behavioral Therapy";

        FAQsDataModel FAQsDataModel1 = new FAQsDataModel(FAQ1, category1,faqs1);
        FAQsDataModel FAQsDataModel2 = new FAQsDataModel(FAQ2, category2,faqs2);
        FAQsDataModel FAQsDataModel3 = new FAQsDataModel(FAQ3, category3,faqs3);



        if (mFAQs.isEmpty()) {

            mFAQs.add(FAQsDataModel1);
            mFAQs.add(FAQsDataModel2);
            mFAQs.add(FAQsDataModel3);


        }
    }
}
