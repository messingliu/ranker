package com.tantan.ranker.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UserFeaturesList {

  private List<UserFeatures> userFeaturesList;

  public UserFeaturesList(List<UserFeatures> userFeaturesList) {
    this.userFeaturesList = userFeaturesList;
  }

  public List<UserFeatures> getUserFeaturesList() {
    return userFeaturesList;
  }

  public void setUserFeaturesList(List<UserFeatures> userFeaturesList) {
    this.userFeaturesList = userFeaturesList;
  }
}
