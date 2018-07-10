package com.sitdh.thesis.core.denim.analysis.structure.instruction;

import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.Instruction;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class InvokeVirtualInstructionParser implements InstructionParser {
	
	private INVOKEVIRTUAL invoke;
	private ConstantPoolGen cpg;

	public InvokeVirtualInstructionParser(Instruction instruction, ConstantPoolGen cpg) {
		this.invoke = (INVOKEVIRTUAL) instruction;
		this.cpg = cpg;
	}

	@Override
	public InvokedTargetEntity parse() {
		log.debug("Parse invoker");
		return InvokedTargetEntity.builder()
				.className(this.invoke.getClassName(cpg))
				.methodName(this.invoke.getMethodName(cpg))
				.build();
	}

}
