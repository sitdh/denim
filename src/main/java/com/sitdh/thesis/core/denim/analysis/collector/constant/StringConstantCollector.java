package com.sitdh.thesis.core.denim.analysis.collector.constant;

import org.apache.bcel.Const;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.ConstantString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sitdh.thesis.core.denim.analysis.collector.CodeInformationCollector;
import com.sitdh.thesis.core.denim.analysis.collector.constant.entity.StringConstantEntity;
import com.sitdh.thesis.core.denim.database.entity.FileInformation;
import com.sitdh.thesis.core.denim.database.service.ConstantValueService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StringConstantCollector implements CodeInformationCollector {
	
	private ConstantValueService constantService;
	
	private StringConstantEntity constantEntity;
	
	@Autowired
	public StringConstantCollector(
			ConstantValueService constantService, 
			StringConstantEntity constantEntity) {
		
		this.constantService = constantService;
		this.constantEntity = constantEntity;
		
	}
	

	@Override
	public void collectInforamtion(Constant constant, ConstantPool constantPool, FileInformation fileInfo) {
		
		if (Const.CONSTANT_String == constant.getTag()) {
			log.debug("String accepted");
			
			ConstantString v = (ConstantString) constant;
			
			this.constantEntity
				.appendValue(v.getBytes(constantPool), fileInfo)
				.constant()
				.ifPresent(c -> {
					log.debug("Constant available: " + c.getConstantValue());
					this.constantService.storeConstant(c);
				});
		}
		
	}

}
