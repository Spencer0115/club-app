package club.service;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import club.dao.ClubRepository;
import club.dao.PlayerRepository;
import club.dao.TeamRepository;
import club.dao.UserRepository;
import club.entity.Club;
import club.entity.Player;
import club.entity.User;
import club.entity.UserRole;
 
 
@Service
@Transactional
public class SignUpService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ClubRepository clubRepository;
	
	@Autowired
	private PlayerRepository playerRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public User addUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		Date date = new Date();    
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");  
        String dateNowStr = sdf.format(date);  
        user.setCreateTime(dateNowStr);
        user.setStatus("0");
		user.setUsername(user.getEmail());
		user = userRepository.save(user);
		String picType = user.getProfile();
		if(picType==null||picType.equals("")) {
			user.setProfile("user.png");
		}else {
			user.setProfile("profile_"+user.getId()+"_"+dateNowStr+"."+picType);
		}
		if(user.getType().equals("0")) {
			Club club = new Club();
			club.setUserId(user.getId());
			club.setName(user.getClub_clubName());
			clubRepository.save(club);
		}
		if(user.getType().equals("1")) {
			Player player = new Player();
			player.setUserId(user.getId());
			player.setTeamId(user.getPlayer_teamId());
			player.setClubId(user.getPlayer_clubId());
			player.setFirstName(user.getFirstName());
			player.setLastName(user.getLastName());
			player.setGender(user.getGender());
			player.setBirthday(user.getBirthday());
			player.setScore(0);
			player.setProfile(user.getProfile());
			playerRepository.save(player);
		}
		userRepository.updateProfile(user.getProfile(),user.getId());
		return user;
	}
	
		
	/**
	 * 
	 * set up a default user with two roles USER and ADMIN
	 * 
	 */
	@PostConstruct
	private void setupDefaultUser() {
		if (userRepository.count() == 0) {
			userRepository.save(new User("crmadmin", 
									passwordEncoder.encode("adminpass"), 
									Arrays.asList(new UserRole("USER"), new UserRole("ADMIN"))));
		}		
	}


	public Integer checkOnly(String email) {
		return userRepository.getUserCountByEmail(email);
	}
	
	
}
