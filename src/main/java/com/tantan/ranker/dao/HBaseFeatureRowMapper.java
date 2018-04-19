package com.tantan.ranker.dao;

import com.tantan.avro.HBaseFeature;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseFeatureRowMapper implements RowMapper<HBaseFeature> {

    private static byte[] COLUMNFAMILY = "features".getBytes();
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

    @Override
    public HBaseFeature mapRow(Result result, int rowNum) throws Exception {
        HBaseFeature feature = new HBaseFeature();
        feature.setAge(Bytes.toString(result.getValue(COLUMNFAMILY, AGE)));
        feature.setGender(Bytes.toString(result.getValue(COLUMNFAMILY, GENDER)));
        feature.setCity(Bytes.toString(result.getValue(COLUMNFAMILY, CITY)));
        feature.setIsVip(Bytes.toString(result.getValue(COLUMNFAMILY, ISVIP)));
        feature.setDeviceOsName(Bytes.toString(result.getValue(COLUMNFAMILY, APP_OS)));
        feature.setDeviceAppVersion(Bytes.toString(result.getValue(COLUMNFAMILY, APP_VERSION)));
        feature.setCountLikeGivingLatest7Days(Bytes.toString(result.getValue(COLUMNFAMILY, LIKE_GIVE)));
        feature.setCountLikeReceivedLatest7Days(Bytes.toString(result.getValue(COLUMNFAMILY, LIKE_RECEIVE)));
        feature.setCountMatchLatest7Days(Bytes.toString(result.getValue(COLUMNFAMILY, MATCHES)));
        feature.setRateGivingLikeOverImpressionLatest7Days(Bytes.toString(result.getValue(COLUMNFAMILY, RATE_LIKE_GIVE)));
        feature.setRateReceivedLikeOverImpressionLatest7Days(Bytes.toString(result.getValue(COLUMNFAMILY, RATE_LIKE_RECEIVE)));
        feature.setRateMatchOverImpressionLatest7Days(Bytes.toString(result.getValue(COLUMNFAMILY, RATE_MATCHE)));
        feature.setMlcType(Bytes.toString(result.getValue(COLUMNFAMILY, MLC_TYPE)));
        feature.setConstellation(Bytes.toString(result.getValue(COLUMNFAMILY, CONSTELLATION)));
        feature.setSpamStatus(Bytes.toString(result.getValue(COLUMNFAMILY, SPAM_STATUS)));
        return feature;
    }

    public static void main(String[] args) {

    }
}