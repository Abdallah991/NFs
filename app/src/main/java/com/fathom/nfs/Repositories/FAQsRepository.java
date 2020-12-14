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
                "What is Psychiatry?", "Psychiatry deals with more extreme mental disorders such as Schizophrenia, where a chemical imbalance plays into an individual’s mental disorder. Issues coming from the “inside” to “out”\n");
        QuestionDataModel question2 = new QuestionDataModel(
                "What Treatments Do Psychiatrists Use?", "Psychiatrists use a variety of treatments – including various forms of psychotherapy, medications, psychosocial interventions, and other treatments (such as electroconvulsive therapy or ECT), depending on the needs of each patient.");
        QuestionDataModel question3 = new QuestionDataModel(
                "What is the difference between psychiatrists and psychologists?", "A psychiatrist is a medical doctor (completed medical school and residency) with special training in psychiatry. A psychologist usually has an advanced degree, most commonly in clinical psychology, and often has extensive training in research or clinical practice.");
        QuestionDataModel question4 = new QuestionDataModel(
                "What is Psychology ?", "Psychology is the scientific study of the mind and behavior, according to the American Psychological Association. Psychology is a multifaceted discipline and includes many sub-fields of study such areas as human development, sports, health, clinical, social behavior, and cognitive processes.");
        QuestionDataModel question5 = new QuestionDataModel(
                "What is psychologists goal?", "Those with issues coming from the “outside” “in” (i.e. lost job and partner so feeling depressed) are better suited with psychologists, they offer guiding you into alternative and healthier perspectives in tackling daily life during ‘talk therapy’");
        QuestionDataModel question6 = new QuestionDataModel(
                "What is the difference between psychiatrists and psychologists?", "Psychiatrists can prescribe and psychologists cannot, they may be able to build a report and suggest medications to a psychiatrist but it is the psychiatrist that has the final say.");
        QuestionDataModel question7 = new QuestionDataModel(
                "What is a behavioural therapist?", "A behavioural therapist such as an ABA (“Applied Behavioural Analysis”) therapist or an occupational therapist focuses not on the psychological but rather the behavioural aspects of an issue. In a sense, it can be considered a “band-aid” fix, however, it has consistently shown to be an extremely effective method in turning maladaptive behaviours with adaptive behaviours.");
        QuestionDataModel question8 = new QuestionDataModel(
                "Why behavioral therapy?", "Cognitive-behavioral therapy is used to treat a wide range of issues. It's often the preferred type of psychotherapy because it can quickly help you identify and cope with specific challenges. It generally requires fewer sessions than other types of therapy and is done in a structured way.");
        QuestionDataModel question9 = new QuestionDataModel(
                "Is behavioral therapy beneficial?", "Psychiatrists can prescribe and psychologists cannot, they may be able to build a report and suggest medications to a psychiatrist but it is the psychiatrist that has the final say.");
        QuestionDataModel question10 = new QuestionDataModel(
                "What is alternative therapy?", "Complementary and alternative therapies typically take a holistic approach to your physical and mental health.");
        QuestionDataModel question11 = new QuestionDataModel(
                "Can they treat mental health problems?", "Complementary and alternative therapies can be used as a treatment for both physical and mental health problems. The particular problems that they can help will depend on the specific therapy that you are interested in, but many can help to reduce feelings of depression and anxiety. Some people also find they can help with sleep problems, relaxation, and feelings of stress.");
        QuestionDataModel question12 = new QuestionDataModel(
                "What else should I consider before starting therapy?", "Only you can decide whether a type of treatment feels right for you, But it might help you to think about: What do I want to get out of it? Could this therapy be adapted to meet my needs?");

        ArrayList<QuestionDataModel> faqs1 = new ArrayList<>();
        ArrayList<QuestionDataModel> faqs2 = new ArrayList<>();
        ArrayList<QuestionDataModel> faqs3 = new ArrayList<>();
        ArrayList<QuestionDataModel> faqs4 = new ArrayList<>();
        faqs1.add(question1);
        faqs1.add(question2);
        faqs1.add(question3);
        faqs2.add(question4);
        faqs2.add(question5);
        faqs2.add(question6);
        faqs3.add(question7);
        faqs3.add(question8);
        faqs3.add(question9);
        faqs4.add(question10);
        faqs4.add(question11);
        faqs4.add(question12);


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
        CategoryDataModel category4 = new CategoryDataModel(R.drawable.alternative_healing,
                "Alternative Therapy",
                "Complementary and alternative therapies typically take a holistic approach to your physical and mental health.",
                R.drawable.alternative_therapy_q);

        String FAQ1 = "Psychiatry";
        String FAQ2 = "Psychology";
        String FAQ3 = "Behavioral Therapy";
        String FAQ4 = "Alternative Therapy";

        FAQsDataModel FAQsDataModel1 = new FAQsDataModel(FAQ1, category1,faqs1);
        FAQsDataModel FAQsDataModel2 = new FAQsDataModel(FAQ2, category2,faqs2);
        FAQsDataModel FAQsDataModel3 = new FAQsDataModel(FAQ3, category3,faqs3);
        FAQsDataModel FAQsDataModel4 = new FAQsDataModel(FAQ4, category4,faqs4);



        if (mFAQs.isEmpty()) {

            mFAQs.add(FAQsDataModel1);
            mFAQs.add(FAQsDataModel2);
            mFAQs.add(FAQsDataModel3);
            mFAQs.add(FAQsDataModel4);


        }
    }
}
