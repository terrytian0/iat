package com.terry.iat.service.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.skyscreamer.jsonassert.*;

import java.util.*;

public class JsonCompare {

    public static boolean compare(String expect,String actual) {
      if(expect.equals(actual)){
        return true;
      }
      JSONCompareResult result = null;
      try {
        result = JSONCompare.compareJSON(expect,actual, JSONCompareMode.NON_EXTENSIBLE);
      } catch (org.json.JSONException e) {
        e.printStackTrace();
      }
      if(result==null){
        return false;
      }
      return result.passed();
    }

    public static boolean compareKey(String expectedStr,String actualStr){
      Object expected = JSON.parse(expectedStr);
      Object actual = JSON.parse(actualStr);
      if ((expected instanceof JSONObject) && (actual instanceof JSONObject)) {
        return compareKey((JSONObject)expected,(JSONObject)actual);
      }else if((expected instanceof JSONArray) && (actual instanceof JSONArray)){
        return compareKey((JSONArray)expected,(JSONArray)actual);
      }else{
        return false;
      }
    }

  public static boolean compareKey(JSONArray expected,JSONArray actual){
    if(expected.size()==0&&actual.size()==0){
      return true;
    }else if(expected.size()>0&&actual.size()>0){
      Object e = expected.get(0);
      Object a = actual.get(0);
      if ((e instanceof JSONObject) && (a instanceof JSONObject)) {
        return compareKey((JSONObject)e,(JSONObject)a);
      }else if((e instanceof JSONArray) && (a instanceof JSONArray)){
        return compareKey((JSONArray)e,(JSONArray)a);
      }else if((e instanceof JSONObject) && !(a instanceof JSONObject)){
        return false;
      }else if((e instanceof JSONArray) && !(a instanceof JSONArray)){
        return false;
      }else if((a instanceof JSONObject) && !(e instanceof JSONObject)){
        return false;
      }else if((a instanceof JSONArray) && !(e instanceof JSONArray)){
        return false;
      }else{
        return true;
      }
    }else{
      return false;
    }
  }

  public static boolean compareKey(JSONObject expected,JSONObject actual){
    Set<String> expectedKeys = expected.keySet();
    Set<String> actualKeys = actual.keySet();
    if(expectedKeys.size()!=actualKeys.size()){
      return false;
    }
    for(String key:expectedKeys){
      if(!actualKeys.contains(key)){
        return false;
      }else{
        Object e = expected.get(key);
        Object a= actual.get(key);
        if ((e instanceof JSONObject) && (a instanceof JSONObject)) {
          if(compareKey((JSONObject)e,(JSONObject)a)){
            continue;
          }else{
            return false;
          }
        }else if((e instanceof JSONArray) && (a instanceof JSONArray)){
          if(compareKey((JSONArray)e,(JSONArray)a)){
            continue;
          }else{
            return false;
          }
        }else if((e instanceof JSONObject) && (a instanceof JSONArray)){
          return false;
        }else if((e instanceof JSONArray) && (a instanceof JSONObject)){
          return false;
        }
      }
    }
    return true;
  }


  public static void main(String[] args) throws JSONException {
    JSONObject j1 = JSONObject.parseObject("{\"a\":1,\"b\":2,\"s\":[[5,4,3,2,1]]}");
    JSONObject j2 = JSONObject.parseObject("{\"b\":2,\"a\":2,\"s\":[[1,2,3,4,5]]}");
//    System.out.println(j1);
//    System.out.println(j2);
    System.out.println(compareKey("2","1"));
  }
}
