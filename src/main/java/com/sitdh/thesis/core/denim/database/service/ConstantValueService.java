package com.sitdh.thesis.core.denim.database.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sitdh.thesis.core.denim.database.entity.ConstantValue;
import com.sitdh.thesis.core.denim.database.repository.ConstantValueRepository;

@Service
public class ConstantValueService {
	
	private ConstantValueRepository constRepo;
	
	@Autowired
	public ConstantValueService(ConstantValueRepository constRepo) {
		this.constRepo = constRepo;
	}

	public void storeConstant(ConstantValue c) {
		this.constRepo.save(c);
	}

}
