package com.tantan.ranker.services;

import com.tantan.ranker.bean.Feature;
import com.tantan.ranker.constants.LogConstants;
import com.tantan.ranker.models.UserFeatures;
import com.tantan.ranker.relevance.SuggestedUserRanker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingServiceImpl implements RankingService {
  private static final Logger LOGGER = LoggerFactory.getLogger(RankingServiceImpl.class);
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
