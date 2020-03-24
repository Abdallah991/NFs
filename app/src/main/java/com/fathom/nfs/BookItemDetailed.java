package com.fathom.nfs;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fathom.nfs.DataModels.BookRowDataModel;
import com.fathom.nfs.DataModels.ShopItemDataModel;
import com.fathom.nfs.RecyclersAndAdapters.BookChildAdapter;
import com.fathom.nfs.ViewModels.BookArrayViewModel;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class BookItemDetailed extends Fragment {

    private LinearLayout bookDetailedContent;
    private ImageView bookImage;
    private TextView price;
    private TextView bookDescription;
    private int positionOfItem;
    private int positionOfBookArray;
    private ArrayList<ShopItemDataModel> mbookItems;
    private BookChildAdapter mBookItemAdapter;
    private RecyclerView horizontalShopItemRecycler;
    private BookArrayViewModel model;
    private NavController mNavController;
    public static ShopItemDataModel bookDetailed;
//    private int actionId = R.id.action_shopItemDetailed_self;

    public BookItemDetailed() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_item_detailed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bookDetailedContent = view.findViewById(R.id.bookItemDetailedContent);
        bookImage = view.findViewById(R.id.bookItemDetailedImage);
        price = view.findViewById(R.id.bookItemDetailedPrice);
        bookDescription = view.findViewById(R.id.bookItemDetailedDescription);

        mNavController = Navigation.findNavController(getActivity(), R.id.nav_host_fragment);

        model = new ViewModelProvider(requireActivity()).get(BookArrayViewModel.class);
        positionOfItem = model.getPositionOfItems();
        positionOfBookArray = model.getPositionOfRow();

        model.getBookArrays().observe(getViewLifecycleOwner(), new Observer<List<BookRowDataModel>>() {
            @Override
            public void onChanged(List<BookRowDataModel> bookRowDataModels) {

//                BookRowDataModel bookRow = bookRowDataModels.get(positionOfBookArray);
//                ShopItemDataModel book =  bookRow.getBooks().get(positionOfItem);

//                notify();





            }
        });

        bookImage.setImageBitmap(bookDetailed.getShopItemImage());
        price.setText(bookDetailed.getPrice());
        bookDescription.setText(bookDetailed.getItemDescription());

        ViewCompat.setLayoutDirection(bookDetailedContent, ViewCompat.LAYOUT_DIRECTION_LTR);



    }
}
