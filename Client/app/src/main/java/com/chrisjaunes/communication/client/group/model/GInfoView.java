package com.chrisjaunes.communication.client.group.model;

import android.graphics.Bitmap;

import com.chrisjaunes.communication.client.utils.BitmapHelper;

public class GInfoView {
    GInfo gInfo;
    Bitmap avatar;
    public GInfoView(final GInfo gInfo) {
        this.gInfo = gInfo;
        this.avatar = BitmapHelper.StringToBitmap(this.gInfo.getGroup_avatar());
    }
    public int getGroup() {return gInfo.getGroup();}
    public String getGroupName() {return gInfo.getGroup_name();}
    public Bitmap getAvatar() {return avatar;}
}
