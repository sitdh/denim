package com.sitdh.thesis.core.denim.analysis.structure.instruction;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data @Builder
public class InvokedTargetEntity {
	
	private @NonNull String className;
	
	private @NonNull String methodName;

}
