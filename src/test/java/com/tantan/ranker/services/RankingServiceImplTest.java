package com.tantan.ranker.services;

import com.tantan.avro.*;
import com.tantan.ranker.relevance.SuggestedUserRanker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;

@RunWith(SpringRunner.class)
public class RankingServiceImplTest {
  @TestConfiguration
  static class RankingServiceImplTestContextConfiguration {

    @Bean
    public RankingService suggestedUsers() {
      return new RankingServiceImpl();
    }
  }

  @Autowired
  private RankingService rankingService;

  @MockBean
  private SuggestedUserRanker _suggestedUserRanker;

  List<Long> suggestedUsers = new ArrayList<>();

  @Before
  public void setUp() {

    suggestedUsers.add(1L);
    Mockito.when(_suggestedUserRanker.getSuggestedUsers(anyLong(), anyList(), anyInt(), anyString(), anyInt()))
        .thenReturn(suggestedUsers);
  }

  @Test
  public void testGetSuggestedUsers() {
    Long returnedUser = rankingService.getSuggestedUsers(anyLong(), anyList(), anyInt(), anyString(), anyInt()).get(0);
    assertThat(returnedUser).isEqualTo(suggestedUsers.get(0));
  }
}
