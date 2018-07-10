package com.sitdh.thesis.core.denim.analysis.structure.instruction;

import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.INVOKEDYNAMIC;
import org.apache.bcel.generic.Instruction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvokeDynamicInstructionParser implements InstructionParser {
		
	private INVOKEDYNAMIC invoke;
	
	private ConstantPoolGen cpg;

	public InvokeDynamicInstructionParser(Instruction instruction, ConstantPoolGen cpg) {
		log.debug("Instruction assigned");
		
		this.invoke = (INVOKEDYNAMIC) instruction;
		this.cpg = cpg;
	}

	@Override
	public InvokedTargetEntity parse() {
		log.debug("Parse instruction");
		
		return InvokedTargetEntity.builder()
				.className(this.invoke.getClassName(cpg))
				.methodName(this.invoke.getMethodName(cpg))
				.build();
	}

}
