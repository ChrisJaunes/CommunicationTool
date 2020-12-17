package com.chrisjaunes.communication.client.group.model;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

import com.chrisjaunes.communication.client.utils.BitmapHelper;
/**
 * 设计模式：适配器模式（对象适配器）
 * version 1.1 : 使用了适配器模式
 * 持有 gInfoRaw，处于编码简便的考虑，没有单独出一个接口
 * version 1.2 : CONTACTS_VIEW_DEFAULT
 * @author chrisjuanes
 * @version 1.2
 */
public class GInfoView {
    private final GInfoRaw gInfoRaw;
    private Bitmap avatarView;
    public GInfoView(final GInfoRaw gInfo) {
        gInfoRaw = gInfo;
        if (null == gInfoRaw.getGroup_avatar()) {
            avatarView = BitmapHelper.AVATAR_DEFAULT;
        } else {
            avatarView = BitmapHelper.StringToBitmap(gInfoRaw.getGroup_avatar());
        }

    }
    public int getGroup() {return gInfoRaw.getGroup();}
    public String getGroupName() {return gInfoRaw.getGroup_name();}
    public Bitmap getAvatarView() {return avatarView;}
    public void setAvatarView(@NonNull final Bitmap avatarView) {this.avatarView = avatarView;}
}
