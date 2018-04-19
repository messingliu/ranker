package com.tantan.ranker.controllers;

import com.tantan.ranker.relevance.HBaseFeatureFecther;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tantan.avro.HBaseFeature;

@RestController
@RequestMapping("/test/")
public class TestController {
    @Autowired
    private HBaseFeatureFecther hBaseFeatureFecther;

    @RequestMapping(("/feature"))
    public HBaseFeature getFeature(@RequestParam(value="rowId")  String rowId) {
        return hBaseFeatureFecther.getHBaseFeature(rowId);
    }

    @RequestMapping(("/feature2"))
    public String getFeature2(@RequestParam(value="rowId")  String rowId) {
        return hBaseFeatureFecther.getHBaseFeatureStr(rowId);
    }

    @RequestMapping(("/feature3"))
    public String getFeature3(@RequestParam(value="rowId")  String rowId) {
        return hBaseFeatureFecther.getHBaseFeatureStr2(rowId);
    }

}
