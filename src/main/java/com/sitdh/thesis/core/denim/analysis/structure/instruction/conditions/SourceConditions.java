package com.sitdh.thesis.core.denim.analysis.structure.instruction.conditions;

import org.apache.bcel.generic.*;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j @Data
@Component
public class SourceConditions extends EmptyVisitor {
	
	private ConstantPoolGen cpg;
	
	public SourceConditions() {
		
	}
	
	@Override
	public void visitBranchInstruction(BranchInstruction obj) {
		log.debug("Branch instruction of 'If' - " + obj.toString());
		log.debug("DDD: " + obj.getTarget().getInstruction().toString());
		InstructionHandle ih = obj.getTarget();
		if (ih.hasTargeters()) {
			InstructionTargeter[] targets = ih.getTargeters();
			for (InstructionTargeter target : targets) {
				log.debug("Target: " + target.toString());
				if (target instanceof LineNumberGen) {
					LineNumberGen lng = (LineNumberGen) target;
					log.debug("LN: " + lng.getSourceLine());
				} else if (target instanceof LocalVariableGen) {
					LocalVariableGen lvg = (LocalVariableGen) target;
					log.debug("Name: " + lvg.getName());
				}
			}
		}
		log.debug("- - - - - - - -");
	}
	
	@Override
    public void visitTABLESWITCH(TABLESWITCH obj) {
    }

	@Override
	public void visitIfInstruction(IfInstruction obj) {
		log.debug("Instruction of 'If'");
	}

	@Override
	public void visitIF_ICMPLT(IF_ICMPLT obj) {
		log.debug(obj.toString(true) + " " + obj.getLength());
	}

	@Override
	public void visitIFLT(IFLT obj) {
		log.debug(obj.toString(true) + " " + obj.getLength());
	}

	@Override
	public void visitIFGE(IFGE obj) {
		log.debug(obj.toString(true) + " " + obj.getLength());
	}

	@Override
	public void visitIFNULL(IFNULL obj) {
		log.debug(obj.toString(true) + " " + obj.getLength());
	}

	@Override
	public void visitIF_ICMPGE(IF_ICMPGE obj) {
		log.debug(obj.toString(true) + " " + obj.getLength());
	}

	@Override
	public void visitIF_ICMPGT(IF_ICMPGT obj) {
		log.debug(obj.toString(true) + " " + obj.getLength());
	}

	@Override
	public void visitIF_ACMPNE(IF_ACMPNE obj) {
		log.debug(obj.toString(true) + " " + obj.getLength());
	}

	@Override
	public void visitIFEQ(IFEQ obj) {
		log.debug(obj.toString(true) + " " + obj.getLength());
	}

	@Override
	public void visitIFNONNULL(IFNONNULL obj) {
		log.debug(obj.toString(true) + " " + obj.getLength());
	}

	@Override
	public void visitIFNE(IFNE obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitIF_ICMPLE(IF_ICMPLE obj) {
		log.debug(obj.toString(true) + " " + obj.getLength());
	}

	@Override
	public void visitIFLE(IFLE obj) {
		log.debug(obj.toString(true) + " " + obj.getLength());
	}

	@Override
	public void visitIF_ACMPEQ(IF_ACMPEQ obj) {
		log.debug(obj.toString(true) + " " + obj.getLength());
	}

	@Override
	public void visitIF_ICMPEQ(IF_ICMPEQ obj) {
		log.debug(obj.toString(true) + " " + obj.getLength());
		
	}

}
