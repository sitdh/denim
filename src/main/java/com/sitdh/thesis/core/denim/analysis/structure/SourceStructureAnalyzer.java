package com.sitdh.thesis.core.denim.analysis.structure;

import com.sitdh.thesis.core.denim.analysis.SourceAnalyzer;
import com.sitdh.thesis.core.denim.database.entity.Project;

public interface SourceStructureAnalyzer<T, P> extends SourceAnalyzer<T> {
	
	public void assignedResource(P resource);

	public T analyze(Project project);
	
}
