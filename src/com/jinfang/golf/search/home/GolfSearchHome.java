package com.jinfang.golf.search.home;

import java.util.Iterator;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Component;

import com.jinfang.golf.solr.SolrServerFactory;
import com.jinfang.golf.user.model.User;

@Component
public class GolfSearchHome {
	
//	public List<User> searchUser(String keyWord){
//		SolrServer server = SolrServerFactory.getSolrServer();
//	    SolrQuery query = new SolrQuery();
//	    query.setQuery("userName:"+keyWord);
//	    
//	    try {
//			QueryResponse rsp = server.query(query);
//			SolrDocumentList docs = rsp.getResults();
//			
//			if(docs!=null){
//			    Iterator<SolrDocument> iter = docs.iterator();
//			    
//			    while (iter.hasNext()) {
//			    
//			    }
//
//			}
//
//		} catch (SolrServerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//
//
//	}
//    
	

}
