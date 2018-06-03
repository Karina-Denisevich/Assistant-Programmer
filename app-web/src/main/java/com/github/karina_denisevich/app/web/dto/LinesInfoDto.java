package com.github.karina_denisevich.app.web.dto;

import java.io.Serializable;

public class LinesInfoDto extends AbstractDTO implements Serializable {

    private static final long serialVersionUID = -5569761957448033790L;

    private String text;
    private String firstLine;
    private String lastLine;
    private String filePath;

    public LinesInfoDto() {

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFirstLine() {
        return firstLine;
    }

    public void setFirstLine(String firstLine) {
        this.firstLine = firstLine;
    }

    public String getLastLine() {
        return lastLine;
    }

    public void setLastLine(String lastLine) {
        this.lastLine = lastLine;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
