package com.tantan.ranker.relevance;

import com.tantan.ranker.relevance.feature.FeatureName;
import com.tantan.ranker.relevance.feature.FeatureVector;
import com.tantan.ranker.utils.ModelUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class SuggestedUserScorer {
  private static final Logger LOGGER = LoggerFactory.getLogger(SuggestedUserScorer.class);
  private static final Map<String, Double> DEFAULT_MODEL = new HashMap<>();
  private final Map<String, Double> _model;
  static {
    DEFAULT_MODEL.put(FeatureName.IS_VIP.name().toLowerCase(), 1000.0);
    DEFAULT_MODEL.put(FeatureName.COUNT_LIKE_GIVE.name().toLowerCase(), 1.0);
    DEFAULT_MODEL.put(FeatureName.COUNT_LIKE_RECEIVE.name().toLowerCase(), 10.0);
    DEFAULT_MODEL.put(FeatureName.COUNT_LIKE_MATCH.name().toLowerCase(), 100.0);
    DEFAULT_MODEL.put(FeatureName.COUNT_MESSAGE_SENT.name().toLowerCase(), 1.0);
    DEFAULT_MODEL.put(FeatureName.AGE.name().toLowerCase(), 0.2);
    DEFAULT_MODEL.put(FeatureName.DISTANCE.name().toLowerCase(), 1.0);
    DEFAULT_MODEL.put(FeatureName.SEARCH_MAX_AGE.name().toLowerCase(), 1.0);
    DEFAULT_MODEL.put(FeatureName.SEARCH_MIN_AGE.name().toLowerCase(), 1.0);
  }

  public SuggestedUserScorer(int modelId, String linearModelParameter) {
    _model = ModelUtil.parseModelParameter(linearModelParameter, DEFAULT_MODEL);
    LOGGER.info("The AB test model is : " + _model);
  }

  public double score(FeatureVector<Double> v) {
    double score = 0.0;
    for (String feature : v.getIndexMap().keySet()) {
      score += (Double)v.getElementData()[v.getIndexMap().get(feature.toLowerCase())] *
          _model.getOrDefault(feature.toLowerCase(), 1.234);
    }
    return score;
  }
}
