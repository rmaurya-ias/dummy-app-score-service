package com.integralads.appscore;

import com.integralads.app.score.grpc.client.AppScoreGrpcClient;
import com.integralads.app.score.grpc.client.AppScoreGrpcClientOptions;
import com.integralads.appscore.grpcdefinition.v1.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class MobileFirewall {
    private static final Logger log = LoggerFactory.getLogger(MobileFirewall.class);
    AppScoreGrpcClient appScoreGrpcClient;

    public MobileFirewall() {
        log.info("In MobileFirewall constructor");
        appScoreGrpcClient = AppScoreGrpcClient.create
                ("localhost",9090, AppScoreGrpcClientOptions.builder() //change host for k8 local run
                        .usePlainText(true)
                        .useWarmUp(true)
                        .build());
        log.info("Created app service grpc client");

    }

    public ReadResponse getReadRequest(String bundleId, Integer storeId) {
        log.info("In getReadRequest");
        ReadRequest request = ReadRequest.newBuilder().setId(bundleId).setStoreId(storeId).build();
        ReadResponse response = appScoreGrpcClient.processReadRequests(request);
        return response;
    }
    public WriteResponse getWriteRequest(BrandSafetyRequest brandSafetyRequest, FraudRequest fraudRequest, ViewabilityRequest viewabilityRequest) {
        log.info("In getWriteRequest");
        WriteRequest request = WriteRequest.newBuilder().setBrandSafetyRequest(brandSafetyRequest).setFraudRequest(fraudRequest).setViewabilityRequest(viewabilityRequest).build();
        WriteResponse response = appScoreGrpcClient.processWriteRequest(request);
        return response;
    }
//    String getvalue(){
//        return "";
//    }
}
