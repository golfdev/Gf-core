package com.jinfang.golf.solr;


import java.io.IOException;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.BinaryRequestWriter;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SolrServerFactory {
    static Logger logger = LoggerFactory.getLogger(SolrServerFactory.class);

    public static final String KEY_CRM_SOLR_HOST = "crm.solr.host";// crm solr host
    public static final String KEY_CRM_SOLR_CLOUD_HOST = "crm.solr.cloud.host";// crm solr host


    private static HttpSolrServer httpServer;

    public static HttpSolrServer getUserSolrServer() {
        if (httpServer == null) {
            httpServer = new HttpSolrServer("http://127.0.0.1:8983/solr/user");
            httpServer.setConnectionTimeout(100);
            httpServer.setDefaultMaxConnectionsPerHost(100);
            httpServer.setMaxTotalConnections(100);
            httpServer.setRequestWriter(new BinaryRequestWriter());
        }
        return httpServer;
    }
    
    public static HttpSolrServer getGolfTeamSolrServer() {
        if (httpServer == null) {
            httpServer = new HttpSolrServer("http://127.0.0.1:8983/solr/#/team");
            httpServer.setConnectionTimeout(100);
            httpServer.setDefaultMaxConnectionsPerHost(100);
            httpServer.setMaxTotalConnections(100);
            httpServer.setRequestWriter(new BinaryRequestWriter());
        }
        
        return httpServer;
    }

}
