package com.fathom.nfs.DataModels;

import androidx.lifecycle.ViewModel;

public class HelpLinesDataModel  {

    private int helpLineImage;
    private String helpLineDescription;
    private String helpLinePhone;

    public HelpLinesDataModel(int helpLineImage, String helpLineDescription) {
        this.helpLineImage = helpLineImage;
        this.helpLineDescription = helpLineDescription;
    }

    public int getHelpLineImage() {
        return helpLineImage;
    }

    public String getHelpLineDescription() {
        return helpLineDescription;
    }

    public String getHelpLinePhone() {
        return helpLinePhone;
    }

}
