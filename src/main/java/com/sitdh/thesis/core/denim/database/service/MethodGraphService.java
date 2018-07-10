package com.sitdh.thesis.core.denim.database.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sitdh.thesis.core.denim.database.entity.MethodGraph;
import com.sitdh.thesis.core.denim.database.entity.MethodInformation;
import com.sitdh.thesis.core.denim.database.repository.MethodGraphRepository;

@Service
public class MethodGraphService {
	
	private MethodGraphRepository graphRepo;
	
	@Autowired
	public MethodGraphService(MethodGraphRepository graphRepo) {
		this.graphRepo = graphRepo;
	}
	
	public MethodGraph saveMethod() {
		
		
		return null;
	}

	public void updateGraph(int linenumber, 
			MethodInformation methodInfo, 
			String instructionInfo,
			List<String> nextLine,
			Optional<MethodInformation> invokedMethod) {
		
		this.graphRepo
			.findByMethodNameAndLineNumber(
					methodInfo,
					String.valueOf(linenumber)).ifPresentOrElse(mg -> {
						// Line exists
						MethodGraph methodGraph = new MethodGraph();
						methodGraph.setInstructions(mg.getInstructions() + "," + instructionInfo);
						List<String> line = this.mergeLineNumber(methodGraph.getLineNumber(), nextLine);
						methodGraph.setLineNumber(StringUtils.join(line, ","));
						
						this.graphRepo.save(methodGraph);
						
					}, () -> {
						// Insert new account
						MethodGraph methodGraph = new MethodGraph();
						methodGraph.setMethodName(methodInfo);
						methodGraph.setLineNumber(String.valueOf(linenumber));
						methodGraph.setNextLines(StringUtils.join(nextLine, ","));
						if (invokedMethod.isPresent()) {
							methodGraph.setInvoke(invokedMethod.get());
						}
						
						this.graphRepo.save(methodGraph);
					});
		
	}
	
	private List<String> separateLines(String line) {
		
		return Lists.newArrayList(
				StringUtils.split(line, ",")
				);
	}

	private List<String> mergeLineNumber(String line, List<String> nextLine) {
		List<String> lineNumber = this.separateLines(line);
		lineNumber.addAll(nextLine);
		
		return new ArrayList<>(Sets.newHashSet(lineNumber));
	}
}
