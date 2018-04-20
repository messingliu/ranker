package com.tantan.ranker.relevance;

import com.tantan.avro.ScoredEntity;
import com.tantan.ranker.bean.Feature;
import com.tantan.ranker.relevance.feature.FeatureName;
import com.tantan.ranker.relevance.feature.FeatureVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import java.util.*;

@Component
public class SuggestedUserRanker {
  private static final Logger LOGGER = LoggerFactory.getLogger(SuggestedUserRanker.class);
  private static final float LARGE_DISTANCE = 10000000;

  @Autowired
  private HBaseFeatureFecther hBaseFeatureFecther;

  public List<Long> getSuggestedUsers(Long userId, List<Long> candidateIds, int modelId, String linearModelParameter, int topK) {
    SuggestedUserScorer suggestedUserScorer = new SuggestedUserScorer(modelId, linearModelParameter);
    List<ScoredEntity> scoredEntityList = new ArrayList<>();
    Map<Long, Feature> features = hBaseFeatureFecther.getUserFeatures(candidateIds);
    for (Long candidateId : candidateIds) {
      ScoredEntity scoredEntity = new ScoredEntity();
      scoredEntity.setId(candidateId);
      scoredEntity.setScore((float)suggestedUserScorer.score(getFeatureVectorForUser(features.get(candidateId))));
      scoredEntity.setSourceModel(String.valueOf(modelId));
      scoredEntityList.add(scoredEntity);
    }
    return rankSuggestedUsers(scoredEntityList, topK);
  }

  private List<Long> rankSuggestedUsers(List<ScoredEntity> scoredEntityList, int topK) {
    if (scoredEntityList == null) {
      return Collections.EMPTY_LIST;
    }
    Collections.sort(scoredEntityList, new Comparator<ScoredEntity>() {
      @Override
      public int compare(ScoredEntity a, ScoredEntity b) {
        if (a.getScore() == b.getScore()) {
          return 0;
        } else if (a.getScore() < b.getScore()) {
          return 1;
        } else {
          return -1;
        }
      }
    });
    List<Long> suggestedUserList = new ArrayList<>();
    for (int i = 0; i < Math.min(topK, scoredEntityList.size()); i ++) {
      LOGGER.info("suggestedUserList " + scoredEntityList.get(i).getId() + " " + scoredEntityList.get(i).getScore());
      suggestedUserList.add(scoredEntityList.get(i).getId());
    }
    return suggestedUserList;
  }

  public FeatureVector<Double> getFeatureVectorForUser(Feature feature) {
    Map<String, Integer> indexMap = new HashMap<>();
    indexMap.put(FeatureName.IS_VIP.name().toLowerCase(), 0);
    indexMap.put(FeatureName.COUNT_LIKE_GIVE.name().toLowerCase(), 1);
    indexMap.put(FeatureName.COUNT_LIKE_RECEIVE.name().toLowerCase(), 2);
    indexMap.put(FeatureName.COUNT_LIKE_MATCH.name().toLowerCase(), 3);
    indexMap.put(FeatureName.COUNT_MESSAGE_SENT.name().toLowerCase(), 4);
    indexMap.put(FeatureName.AGE.name().toLowerCase(), 5);
    indexMap.put(FeatureName.DISTANCE.name().toLowerCase(), 6);
    Double[] value = new Double[7];
    if (feature != null) {
      value[0] = (double)feature.getIs_vip();
      value[1] = feature.getCount_like_giving_latest_7_days();
      value[2] = feature.getCount_like_received_latest_7_days();
      value[3] = feature.getCount_match_latest_7_days();
      value[4] = feature.getCount_message_sent_latest_7_days();
      value[5] = 100.0 - feature.getAge();
      value[6] = 0.0;
    }
    return new FeatureVector<>(value, indexMap);
  }
}
