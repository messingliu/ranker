package com.tantan.ranker.relevance;

import com.tantan.ranker.relevance.feature.Feature;
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
    DEFAULT_MODEL.put(Feature.DISTANCE.name().toLowerCase(), 1.0);
    DEFAULT_MODEL.put(Feature.POPULARITY.name().toLowerCase(), 1.0);
    DEFAULT_MODEL.put(Feature.TYPE.name().toLowerCase(), 1.0);
  }

  public SuggestedUserScorer(int modelId, String linearModelParameter) {
    _model = ModelUtil.parseModelParameter(linearModelParameter, DEFAULT_MODEL);
    LOGGER.info("The AB test model is : " + _model);
  }

  public double score(FeatureVector<Double> v) {
    double score = 0.0;
    for (String feature : v.getIndexMap().keySet()) {
      score += (Double)v.getElementData()[v.getIndexMap().get(feature.toLowerCase())] *
          _model.getOrDefault(feature.toLowerCase(), 0.0);
    }
    return score;
  }
}
