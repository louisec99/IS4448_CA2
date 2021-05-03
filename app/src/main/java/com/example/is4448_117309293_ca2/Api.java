package com.example.is4448_117309293_ca2;

/**
 * Created by Belal on 9/9/2017.
 */

public class Api {

    private static final String ROOT_URL = "https://gleeson.io/IS4447/HeroAPI/v1/Api.php?apicall=";

    public static final String URL_CREATE_HERO = ROOT_URL + "createhero";
    public static final String URL_READ_HEROES = ROOT_URL + "getheroes";
    public static final String URL_UPDATE_HERO = ROOT_URL + "updatehero&id=";
    public static final String URL_DELETE_HERO = ROOT_URL + "deletehero&id=";

}