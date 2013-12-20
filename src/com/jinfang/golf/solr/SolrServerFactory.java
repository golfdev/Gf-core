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


    private static HttpSolrServer userServer;
    private static HttpSolrServer teamServer;


    public static HttpSolrServer getUserSolrServer() {
        if (userServer == null) {
        	userServer = new HttpSolrServer("http://127.0.0.1:8983/solr/user");
        	userServer.setConnectionTimeout(100);
        	userServer.setDefaultMaxConnectionsPerHost(100);
        	userServer.setMaxTotalConnections(100);
        	userServer.setRequestWriter(new BinaryRequestWriter());
        }
        return userServer;
    }
    
    public static HttpSolrServer getGolfTeamSolrServer() {
        if (teamServer == null) {
        	teamServer = new HttpSolrServer("http://127.0.0.1:8983/solr/team");
        	teamServer.setConnectionTimeout(100);
        	teamServer.setDefaultMaxConnectionsPerHost(100);
        	teamServer.setMaxTotalConnections(100);
        	teamServer.setRequestWriter(new BinaryRequestWriter());
        }
        
        return teamServer;
    }

}
