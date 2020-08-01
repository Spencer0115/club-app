package club.common.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import club.dao.UserRepository;
import club.entity.User;


@Service
public class CrmUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
//	@Autowired
//	PasswordEncoder passwordEncoder;
	
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    	
        User user = userRepository.findByUsername(userName);
        if(user == null){
            throw new UsernameNotFoundException("UserName "+userName+" not found");
        }
        return new CrmUserDetails(user);
    }	

}
