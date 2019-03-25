package ru.maxmorev.cloud.sap.response;

public class ErrorInfo {

    public final Integer code = 401;
    public final String url;
    public final String ex;

    public ErrorInfo(String url, Exception ex) {
        this.url = url;
        this.ex = ex.getLocalizedMessage();
    }

}
