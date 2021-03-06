package com.tantan.ranker.dao;

import com.tantan.ranker.bean.Feature;
import com.tantan.ranker.relevance.HBaseFeatureFecther;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class FeatureRowMapper implements RowMapper<Feature> {
    private static byte[] COLUMNFAMILY = HBaseFeatureFecther.COLUMNFAMILY.getBytes();
    private static byte[] APP_VERSION = "device_app_version".getBytes();
    private static byte[] APP_OS = "device_os_name".getBytes();
    private static byte[] ISVIP = "is_vip".getBytes();
    private static byte[] AGE = "age".getBytes();
    private static byte[] GENDER = "gender".getBytes();
    private static byte[] CITY = "city".getBytes();
    private static byte[] LIKE_GIVE = "count_like_giving_latest_7_days".getBytes();
    private static byte[] LIKE_RECEIVE = "count_like_received_latest_7_days".getBytes();
    private static byte[] MATCHES = "count_match_latest_7_days".getBytes();
    private static byte[] RATE_LIKE_GIVE = "rate_giving_like_over_impression_latest_7_days".getBytes();
    private static byte[] RATE_LIKE_RECEIVE = "rate_received_like_over_impression_latest_7_days".getBytes();
    private static byte[] RATE_MATCHE = "rate_match_over_impression_latest_7_days".getBytes();
    private static byte[] MLC_TYPE = "mlc_type".getBytes();
    private static byte[] SPAM_STATUS = "spam_status".getBytes();
    private static byte[] CONSTELLATION = "constellation".getBytes();

    private static byte[] SEARCH_MAX_AGE = "search_max_age".getBytes();
    private static byte[] SEARCH_MIN_AGE = "search_min_age".getBytes();
    private static byte[] SEARCH_RADIUS = "search_radius".getBytes();

    @Override
    public Feature mapRow(Result result, int rowNum) {
        if (result == null || StringUtils.isBlank(Bytes.toString(result.getRow()))) {
            return null;
        }
        Feature feature = new Feature();
        try {
            feature.setRowId(Bytes.toString(result.getRow()));
            feature.setAge(NumberUtils.toInt(Bytes.toString(result.getValue(COLUMNFAMILY, AGE))));
            feature.setGender(Bytes.toString(result.getValue(COLUMNFAMILY, GENDER)));
            feature.setCity(NumberUtils.toInt(Bytes.toString(result.getValue(COLUMNFAMILY, CITY))));
            feature.setIs_vip(NumberUtils.toInt(Bytes.toString(result.getValue(COLUMNFAMILY, ISVIP))));
            feature.setDevice_os_name(Bytes.toString(result.getValue(COLUMNFAMILY, APP_OS)));
            feature.setDevice_app_version(Bytes.toString(result.getValue(COLUMNFAMILY, APP_VERSION)));
            feature.setCount_like_giving_latest_7_days(NumberUtils.toDouble(Bytes.toString(result.getValue(COLUMNFAMILY, LIKE_GIVE))));
            feature.setCount_like_received_latest_7_days(NumberUtils.toDouble(Bytes.toString(result.getValue(COLUMNFAMILY, LIKE_RECEIVE))));
            feature.setCount_match_latest_7_days(NumberUtils.toDouble(Bytes.toString(result.getValue(COLUMNFAMILY, MATCHES))));
            feature.setRate_giving_like_over_impression_latest_7_days(NumberUtils.toDouble(Bytes.toString(result.getValue(COLUMNFAMILY, RATE_LIKE_GIVE))));
            feature.setRate_received_like_over_impression_latest_7_days(NumberUtils.toDouble(Bytes.toString(result.getValue(COLUMNFAMILY, RATE_LIKE_RECEIVE))));
            feature.setRate_match_over_impression_latest_7_days(NumberUtils.toDouble(Bytes.toString(result.getValue(COLUMNFAMILY, RATE_MATCHE))));
            feature.setMlc_type(NumberUtils.toInt(Bytes.toString(result.getValue(COLUMNFAMILY, MLC_TYPE))));
            feature.setConstellation(NumberUtils.toInt(Bytes.toString(result.getValue(COLUMNFAMILY, CONSTELLATION))));
            feature.setSpam_status(Bytes.toString(result.getValue(COLUMNFAMILY, SPAM_STATUS)));
            feature.setSearch_max_age(NumberUtils.toInt(Bytes.toString(result.getValue(COLUMNFAMILY, SEARCH_MAX_AGE))));
            feature.setSearch_min_age(NumberUtils.toInt(Bytes.toString(result.getValue(COLUMNFAMILY, SEARCH_MIN_AGE))));
            feature.setSearch_radius(NumberUtils.toInt(Bytes.toString(result.getValue(COLUMNFAMILY, SEARCH_RADIUS))));
        } catch (Exception e) {
            return null;
        }
        return feature;
    }

}
