package com.hfad.digitalnomads.utils;

import com.hfad.digitalnomads.dataBase.Notes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JSONUtils { //класс для преобразования JSON в обьект

    private static final String KEY_ARTICLES = "articles";
    private static final String KEY_AUTHOR = "author";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_URL = "url";
    private static final String KEY_URL_TO_IMAGE  = "urlToImage";
    private static final String KEY_PIBLISHED_AT = "publishedAt";

//метод для создания массива с фильмами
    public static ArrayList<Notes> getNotesFromJSON(JSONObject jsonObject){
        ArrayList<Notes> result = new ArrayList<>();// массив для хранения фильмов
        if (jsonObject == null){
            return result;
        }
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(KEY_ARTICLES); //получили jsonArray
            for (int i = 0; i < jsonArray.length(); i++){  //через цикл получаем фильм
            JSONObject objectNotes = jsonArray.getJSONObject(i);
            int ID = i;
            String author = objectNotes.getString(KEY_AUTHOR); //получаем автора статьи
            String title = objectNotes.getString(KEY_TITLE); //получаем заголовок
            String description = objectNotes.getString(KEY_DESCRIPTION); //получаем описание
            String url = objectNotes.getString(KEY_URL); //получаем ссылку на статью
            String urlToImage = objectNotes.getString(KEY_URL_TO_IMAGE); //получаем ссылку на картинку
            String publishedAt = objectNotes.getString(KEY_PIBLISHED_AT); //получаем дату публикации

                Notes notes = new Notes(ID, author, title, description, url, urlToImage, publishedAt); //добавляем в обьект автора, заголовок, описание, ссылку на статью. ссылку на картинку, дату публикации
                result.add(notes); //полученый фильм добавляем в массив



            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }


}
