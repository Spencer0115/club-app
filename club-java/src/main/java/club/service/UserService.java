package club.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import club.dao.ClubRepository;
import club.dao.CommonRepository;
import club.dao.EventDetailRepository;
import club.dao.EventRepository;
import club.dao.MenuRepository;
import club.dao.MessageDetailRepository;
import club.dao.MessageRepository;
import club.dao.PlayerRepository;
import club.dao.TeamRepository;
import club.dao.UserRepository;
import club.entity.Club;
import club.entity.Common;
import club.entity.Event;
import club.entity.EventDetail;
import club.entity.Menu;
import club.entity.Message;
import club.entity.MessageDetail;
import club.entity.Player;
import club.entity.Team;
import club.entity.User;
import club.service.UserService;
import club.utils.CommonUtils;

@Service
public class UserService {

	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TeamRepository teamRepository;
	@Autowired	
	private ClubRepository clubRepository;
	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private CommonRepository commonRepository;
	@Autowired
	private MessageRepository messageRepository;
	@Autowired
	private MessageDetailRepository messageDetailRepository;
	@Autowired
	private EventDetailRepository eventDetailRepository;
	
	public List<Menu> getMenuByUserName(String userName) {
		return menuRepository.getMenuByUserType(userRepository.getUserTypeByUserName(userName).getType());
	}

	public String getUserTypeByUserName(String userName) {
		return userRepository.getUserTypeByUserName(userName).getType();
	}

	public User findByUsername(String userName) {
		User user = userRepository.findByUsername(userName);
		String type = user.getType();
		if(type.equals("0")) {//club
			Club club = clubRepository.findByUserId(user.getId());
			user.setClub_clubName(club.getName());
		}else if(type.equals("1")) {
			Player player = playerRepository.findByUserId(user.getId());
			user.setFirstName(player.getFirstName());
			user.setLastName(player.getLastName());
			user.setPlayer_id(player.getId());
			user.setPlayer_teamId(player.getTeamId());
			user.setPlayer_clubId(player.getClubId());
		}
		return userRepository.findByUsername(userName);
	}
	
	public List<Team> getTeamListByClubName(String clubName) {
		return teamRepository.findByClubId(clubRepository.findByName(clubName).getId());
	}

	private static final AtomicLong counter = new AtomicLong();
	
	private static List<User> users;
	
	public List<User> findAllUsers() {
		return users;
	}
	
	public User findById(long id) {
		for(User user : users){
			if(user.getId() == id){
				return user;
			}
		}
		return null;
	}
	
	public User findByName(String name) {
		for(User user : users){
			if(user.getUsername().equalsIgnoreCase(name)){
				return user;
			}
		}
		return null;
	}
	
	public void saveUser(User user) {
		user.setId(counter.incrementAndGet());
		users.add(user);
	}

	public void updateUser(User user) {
		int index = users.indexOf(user);
		users.set(index, user);
	}

	public void deleteUserById(long id) {	
		for (Iterator<User> iterator = users.iterator(); iterator.hasNext(); ) {
		    User user = iterator.next();
		    if (user.getId() == id) {
		        iterator.remove();
		    }
		}
	}

	public boolean isUserExist(User user) {
		return findByName(user.getUsername())!=null;
	}
	
	public void deleteAllUsers(){
		users.clear();
	}

	//downwards are code for teams
	public List<Team> getTeamListByUsername(String username) {
		return teamRepository.findByClubId(clubRepository.findByUserId(userRepository.findByUsername(username).getId()).getId());	
	}

	public Page<Team> getTeamListByUsername(String username,Pageable pageable) {
		return teamRepository.findByClubId(clubRepository.findByUserId(userRepository.findByUsername(username).getId()).getId(),pageable);	
	}
	public int checkTeam(String teamName,String username) {
		return teamRepository.countByNameAndClubId(teamName,clubRepository.findByUserId(userRepository.findByUsername(username).getId()).getId());
	}

	/**
	 * get team by id
	 * @param id
	 * @return
	 */
	public Team getTeamById(Long id) {
		Team team = teamRepository.findById(id).get();
        team.setSportName(commonRepository.findByTypeAndCode("s", team.getSport()).getName());
        team.setTotalScore(eventDetailRepository.getTotalScoreByTeam(id));
		return team;
	}
	
	public Team addTeam(Team team,String username) {
		team.setClubId(clubRepository.findByUserId(userRepository.findByUsername(username).getId()).getId());
		Date date = new Date();   
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
        String dateNowStr = sdf.format(date);
        if(team.getId()==null) {
            team.setTime(dateNowStr);
        }
		return teamRepository.save(team);
	}
	public List<Team> getTeamListByTeamName(String teamName) {
		List<Team> list = teamRepository.findByName(teamName);
        list = list.stream().map(team->{
        	String sport = getTypeName("s",team.getSport());
        	team.setSportName(sport==null?"":sport);
        	return team;
        }).collect(Collectors.toList());
		return list;
	}
	
	public void deleteTeamById(long id) {
		teamRepository.deleteById(id);
	}
	
	//downwards are code for events
	public Event addEvent(Event event,String username) {
		event.setClubId(clubRepository.findByUserId(userRepository.findByUsername(username).getId()).getId());
		Date date = new Date();    
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
        String dateNowStr = sdf.format(date);  
        event.setAddedTime(dateNowStr);
		Event newEvent = eventRepository.save(event);
		String[] participants = event.getParticipantId().split("#");
		for(String id:participants) {
			if(id.length()>0) {
				if(event.getType().equals("0")||event.getType().equals("1")) {//Game or team training
					List<Player> pList = playerRepository.findByTeamId(Long.valueOf(id));
					for(Player p: pList) {
						EventDetail ed = new EventDetail();
						ed.setEventId(newEvent.getId());
						ed.setClubId(event.getClubId());
						ed.setTeamId(p.getTeamId());
						ed.setPlayerId(p.getId());
						ed.setStatus("0");
						ed.setScore(0);
						eventDetailRepository.save(ed);
					}
				}else {
					EventDetail ed = new EventDetail();
					ed.setEventId(newEvent.getId());
					ed.setPlayerId(Long.valueOf(id));
					ed.setStatus("0");
					eventDetailRepository.save(ed);
				}
			}		
		}
	
		return newEvent;
	}
	
	public List<Event> getEventListByTeamId(Long id) {
		Team t = teamRepository.findById(id).get();
		List<Event> list = eventRepository.getEventListByTeamId("#"+id+"#",t.getTime());
		list = list.stream().map(event->{
			if(event.getType().equals("0")) {//game
				Integer score = eventDetailRepository.calculateScoreByTeam(event.getId(),id);
				if(score == null) {
					score = 0;
				}
				event.setScore(score);
			}
			event.setType(commonRepository.findByTypeAndCode("e", event.getType()).getName());
			event.setStartTime(CommonUtils.time13To16_dMy(event.getStartTime()));
			event.setEndTime(CommonUtils.time13To16_dMy(event.getEndTime()));
			return event;	
		}).collect(Collectors.toList());
		return list;
	}

	public List<Event> getEventListByPlayerId(Long id) {
		Long teamId = playerRepository.findById(id).get().getTeamId();
		User u = userRepository.findById(playerRepository.findById(id).get().getUserId()).get();
		List<Event> list = eventRepository.getEventListByPlayerId("#"+id+"#","#"+teamId+"#",u.getCreateTime());
		list = list.stream().map(event->{
			event.setStartTime(CommonUtils.time13To16_dMy(event.getStartTime()));
			event.setEndTime(CommonUtils.time13To16_dMy(event.getEndTime()));
			if(event.getType().equals("0")) {//game
				event.setScore(eventDetailRepository.calculateScoreByPlayer(event.getId(),id));
			}else {
				event.setScore(0);
			}
			event.setType(commonRepository.findByTypeAndCode("e", event.getType()).getName());
			return event;	
		}).collect(Collectors.toList());
		return list;
	}
	
	public Page<Event> getEventListByUsername(String username,String type, Pageable pageable) {
		Page<Event> page;
		User user = userRepository.getUserTypeByUserName(username);
		String userType = user.getType();
		if(userType.equals("0")) {
			Club c = clubRepository.findByUserId(user.getId());
			if(type!=null&&!type.equals("")) {
			     page = eventRepository.findByClubIdAndType(c.getId(),type,pageable);
			}else {
			     page = eventRepository.findByClubId(c.getId(),pageable);
			}
		}else {
			Player p = playerRepository.findByUserId(user.getId());
			if(type!=null&&!type.equals("")) {
			     page = eventRepository.findByPlayerIdAndType("#"+p.getId()+"#","#"+p.getTeamId()+"#",type,pageable,user.getCreateTime());
			}else {
			     page = eventRepository.findByPlayerId("#"+p.getId()+"#","#"+p.getTeamId()+"#",pageable,user.getCreateTime());
			}			
		}
		


		return page;	
	}	

	public List<Event> getEventListByEventName(String eventName) {
		List<Event> list = eventRepository.findByNameLike(eventName);
		list = list.stream().map(event->{
			event.setType(commonRepository.findByTypeAndCode("e", event.getType()).getName());
			event.setStartTime(CommonUtils.time13To16_dMy(event.getStartTime()));
			event.setEndTime(CommonUtils.time13To16_dMy(event.getEndTime()));
			return event;	
		}).collect(Collectors.toList());
		return list;
	}
	
	public void deleteEventById(long id) {	
		eventRepository.deleteById(id);
	}

	public void setFlag(Long eventId,Long participantId) {
			EventDetail e = eventDetailRepository.findByEventIdAndPlayerId(eventId,participantId);
			if(e.getIsFlag().equals("1")) {
				e.setIsFlag("");
				eventDetailRepository.save(e);
			}else {
				e.setIsFlag("1");
				eventDetailRepository.save(e);
			}
	}
	//downwards are function of getting player info
	public List<Player> getPlayersByUsername(String username) {
		return playerRepository.findByClubId(clubRepository.findByUserId(userRepository.findByUsername(username).getId()).getId());
	}
	
	public List<Player> getPlayerListByTeamId(Long teamId) {
		List<Player> list = playerRepository.findByTeamId(teamId);
		list = list.stream().map(player->{
			player.setGender(commonRepository.findByTypeAndCode("g",player.getGender()).getName());
			player.setScore(getScoreByPlayer(player));
			return player;
			}).collect(Collectors.toList());
		return list;
	}

	private int getScoreByPlayer(Player player) {
		
		return 0;
	}

	public List<Player> getPlayerListByUsername(String username) {
		return playerRepository.findByClubId(clubRepository.findByUserId(userRepository.findByUsername(username).getId()).getId());
	}

	public String getTypeName(String type, String code) {
		return commonRepository.findByTypeAndCode(type,code).getName();
	}

	public Player getPlayerById(Long playerId) {
		Player p = playerRepository.findById(playerId).get();
		p.setClubName(clubRepository.findById(p.getClubId()).get().getName());
		p.setTeamName(teamRepository.findById(p.getTeamId()).get().getName());
		p.setGender(commonRepository.findByTypeAndCode("g",p.getGender()).getName());
		Integer score = eventDetailRepository.getScoreByPlayerId(playerId);
		if(score == null) {
			p.setScore(0);
		}else {
			p.setScore(score);
		}
		
		return p;
	}

	public Message sendMessage(Message m) {
		Date date = new Date();  
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
        String dateNowStr = sdf.format(date); 
        m.setTime(dateNowStr);
        Message nm = messageRepository.save(m);
        if(nm.getType().equals("2")) {//if the message is form player
            MessageDetail md = new MessageDetail();
            md.setSenderId(Long.valueOf(nm.getSender()));
            md.setReceiverId(Long.valueOf(nm.getReceiver()));
            md.setMessageId(nm.getId());
            md.setContent(nm.getContent());
            md.setTime(dateNowStr);
            messageDetailRepository.save(md);
        }
		return nm;
	}

	public List<Message> getNews(Integer n) {
		List<Message> list = messageRepository.getLatestNews();
		list = list.stream().map(m -> {
			m.setTime(CommonUtils.time15To17_tdMy(m.getTime()));
			return m;
		}).collect(Collectors.toList());
		List<Message> returnList = new ArrayList<Message>();
		for(int i=0;i<n&&i<list.size();i++) {
			returnList.add(list.get(i));
		}
		return returnList;
	}

	public Page<Message> getNews(Pageable p) {
		Page<Message> list = messageRepository.getLatestNews_P(p);
		return list;
	}
	
	public List<Message> getMessages(Long userId) {
		User u = userRepository.findById(userId).get();
		if(u.getType().equals("1")) {
		Player p = playerRepository.findByUserId(u.getId());
		Long teamId = p.getTeamId();
		List<Message> ml = messageRepository.getMessageListForPlayer(p.getUserId(), teamId);
		return ml.stream().parallel().map(message->{
			message.setSenderName(userRepository.findById(message.getSender()).get().getUsername());
			if(message.getType().equals("2")) {
				message.setMessageDetail(messageDetailRepository.findByMessageId(message.getId()));
			}
			return message;
		}).collect(Collectors.toList());
		}else {
			Club c = clubRepository.findByUserId(u.getId());
			//List<Message> ml = messageRepository.getMessagesForClub(c.getId());
			//return ml;
			return null;
		}
	}

	public void updateScore(Long playerId, Long eventId, Integer score) {
		EventDetail ed = eventDetailRepository.findByEventIdAndPlayerId(eventId, playerId);
		ed.setScore(score);
		eventDetailRepository.save(ed);
	}

	public Common addCommon(Common c) {
		Integer code = commonRepository.findMaxCode(c.getType());
		c.setCode(String.valueOf(code+1));
		return commonRepository.save(c);
	}

	public User getUserByPlayerId(Long playerId) {
		Player p = playerRepository.findById(playerId).get();
		return userRepository.findById(p.getUserId()).get();
	}

	public User updateUserInfo(User u) {
		if(u.getType().equals("1")) {
			Player p = playerRepository.findByUserId(u.getId());
			p.setFirstName(u.getFirstName());
			p.setLastName(u.getLastName());
			p.setGender(u.getGender());
			p.setId(p.getId());
		}
		return userRepository.findById(u.getId()).get();
	}

	public Object updatePlayer(Player p) {
		Player np = playerRepository.findById(p.getId()).get();
		np.setFirstName(p.getFirstName());
		np.setLastName(p.getLastName());
		np.setGender(p.getGender());
		np.setId(p.getId());
		playerRepository.save(np);
		return userRepository.findById(np.getUserId());
	}

	public Common findCommonByNameAndType(String name, String type) {
		return commonRepository.findByNameAndType(name,type);
	}

	public boolean ifCommonInUse(String code,String type) {
		if(type.equals("e")) {
			if(eventRepository.countByType(code)==0) {
				return false;
			}else {
				return true;
			}
		}
		
		if(type.equals("s")) {
			if(teamRepository.countBySport(code)==0) {
				return false;
			}else {
				return true;	
		}
	}
		return false;
	}

	public void deleteCommon(Long id) {
		commonRepository.deleteById(id);
	}

	public Event getEventById(Long id) {
		return eventRepository.findById(id).get();
	}
}
