package com.tantan.ranker.controllers;

import com.tantan.ranker.services.RankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RankerController {
  @Autowired
  private RankingService rankingService;

  @RequestMapping("/ranker")
  public List<Long> suggestedUsers(@RequestParam(value="id") Long id,
                       @RequestParam(value="candidateIds") List<Long> candidateIds,
                       @RequestParam(value="modelId") int modelId,
                       @RequestParam(value="linearModelParameter", defaultValue = "") String linearModelParameter) {
    //User user = new User(counter.incrementAndGet(), 1, 2, 3, "here", "type");
    //RankingService rankingService = new RankingServiceImpl();
    int topK = 25;
    return rankingService.getSuggestedUsers(id, candidateIds, modelId, linearModelParameter, topK);
  }
}
