package com.hovey.frontend.reports;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author JEEVAN
 * Created on 08.08.2013 to handle Pipeline Related Reports.......
 *
 */

/*
 * Controller to handle Pipeline Reports
 */
@Controller
public class PipelineReportsController {

	private static Logger log=Logger.getLogger(PipelineReportsController.class);
	
	@RequestMapping("pipeline.do")
	public String showPipelineReportSelectionPage(Map<String, Object> map){
		log.info("inside showPipelineReportsSelectionPage");
		System.out.println("Inside Reports of Pipeline");
		return "reports/pipeline";
	}
	
	
	
	
	
	
	
	
	
	
}
