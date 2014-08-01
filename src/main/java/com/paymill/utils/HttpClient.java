package com.paymill.utils;

public interface HttpClient {
  public String get(String path);

  public String get(String path, ParameterMap<String, String> params);

  public String post(String path, ParameterMap<String, String> params);

  public String put(String path, ParameterMap<String, String> params);

  public String delete(String path, ParameterMap<String, String> params);
}
