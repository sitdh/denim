package com.sitdh.thesis.core.denim.analysis.structure.connect;

import org.apache.bcel.Const;
import org.apache.bcel.classfile.ConstantPool;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LineNumber;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.IFGE;
import org.apache.bcel.generic.IFGT;
import org.apache.bcel.generic.IFLT;
import org.apache.bcel.generic.IF_ICMPGE;
import org.apache.bcel.generic.IF_ICMPGT;
import org.apache.bcel.generic.IF_ICMPNE;
import org.apache.bcel.generic.INVOKEDYNAMIC;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.IfInstruction;
import org.apache.bcel.generic.Instruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InvokeInstruction;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.verifier.structurals.ControlFlowGraph;
import org.apache.bcel.verifier.structurals.InstructionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sitdh.thesis.core.denim.analysis.structure.instruction.conditions.SourceConditions;
import com.sitdh.thesis.core.denim.analysis.structure.instruction.invokes.SourceInvoke;
import com.sitdh.thesis.core.denim.database.entity.FileInformation;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component("methodConnectivity")
public class MethodConnectivity implements Connectivity {
	
	private FileInformation fileInfo;
	
	private MethodParser methodParser;
	
	private SourceConditions sourceCondition;
	
	private SourceInvoke sourceInvoke;
	
	@Autowired
	public MethodConnectivity(MethodParser methodParser,
			SourceConditions sourceCondition,
			SourceInvoke sourceInvoke) {
		
		this.methodParser = methodParser;
		this.sourceCondition = sourceCondition;
		this.sourceInvoke = sourceInvoke;
	}
	
	@Override
	public void accept(FileInformation fileInfo) {
		log.debug("Accept file: " + fileInfo.getClassName());
		this.fileInfo = fileInfo;
		this.loadClass(fileInfo.getLocation()).ifPresent(this::processClass);
	}
	
	private void processClass(JavaClass javaClass) {
		Method[] methods = javaClass.getMethods();
		ConstantPool constantPool = javaClass.getConstantPool();
		ConstantPoolGen constantPoolGen = new ConstantPoolGen(constantPool);
		ControlFlowGraph cfg = null;
		sourceCondition.setCpg(constantPoolGen);
		sourceInvoke.setCpg(constantPoolGen);
		
		for (Method method : methods) {
			if (null == method || "<init>".equals(method.getName())) continue;
			
			this.methodParser.parse(method, javaClass, constantPoolGen);
			MethodGen methodGen = new MethodGen(
					method,
					this.fileInfo.getClassName(),
					constantPoolGen);
			
			cfg = new ControlFlowGraph(methodGen);
			
			LineNumberTable lnt = method.getLineNumberTable();
			LineNumber[] lines = lnt.getLineNumberTable();
			
			InstructionContext[] instructionContext = cfg.getInstructionContexts();
			log.debug(constantPoolGen.toString());
			for (InstructionContext instruction : instructionContext) {
				InstructionHandle ih = instruction.getInstruction();
				Instruction in = ih.getInstruction();
				
				int lineNumber = lnt.getSourceLine(ih.getPosition());
				
				short opcode = instruction.getInstruction().getInstruction().getOpcode();
				
				Instruction inct = instruction.getInstruction().getInstruction();
				
				if (inct instanceof InvokeInstruction) {
					inct.accept(sourceInvoke);
				} else if (inct instanceof IfInstruction) {
					inct.accept(sourceCondition);
				}
				
			}
			
		}
	}

}
