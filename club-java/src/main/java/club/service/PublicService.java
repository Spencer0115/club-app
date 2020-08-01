package club.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import club.dao.ClubRepository;
import club.dao.TeamRepository;
import club.dao.CommonRepository;
import club.entity.Club;
import club.entity.Common;
import club.entity.Team;

@Service
public class PublicService {
	@Autowired
	private TeamRepository teamRepository;
	@Autowired	
	private ClubRepository clubRepository;
	@Autowired
	private CommonRepository commonRepository;
	
	public List<Club> getClubList(){	
		return clubRepository.findAllOrderByName();
	}
	
	public List<Team> getTeamListByClubId(Long clubId) {
		return teamRepository.findByClubId(clubId);
	}

	public List<Common> getCommonList(String type) {
		return commonRepository.findByType(type);
	}
	
	public Page<Common> getCommonList(String type, Pageable page) {
		return commonRepository.findByType(type,page);
	}
	}
