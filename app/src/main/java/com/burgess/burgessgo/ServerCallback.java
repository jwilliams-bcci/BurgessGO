package com.burgess.burgessgo;

public interface ServerCallback {
    void onSuccess(String message);
    void onFailure(String message);
}
