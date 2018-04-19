package com.tantan.ranker.relevance;

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

    public Feature getHBaseFeature2(String sessionId) {
        Feature row = hbaseTemplate.get(FEATURE_TABLE, sessionId, new FeatureRowMapper());
        return row;
    }

    public String getHBaseFeatureStr(String sessionId) {
        return hbaseTemplate.get(FEATURE_TABLE, sessionId, new HBaseStringRowMapper());
    }

    public String getHBaseFeatureStr2(String sessionId) {
        try {
            HBaseFeature row = hbaseTemplate.get(FEATURE_TABLE, sessionId, new HBaseFeatureRowMapper());
            return JsonMapper.writeObjectAsString(row);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
