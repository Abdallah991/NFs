package com.fathom.nfs;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class OnBoardingAdapter extends PagerAdapter {

    // setting member variables
    Context mContext;
    List<ScreenItem> mScreenItems;

    // constructor
    public OnBoardingAdapter(Context context, List<ScreenItem> screenItems) {
        mContext = context;
        mScreenItems = screenItems;
    }


    // setting up an item
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        // getting the UI for each Item
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = inflater.inflate(R.layout.on_boarding_content, null);

        //linking the views with with variables
        ImageView img = layoutScreen.findViewById(R.id.boardingImage);
        TextView question = layoutScreen.findViewById(R.id.question);
        TextView description = layoutScreen.findViewById(R.id.description);
        TextView onBoardingText = layoutScreen.findViewById(R.id.onBoardingText);

        // setting the values of the views using the data model
        question.setText(mScreenItems.get(position).getQuestion());
        img.setImageResource(mScreenItems.get(position).getImage());
        description.setText(mScreenItems.get(position).getDescription());

        // changing the fonts to poppins light
        Typeface font = ResourcesCompat.getFont(mContext, R.font.poppins_light);
        description.setTypeface(font);
        onBoardingText.setTypeface(font);

        // adding the view to the ViewPager object
        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public int getCount() {
        // the count is the size of the List
        return mScreenItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        //destroy the view when its not visible
        container.removeView((View)object);
    }


}
