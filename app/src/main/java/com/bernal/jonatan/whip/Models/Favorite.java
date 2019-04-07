package com.bernal.jonatan.whip.Models;

public class Favorite {


    private boolean isFav;

    public Favorite() {
    }

    public Favorite(boolean isFav) {
        this.isFav = isFav;
    }


    public boolean isFav() {
        return isFav;
    }

    public void setFav(boolean fav) {
        isFav = fav;
    }


}
