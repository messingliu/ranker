package com.tantan.ranker.services;

import com.tantan.ranker.models.UserFeatures;
import org.lognet.springboot.grpc.GRpcService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tantan.ranker.suggestedusersfeatures.SuggestedUserFeaturesGrpc;
import com.tantan.ranker.suggestedusersfeatures.*;
import io.grpc.ServerInterceptor;
import io.grpc.stub.StreamObserver;

import java.util.List;

@org.lognet.springboot.grpc.GRpcService(interceptors = { LogInterceptor.class })
public class SuggestedUserFeaturesServiceImpl extends  SuggestedUserFeaturesGrpc.SuggestedUserFeaturesImplBase{
    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestedUserFeaturesServiceImpl.class);
    @Autowired
    private RankingService rankingService;

    @Override
    public void getSuggestedUserFeatures(com.tantan.ranker.suggestedusersfeatures.UserRequest request,
                                         io.grpc.stub.StreamObserver<UserFeaturesResponse> responseObserver) {
        List<UserFeatures> userFeaturesList = rankingService.getSuggestedUsers(request.getId(), request.getCandidateIdsList(), request.getModelId(),
                request.getLinearModelParameter(), request.getTopK());
        final UserFeaturesResponse.Builder replyBuilder = UserFeaturesResponse.newBuilder();
        for(UserFeatures userFeatures : userFeaturesList) {
            FeatureInfo.Builder oneInfo = FeatureInfo.newBuilder();
            oneInfo.setId(userFeatures.getId());
            oneInfo.setScore(0.0f);
            oneInfo.addAllFeatureData(() -> userFeatures.getFeatures().iterator());
            replyBuilder.addFeatureInfo(oneInfo);
        }
        responseObserver.onNext(replyBuilder.build());
        responseObserver.onCompleted();
    }
}
