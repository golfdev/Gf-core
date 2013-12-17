package com.jinfang.golf.solr;


import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrServerFactory {
    static Logger logger = LoggerFactory.getLogger(SolrServerFactory.class);

    public static final String KEY_CRM_SOLR_HOST = "crm.solr.host";// crm solr host
    public static final String KEY_CRM_SOLR_CLOUD_HOST = "crm.solr.cloud.host";// crm solr host


    private static HttpSolrServer httpServer;

    public static HttpSolrServer getSolrServer() {
        if (httpServer == null) {
            httpServer = new HttpSolrServer("http://127.0.0.1:8983/solr/");
        }
        return httpServer;
    }

}
