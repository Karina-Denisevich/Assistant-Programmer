package com.github.karina_denisevich.app.datamodel;

import org.apache.commons.lang3.builder.EqualsBuilder;

import java.io.Serializable;

public class LinesInfo extends DbModel implements Serializable {

    private static final long serialVersionUID = -556619357448033790L;

    private String text;
    private String firstLine;
    private String lastLine;
    private String filePath;

    public LinesInfo() {

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

    @Override
    public boolean equals(Object o) {
        if (o == this) return true;
        if (!(o instanceof LinesInfo)) {
            return false;
        }

        LinesInfo linesInfo = (LinesInfo) o;

        return new EqualsBuilder()
                .append(text, linesInfo.text)
                .append(filePath, linesInfo.filePath)
                .append(firstLine, linesInfo.firstLine)
                .append(lastLine, linesInfo.lastLine)
                .isEquals();
    }
}