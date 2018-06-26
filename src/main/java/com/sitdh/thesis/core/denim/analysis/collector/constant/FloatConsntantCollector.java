package com.sitdh.thesis.core.denim.analysis.collector.constant;

import org.apache.bcel.Const;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantFloat;
import org.apache.bcel.classfile.ConstantPool;
import org.springframework.stereotype.Component;

import com.sitdh.thesis.core.denim.analysis.collector.CodeInformationCollector;
import com.sitdh.thesis.core.denim.analysis.collector.constant.entity.FloatConstantEntity;
import com.sitdh.thesis.core.denim.database.entity.FileInformation;
import com.sitdh.thesis.core.denim.database.service.ConstantValueService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FloatConsntantCollector implements CodeInformationCollector {

	private ConstantValueService constantService;
	private FloatConstantEntity constantEntity;
	
	public FloatConsntantCollector(
			ConstantValueService constantService, 
			FloatConstantEntity constantEntity) {
		
		this.constantService = constantService;
		this.constantEntity = constantEntity;
		
	}
	
	@Override
	public void collectInforamtion(Constant constant, ConstantPool constantPool, FileInformation fileInfo) {
		if (Const.CONSTANT_Float == constant.getTag()) {
			log.debug("Integer accepted");
			
			ConstantFloat v = (ConstantFloat) constant;

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
