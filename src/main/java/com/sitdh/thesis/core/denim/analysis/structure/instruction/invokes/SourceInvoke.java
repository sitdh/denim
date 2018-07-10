package com.sitdh.thesis.core.denim.analysis.structure.instruction.invokes;

import org.apache.bcel.generic.*;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j @Data
@Component 
public class SourceInvoke extends EmptyVisitor {
	
	private ConstantPoolGen cpg;
	
	public SourceInvoke() {
		
	}

	@Override
	public void visitLocalVariableInstruction(LocalVariableInstruction obj) {
		log.debug(obj.toString(true));	
	}

	@Override
	public void visitInvokeInstruction(InvokeInstruction obj) {
		log.debug(obj.toString(true));
	}

	@Override
	public void visitINVOKESTATIC(INVOKESTATIC obj) {
		log.debug(obj.toString(true));
	}

	@Override
	public void visitINVOKESPECIAL(INVOKESPECIAL obj) {
		log.debug(obj.toString(true));
	}

	@Override
	public void visitINVOKEINTERFACE(INVOKEINTERFACE obj) {
		log.debug(obj.toString(true));
	}

	@Override
	public void visitINVOKEVIRTUAL(INVOKEVIRTUAL obj) {
		log.debug(obj.toString(true));
	}

	@Override
	public void visitINVOKEDYNAMIC(INVOKEDYNAMIC obj) {
		log.debug(obj.toString(true));
	}

	@Override
	public void visitFASTORE(FASTORE obj) {
		log.debug(obj.toString(true));
	}

}
