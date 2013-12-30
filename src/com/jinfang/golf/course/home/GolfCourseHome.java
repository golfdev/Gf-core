package com.jinfang.golf.course.home;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.jinfang.golf.course.dao.GolfCourseDAO;
import com.jinfang.golf.course.dao.GolfCourseHoleScoreDAO;
import com.jinfang.golf.course.dao.GolfCoursePlayerDAO;
import com.jinfang.golf.course.model.GolfCourse;
import com.jinfang.golf.course.model.GolfCourseHoleScore;
import com.jinfang.golf.course.model.GolfCoursePlayer;

@Component
public class GolfCourseHome {
    
	@Autowired
	private GolfCourseDAO golfCourseDAO;
	
	@Autowired
	private GolfCourseHoleScoreDAO golfCourseHoleScoreDAO;
	
	@Autowired
	private GolfCoursePlayerDAO golfCoursePlayerDAO;
	
	public void saveCourse(GolfCourse course){
		Gson gson = new Gson();
		String json = gson.toJson(course.getPlayerList());
		course.setPlayerSetting(json);
		int courseId = golfCourseDAO.save(course).intValue();
		
		List<GolfCoursePlayer> playerList = course.getPlayerList();
		
		if(CollectionUtils.isNotEmpty(playerList)){
			for(GolfCoursePlayer player:playerList){
				player.setCourseId(courseId);
				golfCoursePlayerDAO.save(player);
			}
		}

		
		
	}
	
	public void saveHoleScore(GolfCourseHoleScore holeScore){
		golfCourseHoleScoreDAO.save(holeScore);
	}
	
	
	
   	
	
}
