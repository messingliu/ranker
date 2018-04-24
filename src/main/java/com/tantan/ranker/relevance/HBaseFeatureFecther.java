package com.tantan.ranker.relevance;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.tantan.avro.HBaseFeature;
import com.tantan.ranker.bean.Feature;
import com.tantan.ranker.constants.LogConstants;
import com.tantan.ranker.dao.*;
import org.apache.commons.lang.math.NumberUtils;
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
    private static final String FEATURE_TABLE = "test_user_bucked_b";
    private static final String ROW_KEY_PREFIX = "row_key_";
    public static final String COLUMNFAMILY = "f";

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
        return hbaseTemplate.batchGet(rowIds, FEATURE_TABLE, COLUMNFAMILY, null, new FeatureRowMapper());
    }

    public Map<Long, Feature> getUserFeatures(List<Long> rowIds) {
        long startTime = System.currentTimeMillis();
        List < String > rowIdList = Lists.newArrayList();
        for (Long rowId : rowIds) {
            rowIdList.add(ROW_KEY_PREFIX + Long.toString(rowId));
        }
        Map<Long, Feature> map = Maps.newHashMap();
        for (Feature feature : getFeatures(rowIdList)) {
            if (feature != null && feature.getRowId() != null && feature.getRowId().startsWith(ROW_KEY_PREFIX)) {
                map.put(NumberUtils.toLong(feature.getRowId().substring(ROW_KEY_PREFIX.length())), feature);
            }
        }
        long endTime = System.currentTimeMillis();
        LOGGER.info("[{}: {}][{}: {}][{}: {}][{}: {}]", LogConstants.LOGO_TYPE, LogConstants.CLIENT_CALL,
                LogConstants.CLIENT_NAME, LogConstants.RANKER, LogConstants.RESPONSE_TIME, endTime - startTime,
                LogConstants.DATA_SIZE, rowIdList.size());
        return map;
    }

    public Map<Long, Feature> getMockFeatures(List<Long> rowIds) {
        Map<Long, Feature> map = Maps.newHashMap();
        for (Long id: rowIds ) {
            Feature feature = new Feature();
                feature.setRowId(rowIds.toString());
                feature.setAge((int)(10*Math.random()));
                feature.setGender("male");
                feature.setCity((int)(10*Math.random()));
                feature.setIs_vip((int)(10*Math.random()));
                feature.setDevice_os_name("os");
                feature.setDevice_app_version("version");
                feature.setCount_like_giving_latest_7_days((10*Math.random()));
                feature.setCount_like_received_latest_7_days((10*Math.random()));
                feature.setCount_match_latest_7_days(10*Math.random());
                feature.setRate_giving_like_over_impression_latest_7_days(10*Math.random());
                feature.setRate_received_like_over_impression_latest_7_days(10*Math.random());
                feature.setRate_match_over_impression_latest_7_days(10*Math.random());
                feature.setMlc_type((int)(10*Math.random()));
                feature.setConstellation((int)(10*Math.random()));
                feature.setSpam_status("spam");
            map.put(id, feature);
        }
        return map;
    }
}
