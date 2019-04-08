package com.bernal.jonatan.whip.Presenters;


import android.content.Context;

import com.bernal.jonatan.whip.Models.Favorite;
import com.bernal.jonatan.whip.Services.ServiceFavorite;


public class FavoritesController {

    Favorite fav;
    ServiceFavorite sfav;


    public Favorite getFavorite(Context ViewContext, String Post_ID) {
        sfav = new ServiceFavorite(ViewContext);
        fav = sfav.getLike(Post_ID);
        return fav;
    }

    public void setDislike(Context ViewContext, String Post_ID) {
        sfav = new ServiceFavorite(ViewContext);
        sfav.setDislike(Post_ID);
    }

    public void setLike(Context ViewContext, String Post_ID) {
        sfav = new ServiceFavorite(ViewContext);
        sfav.setLike(Post_ID);
    }





}
