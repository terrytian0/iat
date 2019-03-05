package com.terry.iat.service.core;

import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;
import com.terry.iat.dao.entity.*;
import com.terry.iat.service.common.enums.AssertLocale;
import com.terry.iat.service.common.enums.AssertMethod;
import com.terry.iat.service.common.enums.ExtractorType;
import com.terry.iat.service.common.enums.HttpMethod;
import com.terry.iat.service.common.utils.StringUtils;
import lombok.Builder;
import lombok.Data;
import okhttp3.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class HttpDriver {

//    private static Map<String, String> globalParams = new ConcurrentHashMap<>();

    public static void main(String[] args) throws IOException, InterruptedException {
//        HttpUrl url = new HttpUrl.Builder().host("127.0.0.1").port(8080).addEncodedQueryParameter("a","b").addEncodedPathSegments("abc").scheme("http").build();
//        System.out.println(url.toString());
//        OkHttpClient httpClient = OkHttpClientUtils.instance("127.0.0.1");
//        String apiStr = "{\"id\":80,\"name\":\"获取api详情\",\"path\":\"/api/info\",\"method\":\"GET\",\"createUser\":\"admin\",\"createTime\":1543742781000,\"header\":[{\"id\":22,\"apiId\":80,\"name\":\"Content-Type\",\"defaultValue\":\"application/x-www-form-urlencoded\",\"type\":\"String\"}],\"formData\":[{\"id\":25,\"apiId\":80,\"name\":\"id\",\"defaultValue\":\"79\",\"type\":\"String\"}],\"body\":{}}";
//        ApiEntity api = JSON.parseObject(apiStr, new TypeReference<ApiEntity>() {
//        });
//        Request.Builder build = new Request.Builder().url("http://127.0.0.1" + api.getPath());
//        HttpMethod method = HttpMethod.getHttpMethod(api.getMethod());
//        RequestBody requestBody = getRequestBody(api, globalParams);
//        if (method == HttpMethod.POST) {
//            build.post(requestBody);
//        } else if (method == HttpMethod.GET) {
//            build.get();
//            build.url("http://127.0.0.1" + api.getPath() + getQueryString(api.getFormData(), globalParams));
//        } else if (method == HttpMethod.PUT) {
//            build.put(requestBody);
//        } else if (method == HttpMethod.DELETE) {
//            if (requestBody == null) {
//                build.delete();
//            } else {
//                build.delete(requestBody);
//            }
//        }
//        build.headers(getHeaders(api.getHeader(), globalParams));
//
//        Response response = httpClient.newCall(build.build()).execute();
//        System.out.println(response.body().string());
    }

    public static HttpResult execute(String scheme, String host, Integer port, ApiEntity api, Map<String, String> parameters, List<ExtractorEntity> extractors, List<AssertEntity> asserts) {
        HttpResult result = new HttpResult();
        Map<String, String> general = new HashMap<>();
        Map<String, String> requestHeader = replaceParameters(api.getHeader(), parameters);
        Map<String, String> rfd = replaceParameters(api.getFormData(), parameters);
        String rb = replaceParameters(api.getBody(), parameters);
        String rbt = getBodyType(api.getBody());
        MediaType mediaType = getMediaType(api.getHeader(), parameters);
        OkHttpClient httpClient = OkHttpClientUtils.instance(host);
        HttpUrl url = getHttpUrl(scheme, host, port, api.getPath(), api.getFormData(), parameters);
        general.put("url", url.toString());
        Request.Builder build = new Request.Builder().url(url);
        RequestBody requestBody = HttpDriver.getRequestBody(mediaType, rfd, rb, rbt);
        HttpMethod method = HttpMethod.getHttpMethod(api.getMethod());
        general.put("method", method.name());
        if (method == HttpMethod.POST) {
            build.post(requestBody);
        } else if (method == HttpMethod.GET) {
            build.get();
        } else if (method == HttpMethod.PUT) {
            build.put(requestBody);
        } else if (method == HttpMethod.DELETE) {
            if (requestBody == null) {
                build.delete();
            } else {
                build.delete(requestBody);
            }
        }
        build.headers(getHeaders(requestHeader));
        result.setGeneral(general);
        result.setRequestBody(rb);
        result.setRequestHeader(requestHeader);
        result.setRequestFormData(rfd);
        try {
            Response response = httpClient.newCall(build.build()).execute();
            Integer httpcode = response.code();
            result.setStatusCode(httpcode);
            Map<String, String> responseHeader = new HashMap<>();
            Headers headers = response.headers();
            for (String name : headers.names()) {
                responseHeader.put(name, headers.get(name));
            }
            result.setResponseHeader(responseHeader);
            String body = response.body().string();
            result.setResponseBody(body);
            AssertResult assertResult = asserts(httpcode,requestHeader,body,asserts,parameters);
            if(assertResult.status==false){
                result.setSuccessful(false);
                result.setError(assertResult.message);
            }
            if (response.isSuccessful()) {
                try {
                    extractors = extract(extractors, body);
                    parameters.putAll(extractorConvertParameters(extractors));
                    result.setParameters(parameters);
                } catch (PathNotFoundException pnfe) {
                    result.setError(pnfe.getMessage());
                    result.setSuccessful(false);
                }
            }
            result.setExtractors(extractors);
            result.setAsserts(asserts);
        } catch (Exception e) {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            e.printStackTrace(new PrintWriter(buf, true));
            result.setResponseBody(buf.toString());
            result.setSuccessful(false);
            try {
                buf.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return result;
    }

    private static Map<String, String> extractorConvertParameters( List<ExtractorEntity> extractors){
        if(extractors==null){
            return Collections.EMPTY_MAP;
        }
        Map<String, String> map = new HashMap<>();
        extractors.forEach(e->{
            if(e.getValue()!=null){
                map.put(e.getName(),e.getValue());
            }
        });
        return map;
    }

    private static AssertResult asserts(Integer httpCode, Map<String, String> header, String body, List<AssertEntity> asserts,Map<String,String> parameters) {
        for (AssertEntity anAssert : asserts) {
            String value = "";
            if (anAssert.getLocale().equals(AssertLocale.HTTPCODE.name())) {
                value = String.valueOf(httpCode);
            } else if (anAssert.getLocale().equals(AssertLocale.HEADER.name())) {
                value = header.get(anAssert.getRule());
            } else if (anAssert.getLocale().equals(AssertLocale.BODY.name())) {
                value = extract(body, anAssert.getType(), anAssert.getRule());
            }else{
                anAssert.setStatus(false);
                return AssertResult.builder().status(false).message("比较区域不存在").build();
            }
            anAssert.setActual(value);
            String expect = anAssert.getValue();
            if(isParameter(expect)){
                String pName = getParameterName(expect);
                expect = parameters.get(pName);
            }
            AssertResult assertResult = compare(anAssert.getMethod(), expect, value);
            if(assertResult.status==false){
                anAssert.setStatus(false);
                return assertResult;
            }
            anAssert.setStatus(true);
        }
        return AssertResult.builder().status(true).build();
    }


    private static AssertResult compare(String method, String expect, String actual) {
        if (AssertMethod.CONTAINS.name().equals(method)) {
            return contains(expect, actual);
        } else if (AssertMethod.EQUALS.name().equals(method)) {
            return equals(expect, actual);
        } else if (AssertMethod.GREATER.name().equals(method)) {
            return greter(expect, actual);
        } else if (AssertMethod.LESS.name().equals(method)) {
            return less(expect, actual);
        }
        return AssertResult.builder().status(false).message("断言错误："+"比较方法不存在").build();
    }

    private static AssertResult contains(String expect, String actual) {
        if (actual.contains(expect)) {
            return AssertResult.builder().status(true).build();
        }
        return AssertResult.builder().status(false).message("断言错误："+actual + "不包含" + expect).build();

    }

    private static AssertResult equals(String expect, String actual) {
        if (expect.equals(actual)) {
            return AssertResult.builder().status(true).build();
        }
        return AssertResult.builder().status(false).message("断言错误："+actual + "不等于" + expect).build();

    }

    private static AssertResult greter(String expect, String actual) {
        Double ex = 0D;
        Double ac = 0D;
        try {
            ex = Double.parseDouble(expect);
            ac = Double.parseDouble(actual);
        } catch (NumberFormatException nfe) {
            return AssertResult.builder().status(false).message("断言错误："+nfe.getMessage()).build();
        }
        if (ac > ex) {
            return AssertResult.builder().status(true).build();
        } else {
            return AssertResult.builder().status(false).message("断言错误："+actual + "不大于" + expect).build();
        }
    }

    private static AssertResult less(String expect, String actual) {
        Double ex = 0D;
        Double ac = 0D;
        try {
            ex = Double.parseDouble(expect);
            ac = Double.parseDouble(actual);
        } catch (NumberFormatException nfe) {
            return AssertResult.builder().status(false).message("断言错误："+nfe.getMessage()).build();
        }
        if (ac < ex) {
            return AssertResult.builder().status(true).build();
        } else {
            return AssertResult.builder().status(false).message("断言错误："+actual + "不小于" + expect).build();
        }
    }

    public static Headers getHeaders(Map<String, String> headers) {
        Headers.Builder builder = new Headers.Builder();
        if (headers == null) {
            return builder.build();
        }
        headers.forEach((k, v) -> {
            builder.add(k, v);
        });
        return builder.build();
    }

    public static HttpUrl getHttpUrl(String scheme, String host, Integer port, String path, List<FormDataEntity> formDatas, Map<String, String> parameters) {
        HttpUrl.Builder builder = new HttpUrl.Builder();
        builder.scheme(scheme);
        builder.host(host);
        builder.port(port);
        builder.encodedPath(path);
        if (formDatas != null && !formDatas.isEmpty()) {
            for (FormDataEntity formData : formDatas) {
                if (isParameter(formData.getDefaultValue())) {
                    String key = getParameterName(formData.getDefaultValue());
                    String value = parameters.get(key);
                    builder.addEncodedQueryParameter(formData.getName(), value);
                } else {
                    builder.addEncodedQueryParameter(formData.getName(), formData.getDefaultValue());
                }
            }
        }
        return builder.build();
    }

    public static RequestBody getRequestBody(MediaType mediaType, Map<String, String> formDatas, String body, String bodyType) {
        if (!formDatas.isEmpty()) {
            return getFormBody(formDatas);
        } else if (body != null) {
            return getRequestBody(mediaType, body, bodyType);
        }
        return RequestBody.create(mediaType, "");
    }

    private static Map<String, String> replaceParameters(List items, Map<String, String> parameters) {
        Map<String, String> requestFormdata = new HashMap<>();
        if(items==null){
            return requestFormdata;
        }
        for (Object item : items) {
            String dvalue = "";
            String name = "";
            if (item instanceof FormDataEntity) {
                FormDataEntity fde = (FormDataEntity) item;
                name = fde.getName();
                dvalue = fde.getDefaultValue();
            } else if (item instanceof HeaderEntity) {
                HeaderEntity he = (HeaderEntity) item;
                name = he.getName();
                dvalue = he.getDefaultValue();
            }
            if (isParameter(dvalue)) {
                String key = getParameterName(dvalue);
                String value = parameters.get(key);
                if (value != null) {
                    requestFormdata.put(name, value);
                }
            } else {
                requestFormdata.put(name, dvalue);
            }
        }
        return requestFormdata;
    }

    private static RequestBody getFormBody(Map<String, String> formDatas) {
        FormBody.Builder builder = new FormBody.Builder();
        formDatas.forEach((k, v) -> {
            builder.addEncoded(k, v);
        });
        return builder.build();
    }

    private static String getBodyType(BodyEntity bodyEntity) {
        if (bodyEntity == null || bodyEntity.getDefaultValue() == null) {
            return null;
        }
        return bodyEntity.getType();
    }

    private static String replaceParameters(BodyEntity bodyEntity, Map<String, String> parameters) {
        if (bodyEntity == null || bodyEntity.getDefaultValue() == null) {
            return null;
        }
        String body = bodyEntity.getDefaultValue();
        Set<String> ps = StringUtils.getParameters(body);
        if (!ps.isEmpty()) {
            for (String p : ps) {
                if (parameters.get(p) != null) {
                    body = body.replace("#{" + p + "}", parameters.get(p));
                }
            }
        }
        return body;
    }

    private static RequestBody getRequestBody(MediaType mediaType, String body, String bodyType) {
        if ("string".equals(bodyType)) {
            return RequestBody.create(mediaType, body);
        } else {
            return RequestBody.create(mediaType, body.getBytes());
        }
    }

    public static MediaType getMediaType(List<HeaderEntity> heades, Map<String, String> parameters) {
        if (heades == null) {
            return MediaType.parse("");
        }
        for (HeaderEntity heade : heades) {
            if (heade.getName().toLowerCase().equals("content-type")) {
                if (isParameter(heade.getDefaultValue())) {
                    String key = getParameterName(heade.getDefaultValue());
                    String value = parameters.get(key);
                    return MediaType.parse(value);
                } else {
                    return MediaType.parse(heade.getDefaultValue());
                }
            }
        }
        return MediaType.parse("");
    }

    private static boolean isParameter(String str) {
        if (str.startsWith("#{") && str.endsWith("}")) {
            return true;
        }
        return false;
    }

    private static String getParameterName(String str) {
        return str.substring(2, str.length() - 1);
    }

    private static List<ExtractorEntity> extract(List<ExtractorEntity> extractors, String body) throws PathNotFoundException {
        if (extractors.isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        for (ExtractorEntity extractorEntity : extractors) {
            String value = extract(body, extractorEntity.getType(), extractorEntity.getRule());
            extractorEntity.setValue(value);
        }
        return extractors;
    }

    private static String extract(String content, String extractType, String rule) {
        try {
            if (ExtractorType.JSON.name().equals(extractType)) {
                if (content.startsWith("\"") && content.endsWith("\"")) {
                    content = content.substring(1, content.length() - 1);
                }
                Object value = JsonPath.read(content, rule);
                return String.valueOf(value);
            } else if (ExtractorType.REGEXP.name().equals(extractType)) {

            }
            return "";
        }catch (Exception e){
            return "";
        }
    }
}
