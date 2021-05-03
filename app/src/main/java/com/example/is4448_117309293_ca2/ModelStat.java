package com.example.is4448_117309293_ca2;

public class ModelStat {
    //use some variable names as in json response
//    {
//        "ID": "5c8c585b-9cea-49b4-91fa-baa1fbd5c5ba",
//            "Country": "Afghanistan",
//            "CountryCode": "AF",
//            "Slug": "afghanistan",
//            "NewConfirmed": 0,
//            "TotalConfirmed": 59576,
//            "NewDeaths": 0,
//            "TotalDeaths": 2618,
//            "NewRecovered": 0,
//            "TotalRecovered": 53103,
//            "Date": "2021-04-30T08:59:21.677Z",
//            "Premium": {}
//    }

    String Country, CountryCode, Slug, NewConfirmed,
            TotalConfirmed, NewDeaths, TotalDeaths,
            NewRecovered, TotalRecovered, Date;

    public ModelStat() {
    }

    public ModelStat(String country, String countryCode, String slug, String newConfirmed, String totalConfirmed, String newDeaths, String totalDeaths, String newRecovered, String totalRecovered, String date) {
        Country = country;
        CountryCode = countryCode;
        Slug = slug;
        NewConfirmed = newConfirmed;
        TotalConfirmed = totalConfirmed;
        NewDeaths = newDeaths;
        TotalDeaths = totalDeaths;
        NewRecovered = newRecovered;
        TotalRecovered = totalRecovered;
        Date = date;
    }


    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getCountryCode() {
        return CountryCode;
    }

    public void setCountryCode(String countryCode) {
        CountryCode = countryCode;
    }

    public String getSlug() {
        return Slug;
    }

    public void setSlug(String slug) {
        Slug = slug;
    }

    public String getNewConfirmed() {
        return NewConfirmed;
    }

    public void setNewConfirmed(String newConfirmed) {
        NewConfirmed = newConfirmed;
    }

    public String getTotalConfirmed() {
        return TotalConfirmed;
    }

    public void setTotalConfirmed(String totalConfirmed) {
        TotalConfirmed = totalConfirmed;
    }

    public String getNewDeaths() {
        return NewDeaths;
    }

    public void setNewDeaths(String newDeaths) {
        NewDeaths = newDeaths;
    }

    public String getTotalDeaths() {
        return TotalDeaths;
    }

    public void setTotalDeaths(String totalDeaths) {
        TotalDeaths = totalDeaths;
    }

    public String getNewRecovered() {
        return NewRecovered;
    }

    public void setNewRecovered(String newRecovered) {
        NewRecovered = newRecovered;
    }

    public String getTotalRecovered() {
        return TotalRecovered;
    }

    public void setTotalRecovered(String totalRecovered) {
        TotalRecovered = totalRecovered;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
}
