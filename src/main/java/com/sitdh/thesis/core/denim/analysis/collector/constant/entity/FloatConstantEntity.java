package com.sitdh.thesis.core.denim.analysis.collector.constant.entity;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.sitdh.thesis.core.denim.database.entity.ConstantValue;
import com.sitdh.thesis.core.denim.database.entity.FileInformation;

@Component
public class FloatConstantEntity implements ConstantEntity {
	
	private ConstantValue constantValue;

	@Override
	public String entityType() {
		return "Float";
	}

	@Override
	public ConstantEntity appendValue(String value, FileInformation fileInfo) {
		constantValue = new ConstantValue();
		
		constantValue.setConstantValue(value);
		constantValue.setFileInfo(fileInfo);
		constantValue.setType(this.entityType());
		
		return this;
	}

	@Override
	public Optional<ConstantValue> constant() {
		return StringUtils.isEmpty(constantValue.getConstantValue()) ? Optional.empty() : Optional.of(constantValue);
	}

}
