package com.innolux.procotol;

import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.PostFormBuilder;
import com.zhy.http.okhttp.callback.StringCallback;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import okhttp3.Call;

/**
 * 类    名:  BaseProtocol
 * 创 建 者:  伍碧林
 * 创建时间:  2016/11/4 10:06
 * 描    述： ${TODO}
 */
public abstract class BaseProtocol<RESTYPE> {
    public Map<String, String> mParmasMap;

    /**
     * 加载数据
     *
     * @return
     */
    public void loadDataByGet(Callback callback, int reqType) {
        //在网络
        loadDataFromNetByGet(callback, reqType);
    }

    public void loadDataByPost(Callback callback, int reqType) {
        //在网络
        loadDataFromNetByPost(callback, reqType);
    }

    /**
     * 从网络获取数据
     *
     * @param callback
     * @param reqType
     * @throws IOException
     */
    private void loadDataFromNetByGet(final Callback callback, final int reqType) {
        //从网络加载数据
        String url = getUrl();
        //?pageIndex=0&catalog=1&pageSize=20
        OkHttpUtils
                .get()
                .url(url)
                .params(getParmasMap())
                .headers(getHeadersMap())
                .build()
                .execute(new StringCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 if (callback != null) {
                                     callback.onError(call, e, id, reqType);
                                 }
                             }

                             @Override
                             public void onResponse(String resXml, int id) {
                                 RESTYPE restype = parseXml(resXml);
                                 if (callback != null) {
                                     callback.onResponse(restype, id, reqType);
                                 }
                             }
                         }
                );
    }


    /**
     * 发起post请求
     *
     * @param callback
     */
    private void loadDataFromNetByPost(final Callback callback, final int reqType) {
        //从网络加载数据
        String url = getUrl();
        //?pageIndex=0&catalog=1&pageSize=20
        File file = null;

        Map<String, File> fileMap = getFileMap();

        PostFormBuilder postFormBuilder = OkHttpUtils
                .post()
                .url(url)
                .params(getParmasMap())
                .headers(getHeadersMap());


        //遍历集合fileMap,动态添加图片
        if (fileMap != null) {//需要上传图片
            for (Map.Entry<String, File> info : fileMap.entrySet()) {
                String key = info.getKey();
                File value = info.getValue();
                postFormBuilder.addFile(key, value.getName(), value);
            }
        }

        postFormBuilder.build()
                .execute(new StringCallback() {
                             @Override
                             public void onError(Call call, Exception e, int id) {
                                 if (callback != null) {
                                     callback.onError(call, e, id, reqType);
                                 }
                             }

                             @Override
                             public void onResponse(String response, int id) {
                                 RESTYPE restype = parseXml(response);
                                 if (callback != null) {
                                     callback.onResponse(restype, id, reqType);
                                 }
                             }
                         }
                );

    }

    public interface Callback<RESTYPE> {
        void onError(Call call, Exception e, int id, int reqType);

        void onResponse(RESTYPE restype, int id, int reqType);
    }

    /**
     * 基类完成统一的泛型解析
     *
     * @param response
     * @return
     */
    private RESTYPE parseXml(String response) {
        Type type = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        Gson gson = new Gson();
        return gson.fromJson(response, type);
    }


    /**
     * 决定url
     *
     * @return
     */
    @NonNull
    public abstract String getUrl();


    /**
     * 传递对应的参数信息
     */
    protected Map<String, String> getParmasMap() {
        return null;
    }

    /**
     * 添加请求头
     *
     * @return
     */
    protected Map<String, String> getHeadersMap() {
        return null;
    }

    /**
     * 添加需要上传的图片
     *
     * @return
     */
    protected Map<String, File> getFileMap() {
        return null;
    }

}
