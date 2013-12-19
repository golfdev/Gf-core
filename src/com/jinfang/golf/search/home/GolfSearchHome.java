package com.jinfang.golf.search.home;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.jinfang.golf.solr.SolrServerFactory;
import com.jinfang.golf.team.home.UserTeamHome;
import com.jinfang.golf.team.model.GolfTeam;
import com.jinfang.golf.user.home.UserHome;
import com.jinfang.golf.user.model.User;

@Component
public class GolfSearchHome {

    
    @Autowired
    private UserHome userHome;
    
    @Autowired
    private UserTeamHome userTeamHome;
    
    
    
    public List<User> searchUserList(String keyWord,Integer offset,Integer limit){
        
        SolrServer solrServer = SolrServerFactory.getUserSolrServer();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("user_name:"+keyWord);
        solrQuery.setFields("id");
        solrQuery.setStart(offset*limit);
        solrQuery.setRows(limit);
        QueryResponse queryResponse = null;
        try {
            queryResponse = solrServer.query(solrQuery);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        List<User> userList = new ArrayList<User>();
        if(queryResponse!=null){
            userList = transformToUser(queryResponse);
        }
        return userList;
        
    }
    
    public List<GolfTeam> searchTeamList(String keyWord,Integer offset,Integer limit){
        
        SolrServer solrServer = SolrServerFactory.getGolfTeamSolrServer();
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("team_name:"+keyWord);
        solrQuery.setFields("id");
        solrQuery.setStart(offset*limit);
        solrQuery.setRows(limit);
        QueryResponse queryResponse = null;
        try {
            queryResponse = solrServer.query(solrQuery);
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        List<GolfTeam> teamList = new ArrayList<GolfTeam>();
        if(queryResponse!=null){
            teamList = transformToGolfTeam(queryResponse);
        }
        return teamList;
        
    }
    
    
    public void fullImportUser(){
        SolrServer solrServer = SolrServerFactory.getUserSolrServer();

    	Integer count = userHome.getTotalUserCount();
    	
    	Integer size = 1000;
    	
    	Integer num = count/size+1;
    	
    	int i = 0;
    	while(i<num){
    		List<User> userList = userHome.getAllUserList(i*size, size);
    		List<SolrInputDocument>  docList = userToSolrDoc(userList);
    		try {
				solrServer.add(docList);
				solrServer.commit();
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		i++;
    	}
    }
    
    
    public void fullImportTeam(){
        SolrServer solrServer = SolrServerFactory.getGolfTeamSolrServer();

    	Integer count = userTeamHome.getTotalTeamCount();
    	
    	Integer size = 1000;
    	
    	Integer num = count/size+1;
    	
    	int i = 0;
    	while(i<num){
    		List<GolfTeam> teamList = userTeamHome.getAllGolfTeamList(i*size, size);
    		List<SolrInputDocument>  docList = teamToSolrDoc(teamList);
    		try {
				solrServer.add(docList);
				solrServer.commit();
			} catch (SolrServerException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
    		i++;
    	}
    }
    
    private List<User> transformToUser(QueryResponse queryResponse){
        SolrDocumentList  docList = queryResponse.getResults();
        List<Integer> userIds = new ArrayList<Integer>();
        
        if (!CollectionUtils.isEmpty(docList)) {
            for (SolrDocument solrDocument : docList) {
                Integer userId = MapUtils.getInteger(solrDocument, "id");
                if (userId != null) {
                    userIds.add(userId);
                }
            }
        }
        List<User> userList = new ArrayList<User>();
        if(!CollectionUtils.isEmpty(userIds)){
             userList = userHome.getUserListByIds(userIds);
        }
        return userList;
    }    
    
    private List<GolfTeam> transformToGolfTeam(QueryResponse queryResponse){
        SolrDocumentList  docList = queryResponse.getResults();
        List<Integer> teamIds = new ArrayList<Integer>();
        
        if (!CollectionUtils.isEmpty(docList)) {
            for (SolrDocument solrDocument : docList) {
                Integer teamId = MapUtils.getInteger(solrDocument, "id");
                if (teamId != null) {
                    teamIds.add(teamId);
                }
            }
        }
        List<GolfTeam> teamList = new ArrayList<GolfTeam>();
        if(!CollectionUtils.isEmpty(teamIds)){
            teamList = userTeamHome.getTeamListByIds(teamIds);
        }
        return teamList;
    }    
    
    
    private List<SolrInputDocument>  userToSolrDoc(List<User> userList){
    	List<SolrInputDocument>  docList = new ArrayList<SolrInputDocument> ();
    	for(User user:userList){
    		SolrInputDocument doc = new SolrInputDocument();
    		doc.addField("id", user.getId());
    		doc.addField("user_name", user.getUserName());
    		docList.add(doc);
    	}
    	return docList;
    }
    
    private List<SolrInputDocument>  teamToSolrDoc(List<GolfTeam> teamList){
    	List<SolrInputDocument>  docList = new ArrayList<SolrInputDocument> ();
    	for(GolfTeam team:teamList){
    		SolrInputDocument doc = new SolrInputDocument();
    		doc.addField("id", team.getId());
    		doc.addField("team_name", team.getName());
    		docList.add(doc);
    	}
    	return docList;
    }
    
    
    
	

}
