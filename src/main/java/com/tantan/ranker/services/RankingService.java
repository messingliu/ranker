package com.tantan.ranker.services;

import com.tantan.ranker.models.UserFeatures;

import java.util.List;


public interface RankingService {
  /**
   * This method will get a list of users from id
   * @param id - user id
   * @return
   */
  public List<UserFeatures> getSuggestedUsers(Long id, List<Long> candidateIds, int modelId, String linearModelParameter, int topK);
}