package com.jinfang.golf.search.home;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.MapUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
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
        
        SolrServer solrServer = SolrServerFactory.getSolrServer();
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
        
        SolrServer solrServer = SolrServerFactory.getSolrServer();
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
    
    
    
	

}
