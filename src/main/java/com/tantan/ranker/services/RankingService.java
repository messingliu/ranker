package com.tantan.ranker.services;

import java.util.List;


public interface RankingService {
  /**
   * This method will get a list of users from id
   * @param id - user id
   * @return
   */
  public List<Long> getSuggestedUsers(Long id, List<Long> candidateIds, int modelId, String linearModelParameter, int topK);
}