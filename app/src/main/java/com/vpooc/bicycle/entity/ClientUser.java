package com.vpooc.bicycle.entity;

import android.graphics.Bitmap;

/**
 * Created by Administrator on 2016/6/23.
 */
public class ClientUser {
    private String ClientID;
    private Bitmap ClientImage;
    private String UrlClientImage;

    public ClientUser() {
    }

    public String getUrlClientImage() {
        return UrlClientImage;
    }

    public void setUrlClientImage(String urlClientImage) {
        UrlClientImage = urlClientImage;
    }

    public Bitmap getClientImage() {
        return ClientImage;
    }

    public void setClientImage(Bitmap clientImage) {
        ClientImage = clientImage;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }
}
