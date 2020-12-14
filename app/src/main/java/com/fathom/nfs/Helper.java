package com.fathom.nfs;


import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;
import androidx.core.content.res.ResourcesCompat;


// Helper class will contain redundant code use and block it here to reuse it
public class Helper {

    public Helper() {

    }

    // changing the font to light poppins
    public final static void setTypeFace(Context c, TextView v) {
        Typeface font = ResourcesCompat.getFont(c, R.font.poppins_light);
        v.setTypeface(font);

    }

}
