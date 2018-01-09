package controllers;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import utils.FileUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Controller
@RequestMapping("/")
public class IndexController {
	
	/** 
	 * Tidak perlu menggunakan session disini 
	 * Karena sudah ditangani oleh interceptor di spring-servlet.xml
	 */
	
	@Autowired
	ServletContext servletContext;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index() {		
		//System.out.println(FileUtils.readFileFromWebInf(servletContext, "data.txt"));
		System.out.println(FileUtils.thalesEncrypt(servletContext,"Hello World"));
		return "index";
	}
	
	@RequestMapping(value="/do_encrypt", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody 
	public ResponseEntity<String> encrypt(@RequestParam(value="msg",required=false) String msg) {
	    /* setting headernya */
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
	    /* setting result datanya */
	    Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", true);
		result.put("data",FileUtils.thalesEncrypt(servletContext,msg));
		
		/* setting genericnya */
		Type type = new TypeToken<Map<String,String>>(){}.getType();
		
		/* kembalikan dalam bentuk string json */
		return new ResponseEntity<String>(new Gson().toJson(result,type), httpHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value="/do_decrypt", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody 
	public ResponseEntity<String> decrypt(@RequestParam(value="msg",required=false) String msg) {
	    /* setting headernya */
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
	    /* setting result datanya */
	    Map<String,Object> result = new HashMap<String,Object>();
		result.put("status", true);
		result.put("data",FileUtils.thalesEncrypt(servletContext,msg));
		
		/* setting genericnya */
		Type type = new TypeToken<Map<String,String>>(){}.getType();
		
		/* kembalikan dalam bentuk string json */
		return new ResponseEntity<String>(new Gson().toJson(result,type), httpHeaders, HttpStatus.OK);
	}
	
	@RequestMapping(value = "/do_upload", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
	    /* setting headernya */
		final HttpHeaders httpHeaders= new HttpHeaders();
	    httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
	    /* setting result datanya */
	    Map<String,String> result = new HashMap<String,String>();
		result.put("status", "success");
		result.put("redirect", "index.jsp");
		
		/* setting genericnya */
		Type type = new TypeToken<Map<String,String>>(){}.getType();
		
		/* kembalikan dalam bentuk string json */
		return new ResponseEntity<String>(new Gson().toJson(result,type), httpHeaders, HttpStatus.OK);
	}
}
