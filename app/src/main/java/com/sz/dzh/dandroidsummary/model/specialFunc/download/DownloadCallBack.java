package com.sz.dzh.dandroidsummary.model.specialFunc.download;

public interface DownloadCallBack {

    void onProgress(int progress);

    void onCompleted();

    void onError(String msg);

}
