package com.sitdh.thesis.core.denim.analysis.structure.instruction;

import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.Instruction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvokeSpecialInstructionParser implements InstructionParser {
	
	private INVOKESPECIAL invoke;
	private ConstantPoolGen cpg;
	
	public InvokeSpecialInstructionParser(
			Instruction instruction,
			ConstantPoolGen cpg) {
		
		this.invoke = (INVOKESPECIAL) instruction;
		this.cpg = cpg;
	}

	@Override
	public InvokedTargetEntity parse() {
		log.debug("Start parse: " + invoke.getName());
		
		return InvokedTargetEntity.builder()
				.className(this.invoke.getClassName(cpg))
				.methodName(this.invoke.getClassName(cpg))
				.build();
	}

}
