package com.sitdh.thesis.core.denim.analysis.collector.constant.entity;

import java.util.Optional;

import com.sitdh.thesis.core.denim.database.entity.ConstantValue;
import com.sitdh.thesis.core.denim.database.entity.FileInformation;

public interface ConstantEntity {

	public String entityType();
	
	public ConstantEntity appendValue(String value, FileInformation fileInfo);
	
	public Optional<ConstantValue> constant();
}
