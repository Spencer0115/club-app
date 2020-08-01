package club.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import club.service.StorageService;
 
@Controller
public class UploadController {
 
  @Autowired
  StorageService storageService;
 
  List<String> files = new ArrayList<String>();
 
	@RequestMapping(value = "/signup/upload", method = RequestMethod.POST)   
	@ResponseBody 
  public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
    String message = "";
    try {
      storageService.store(file);
      files.add(file.getOriginalFilename());
      message = "You successfully uploaded " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.OK).body(message);
    } catch (Exception e) {
      message = "FAIL to upload " + file.getOriginalFilename() + "!";
      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
    }
  }
 
  @GetMapping("/user/getallfiles")
  public ResponseEntity<List<String>> getListFiles(Model model) {
    List<String> fileNames = files
        .stream().map(fileName -> MvcUriComponentsBuilder
            .fromMethodName(UploadController.class, "getFile", fileName).build().toString())
        .collect(Collectors.toList());
    return ResponseEntity.ok().body(fileNames);
  }
  
   
}
