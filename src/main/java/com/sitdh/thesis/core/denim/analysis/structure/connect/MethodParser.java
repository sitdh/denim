package com.sitdh.thesis.core.denim.analysis.structure.connect;

import java.util.List;
import java.util.Optional;

import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.LineNumberTable;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.verifier.structurals.ControlFlowGraph;
import org.apache.bcel.verifier.structurals.InstructionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.Lists;
import com.sitdh.thesis.core.denim.analysis.structure.instruction.InstructionParser;
import com.sitdh.thesis.core.denim.analysis.structure.instruction.InstructionParserFactory;
import com.sitdh.thesis.core.denim.analysis.structure.instruction.InvokedTargetEntity;
import com.sitdh.thesis.core.denim.database.entity.MethodGraph;
import com.sitdh.thesis.core.denim.database.entity.MethodInformation;
import com.sitdh.thesis.core.denim.database.service.MethodGraphService;
import com.sitdh.thesis.core.denim.database.service.MethodInformationService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MethodParser {
	
	private InstructionParserFactory parserFactory;
	
	private MethodInformationService methodInfoService;
	
	private MethodGraphService graphService;
	
	@Autowired
	public MethodParser(
			InstructionParserFactory parserFactory,
			MethodInformationService methodInfoService,
			MethodGraphService graphService) {
		
		this.graphService = graphService;
		this.parserFactory = parserFactory;
		this.methodInfoService = methodInfoService;
	}

	public void parse(Method method, JavaClass javaClass, ConstantPoolGen constantPoolGen) {
		log.debug("Accept method: " + method.getName());
		
		MethodGen methodGen = new MethodGen(
				method,
				javaClass.getClassName(),
				constantPoolGen);
		
		ControlFlowGraph cfg = new ControlFlowGraph(methodGen);
		
		LineNumberTable lnt = method.getLineNumberTable();
		
		InstructionContext[] context = cfg.getInstructionContexts();
		
		Optional<MethodInformation> methodInfoQuery = this.methodInfoService
				.getMethodFromClassAndMethod(
						javaClass.getClassName(),
						method.getName());
		
		if (methodInfoQuery.isPresent()) {
			MethodInformation methodInfo = methodInfoQuery.get(); 
			for (InstructionContext intContext: context) {
				MethodGraph graph = new MethodGraph();
				graph.setMethodName(methodInfo);
				
				InstructionHandle ih = intContext.getInstruction();
				int linenumber = lnt.getSourceLine(ih.getPosition());
				String text = ih.getInstruction().getName();
				
				Optional<InstructionParser> inParser = parserFactory.factory(
						ih.getInstruction(),
						constantPoolGen);
				
				Optional<MethodInformation> invokedMethod = Optional.empty();
				
				if (inParser.isPresent()) {
					InvokedTargetEntity parser = inParser.get().parse();
					MethodInformation invMethod = methodInfoService.getMethodFromClassAndMethod(
							parser.getClassName(),
							parser.getMethodName()).get();
					
					invokedMethod = Optional.ofNullable(invMethod);
				} else {
					// normal code it does not invoked another object
					
				}
				
				List<String> nextLine = this.next(intContext.getSuccessors(), lnt);
				
				graphService.updateGraph(linenumber, methodInfo, text, nextLine, invokedMethod);
			}
		}
	}
	
	public List<String> next(InstructionContext[] context, LineNumberTable lnt) {
		List<String> nextPosition = Lists.newArrayList();
		
		for(InstructionContext instruction : context) {
			int pos = instruction.getInstruction().getPosition();
			nextPosition.add(String.valueOf(lnt.getSourceLine(pos)));
		}
		
		return nextPosition;
	}

}
