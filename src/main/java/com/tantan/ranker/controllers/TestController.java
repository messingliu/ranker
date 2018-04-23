package com.tantan.ranker.controllers;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.tantan.ranker.bean.Feature;
import com.tantan.ranker.relevance.HBaseFeatureFecther;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tantan.avro.HBaseFeature;

import java.util.List;

@RestController
@RequestMapping("/test/")
public class TestController {
    private static final Splitter SPLITTER = Splitter.on(",").omitEmptyStrings();

    @Autowired
    private HBaseFeatureFecther hBaseFeatureFecther;

    @RequestMapping(("/feature"))
    public HBaseFeature getFeature(@RequestParam(value="rowId")  String rowId) {
        return hBaseFeatureFecther.getHBaseFeature(rowId);
    }

    @RequestMapping(("/feature2"))
    public List<Feature> getFeature2(@RequestParam(value="rowIds")  String rowIds) {
        List<String> rowIdList = SPLITTER.splitToList(rowIds);
        if (rowIdList.size() > 1) {
            return hBaseFeatureFecther.getFeatures(rowIdList);
        } else {
            return Lists.newArrayList(hBaseFeatureFecther.getFeature(rowIdList.get(0)));
        }
    }

}
