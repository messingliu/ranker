package com.tantan.ranker.services;

import com.tantan.ranker.bean.Feature;
import com.tantan.ranker.constants.LogConstants;
import com.tantan.ranker.models.UserFeatures;
import com.tantan.ranker.relevance.SuggestedUserRanker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
  public List<UserFeatures> getSuggestedUsers(Long id, List<Long> candidateIds, String modelId, String linearModelParameter, int topK) {
    List<Long> topKUsers = _suggestedUserRanker.getSuggestedUsers(id, candidateIds, modelId, linearModelParameter, topK);
    List<UserFeatures> userFeaturesList = getMockFeatureList(topKUsers);

    return userFeaturesList;
  }

  public List<UserFeatures> getMockFeatureList(List<Long> topKUsers) {
    int topK = topKUsers.size();
    List<UserFeatures> userFeaturesList = new ArrayList<>();
    for (int i = 0; i < topK; i ++) {
      List<Float> feature = new ArrayList<>();
      float randomFeature = 1.2f;
      for (int j = 0; j < 100; j ++) {
        feature.add(j, randomFeature);
      }
      UserFeatures userFeatures = new UserFeatures(topKUsers.get(i), feature);
      userFeaturesList.add(i, userFeatures);
    }
    return userFeaturesList;
  }

}
