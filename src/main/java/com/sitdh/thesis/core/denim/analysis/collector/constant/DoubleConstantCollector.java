package com.sitdh.thesis.core.denim.analysis.collector.constant;

import org.apache.bcel.Const;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantDouble;
import org.apache.bcel.classfile.ConstantPool;
import org.springframework.stereotype.Component;

import com.sitdh.thesis.core.denim.analysis.collector.CodeInformationCollector;
import com.sitdh.thesis.core.denim.analysis.collector.constant.entity.DoubleConstantEntity;
import com.sitdh.thesis.core.denim.database.entity.FileInformation;
import com.sitdh.thesis.core.denim.database.service.ConstantValueService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class DoubleConstantCollector implements CodeInformationCollector {

	private ConstantValueService constantService;
	private DoubleConstantEntity constantEntity;
	
	public DoubleConstantCollector(
			ConstantValueService constantService, 
			DoubleConstantEntity constantEntity) {
		
		this.constantService = constantService;
		this.constantEntity = constantEntity;
		
	}
	
	@Override
	public void collectInforamtion(Constant constant, ConstantPool constantPool, FileInformation fileInfo) {
		if (Const.CONSTANT_Double == constant.getTag()) {
			log.debug("Double accepted");
			
			ConstantDouble v = (ConstantDouble) constant;
			
			this.constantEntity
				.appendValue(String.valueOf(v.getBytes()), fileInfo)
				.constant()
				.ifPresent(c -> {
					log.debug("Constant available: " + c.getConstantValue());
					this.constantService.storeConstant(c);
				});
		}
		
	}

}
