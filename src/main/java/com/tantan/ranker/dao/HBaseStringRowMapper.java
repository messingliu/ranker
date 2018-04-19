package com.tantan.ranker.dao;

import org.apache.hadoop.hbase.client.Result;

public class HBaseStringRowMapper implements RowMapper<String> {
    private static byte[] COLUMNFAMILY = "f".getBytes();
    private static byte[] NAME = "name".getBytes();
    private static byte[] AGE = "age".getBytes();

    @Override
    public String mapRow(Result result, int rowNum) {
        return result.toString();
    }


}
