package club.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import club.common.security.CrmUserDetails;
import club.entity.Common;
import club.entity.Event;
import club.entity.Message;
import club.entity.Player;
import club.entity.Team;
import club.service.UserService;
import club.utils.CommonUtils;

@Controller
public class UserController { 
	@Autowired
	private UserService userService;
	/**
	 * get menus by username
	 * @return
	 */
	@RequestMapping(value = "/users/getMenus", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public ResponseEntity<?> getMenusByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.getMenuByUserName(username), HttpStatus.OK);
		} 

	/**
	 * get current user information
	 * @return
	 */
	@RequestMapping(value = "/users/getUser", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public ResponseEntity<?> getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.findByUsername(username), HttpStatus.OK);
		}
	
	/**
	 * get current user information
	 * @return
	 */
	@RequestMapping(value = "/users/getUserByPlayerId", method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getUserByPlayerId(@RequestParam("id") Long playerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.getUserByPlayerId(playerId), HttpStatus.OK);
		}
	
	/**
	 * get teams
	 * @return
	 */
	@RequestMapping(value="/users/teams",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getTeamsByUserName(
			@RequestParam(value="page") Integer page,
			@RequestParam(value="size") Integer size){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        if(page==-1) {
        	return new ResponseEntity<>(userService.getTeamListByUsername(username), HttpStatus.OK);
        }
        Sort sort = new Sort(Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Team> teamPage = userService.getTeamListByUsername(username,pageable);
        List<Team> list = teamPage.getContent();
        list = list.stream().map(team->{
        	String sport = userService.getTypeName("s",team.getSport());
        	team.setSportName(sport==null?"":sport);
        	return team;
        }).collect(Collectors.toList());
        Map<String,Object> returnMap = new HashMap<String,Object>();
        returnMap.put("totalPage", teamPage.getTotalPages());
        returnMap.put("data", list);
		return new ResponseEntity<>(returnMap, HttpStatus.OK);
		} 

	@RequestMapping(value="/users/deleteTeam",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> deleteTeam(@RequestParam(value="id") Long id){
		userService.deleteTeamById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	@RequestMapping(value="/users/deleteEvent",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> deleteEvent(@RequestParam(value="id") Long id){
		userService.deleteEventById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value="/users/setFlag",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> setFlag(@RequestParam(value="type") String type, @RequestParam(value="id") Long id ){
		//userService.setFlag(type, id);
		return new ResponseEntity<>(HttpStatus.OK);
	}	
	
	@RequestMapping(value="/users/getTeamById",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getTeamById(@RequestParam(value="id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.getTeamById(id), HttpStatus.OK);
		} 
	
	@RequestMapping(value="/users/getEventListByTeamId",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getEventListByTeamId(@RequestParam(value="teamId") Long teamId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.getEventListByTeamId(teamId), HttpStatus.OK);
		} 

	@RequestMapping(value="/users/getEventListByPlayerId",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getEventListByPlayerId(@RequestParam(value="playerId") Long playerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.getEventListByPlayerId(playerId), HttpStatus.OK);
		} 
	
	@RequestMapping(value="/users/getPlayerListByTeamId",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getPlayerListByTeamId(@RequestParam(value="teamId") Long teamId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.getPlayerListByTeamId(teamId), HttpStatus.OK);
		} 
	
	@RequestMapping(value="/users/allPlayerByUsername",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> allPlayerByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.getPlayersByUsername(username), HttpStatus.OK);
		} 
	
	@RequestMapping(value="/users/getPlayerListByUsername",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getPlayerListByUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.getPlayerListByUsername(username), HttpStatus.OK);
		}
	@RequestMapping(value="/users/teamsByName",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getTeamsByTeamName(@RequestParam(value="teamName") String teamName) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.getTeamListByTeamName(teamName), HttpStatus.OK);
		} 
	
	@RequestMapping(value="/users/addTeam",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public ResponseEntity<?> addTeam(@RequestBody Team team) { 
		/**
		 * Obtaining information about the current user
		 */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        userService.addTeam(team,principal.getUsername());
		return new ResponseEntity<>(userService.getTeamListByUsername(principal.getUsername()), HttpStatus.OK);
		} 
	@RequestMapping(value = "/users/checkTeam", method = RequestMethod.GET)
    @ResponseBody
	public Map<String,Object> checkTeam(@RequestParam(value="teamName") String teamName) {
		Map<String,Object> result = new HashMap<String,Object>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return null;
        }
		if(userService.checkTeam(teamName,username)>0) {	
			result.put("repeated",true);
		}else {
			result.put("repeated",false);
		}
     return result;
    }
	
	@RequestMapping(value="/users/events",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getEventList(@RequestParam(value="type") String type,
			@RequestParam(value="order") String order,
	        @RequestParam(value="page") Integer page,
			@RequestParam(value="size") Integer size) { 
		/**
		 * Obtaining information about the current user
		 */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }

        Sort sort = new Sort(Sort.Direction.ASC,"startTime");
		if(order!=null&&!order.equals("")) {
			sort = new Sort(Sort.Direction.ASC,order);
		}
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Event> eventPage = userService.getEventListByUsername(username,type,pageable);
        Map<String,Object> returnMap = new HashMap<String,Object>();
        List<Event> dataList = eventPage.getContent();
        dataList = dataList.stream().map(event->{
		event.setType(userService.getTypeName("e", event.getType()));
		event.setStartTime(CommonUtils.time13To16_dMy(event.getStartTime()));
		event.setEndTime(CommonUtils.time13To16_dMy(event.getEndTime()));
		return event;	
		}).collect(Collectors.toList());
        
        returnMap.put("totalPage", eventPage.getTotalPages());
        returnMap.put("data",dataList );
		return new ResponseEntity<>(returnMap, HttpStatus.OK);
		} 
	
	@RequestMapping(value="/users/eventsByName",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getEventListByEventName(@RequestParam(value="eventName") String eventName) { 
		/**
		 * Obtaining information about the current user
		 */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
 
		return new ResponseEntity<>(userService.getEventListByEventName(eventName), HttpStatus.OK);
		} 	
	@RequestMapping(value="/users/addEvent",method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public HttpStatus addEvent(@RequestBody Event event) { 
		/**
		 * Obtaining information about the current user
		 */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return HttpStatus.UNAUTHORIZED;
        }
        userService.addEvent(event,principal.getUsername());
		return HttpStatus.OK;
		} 
	
	@RequestMapping(value="/users/eventById",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> eventById(@RequestParam("id")Long id){
		return new ResponseEntity<>(userService.getEventById(id), HttpStatus.OK);
	}
	@RequestMapping(value="/users/players",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getPlayersByUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.getPlayersByUsername(username), HttpStatus.OK);
		} 

	@RequestMapping(value="/users/getPlayerById",method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getPlayerById(@RequestParam(value="id")Long playerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.getPlayerById(playerId), HttpStatus.OK);
		} 
	
	@RequestMapping(value = "/users/sendMessage", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public ResponseEntity<?> sendMessage(@RequestBody Message m) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.sendMessage(m), HttpStatus.OK);
		} 
	
	@RequestMapping(value = "/users/updatePlayer", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public ResponseEntity<?> updateProfile(@RequestBody Player p) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        
		return new ResponseEntity<>(userService.updatePlayer(p), HttpStatus.OK);
		} 
	
	@RequestMapping(value = "/users/getNews", method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getMessage(
			@RequestParam(value="n") Integer n,
			@RequestParam(value="page") Integer page,
			@RequestParam(value="jump") Integer jump) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        if(page==-1) {
    		return new ResponseEntity<>(userService.getNews(n), HttpStatus.OK);
        }else {
        	Sort sort = new Sort(Direction.ASC, "time");
			Integer size = 8;
	        Pageable pageable = PageRequest.of(page, size, sort);
	        Page<Message> pages = userService.getNews(pageable);
	        List<Message> l = pages.getContent();
	        for(Message m:l) {
	        	m.setTime(CommonUtils.time15To17_tdMy(m.getTime()));
	        }
	        Map<String,Object> returnMap = new HashMap<String,Object>();
	        returnMap.put("totalPage", pages.getTotalPages());
	        returnMap.put("data", l);
			return new ResponseEntity<>(returnMap, HttpStatus.OK);
        }
		} 

	@RequestMapping(value = "/users/getMessages", method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> getMessages(@RequestParam(value="userId") Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
		return new ResponseEntity<>(userService.getMessages(userId), HttpStatus.OK);
		}

	@RequestMapping(value = "/users/updateScore", method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> updateScore(@RequestParam(value="playerId") Long playerId,
			@RequestParam(value="eventId") Long eventId,
			@RequestParam(value="score") Integer score) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        userService.updateScore(playerId,eventId,score);
		return new ResponseEntity<>(HttpStatus.OK);
		} 
	
	@RequestMapping(value = "/users/addCommon", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public ResponseEntity<?> addCommon(@RequestBody Common c) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        if(c.getType().equals("s")) {
        	c.setDescription("sport");
        }else if(c.getType().equals("e")) {
        	c.setDescription("event");
        }
		return new ResponseEntity<>(userService.addCommon(c),HttpStatus.OK);
		}
	
	@RequestMapping(value = "/users/checkCommon", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public ResponseEntity<?> checkCommon(@RequestBody Common c) {
		Map<String,Object> result = new HashMap<String,Object>();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
        if(userService.findCommonByNameAndType(c.getName(),c.getType())!=null) {
        	result.put("repeated", true);
        }else {
        	result.put("repeated", false);
        }
		return new ResponseEntity<>(result,HttpStatus.OK);
		}
	
	@RequestMapping(value = "/users/deleteCommon", method = RequestMethod.GET)
    @ResponseBody
	public ResponseEntity<?> deleteCommonById(@RequestParam("id") Long id){
		userService.deleteCommon(id);
		return new ResponseEntity<>(null,HttpStatus.OK);
	}
	@RequestMapping(value = "/users/showImg", method = RequestMethod.GET)
    @ResponseBody
    public void showImg( HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CrmUserDetails principal = (CrmUserDetails) authentication.getPrincipal();
        String username = principal.getUsername();
        if(username == null) {
        	return;
        }
		String fileName = request.getParameter("fileName");
		if(fileName == null || fileName.equals("")||fileName.equals("undefined")||fileName.equals("null")) {
			return;
		}
		String path = "upload-dir/";
		File imgFile;
		try {
			imgFile = new File(path+fileName);
	    }catch(Exception e) {
	    	return;
	    }
	    FileInputStream fin = null;
	    OutputStream output=null;
	    try {
	     output = response.getOutputStream();
	     fin = new FileInputStream(imgFile);
	     byte[] arr = new byte[1024*10];
	     int n;
	     while((n=fin.read(arr))!= -1){
	      output.write(arr, 0, n);
	     }
	      output.flush();
	      output.close();
	     } catch (IOException e) {
	      e.printStackTrace();
	      }
	    }

}
