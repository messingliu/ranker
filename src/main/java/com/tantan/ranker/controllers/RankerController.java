package com.tantan.ranker.controllers;

import com.google.common.collect.Lists;
import com.tantan.ranker.constants.LogConstants;
import com.tantan.ranker.models.UserFeatures;
import com.tantan.ranker.models.UserFeaturesList;
import com.tantan.ranker.relevance.HBaseFeatureFecther;
import com.tantan.ranker.services.RankingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RankerController {
  private static final Logger LOGGER = LoggerFactory.getLogger(HBaseFeatureFecther.class);
  @Autowired
  private RankingService rankingService;

  @RequestMapping("/ranker")
  public List<UserFeatures> suggestedUsers(@RequestParam(value="id") Long id,
                       @RequestParam(value="candidateIds") List<Long> candidateIds,
                       @RequestParam(value="modelId") int modelId,
                       @RequestParam(value="linearModelParameter", defaultValue = "") String linearModelParameter,
                       @RequestParam(value="topK", defaultValue = "25") int topK) {
    try {
      long startTime = System.currentTimeMillis();
      List<UserFeatures> userFeaturesList = rankingService.getSuggestedUsers(id, candidateIds, "", linearModelParameter, topK);
      long endTime = System.currentTimeMillis();
      LOGGER.info("[{}: {}][{}: {}][{}: {}]", LogConstants.LOGO_TYPE, LogConstants.CLIENT_CALL,
              LogConstants.CLIENT_NAME, LogConstants.RANKER, LogConstants.RESPONSE_TIME, endTime - startTime);
      return userFeaturesList;
    } catch (Exception e) {
      LOGGER.error("Ranker Error id=" + id + ", candidateIds=" + candidateIds, e);
      return null;
    }
  }
}
