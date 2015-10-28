package com.hovey.dataload.controller;



import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hovey.dataload.service.UploadService;

@Controller
public class PipelineUploadController {

	private static Logger log=Logger.getLogger(PipelineUploadController.class);
	
	@Resource(name="uploadService")
	private UploadService uploadService;
	
	
	@RequestMapping("/parsepipeline.do")
	public void parsePipeline(@RequestParam("fileName")String fileName,@RequestParam("agent")String agentName){
		log.info("inside method");
		try{
			//this.uploadService.parsePipelineData(fileName,agentName);
			this.uploadService.processReports(fileName);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
}
