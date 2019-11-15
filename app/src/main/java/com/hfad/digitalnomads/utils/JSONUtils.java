package com.hfad.digitalnomads.utils;

import com.hfad.digitalnomads.dataBase.Notes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils {

    private static final String KEY_ARTICLES = "articles";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_URL = "url";
    private static final String KEY_URL_TO_IMAGE  = "urlToImage";
    private static final String KEY_PIBLISHED_AT = "publishedAt";


    public static ArrayList<Notes> getNotesFromJSON(JSONObject jsonObject){
        ArrayList<Notes> result = new ArrayList<>();
        if (jsonObject == null){
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_ARTICLES);
            for (int i = 0; i < jsonArray.length(); i++){
            JSONObject objectNotes = jsonArray.getJSONObject(i);
            int ID = i;
            String author = objectNotes.getString(KEY_AUTHOR);
            String title = objectNotes.getString(KEY_TITLE);
            String description = objectNotes.getString(KEY_DESCRIPTION);
            String url = objectNotes.getString(KEY_URL);
            String urlToImage = objectNotes.getString(KEY_URL_TO_IMAGE);
            String publishedAt = objectNotes.getString(KEY_PIBLISHED_AT);
                Notes notes = new Notes(ID, author, title, description, url, urlToImage, publishedAt);
                result.add(notes);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


}
