package com.fathom.nfs;


import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.fathom.nfs.RecyclersAndAdapters.CartAdapter;

import static com.fathom.nfs.DataModels.CartDataModel.shoppingCartItems;


/**
 * A simple {@link Fragment} subclass.
 */
public class Cart extends Fragment {

    private ScrollView cartContent;
    private RecyclerView cartRecycler;
    private CartAdapter mCartAdapter;
    private Button checkoutButton;
    private Button continueShoppingButton;
    private TextView totalPrice;
    private NavController mNavController;
    private double totalCartPrice = 0;

    public Cart() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cartContent = view.findViewById(R.id.cartContent);
        cartRecycler = view.findViewById(R.id.cartItemsRecyclerView);
        totalPrice = view.findViewById(R.id.totalPrice);
        continueShoppingButton = view.findViewById(R.id.continueShopping);


        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        ViewCompat.setLayoutDirection(cartContent, ViewCompat.LAYOUT_DIRECTION_LTR);



        initRecyclers();

        // This callback will only be called when MyFragment is at least Started.
        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Handle the back button event

                mNavController.navigate(R.id.action_cart_to_shopFragment);
            }
        };

        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);

        // The callback can be enabled or disabled here or in handleOnBackPressed()

        continueShoppingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavController.navigate(R.id.action_cart_to_shopFragment);
            }
        });

    }



    private void initRecyclers() {


        Toast.makeText(getContext(), "cart items: " +shoppingCartItems.size(), Toast.LENGTH_SHORT).show();

        // setting the adapter to recycler
        mCartAdapter = new CartAdapter(shoppingCartItems, getContext(), mNavController);
        cartRecycler.setAdapter(mCartAdapter);
        cartRecycler.setLayoutManager(new LinearLayoutManager(getContext()));


        for (int i =0; i < shoppingCartItems.size(); i++ ) {

            totalCartPrice += Double.parseDouble( shoppingCartItems.get(i).getPrice());


        }

        String toatlCartAmount = Double.toString(totalCartPrice);
        totalPrice.setText( toatlCartAmount);

    }
}
