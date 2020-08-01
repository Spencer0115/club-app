package club.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import club.entity.User;
import club.entity.UserRole;
import club.service.SignUpService;
import net.sf.json.JSONObject;
 
@Controller
public class SignupController {
	
	@Autowired
	private SignUpService signUpService;
 
    /**
     * signup
     * @param user
     * @return
     */
	@RequestMapping(value = "/signup", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
	public User signup(@RequestBody User user) {	
		if(user.getType().equals("0")) {
			user.setRoles(Arrays.asList(new UserRole("ADMIN")));
			user.setRoles(Arrays.asList(new UserRole("USER")));
		}else {
			user.setRoles(Arrays.asList(new UserRole("USER")));
		}
    return signUpService.addUser(user);
    }

	@RequestMapping(value = "/signup/checkOnly", method = RequestMethod.GET)
    @ResponseBody
	public Map<String,Object> checkOnly(@RequestParam(value="email") String email) {
		Map<String,Object> result = new HashMap<String,Object>();
		if(signUpService.checkOnly(email)>0) {	
			result.put("repeated",true);
		}else {
			result.put("repeated",false);
		}
     return result;
    }

	
	@RequestMapping(value = "/signup/upload2", method = RequestMethod.POST)    
	@ResponseBody    
	public String handleFileUpload(HttpServletRequest request) {   
	    MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);  
	      List<MultipartFile> files = ((MultipartHttpServletRequest) request)    
	              .getFiles("file");   
	      String name=params.getParameter("name");  
	      System.out.println("name:"+name);  
	      String id=params.getParameter("id");  
	      System.out.println("id:"+id);  
	      MultipartFile file = null;    
	      BufferedOutputStream stream = null;    
	      for (int i = 0; i < files.size(); ++i) {    
	          file = files.get(i);    
	          if (!file.isEmpty()) {    
	              try {    
	                  byte[] bytes = file.getBytes();    
	                  stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
	                  stream.write(bytes);    
	                  stream.close();    
	              } catch (Exception e) {    
	                  stream = null;    
	                  return "You failed to upload " + i + " => "    
	                          + e.getMessage();  
	              }    
	          } else {    
	              return "You failed to upload " + i    
	                      + " because the file was empty.";    
	          }  
	      }    
	      return "upload successful";  
	  } 
    
}
