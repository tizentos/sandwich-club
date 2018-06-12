package com.udacity.sandwichclub.utils;


import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    //Define a static sandwich object to receive parsed JSON data
    public static Sandwich mSandwich;

    public static Sandwich parseSandwichJson(String json) {
        //define important fields for storing various keys in the JSON data
        JSONObject jsonObject;
        JSONObject name;
        String placeOfOrigin=null;
        String description=null;
        String image = null;
        String mainName = null;


        JSONArray alsoKnownAsJSONArray;
        JSONArray ingredientsJSONArray;
        List<String> alsoKnownAs = new ArrayList<>();
        List<String> ingredients = new ArrayList<>();
        try{

            //Code to parse the JSON Data

            jsonObject=new JSONObject(json);

            name=jsonObject.getJSONObject("name");
            mainName=name.getString("mainName");
            alsoKnownAsJSONArray=name.getJSONArray("alsoKnownAs");

            for (int i=0; i<alsoKnownAsJSONArray.length();i++){
                alsoKnownAs.add(alsoKnownAsJSONArray.getString(i));
            }

            placeOfOrigin=jsonObject.getString("placeOfOrigin");
            description=jsonObject.getString("description");
            image=jsonObject.getString("image");

            ingredientsJSONArray=jsonObject.getJSONArray("ingredients");
            for (int i=0; i<ingredientsJSONArray.length();i++){
                ingredients.add(ingredientsJSONArray.getString(i));
            }

        }catch (JSONException e){
            e.printStackTrace();
        }

        mSandwich=new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);
        return mSandwich;

    }
}
