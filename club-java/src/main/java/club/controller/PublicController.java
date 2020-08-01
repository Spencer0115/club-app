package club.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import club.dao.EventRepository;
import club.entity.Common;
import club.entity.Team;
import club.service.PublicService;
import club.service.UserService;
import net.sf.json.JSONArray;
 
@Controller
@RestController 
public class PublicController {
	
	@Autowired
	private PublicService publicService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/public/getClubs")
    @ResponseBody
	public String getAllClub(){
		JSONArray result = JSONArray.fromObject(publicService.getClubList());
		return result.toString();
	}
 
	@RequestMapping(value = "/public/getTeams/{clubId}",method = RequestMethod.GET)
    @ResponseBody
	public String getAllTeams(@PathVariable Long clubId){
		JSONArray result = JSONArray.fromObject(publicService.getTeamListByClubId(clubId));
		return result.toString();
	}
	
	@RequestMapping("/public/getCommon/{type}/{page}")
    @ResponseBody
	public  ResponseEntity<?> getCommonList(
			@PathVariable String type,
			@PathVariable Integer page) {
		if(page == -1) {
			List<Common> list = publicService.getCommonList(type);
			for(Common c:list) {
				c.setInUse(userService.ifCommonInUse(c.getCode(),c.getType()));
			}
			return new ResponseEntity<>(list, HttpStatus.OK);
		}else {
			Sort sort = new Sort(Direction.ASC, "name");
			Integer size = 5;
	        Pageable pageable = PageRequest.of(page, size, sort);
	        Page<Common> pages = publicService.getCommonList(type,pageable);
	        Map<String,Object> returnMap = new HashMap<String,Object>();
	        returnMap.put("totalPage", pages.getTotalPages());
	        List<Common> list = pages.getContent();
			for(Common c:list) {
				c.setInUse(userService.ifCommonInUse(c.getCode(),c.getType()));
			}
	        returnMap.put("data", list);
			return new ResponseEntity<>(returnMap, HttpStatus.OK);
		}
		} 
}
