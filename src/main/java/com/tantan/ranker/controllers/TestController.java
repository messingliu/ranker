package com.tantan.ranker.controllers;

import com.tantan.ranker.relevance.HBaseFeatureFecther;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tantan.avro.HBaseFeature;

@RestController("/test")
public class TestController {
    @Autowired
    private HBaseFeatureFecther hBaseFeatureFecther;

    @RequestMapping("feature")
    public HBaseFeature getFeature(String rowId) {
        return hBaseFeatureFecther.getHBaseFeature(rowId);
    }

}
