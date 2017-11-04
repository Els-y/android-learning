package com.example.servicebestpractice;

/**
 * Created by els on 17-3-10.
 */

public interface DownloadListener {
    void onProgress(int progress);

    void onSuccess();

    void onFailed();

    void onPaused();

    void onCanceled();
}
