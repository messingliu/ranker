package com.tantan.ranker.config;

import com.tantan.ranker.dao.HbaseTemplate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(HbaseProperties.class)
@ConditionalOnClass(HbaseTemplate.class)
public class HbaseAutoConfiguration {

    private static final String HBASE_QUORUM = "hbase.zookeeper.quorum";
    private static final String HBASE_ROOTDIR = "hbase.rootdir";
    private static final String HBASE_ZNODE_PARENT = "zookeeper.znode.parent";


    @Autowired
    private HbaseProperties hbaseProperties;

    @Bean
    @ConditionalOnMissingBean(HbaseTemplate.class)
    public HbaseTemplate hbaseTemplate() {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set(HBASE_QUORUM, "zk-1.static.bjs-datalake.p1staff.com:2181,zk-2.static.bjs-datalake.p1staff.com:2181,zk-3.static.bjs-datalake.p1staff.com:2181"); //this.hbaseProperties.getQuorum());
        configuration.set(HBASE_ROOTDIR, "hdfs:/datalake/hbase/hbase-algo"); //hbaseProperties.getRootDir());
        configuration.set(HBASE_ZNODE_PARENT, "hbase-algo"); //hbaseProperties.getNodeParent());

        return new HbaseTemplate(configuration);
    }
}
