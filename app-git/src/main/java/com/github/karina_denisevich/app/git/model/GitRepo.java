package com.github.karina_denisevich.app.git.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Karyna_Dzenisevich on 04-May-17.
 */
public class GitRepo {

    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("owner")
    private String  owner;
    @JsonProperty("url")
    private String url;
    @JsonProperty("language")
    private String language;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
