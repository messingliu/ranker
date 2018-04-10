package com.tantan.ranker.services;

import com.tantan.ranker.relevance.SuggestedUserRanker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingServiceImpl implements RankingService {

  @Autowired
  private SuggestedUserRanker _suggestedUserRanker;

  /**
   * This method will get a user from id
   *
   * @param id - user id
   * @return
   */
  @Override
  public List<Long> getSuggestedUsers(Long id, List<Long> candidateIds, int modelId, String linearModelParameter, int topK) {
    List<Long> topKUsers = _suggestedUserRanker.getSuggestedUsers(id, candidateIds, modelId, linearModelParameter, topK);
    return topKUsers;
  }
}
