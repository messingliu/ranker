package com.tantan.ranker.relevance;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tantan.avro.HBaseFeature;
import com.tantan.ranker.bean.Feature;
import com.tantan.ranker.dao.*;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.hbase.util.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class HBaseFeatureFecther {
    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseFeatureFecther.class);
    public static final String FEATURE_TABLE = "user_features";

    @Autowired
    private HbaseTemplate hbaseTemplate;

    public HBaseFeature getHBaseFeature(String sessionId) {
        HBaseFeature row = hbaseTemplate.get(FEATURE_TABLE, sessionId, new HBaseFeatureRowMapper());
        return row;
    }

    public Feature getFeature(String rowId) {
        Feature row = hbaseTemplate.get(FEATURE_TABLE, rowId, new FeatureRowMapper());
        return row;
    }

    public List<Feature> getFeatures(List<String> rowIds) {
        return hbaseTemplate.batchGet(rowIds, FEATURE_TABLE, null, null, new FeatureRowMapper());
    }

    public Map<Long, Feature> getUserFeatures(List<Long> rowIds) {
        List<String> rowIdList = Lists.newArrayList();
        for (Long rowId : rowIds) {
            rowIdList.add(Long.toString(rowId));
        }
        Map<Long, Feature> map = Maps.newHashMap();
        for (Feature feature : getFeatures(rowIdList)) {
            if (feature != null && feature.getRowId() != null) {
                map.put(Long.parseLong(feature.getRowId()), feature);
            }
        }
        return map;
    }





}
