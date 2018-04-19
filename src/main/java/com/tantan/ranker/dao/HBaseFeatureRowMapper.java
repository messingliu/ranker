package com.tantan.ranker.dao;

import com.tantan.avro.HBaseFeature;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

public class HBaseFeatureRowMapper implements RowMapper<HBaseFeature> {

    private static byte[] COLUMNFAMILY = "features".getBytes();
    private static byte[] NAME = "name".getBytes();
    private static byte[] AGE = "age".getBytes();

    @Override
    public HBaseFeature mapRow(Result result, int rowNum) throws Exception {
        HBaseFeature hBaseFeature = new HBaseFeature();
        String name = Bytes.toString(result.getValue(COLUMNFAMILY, NAME));
        int age = Bytes.toInt(result.getValue(COLUMNFAMILY, AGE));
        hBaseFeature.setAge(age);

        return hBaseFeature;
    }
}