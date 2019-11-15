package com.hfad.digitalnomads.dataBase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "notes")
public class Notes {
    @PrimaryKey
    private int ID;
    private String author; //Автор статьи
    private String title;  //Заголовок статьи
    private String description; // Краткое Описание статьи
    private String url; //ссылка на статью
    private String urlToImage; //ссылка на картинку статьи
    private String publishedAt; //дата создания статьи





    //Конструктор
    public Notes(int ID, String author, String title, String description, String  url, String urlToImage, String publishedAt) {
        this.ID = ID;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;

    }

    //геттеры и сеттеры для каждого поля, чтобы использовать в базе даных



    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
