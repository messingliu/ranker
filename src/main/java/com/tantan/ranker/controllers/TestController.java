package com.tantan.ranker.controllers;

import com.tantan.ranker.relevance.HBaseFeatureFecther;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tantan.avro.HBaseFeature;

@RestController
public class TestController {
    @Autowired
    private HBaseFeatureFecther hBaseFeatureFecther;

    @RequestMapping(("/test"))
    public HBaseFeature getFeature(@RequestParam(value="rowId")  String rowId) {
        return hBaseFeatureFecther.getHBaseFeature(rowId);
    }

}
