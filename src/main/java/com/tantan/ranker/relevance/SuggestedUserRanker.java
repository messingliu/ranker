package com.tantan.ranker.relevance;

import com.tantan.avro.ScoredEntity;
import com.tantan.ranker.bean.Feature;
import com.tantan.ranker.relevance.feature.FeatureName;
import com.tantan.ranker.relevance.feature.FeatureVector;
import com.tantan.ranker.services.ConfigureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



import java.util.*;

@Component
public class SuggestedUserRanker {
  private static final Logger LOGGER = LoggerFactory.getLogger(SuggestedUserRanker.class);
  private static final float LARGE_DISTANCE = 10000000;

  private static Double[] featureVector = new Double[5000];
  static {
    for (int i = 0; i < 5000; i++) {
      featureVector[i] = Math.random();
    }
  }

  @Autowired
  private HBaseFeatureFecther hBaseFeatureFecther;
  @Autowired
  private ConfigureService configureService;

  public List<Long> getSuggestedUsers(Long userId, List<Long> candidateIds, int modelId, String linearModelParameter, int topK) {
    SuggestedUserScorer suggestedUserScorer = new SuggestedUserScorer(modelId, linearModelParameter);
    List<ScoredEntity> scoredEntityList = new ArrayList<>();
    Map<Long, Feature> features = hBaseFeatureFecther.getUserFeatures(candidateIds);
    int hit = 0;
    for (Long candidateId : candidateIds) {
      ScoredEntity scoredEntity = new ScoredEntity();
      scoredEntity.setId(candidateId);
      scoredEntity.setScore((float)suggestedUserScorer.score(getFeatureVectorForUser(features.get(candidateId))));
      scoredEntity.setSourceModel(String.valueOf(modelId));
      scoredEntityList.add(scoredEntity);
      if (features.get(candidateId) != null) {
        hit++;
      }
    }
    LOGGER.info("HBase search total=" + candidateIds.size() + ", hit=" + hit);
    int hitRate = (int)(100.0 * hit / candidateIds.size());
    LOGGER.info("[LogType: client] [ClientName: hbase-hitRate] [DataSize: " + hitRate + "]");
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

  private FeatureVector<Double> getFeatureVectorForUser(Feature feature) {
    Map<String, Integer> indexMap = new HashMap<>();
    final int featureNum = configureService.getFeatureNum();
    indexMap.put(FeatureName.IS_VIP.name().toLowerCase(), 0);
    indexMap.put(FeatureName.COUNT_LIKE_GIVE.name().toLowerCase(), 1);
    indexMap.put(FeatureName.COUNT_LIKE_RECEIVE.name().toLowerCase(), 2);
    indexMap.put(FeatureName.COUNT_LIKE_MATCH.name().toLowerCase(), 3);
    indexMap.put(FeatureName.COUNT_MESSAGE_SENT.name().toLowerCase(), 4);
    indexMap.put(FeatureName.AGE.name().toLowerCase(), 5);
    indexMap.put(FeatureName.DISTANCE.name().toLowerCase(), 6);
    indexMap.put(FeatureName.SEARCH_MAX_AGE.name().toLowerCase(), 7);
    indexMap.put(FeatureName.SEARCH_MIN_AGE.name().toLowerCase(), 8);
    for (int i = 9; i < featureNum; i++) {
      indexMap.put("feature" + i, i);
    }

    Double[] value = new Double[featureNum];
    if (feature != null) {
      value[0] = (double) feature.getIs_vip();
      value[1] = feature.getCount_like_giving_latest_7_days();
      value[2] = feature.getCount_like_received_latest_7_days();
      value[3] = feature.getCount_match_latest_7_days();
      value[4] = feature.getCount_message_sent_latest_7_days();
      value[5] = 100.0 - feature.getAge();
      value[6] = (double) feature.getSearch_radius();
      value[7] = (double) feature.getSearch_max_age();
      value[8] = (double) feature.getSearch_min_age();
    } else {
      Arrays.fill(value, 0);
    }
    for (int i = 9; i < featureNum; i++) {
      value[i] = featureVector[i];
    }

    return new FeatureVector<>(value, indexMap);
  }

}
