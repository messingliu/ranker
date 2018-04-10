package com.tantan.ranker.relevance;

import com.tantan.avro.ScoredEntity;
import com.tantan.ranker.relevance.feature.Feature;
import com.tantan.ranker.relevance.feature.FeatureVector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.util.*;

@Component
public class SuggestedUserRanker {
  private static final Logger LOGGER = LoggerFactory.getLogger(SuggestedUserRanker.class);
  private static final float LARGE_DISTANCE = 10000000;

  public List<Long> getSuggestedUsers(Long userId, List<Long> candidateIds, int modelId, String linearModelParameter, int topK) {
    SuggestedUserScorer suggestedUserScorer = new SuggestedUserScorer(modelId, linearModelParameter);
    List<ScoredEntity> scoredEntityList = new ArrayList<>();
    for (Long candidateId : candidateIds) {
      ScoredEntity scoredEntity = new ScoredEntity();
      scoredEntity.setId(candidateId);
      scoredEntity.setScore((float)suggestedUserScorer.score(getFeatureVectorForUser(candidateId)));
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
    for(ScoredEntity scoredEntity: scoredEntityList) {
      LOGGER.error("suggestedUserList " + scoredEntity.getId() + " " + scoredEntity.getScore());
    }
    for (int i = 0; i < Math.min(topK, scoredEntityList.size()); i ++) {
      suggestedUserList.add(scoredEntityList.get(i).getId());
    }
    return suggestedUserList;
  }

  public FeatureVector<Double> getFeatureVectorForUser(Long candidateId) {
    Map<String, Integer> indexMap = new HashMap<>();
    indexMap.put(Feature.POPULARITY.name().toLowerCase(), 0);
    indexMap.put(Feature.TYPE.name().toLowerCase(), 1);
    indexMap.put(Feature.DISTANCE.name().toLowerCase(), 2);
    Double[] value = new Double[3];
    value[0] = new Double(candidateId);
    value[1] = 0.5;
    value[2] = new Double(1 -  candidateId * candidateId / LARGE_DISTANCE);
    return new FeatureVector<>(value, indexMap);
  }
}
