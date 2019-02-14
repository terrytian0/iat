package com.terry.iat.swagger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.terry.iat.service.vo.*;
import io.swagger.models.*;
import io.swagger.models.parameters.BodyParameter;
import io.swagger.models.parameters.Parameter;
import io.swagger.models.parameters.QueryParameter;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.properties.RefProperty;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.*;
import org.springframework.context.ApplicationContext;
import springfox.documentation.service.Documentation;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**  
  * @author terry
  * @Description Swagger工具类
  * @Date 2019/1/22 11:23
  * @Version 1.0        
  **/

@Slf4j
public class SwaggerUtils {

    private static OkHttpClient client;
    private static Boolean isRunner = false;

    /**  
      * @author terry
      * @Description 
      * @Date 2019/1/22 11:22
      * @Param [url, uniqueKey, context]
      * @return void
      **/
    public static void postSwagger(String url, String uniqueKey, ApplicationContext context) {
        synchronized (isRunner) {
            if (isRunner == false) {
                isRunner = true;
            } else {
                return;
            }
        }
        new Thread(() -> {
            log.info("开始同步《" + uniqueKey + "》服务接口到" + url);
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            DocumentationCache documentationCache = context.getBean(DocumentationCache.class);
            ServiceModelToSwagger2Mapper mapper = context.getBean(ServiceModelToSwagger2Mapper.class);
            Map<String, Documentation> documentationMap = documentationCache.all();
            List<ApiVO> pushApis = new ArrayList<>();
            for (String group : documentationMap.keySet()) {
                Swagger swagger = mapper.mapDocumentation(documentationMap.get(group));
                Map<String, Object> ds = new HashMap<>();
                ds = getDefinitions(swagger.getDefinitions(), ds);
                Map<String, Path> pathMap = swagger.getPaths();
                for (String key : pathMap.keySet()) {
                    List<ApiVO> apis = getApis(pathMap.get(key), ds);
                    for (ApiVO api : apis) {
                        StringBuilder p = new StringBuilder();
                        if (swagger.getBasePath().length() > 1) {
                            p.append(swagger.getBasePath());
                        }
                        p.append(key);
                        api.setPath(p.toString());
                        api.setUniqueKey(uniqueKey);
                        pushApis.add(api);
                    }
                }
            }
            postApiServer(url, pushApis);
        }).start();

    }
    /**
      * @Description TODO
      * @author          
      * @Date 2019/1/22 11:25
      * @Version 1.0        
      **/
    private static List<ApiVO> getApis(Path path, Map<String, Object> definitions) {
        List<ApiVO> apis = new ArrayList<>();
        if (path.getGet() != null) {
            ApiVO api = getApi(path.getGet(), definitions);
            api.setMethod("get");
            apis.add(api);
        }
        if (path.getGet() != null) {
            ApiVO api = getApi(path.getGet(), definitions);
            api.setMethod("get");
            apis.add(api);
        }
        if (path.getPost() != null) {
            ApiVO api = getApi(path.getPost(), definitions);
            api.setMethod("post");
            apis.add(api);
        }
        if (path.getPut() != null) {
            ApiVO api = getApi(path.getPut(), definitions);
            api.setMethod("put");
            apis.add(api);
        }
        if (path.getDelete() != null) {
            ApiVO api = getApi(path.getDelete(), definitions);
            api.setMethod("delete");
            apis.add(api);
        }
        if (path.getHead() != null) {
            ApiVO api = getApi(path.getHead(), definitions);
            api.setMethod("head");
            apis.add(api);
        }
        if (path.getPatch() != null) {
            ApiVO api = getApi(path.getPatch(), definitions);
            api.setMethod("patch");
            apis.add(api);
        }
        return apis;
    }

    private static ApiVO getApi(Operation operation, Map<String, Object> definitions) {
        ApiVO api = new ApiVO();
        api.setDescription(operation.getDescription());
        api.setName(operation.getSummary());
        if (operation.getConsumes() != null && !operation.getConsumes().isEmpty()) {
            api.setContentType(getContentType(operation.getConsumes()));
        }
        if (operation.getParameters() != null && !operation.getParameters().isEmpty()) {
            List<FormDataVO> formData = getFormDatas(operation.getParameters());
            if (formData != null && !formData.isEmpty()) {
                api.setFormDatas(formData);
            }
            if (api.getContentType() != null) {
                List<HeaderVO> headers = new ArrayList<>();
                HeaderVO header = new HeaderVO();
                header.setName("Content-Type");
                header.setDefaultValue(api.getContentType());
                header.setType("string");
                headers.add(header);
                api.setHeaders(headers);
            }
            BodyVO body = getBody(operation.getParameters(), definitions);
            if (body != null) {
                api.setBody(body);
            }
        }
        if (operation.getResponses() != null && !operation.getResponses().isEmpty()) {
            List<ResultCodeVO> resultCodes = new ArrayList<>();
            operation.getResponses().forEach((k, v) -> {
                ResultCodeVO resultCode = new ResultCodeVO();
                resultCode.setCode(k);
                Model model = v.getResponseSchema();
                if (model == null) {
                    resultCode.setDescription(v.getDescription());
                } else if (model instanceof ArrayModel) {
                    List<Object> objects = new ArrayList<>();
                    Property p = ((ArrayModel) model).getItems();
                    if (p instanceof RefProperty) {
                        String key = ((RefProperty) p).getSimpleRef();
                        objects.add(definitions.get(key));
                    } else {
                        objects.add(p.getType());
                    }
                    resultCode.setDescription(JSON.toJSONString(objects));
                } else if (model instanceof RefModel) {
                    String key = ((RefModel) model).getSimpleRef();
                    resultCode.setDescription(JSON.toJSONString(definitions.get(key)));
                } else if (model instanceof Proxy) {
                    String key = model.getReference();
                    key = key.substring(key.lastIndexOf("/") + 1, key.length());
                    resultCode.setDescription(JSON.toJSONString(definitions.get(key)));
                }
                resultCodes.add(resultCode);
            });
            api.setResultCodes(resultCodes);
        }
        return api;
    }

    private static void postApiServer(String url, List<ApiVO> apis) {
        log.info("***********************************************************");
        log.info(JSONArray.toJSONString(apis));
        RequestBody body =
                (RequestBody)
                        RequestBody.create(MediaType.get("application/json"), JSONArray.toJSONString(apis));
        Request request = new Request.Builder().url(url).post(body).build();
        OkHttpClient client = getOKHttpClient();
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                log.error("post swagger error! httpcode=" + response.code());
                log.error("body=" + response.body().string());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static OkHttpClient getOKHttpClient() {
        if (client == null) {
            client = new OkHttpClient().newBuilder().build();
        }
        return client;
    }

    private static String getContentType(List<String> consumers) {
        StringBuilder stringBuilder = new StringBuilder();
        if (consumers.size() > 0) {
            consumers.forEach(
                    c -> {
                        stringBuilder.append(c + ",");
                    });
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        }
        return "";
    }

    private static List<FormDataVO> getFormDatas(List<Parameter> parameters) {
        List<FormDataVO> formDatas = new ArrayList<>();
        parameters.forEach(
                parameter -> {
                    if (parameter instanceof QueryParameter) {
                        QueryParameter queryParameter = (QueryParameter) parameter;
                        FormDataVO formData = new FormDataVO();
                        formData.setType(queryParameter.getType());
                        formData.setName(queryParameter.getName());
                        formData.setRequired(queryParameter.getRequired());
                        formDatas.add(formData);
                    }
                });
        return formDatas;
    }

    private static BodyVO getBody(List<Parameter> parameters, Map<String, Object> definitions) {
        BodyVO body = new BodyVO();
        parameters.forEach(
                parameter -> {
                    if (parameter instanceof BodyParameter) {
                        Model m = ((BodyParameter) parameter).getSchema();
                        if (m instanceof ArrayModel) {
                            List<Object> objects = new ArrayList<>();
                            Property p = ((ArrayModel) m).getItems();
                            if (p instanceof RefProperty) {
                                String key = ((RefProperty) p).getSimpleRef();
                                objects.add(definitions.get(key));
                            } else {
                                objects.add(p.getType());
                            }
                            body.setContent(JSON.toJSONString(objects));
                        } else if (m instanceof RefModel) {
                            String key = ((RefModel) m).getSimpleRef();
                            body.setContent(JSON.toJSONString(definitions.get(key)));
                        }
                    }
                });
        body.setType("string");
        if (body.getContent() != null) {
            return body;
        } else {
            return null;
        }
    }

    private static Map<String, Object> getDefinitions(
            Map<String, Model> treeMap, Map<String, Object> definitions) {
        Map<String, Object> objectMap = new HashMap<>();
        for (String key : treeMap.keySet()) {
            objectMap.put(key, getDefinitionsByKey(key, treeMap, definitions));
        }
        return objectMap;
    }

    private static Object getDefinitionsByPath(
            String path, Map<String, Model> treeMap, Map<String, Object> definitions) {
        String key = path.substring(path.lastIndexOf("/") + 1, path.length());
        if (definitions.containsKey(key)) {
            return definitions.get(key);
        } else {
            return getDefinitionsByKey(key, treeMap, definitions);
        }
    }

    private static Map<String, Object> getDefinitionsByKey(
            String key, Map<String, Model> treeMap, Map<String, Object> definitions) {
        Map<String, Object> objectMap = new HashMap<>();
        Model model = treeMap.get(key);
        Map<String, Property> propertyMap = model.getProperties();
        if (propertyMap == null) {
            objectMap.put("string", "string");
            return objectMap;
        }
        propertyMap.forEach(
                (k, v) -> {
                    if (v instanceof RefProperty) {
                        if (definitions.containsKey(k)) {
                            objectMap.put(k, definitions.get(k));
                        } else {
                            objectMap.put(
                                    k, getDefinitionsByPath(((RefProperty) v).get$ref(), treeMap, definitions));
                        }
                    } else if (v instanceof ArrayProperty) {
                        List<Object> list = new ArrayList<>();
                        Property property = ((ArrayProperty) v).getItems();
                        if (property instanceof RefProperty) {
                            list.add(
                                    getDefinitionsByPath(((RefProperty) property).get$ref(), treeMap, definitions));
                        } else {
                            list.add(property.getType());
                        }
                        objectMap.put(k, list);
                    } else {
                        objectMap.put(k, v.getType());
                    }
                });
        return objectMap;
    }
//
//    public static Map<String, Object> getDefinitions(JSONObject definitions) {
//        Map<String, Object> map = new HashMap<>();
//        for (String key : definitions.keySet()) {
//            getDefinition(map, definitions, key);
//        }
//        return map;
//    }
//
//    public static Map<String, Object> getDefinition(Map<String, Object> collect, JSONObject definitions, String key) {
//        JSONObject definition = definitions.getJSONObject(key);
//        Map<String, Object> map = new HashMap<>();
//        String type = definition.getString("type");
//        String dname = definition.getString("name");
//        switch (type) {
//            case "object":
//                JSONObject additionalProperties = definition.getJSONObject("additionalProperties");
//                if (additionalProperties != null) {
//                    String apType = additionalProperties.getString("type");
////                    Map<String, Object> apMap = new HashMap<>();
//                    map.put("additionalProperties", apType);
//                    collect.put(dname, map);
//                } else {
//                    JSONObject properties = definition.getJSONObject("properties");
//                    for (String pkey : properties.keySet()) {
//                        JSONObject pvalue = properties.getJSONObject(pkey);
//                        String ptype = pvalue.getString("type");
//                        String pname = pvalue.getString("name");
//                        if (ptype.equals("array")) {
//                            JSONObject items = pvalue.getJSONObject("items");
//                            List<Object> mapList = getArrayProperties(collect, definitions, items);
//                            map.put(pname, mapList);
//                        } else if (ptype.equals("ref")) {
//                            String refKey = pvalue.getString("simpleRef");//取出引用名字
//                            if (collect.get(refKey) == null) {
//                                Map<String, Object> ref = getDefinition(collect, definitions, refKey);
//                                map.put(pname, ref);
//                            } else {
//                                map.put(pname, collect.get(refKey));
//                            }
//                        } else if (ptype.equals("object")) {
//                            JSONObject ap = pvalue.getJSONObject("additionalProperties");
//                            if (ap != null) {
//                                String atype = ap.getString("type");
//                                Map<String, String> a = new HashMap<>();
//                                a.put("additionalProperties", atype);
//                                map.put(pname, a);
//                            } else {
//                                map.put(pname, ptype);
//                            }
//                        } else {
//                            map.put(pname, ptype);
//                        }
//                    }
//                    collect.put(dname, map);
//                }
//                break;
//            default:
//                //TODO 带扩展
//        }
//        return map;
//    }
//
//    private static List<Object> getArrayProperties(Map<String, Object> collect, JSONObject definitions, JSONObject items) {
//        List<Object> mapList = new ArrayList<>();
//        String itype = items.getString("type");
//        String iname = items.getString("name");
//        Map<String, Object> map = new HashMap<>();
//        if (itype.equals("array")) {
//            System.out.println("peoperties中存在数组！");
//        } else if (itype.equals("ref")) {
//            String refKey = items.getString("simpleRef");
//            if (collect.get(refKey) == null) {
//                Map<String, Object> ref = getDefinition(collect, definitions, refKey);
//                mapList.add(ref);
//            } else {
//                mapList.add(collect.get(refKey));
//            }
//        } else {
//            map.put(iname, itype);
//        }
//        mapList.add(map);
//        return mapList;
//    }
}