package com.sitdh.thesis.core.denim.analysis.collector;

import java.io.IOException;
import java.util.List;

import org.apache.bcel.classfile.ClassFormatException;
import org.apache.bcel.classfile.ClassParser;
import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.generic.ConstantPoolGen;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sitdh.thesis.core.denim.database.entity.FileInformation;

@Component
public class DataCollectionManager {
	
	private List<CodeInformationCollector> collectors;
	
	@Autowired
	public DataCollectionManager(List<CodeInformationCollector> collectors) {
		this.collectors = collectors;
	}

	public void accept(FileInformation fileInfo) {
		try {
			JavaClass javaParser = new ClassParser(fileInfo.getLocation()).parse();
			
			ConstantPool constantPool = javaParser.getConstantPool();
			ConstantPoolGen constantPoolGen = new ConstantPoolGen(constantPool);
			
			for (int i = 0; i < constantPoolGen.getSize(); i++) {
				Constant constant = constantPool.getConstant(i);
				if (null == constant) continue;
				
				this.notifyConsntantWithFileInfo(constant, constantPool, fileInfo);
			}
			
		} catch (ClassFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void notifyConsntantWithFileInfo(
			Constant constant, 
			ConstantPool constantPool, 
			FileInformation fileInfo) {
		
		collectors.forEach(c -> c.collectInforamtion(constant, constantPool, fileInfo));
	}
}
