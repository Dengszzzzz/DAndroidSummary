package com.sz.dzh.dandroidsummary.model.specialFunc.download;


import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author
 * @date 2018/8/3
 * @description
 */
public interface DownloadApiService {

    /**
     * @Streaming 注解是为了避免将整个文件读进内存，这是在下载大文件时需要注意的地方。
     * 在请求头添加Range就可以实现服务器文件的下载内容范围了。
     * */
    @Streaming
    @GET
    //range下载参数，传下载区间使用
    //url 下载链接
    Observable<ResponseBody> executeDownload(@Header("Range") String range, @Url() String url);


}
