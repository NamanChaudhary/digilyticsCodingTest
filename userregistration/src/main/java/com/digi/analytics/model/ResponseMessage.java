package com.digi.analytics.model;

public class ResponseMessage {

    private int no_of_rows_parsed ;
    private int no_of_rows_failed ;
    private String error_file_url;

    public int getNo_of_rows_parsed() {
        return no_of_rows_parsed;
    }

    public void setNo_of_rows_parsed(int no_of_rows_parsed) {
        this.no_of_rows_parsed = no_of_rows_parsed;
    }

    public int getNo_of_rows_failed() {
        return no_of_rows_failed;
    }

    public void setNo_of_rows_failed(int no_of_rows_failed) {
        this.no_of_rows_failed = no_of_rows_failed;
    }

    public String getError_file_url() {
        return error_file_url;
    }

    public void setError_file_url(String error_file_url) {
        this.error_file_url = error_file_url;
    }

    @Override
    public String toString() {
        return "ResponseMessage{" +
                "no_of_rows_parsed=" + no_of_rows_parsed +
                ", no_of_rows_failed=" + no_of_rows_failed +
                ", error_file_url='" + error_file_url + '\'' +
                '}';
    }
}