package com.terry.iat.service.common.exception;

import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.internal.http.HttpHeaders;
import okio.Buffer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpLoggingInterceptor implements Interceptor {
    private static final Charset UTF8 = Charset.forName("UTF-8");

    private volatile Level printLevel = Level.NONE;

    public enum Level {
        NONE,       //不打印log
        BASIC,      //只打印 请求首行 和 响应首行
        HEADERS,    //打印请求和响应的所有 Header
        BODY        //所有数据全部打印
    }

    public void setPrintLevel(Level level) {
        if (level == null) throw new NullPointerException("level == null. Use Level.NONE instead.");
        printLevel = level;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (printLevel == Level.NONE) {
            return chain.proceed(request);
        }

        //请求日志拦截
        logForRequest(request, chain.connection());

        //执行请求，计算请求时间
        long startNs = System.nanoTime();
        Response response;
        try {
            response = chain.proceed(request);
        } catch (Exception e) {
            log.info("<-- HTTP FAILED: " + e);
            throw e;
        }
        long tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs);

        //响应日志拦截
        return logForResponse(response, tookMs);
    }

    private void logForRequest(Request request, Connection connection) throws IOException {
        boolean logBody = (printLevel == Level.BODY);
        boolean logHeaders = (printLevel == Level.BODY || printLevel == Level.HEADERS);
        RequestBody requestBody = request.body();
        boolean hasRequestBody = requestBody != null;
        Protocol protocol = connection != null ? connection.protocol() : Protocol.HTTP_1_1;

        try {
            String requestStartMessage = "--> " + request.method() + ' ' + request.url() + ' ' + protocol;
            log.info(requestStartMessage);

            if (logHeaders) {
                if (hasRequestBody) {
                    // Request body headers are only present when installed as a network interceptor. Force
                    // them to be included (when available) so there values are known.
                    if (requestBody.contentType() != null) {
                        log.info("\tContent-Type: " + requestBody.contentType());
                    }
                    if (requestBody.contentLength() != -1) {
                        log.info("\tContent-Length: " + requestBody.contentLength());
                    }
                }
                Headers headers = request.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    String name = headers.name(i);
                    // Skip headers from the request body as they are explicitly logged above.
                    if (!"Content-Type".equalsIgnoreCase(name) && !"Content-Length".equalsIgnoreCase(name)) {
                        log.info("\t" + name + ": " + headers.value(i));
                    }
                }

                log.info(" ");
                if (logBody && hasRequestBody) {
                    if (isPlaintext(requestBody.contentType())) {
                        bodyToString(request);
                    } else {
                        log.info("\tbody: maybe [binary body], omitted!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("--> END " + request.method());
        }
    }

    private Response logForResponse(Response response, long tookMs) {
        Response.Builder builder = response.newBuilder();
        Response clone = builder.build();
        ResponseBody responseBody = clone.body();
        boolean logBody = (printLevel == Level.BODY);
        boolean logHeaders = (printLevel == Level.BODY || printLevel == Level.HEADERS);

        try {
            log.info("<-- " + clone.code() + ' ' + clone.message() + ' ' + clone.request().url() + " (" + tookMs + "ms）");
            if (logHeaders) {
                Headers headers = clone.headers();
                for (int i = 0, count = headers.size(); i < count; i++) {
                    log.info("\t" + headers.name(i) + ": " + headers.value(i));
                }
                log.info(" ");
                if (logBody && HttpHeaders.hasBody(clone)) {
                    if (responseBody == null) return response;

                    if (isPlaintext(responseBody.contentType())) {
                        byte[] bytes = toByteArray(responseBody.byteStream());
                        MediaType contentType = responseBody.contentType();
                        String body = new String(bytes, getCharset(contentType));
                        log.info("\tbody:" + body);
                        responseBody = ResponseBody.create(responseBody.contentType(), bytes);
                        return response.newBuilder().body(responseBody).build();
                    } else {
                        log.info("\tbody: maybe [binary body], omitted!");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            log.info("<-- END HTTP");
        }
        return response;
    }

    private static Charset getCharset(MediaType contentType) {
        Charset charset = contentType != null ? contentType.charset(UTF8) : UTF8;
        if (charset == null) charset = UTF8;
        return charset;
    }

    /**
     * Returns true if the body in question probably contains human readable text. Uses a small sample
     * of code points to detect unicode control characters commonly used in binary file signatures.
     */
    private static boolean isPlaintext(MediaType mediaType) {
        if (mediaType == null) return false;
        if (mediaType.type() != null && mediaType.type().equals("text")) {
            return true;
        }
        String subtype = mediaType.subtype();
        if (subtype != null) {
            subtype = subtype.toLowerCase();
            if (subtype.contains("x-www-form-urlencoded") || subtype.contains("json") || subtype.contains("xml") || subtype.contains("html")) //
                return true;
        }
        return false;
    }

    private void bodyToString(Request request) {
        try {
            Request copy = request.newBuilder().build();
            RequestBody body = copy.body();
            if (body == null) return;
            Buffer buffer = new Buffer();
            body.writeTo(buffer);
            Charset charset = getCharset(body.contentType());
            log.info("\tbody:" + buffer.readString(charset));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] toByteArray(Object input) {
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(input);
            oos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
