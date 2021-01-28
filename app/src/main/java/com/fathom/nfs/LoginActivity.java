package com.fathom.nfs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.results.SignInResult;
import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.DataModels.ReviewDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.DataModels.UserDataModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import static com.fathom.nfs.SignUpActivity.USER;


public class LoginActivity extends AppCompatActivity {

    // declaring class variables
    private TextView forgotPassword;
    private EditText userName;
    private EditText password;
    private Button signUp;
    private Button login;
    private FirebaseAuth mAuth;
    private final String TAG = "SIGN IN";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DoctorDataModel doctor = new DoctorDataModel();
    private ShopItemDataModel shopItem = new ShopItemDataModel();
    private ArticleDataModel article = new ArticleDataModel();
    private ReviewDataModel review1 = new ReviewDataModel();
    private ReviewDataModel review2 = new ReviewDataModel();
    private ReviewDataModel review3 = new ReviewDataModel();
    private UserDataModel user = new UserDataModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // init member variables
        forgotPassword = findViewById(R.id.forgotPassword);
        userName = findViewById(R.id.email);
        password = findViewById(R.id.lastName);
        signUp = findViewById(R.id.signUp);
        login = findViewById(R.id.login);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Go to SignUp Activity
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        SignUpActivity.class);
                startActivity(intent);

            }
        });

        // Go to Forget Password Activity
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),
                        ForgotPasswordActivity.class);
                startActivity(intent);

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkIfDocumentExist();
                    SignIn();


//                uploadArticle();

            }
        });



    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.e(TAG, "User is " +currentUser);

    }

    private void SignIn() {

        String username = userName.getText().toString();
        String userPassword = password.getText().toString();

        SharedPreferences userPrefs = getSharedPreferences(USER, 0);
        userPrefs.edit().putString("Email", username).apply();


        if (username.isEmpty() || userPassword.isEmpty()) return;
            mAuth.signInWithEmailAndPassword(username, userPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                String name = user.getDisplayName();
//                                Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
                                FirebaseUser signedUpUser = FirebaseAuth.getInstance().getCurrentUser();
                                boolean emailVerified = signedUpUser.isEmailVerified();
                                if (emailVerified) {
                                    Intent intent = new Intent(getApplicationContext(),
                                            MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else  {
                                    Toast.makeText(getApplicationContext(), "Please Verify the email that have been sent to you", Toast.LENGTH_SHORT).show();
                                }

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getApplicationContext(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                            // ...
                        }
                    });

    }

    private void uploadArticle() {
        article.setArticleContent("Addressing the Misconceptions of Generalized Anxiety Disorder\n" +
                "\n" +
                "What are anxiety disorders? \n" +
                "Anxiety is a normal, adaptive response from our bodies in situations where we are in danger and must protect ourselves and pay attention to our surroundings. External stressors evoke a fight or flight response, which allows a quick response to a perceived threat as we either fight the danger or escape the situation. \n" +
                "\n" +
                "Experiencing anxiety in stressful situations can be beneficial as our bodies are alerted, motivated, and prepared to deal with stress. However, this adaptive response is typically confused with anxiety disorders, a class of mental disorders associated with fear and anxiety. What makes these disorders what they are is that the fear is out of proportion to the situation and is not adaptive or beneficial. Instead, it negatively impacts one’s ability to function normally. Anxiety disorders may lead to avoidance behaviours that can worsen one’s symptoms and lead to struggles for relationships, performance in the workplace, or social situations. \n" +
                "\n" +
                "Anxiety disorders are the most common mental disorders as they affect approximately 30% of adults. They are often associated with other psychiatric disorders, including major depressive disorder (MDD), post-traumatic stress disorder (PTSD) and obsessive-compulsive disorder (OCD). There are various types of anxiety disorders; these include Generalized Anxiety Disorder (GAD), Social Anxiety Disorder, Panic Disorder, and several specific phobias. \n" +
                "\n" +
                "What is Generalized Anxiety Disorder (GAD)?\n" +
                "Generalized Anxiety Disorder is the most common type of anxiety disorder in adults. It includes excessive and intrusive worry even when there is nothing necessarily wrong and worrying about a perceived threat in a way that is not proportionate to the severity of the situation. Those with GAD usually tend to be hypervigilant to their surroundings and continuously scan their surroundings for any signs of threat. The course of GAD may be chronic and lifelong in some cases. There is no one specific cause of anxiety disorders such as GAD; however, risk factors may include biological factors, developmental factors, environmental factors, and psychological factors. There are, however, a few predictors of the course of GAD. These include family relationships, gender, and comorbidity with other mental disorders. When it comes to the prevalence of GAD, it appears to be higher in adults and women, as well as people with lower income and those who are divorced or widowed. In particular, GAD occurs around twice as often in women as it does in men. \n" +
                "\n" +
                "When it comes to symptoms of GAD, those can be both cognitive as well as physical. Physical symptoms include restlessness, disturbed sleep, fatigue, muscle aches or soreness, and always feeling on edge and sweating or nausea at times. As for cognitive symptoms, these include excessive and constant anxiety and worry around a variety of topics or situations, fear, irritability, and impaired concentration. Individuals with GAD also usually struggle with negative cognitions, overthinking and dealing with cognitive biases (which is scanning the environment for confirmation of their negative thoughts), as well as memory biases (which focus on negative past situations). Moreover, those with GAD experience thought patterns that contribute to anxiety, such as ‘catastrophizing,’ which is exaggerating the extent to which a situation is threatening and overworking the brain regions associated with the fight or flight response. These symptoms typically have to be occurring for a minimum of 6 months to be diagnosed with GAD according to the Diagnostic and Statistical Manual of Mental Disorders (DSM V) criteria. \n" +
                "\n" +
                "It is quite typical to assume that one does not have GAD when panic attacks are not present, which is a common misconception as panic attacks do not always have to be present to be diagnosed with GAD. This misconception can often lead to people avoiding help-seeking and dismissing the issue and therefore living with the symptoms with harmful coping mechanisms that they develop over time. Besides, some individuals are reassured by others that their thoughts and feelings are rational or will pass with time. However, their worry is persistent, and their symptoms are evident, which is yet another misconception that causes people to cope on their own and avoid diagnoses and treatment interventions. \n" +
                "\n" +
                "The most effective psychological treatment for GAD\n" +
                "There are psychological interventions one can take to manage GAD before turning to medication. The most effective and most researched psychological treatment for this specific disorder is cognitive behavioural therapy (CBT). CBT aims to identify the issue and face it, assessing what could trigger anxiety, focusing on consequences, and coping with worries. This therapy helps detect which thoughts are intrusive and most commonly occurring and taking the necessary steps to overcome them. A significant part of CBT is being aware of one’s thoughts to control them and take charge rather than letting them take over and develop into intrusive thoughts. CBT also includes observation and recording of one’s thoughts, feelings, and behaviours, as well as cognitive restructuring and logical thinking, which involves challenging maladaptive ways of thinking.  \n" +
                "\n" +
                "Final note \n" +
                "Generalized Anxiety Disorder is the most common type of anxiety disorder. However, many misconceptions surround this particular mental disorder, as people often get confused between anxiety, a normal, adaptive response to threat and anxiety disorders, which are maladaptive and negatively impact everyday functioning. Even though many people suffering from GAD continue with their lives untreated, effective therapeutic interventions aim to identify triggers and intrusive thoughts and overcome them. \n");

        article.setArticleType("Article");
        article.setArticleTitle("Misconceptions of Generalized Anxiety Disorder");
        article.setAuthorName("Sara Alekri");
        article.setBookmark(false);

        db.collection("Articles").document(article.getArticleTitle()).set(article);
    }

    private void checkIfDocumentExist() {

        SharedPreferences prefs = getSharedPreferences(USER, MODE_PRIVATE);
        String userEmail = prefs.getString("Email", "");
        String firstName = prefs.getString("FirstName", "");
        String lastName = prefs.getString("LastName", "");

        if (!userEmail.equals("")) {
            db.collection("Users").document(userEmail).get().
                    addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                if (document.exists()) {
                                    Log.d("USER1", "Document exists!");
                                    return;
                                } else {
                                    Log.d("USER1", "Document does not exist!");
                                    user.setEmail(userEmail);
                                    user.setFirstName(firstName);
                                    user.setLastName(lastName);
                                    db.collection("Users")
                                            .document(user.getEmail()).set(user);
                                    return;
                                }
                            } else {
                                Log.d(TAG, "Failed with: ", task.getException());
                            }
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "The email is not signed up", Toast.LENGTH_SHORT).show();
        }


    }
}
