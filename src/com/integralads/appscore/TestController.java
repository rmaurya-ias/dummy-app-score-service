package com.integralads.appscore;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.integralads.appscore.grpcdefinition.v1.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@Component
@Slf4j
//@RequestMapping("v1")
@RequiredArgsConstructor
public class TestController implements InitializingBean {
    @Autowired
    private MobileFirewall mobileFirewall;
    private FraudScore fraudScore = FraudScore.newBuilder().setAppId(1).setSamScore("samScore").setBundleId("bundleId").setStoreType(2).build();
    private FraudRequest fraudRequest = FraudRequest.newBuilder().addFraudScore(fraudScore).build();
    private ViewabilityScore viewabilityScore = ViewabilityScore.newBuilder().setMrcDisplayViewabilityScore("mrcDisplayViewabilityScore").setMrcVideoViewabilityScore("mrcVideoViewabilityScore").setStoreType(3).setAppId(4).setBundleId("bundleId").build();
    private ViewabilityRequest viewabilityRequest = ViewabilityRequest.newBuilder().addViewabilityScores(viewabilityScore).build();

    private BrandSafetyScore  brandSafetyScores= BrandSafetyScore.newBuilder().setBundleId("bundleId").setAdultScore(1).setHateSpeechScore(2).setAppId(3).setStoreType(4).setAlcoholScore(5).setGamblingScore(6).setDrugScore(7).setOffensiveScore(8).setViolenceScore(9).setIllegalDownloadScore(10).setVisibilityScore(11).build();
    private BrandSafetyRequest brandSafetyRequest = BrandSafetyRequest.newBuilder().addBrandSafetyScore(brandSafetyScores).build();

    @Override
    public void afterPropertiesSet() throws Exception {
        WriteResponse response1 = mobileFirewall.getWriteRequest(brandSafetyRequest,fraudRequest,viewabilityRequest);
        log.info("write response {}",response1);

    }

@PostMapping(value = "/readRequest")
void readRequest(@RequestParam String bundleId, int storeId){
    log.info("in rest");
    log.info("Trying request...");
    ReadResponse response = mobileFirewall.getReadRequest(bundleId, storeId);
    log.info("responseId {}, violenceScore {}", response.getResponseId(),response.getViolenceScore());
}
//@PostMapping(value = "/writeRequest", consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
//    void writeRequest(@RequestBody JsonArray fraudScoreJson ){
//        log.info("in WriteRequest");
//        log.info(fraudScoreJson.toString());
//        fraudScoreJson.toString();
//
//     BrandSafetyScore  brandSafetyScores= BrandSafetyScore.newBuilder().setBundleId("bundleId").setAdultScore(1).setHateSpeechScore(2).setAppId(3).setStoreType(4).setAlcoholScore(5).setGamblingScore(6).setDrugScore(7).setOffensiveScore(8).setViolenceScore(9).setIllegalDownloadScore(10).setVisibilityScore(11).build();
//     BrandSafetyRequest brandSafetyRequest = BrandSafetyRequest.newBuilder().addBrandSafetyScore(brandSafetyScores).build();
////    FraudRequest fraudRequest = FraudRequest.newBuilder().addFraudScore(fraudScore).build();
//}
@PostMapping(value = "/writeRequest", consumes={"application/json"},produces = MediaType.APPLICATION_JSON_VALUE)
public WriteResponse create(@RequestBody WriteRequest writeRequest ){
    log.info(writeRequest.toString());
    return null;
}
}
