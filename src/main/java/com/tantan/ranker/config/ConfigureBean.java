package com.tantan.ranker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "config")
public class ConfigureBean {
  private int featureNum;

  public int getFeatureNum() {
    return featureNum;
  }

  public void setFeatureNum(int featureNum) {
    this.featureNum = featureNum;
  }
}
