package com.hfad.digitalnomads.utils;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

//вся работа с сетью осуществляется в этом классе
public class NetworkUtils {

    private static final String BASE_URL = "https://newsapi.org/v2/everything?q=android&from=2019-04-00&sortBy=publi%20shedAt&apiKey=26eddb253e7840f988aec61f2ece2907";  //Базовый урл для составления запроса
    private static final String PARAMS_PAGE = "page";  //параметр номера страницы для составления запроса


    //метод для создания нашего запроса
    public static URL buildURL (int page){
        URL result = null; //создаем наш запрос  с нуливым значением

        Uri uri = Uri.parse(BASE_URL).buildUpon()  //создаем uri и передаем базовый урл. то есть поулчили строку в виде адреса к которому будем прикреплять параметр страницы
                .appendQueryParameter(PARAMS_PAGE, Integer.toString(page))  //добавляем значение страницы, полученной ранее (page)
                .build(); //собираем наш запрос

        try {
            result = new URL(uri.toString());  //создаем наш запрос и оборачиваем в try catch
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return result; //Возвращаем готовый запрос
    }



    //Метод получения JSON из сети
    public static JSONObject getJSONFromNetwork(int page){
        JSONObject result = null;
        URL url = buildURL(page); //вызываем метод создания запроса
        try {
            result = new JSONLoadTask().execute(url).get(); //получаем JSON
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }



    public static class JSONloader extends AsyncTaskLoader<JSONObject>{

        private Bundle bundle;
        private OnStartLoadingListener onStartLoadingListener;

        public interface OnStartLoadingListener {
            void onStartLoading();
        }

        public void setOnStartLoadingListener(OnStartLoadingListener onStartLoadingListener) {
            this.onStartLoadingListener = onStartLoadingListener;
        }

        public JSONloader(@NonNull Context context, Bundle bundle) {
            super(context);
            this.bundle = bundle;
        }

        @Override
        protected void onStartLoading() {
            super.onStartLoading();
            if (onStartLoadingListener != null){
                onStartLoadingListener.onStartLoading();
            }
            forceLoad();
        }

        @Nullable
        @Override
        public JSONObject loadInBackground() {
            if (bundle == null){
                return null;
            }
            String urlAsString = bundle.getString("url");
            URL url = null;
            try {
                url = new URL(urlAsString);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            JSONObject result = null;
            if (url == null) { //Проверка
                return result;
            }
            HttpsURLConnection connection = null; //создаем соединение
            try {
                connection = (HttpsURLConnection) url.openConnection(); //открываем соединение
                InputStream inputStream = connection.getInputStream(); //создаем поток ввода
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream); //чтение строки
                BufferedReader reader = new BufferedReader(inputStreamReader); //читаем сразу строками
                StringBuilder builder = new StringBuilder(); //сюда сохраняем полученную строку
                String line = reader.readLine();  //читаем данные
                while (line != null){
                    builder.append(line);
                    line = reader.readLine();

                }
                result = new JSONObject(builder.toString());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null){ //проверка на соединение
                    connection.disconnect(); //закрытие соединения
                }
            }


            return result;
        }
    }



    //класс загрузки данных из интернета
    private static class JSONLoadTask extends AsyncTask<URL, Void, JSONObject>{
        @Override
        protected JSONObject doInBackground(URL... urls) {
            JSONObject result = null;
            if (urls == null || urls.length == 0) { //Проверка
                return result;
            }
            HttpsURLConnection connection = null; //создаем соединение
            try {
                connection = (HttpsURLConnection) urls[0].openConnection(); //открываем соединение
                InputStream inputStream = connection.getInputStream(); //создаем поток ввода
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream); //чтение строки
                BufferedReader reader = new BufferedReader(inputStreamReader); //читаем сразу строками
                StringBuilder builder = new StringBuilder(); //сюда сохраняем полученную строку
                String line = reader.readLine();  //читаем данные
                while (line != null){
                    builder.append(line);
                    line = reader.readLine();

                }
                result = new JSONObject(builder.toString());

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null){ //проверка на соединение
                    connection.disconnect(); //закрытие соединения
                }
            }


            return result;
        }

    }
}
