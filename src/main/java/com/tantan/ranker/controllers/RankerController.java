package com.tantan.ranker.controllers;

import com.tantan.ranker.relevance.HBaseFeatureFecther;
import com.tantan.ranker.services.RankingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RankerController {
  private static final Logger LOGGER = LoggerFactory.getLogger(HBaseFeatureFecther.class);
  @Autowired
  private RankingService rankingService;

  @RequestMapping("/ranker")
  public List<Long> suggestedUsers(@RequestParam(value="id") Long id,
                       @RequestParam(value="candidateIds") List<Long> candidateIds,
                       @RequestParam(value="modelId") int modelId,
                       @RequestParam(value="linearModelParameter", defaultValue = "") String linearModelParameter) {
    try {
      //User user = new User(counter.incrementAndGet(), 1, 2, 3, "here", "type");
      //RankingService rankingService = new RankingServiceImpl();
      int topK = 25;
      return rankingService.getSuggestedUsers(id, candidateIds, modelId, linearModelParameter, topK);
    } catch (Exception e) {
      LOGGER.error("Ranker Error id=" + id + ", candidateIds=" + candidateIds, e);
      throw e;
    }
  }

}
