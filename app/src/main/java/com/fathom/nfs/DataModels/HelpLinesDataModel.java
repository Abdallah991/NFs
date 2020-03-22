package com.fathom.nfs.DataModels;

import androidx.lifecycle.ViewModel;

public class HelpLinesDataModel  {

    private int helpLineImage;
    private int descriptionImage;
    private String helpLineDescription;
    private String helpLinePhone;

    public HelpLinesDataModel(int helpLineImage, int descImage, String helpLineDescription) {
        this.helpLineImage = helpLineImage;
        this.helpLineDescription = helpLineDescription;
        this.descriptionImage = descImage;
    }

    public HelpLinesDataModel(int helpLineImage, int descriptionImage, String helpLineDescription, String helpLinePhone) {
        this.helpLineImage = helpLineImage;
        this.descriptionImage = descriptionImage;
        this.helpLineDescription = helpLineDescription;
        this.helpLinePhone = helpLinePhone;
    }

    public int getHelpLineImage() {
        return helpLineImage;
    }

    public String getHelpLineDescription() {
        return helpLineDescription;
    }

    public int getDescriptionImage() {
        return descriptionImage;
    }


    public String getHelpLinePhone() {
        return helpLinePhone;
    }

}
