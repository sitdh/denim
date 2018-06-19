package com.sitdh.thesis.core.denim.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.sitdh.thesis.core.denim.file.storage.StorageService;

@RestController
public class ProjectReceiverServiceController {

	public StorageService storage;
	
	@Autowired
	public ProjectReceiverServiceController(StorageService storage) {
		this.storage = storage;
	}
}
