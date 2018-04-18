package com.tantan.ranker.relevance;

import com.tantan.avro.HBaseFeature;
import com.tantan.ranker.dao.HBaseFeatureRowMapper;
import com.tantan.ranker.dao.HbaseTemplate;
import com.tantan.ranker.dao.RowMapper;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HBaseFeatureFecther {
    private static final Logger LOGGER = LoggerFactory.getLogger(HBaseFeatureFecther.class);

    @Autowired
    HbaseTemplate hbaseTemplate;
    public HBaseFeature getHBaseFeature(String sessionId) {
        HBaseFeature row = hbaseTemplate.get("MyTable", "SomeColumn", new HBaseFeatureRowMapper());
        return row;
    }
}
