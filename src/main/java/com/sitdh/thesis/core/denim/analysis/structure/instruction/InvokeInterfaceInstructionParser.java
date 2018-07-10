package com.sitdh.thesis.core.denim.analysis.structure.instruction;

import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.INVOKEINTERFACE;
import org.apache.bcel.generic.Instruction;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class InvokeInterfaceInstructionParser implements InstructionParser {
	
	private INVOKEINTERFACE invoke;
	
	private ConstantPoolGen cpg;

	public InvokeInterfaceInstructionParser(Instruction instruction, ConstantPoolGen cpg) {
		log.debug("Interface");
		this.invoke = (INVOKEINTERFACE) instruction;
		this.cpg = cpg;
	}

	@Override
	public InvokedTargetEntity parse() {
		log.debug("Start parse");
		
		return InvokedTargetEntity.builder()
				.className(this.invoke.getClassName(cpg))
				.methodName(this.invoke.getMethodName(cpg))
				.build();
	}

}
