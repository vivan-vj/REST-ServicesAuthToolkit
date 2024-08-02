package com.example.demo.controllers;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.RequestObj;
import com.example.demo.model.ResponseObj;
import com.example.demo.util.RestUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class MyRestController.
 */
@RestController
@RequestMapping("/java/rs")
@Slf4j
public class MyRestController {

	/**
	 * Submit.
	 *
	 * @param requestObj the request obj
	 * @return the response entity
	 */
	@PostMapping(value="/submit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<ResponseObj> submit(@RequestBody RequestObj requestObj) {
		ResponseObj responseObj = new ResponseObj();
		log.info("Date Received from Request : "+requestObj.toString());
		// TO DO - Service Call
		responseObj.setStatus(RestUtil.SUCCESS);
		responseObj.setMessage("Submitted DATA ");
		return ResponseEntity.ok().body(responseObj);

	}

}
