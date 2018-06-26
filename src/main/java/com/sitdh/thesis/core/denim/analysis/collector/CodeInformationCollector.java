package com.sitdh.thesis.core.denim.analysis.collector;

import org.apache.bcel.classfile.Constant;
import org.apache.bcel.classfile.ConstantPool;

import com.sitdh.thesis.core.denim.database.entity.FileInformation;

public interface CodeInformationCollector {

	public void collectInforamtion(Constant constant, ConstantPool constantPool, FileInformation fileInfo);
}
