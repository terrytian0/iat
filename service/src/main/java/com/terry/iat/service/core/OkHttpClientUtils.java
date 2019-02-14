package com.terry.iat.service.core;

import com.terry.iat.service.common.exception.HttpLoggingInterceptor;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OkHttpClientUtils {
    private static Map<String, OkHttpClient> okHttpClientMap = new ConcurrentHashMap<>();
    private static Integer LOCK = 0;

    public static OkHttpClient instance(String host) {

        OkHttpClient okHttpClient = okHttpClientMap.get(host);
        synchronized (LOCK) {
            if (okHttpClient == null) {
                okHttpClient = new OkHttpClient()
                        .newBuilder()
                        .addInterceptor(new HttpLoggingInterceptor())
                        .cookieJar(new CookieJar() {
                            private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

                            @Override
                            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                                cookieStore.put(url.host(), cookies);
                            }

                            @Override
                            public List<Cookie> loadForRequest(HttpUrl url) {
                                List<Cookie> cookies = cookieStore.get(url.host());
                                return cookies != null ? cookies : new ArrayList<Cookie>();
                            }
                        })
                        .build();
                okHttpClientMap.put(host, okHttpClient);
            }
            return okHttpClient;
        }
    }


}
