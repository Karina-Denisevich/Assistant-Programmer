package com.github.karina_denisevich.app.git.model;


import java.io.Serializable;

public class GitRepo implements Serializable {

    private static final long serialVersionUID = -886981638927817712L;

    private String id;
    private String name;
    private String owner;
    private String url;
    private String language;

    public GitRepo() {

    }

    public GitRepo(String id, String name, String owner, String url, String language) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.url = url;
        this.language = language;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static class Builder {
        private String id;
        private String name;
        private String owner;
        private String url;
        private String language;

        public GitRepo build() {
            return new GitRepo(id, name, owner, url, language);
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withOwner(String owner) {
            this.owner = owner;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder withLanguage(String language) {
            this.language = language;
            return this;
        }
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
