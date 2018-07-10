package com.sitdh.thesis.core.denim.analysis.structure.instruction;

import java.util.Optional;

import org.apache.bcel.Const;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.Instruction;
import org.springframework.stereotype.Component;

@Component
public class InstructionParserFactory {
	
	public Optional<InstructionParser> factory(Instruction instruction, ConstantPoolGen contantPoolGen) {
		
		InstructionParser parser = null;
		
		if (Const.INVOKEDYNAMIC == instruction.getOpcode()) {
			parser = new InvokeDynamicInstructionParser(instruction, contantPoolGen);
			
		} else if (Const.INVOKESPECIAL == instruction.getOpcode()) {
			parser = new InvokeSpecialInstructionParser(instruction, contantPoolGen);
			
		} else if (Const.INVOKEVIRTUAL == instruction.getOpcode()) {
			parser = new InvokeVirtualInstructionParser(instruction, contantPoolGen);
			
		} else if (Const.INVOKEINTERFACE == instruction.getOpcode()) {
			parser = new InvokeInterfaceInstructionParser(instruction, contantPoolGen);
			
		}
		
		return Optional.ofNullable(parser);
	}

}
