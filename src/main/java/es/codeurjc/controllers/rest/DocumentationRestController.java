package es.codeurjc.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controls the mapping of doc page which contains all the requests related
 * to the documentation of java related to the project
 * @author gu4re
 * @version 1.0
 */
@RestController
@RequestMapping("/doc")
public class DocumentationRestController {
	/**
	 * Private constructor avoiding initialize of DocumentationRestController
	 */
	private DocumentationRestController(){}
	
	/**
	 * Get mapping of documentation of java
	 * @return the index.html of documentation
	 */
	@GetMapping("/doc")
	public String doc(){
		return "src/main/resources/doc/index.html";
	}
}
