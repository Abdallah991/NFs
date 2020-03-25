package com.fathom.nfs;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import com.fathom.nfs.DataModels.ArticleDataModel;
import com.fathom.nfs.DataModels.CategoryDataModel;
import com.fathom.nfs.DataModels.DoctorDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.DataModels.UserDataModel;
import com.fathom.nfs.RecyclersAndAdapters.ArticleAdapter;
import com.fathom.nfs.RecyclersAndAdapters.DoctorsAdapter;
import com.fathom.nfs.RecyclersAndAdapters.HorizontalRecyclerView;
import com.fathom.nfs.RecyclersAndAdapters.ShopItemAdapter;
import com.fathom.nfs.ViewModels.ArticleViewModel;
import com.fathom.nfs.ViewModels.CategoryViewModel;
import com.fathom.nfs.ViewModels.DoctorsViewModel;
import com.fathom.nfs.ViewModels.ShopItemsViewModel;
import com.fathom.nfs.ViewModels.UserViewModel;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import static android.content.Context.MODE_PRIVATE;
import static com.fathom.nfs.SignUpActivity.USER;


public class Home extends Fragment {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private UserDataModel user = new UserDataModel();

    // TAG for Debugging
    private static final String TAG = "Recycler View";
    private static final String TAG2 = "Doctors";
    private static final String TAG3 = "Articles";
    private static final String TAG4 = "ShopItems";
    private static final String TAG5 = "FIREBASE";
    private static final String TAG6 = "User";
    // declaring member variables
    private ArrayList<DoctorDataModel> mDoctors = new ArrayList<>();
    private ArrayList<DoctorDataModel> homeDoctors = new ArrayList<>();
    private ArrayList<ArticleDataModel> mArticles = new ArrayList<>();
    private ArrayList<ArticleDataModel> homeArticles = new ArrayList<>();
    private ArrayList<ShopItemDataModel> mShopItems = new ArrayList<>();
    private ArrayList<ShopItemDataModel> homeShopItems = new ArrayList<>();
    private ArrayList<CategoryDataModel> mCategories = new ArrayList<>();
    private RecyclerView mDoctorsRecycler;
    private RecyclerView mArticlesRecycler;
    private RecyclerView mShopRecycler;
    private DoctorsAdapter mDoctorsAdapter;
    private ArticleAdapter mArticleAdapter;
    private ShopItemAdapter mShopItemAdapter;
    private HorizontalRecyclerView horizontalAdapter;
    private ScrollView content;
    private NavController mNavController;
    private DoctorsViewModel mDoctorsViewModel;
    private CategoryViewModel mCategoryViewModel;
    private ArticleViewModel mArticleViewModel;
    private ShopItemsViewModel mShopItemsViewModel;
    private UserViewModel mUserViewModel;
    private SearchView mSearchView;
    private ListView searchList;
    private ArrayAdapter<String> searchAdapter;
    private ArrayList<String> searchListItems = new ArrayList<>();

    private Button viewAllDoctors;
    private Button viewAllArticles;
    private Button viewAllShopItems;

    private final int actionId = R.id.action_homeFragment_to_doctorsDetails;
    private int actionSpecialityId = R.id.action_homeFragment_to_doctorsSpecialities;
    private int actionArticle = R.id.action_homeFragment_to_articleDetailed2;
    private int actionToDetailedShopItem = R.id.action_homeFragment_to_shopItemDetailed;

    private TextView userName;
    private ImageView sliderUserImage;
    private DoctorDataModel doctor = new DoctorDataModel();
    private static ProgressDialog myProgressDialog;



    public Home() {


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

         content = view.findViewById(R.id.content);
         mDoctorsRecycler = view.findViewById(R.id.doctorsRecyclerView);
         mArticlesRecycler = view.findViewById(R.id.articlesRecyclerView);
         mShopRecycler = view.findViewById(R.id.shopRecyclerView);

         mSearchView = view.findViewById(R.id.search);
         searchList = view.findViewById(R.id.searchResults);

         userName = view.findViewById(R.id.userName);

        LayoutInflater inflater = getLayoutInflater();
//        View slider = inflater.from(getApplicationContext()).inflate(R.layout.drawer_header, null);
        ViewGroup viewRoot = (ViewGroup) inflater.inflate(R.layout.drawer_header,null);

        Log.d("User Image", " " + sliderUserImage);
        sliderUserImage = viewRoot.findViewById(R.id.sliderUserImage);


        Log.d("User Image", " " + sliderUserImage);



        int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
        TextView textView = mSearchView.findViewById(id);
        textView.setTextColor(R.color.colorPrimary);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        if (searchListItems.isEmpty()) {

            searchListItems.addAll(Arrays.asList(getResources().getStringArray(R.array.home_searchables)));
        }

        searchAdapter = new ArrayAdapter<>(
                getContext(),
                R.layout.search_list_item,
                searchListItems
        );

        searchList.setAdapter(searchAdapter);


        searchList.setVisibility(View.GONE);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
             @Override
             public boolean onQueryTextSubmit(String s) {
                 Toast.makeText(getContext(),"Submit "   , Toast.LENGTH_SHORT).show();


                 searchList.setVisibility(View.GONE);

                 return false;
             }

             @Override
             public boolean onQueryTextChange(String s) {

                 searchList.setVisibility(View.VISIBLE);
                 searchAdapter.getFilter().filter(s);


                 return false;
             }
         });

        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                searchList.setVisibility(View.GONE);

                return false;
            }
        });

        searchList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

               String listItem = (String) searchList.getItemAtPosition(i);

                Toast.makeText(getContext(),"Selected item is " +listItem   , Toast.LENGTH_SHORT).show();


                switch (listItem) {

                    case "Shop":
//                    case 0:
                        mNavController.navigate(R.id.action_homeFragment_to_shopFragment);
                        break;
//                    case 1:
                    case "Appointments":
                        mNavController.navigate(R.id.action_homeFragment_to_appointmentsFragment);
                        break;

                    case "Articles":
//                    case 2:
                        mNavController.navigate(R.id.action_homeFragment_to_articles);
                        break;

                    case "Help Lines":
//                    case 3:
                        mNavController.navigate(R.id.action_homeFragment_to_helpLinesFragment);
                        break;

                    case "Book Marks":
//                    case 4:
                        mNavController.navigate(R.id.action_homeFragment_to_bookMarksFragment);
                        break;

                    case "Doctors":
//                    case 5:
                        mNavController.navigate(R.id.action_homeFragment_to_doctors);
                        break;


                }

                InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        // Calling User View Model

        mUserViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
        mUserViewModel.initUser(getContext());

        mUserViewModel.getUser(getContext()).observe(getViewLifecycleOwner(), new Observer<UserDataModel>() {
            @Override
            public void onChanged(UserDataModel userDataModel) {
                user = userDataModel;
//                user.notify();
                userName.setText(user.getFirstName());
            }
        });

         // Calling the View Model
        mDoctorsViewModel = new ViewModelProvider(requireActivity()).get(DoctorsViewModel.class);
        mDoctorsViewModel.initDoctors();

        mDoctorsViewModel.getDoctors().observe(getViewLifecycleOwner(), new Observer<List<DoctorDataModel>>() {
            @Override
            public void onChanged(List<DoctorDataModel> doctorDataModels) {

                Log.d("MVVM"," Adpter is being notified with any CHANGE");
                mDoctorsAdapter.notifyDataSetChanged();
                initRecyclerView();
            }
        });

        mCategoryViewModel = new ViewModelProvider(requireActivity()).get(CategoryViewModel.class);
        mCategoryViewModel.initCategories();

        mCategoryViewModel.getCategories().observe(getViewLifecycleOwner(), new Observer<List<CategoryDataModel>>() {
            @Override
            public void onChanged(List<CategoryDataModel> categoryDataModels) {

                horizontalAdapter.notifyDataSetChanged();

            }
        });

        mArticleViewModel = new ViewModelProvider(requireActivity()).get(ArticleViewModel.class);
        mArticleViewModel.initArticles();

        mArticleViewModel.getArticles().observe(getViewLifecycleOwner(), new Observer<List<ArticleDataModel>>() {
            @Override
            public void onChanged(List<ArticleDataModel> articleDataModels) {

                mArticleAdapter.notifyDataSetChanged();
            }
        });

        mShopItemsViewModel = new ViewModelProvider(requireActivity()).get(ShopItemsViewModel.class);
        mShopItemsViewModel.initShopItem();

        mShopItemsViewModel.getShopItems().observe(getViewLifecycleOwner(), new Observer<List<ShopItemDataModel>>() {
            @Override
            public void onChanged(List<ShopItemDataModel> shopItemDataModels) {

                mShopItemAdapter.notifyDataSetChanged();
            }
        });


        viewAllDoctors = view.findViewById(R.id.viewDoctors);
        viewAllArticles = view.findViewById(R.id.viewArticles);
        viewAllShopItems = view.findViewById(R.id.viewShop);
        // Readjusting the position of layout elements
        ViewCompat.setLayoutDirection(content, ViewCompat.LAYOUT_DIRECTION_LTR);

        // Setting click listeners
        viewAllDoctors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.action_homeFragment_to_doctors);

            }
        });

        viewAllShopItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.action_homeFragment_to_shopFragment);

            }
        });

        viewAllArticles.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.action_homeFragment_to_articles);

            }
        });

            // initializing the recycler
        Log.d("MVVM"," Arraylist size" +mDoctors.size());

        initRecyclerView();

            if (mDoctors.isEmpty()) {

                loadingRecycler();
                Log.d("MVVM"," Arraylist is empty" +mDoctors.size());

            }
        SharedPreferences prefs = getActivity().getSharedPreferences(USER, MODE_PRIVATE);
        String userFirstName = prefs.getString("FirstName", "");


            if ((!userFirstName.equals("")) && userName.getText().equals("")) {
                setUpDisplayName();

        }


            if (mDoctors.isEmpty()) {
                myProgressDialog = new ProgressDialog(getContext());
                myProgressDialog.setProgressStyle(R.style.MyAlertDialogStyle);
                myProgressDialog.setCancelable(false);
                myProgressDialog.setMessage("Please Wait ...");
                myProgressDialog.show();
            }




    }



    private void initRecyclerView(){

        Log.d(TAG, "initRecyclerView: init recyclerview");

        // setting the adapter and adjusting to horizontal view
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = getView().findViewById(R.id.specialties);
        recyclerView.setLayoutManager(layoutManager);
        mCategories = (ArrayList<CategoryDataModel>)  mCategoryViewModel.getCategories().getValue();
        horizontalAdapter = new HorizontalRecyclerView( mCategories, getContext(), actionSpecialityId, mNavController, mCategoryViewModel);
        recyclerView.setAdapter(horizontalAdapter);

        // setting the adapter to recycler
        mDoctors = (ArrayList<DoctorDataModel>) mDoctorsViewModel.getDoctors().getValue();


        mDoctorsAdapter = new DoctorsAdapter(homeDoctors, getContext(), mNavController, actionId, mDoctorsViewModel );
        mDoctorsRecycler.setAdapter(mDoctorsAdapter);
        mDoctorsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
//        mDoctorsAdapter.notifyDataSetChanged();

        // setting the adapter to recycler
        mArticles = (ArrayList<ArticleDataModel>) mArticleViewModel.getArticles().getValue();
        mArticleAdapter = new ArticleAdapter(homeArticles, getContext(), mNavController, actionArticle, mArticleViewModel);
        mArticlesRecycler.setAdapter(mArticleAdapter);
        mArticlesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        // setting the adapter to recycler
        mShopItems = (ArrayList<ShopItemDataModel>) mShopItemsViewModel.getShopItems().getValue();
        mShopItemAdapter = new ShopItemAdapter(homeShopItems, getContext(), mNavController, actionToDetailedShopItem, mShopItemsViewModel);
        mShopRecycler.setAdapter(mShopItemAdapter);
        mShopRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onPause() {
        super.onPause();

        searchList.setVisibility(View.GONE);

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.d("Home", "onResume() got fired");
        int i = 0;
        int j = 0;
        int k = 0;

        if (!mDoctors.isEmpty()) {
            i = mDoctors.size();
            j = mArticles.size();
            k = mShopItems.size();

            if (homeDoctors.isEmpty()) {
                for (DoctorDataModel doctor : mDoctors) {
                    homeDoctors.add(doctor);


                }
                homeDoctors.subList(3, i).clear();
            }

            if (homeArticles.isEmpty()) {
                for (ArticleDataModel article : mArticles) {
                    homeArticles.add(article);


                }
                homeArticles.subList(3, j).clear();

            }

            if(homeShopItems.isEmpty()) {
                for (ShopItemDataModel shopItem : mShopItems) {
                    homeShopItems.add(shopItem);

                }
                homeShopItems.subList(3, k).clear();

            }


        }

        initRecyclerView();

        searchList.setVisibility(View.GONE);



            Toast.makeText(getContext(), "User is " +user.getFirstName() , Toast.LENGTH_SHORT ).show();
//            sliderUserImage.setImageResource(R.drawable.user);




    }

    private void setUpDisplayName() {

        SharedPreferences preferences = getActivity().getSharedPreferences(USER, MODE_PRIVATE);
        String name = preferences.getString("FirstName", null);
        Log.d(TAG6, name);
        userName.setText(name);




    }

    private void loadingRecycler() {

        Handler myHandler;
        int SPLASH_TIME_OUT = 7000;
        myHandler = new Handler();

        Log.d(TAG2, "loading Recycler been called");

        // showing the Splash screen for two seconds then going to on boarding activity
        myHandler.postDelayed(new Runnable() {
            @Override
            public void run() {



                int i = 0;
                int j = 0;
                int k = 0;

                for (DoctorDataModel doctor : mDoctors) {
                    homeDoctors.add(doctor);
                }

                for (ArticleDataModel article : mArticles) {
                    homeArticles.add(article);
                }

                for (ShopItemDataModel shopItem : mShopItems) {
                    homeShopItems.add(shopItem);
                }

                i = mDoctors.size();
                j = mArticles.size();
                k = mShopItems.size();


                homeDoctors.subList(3, i).clear();
                homeArticles.subList(3, j).clear();
                homeShopItems.subList(3, k).clear();


                initRecyclerView();
                Log.d(TAG2, "initialising recycler with a delay called ");

                sliderUserImage.setImageBitmap(user.getUserImage());

                myProgressDialog.dismiss();
            }
        }, SPLASH_TIME_OUT);
    }
}
