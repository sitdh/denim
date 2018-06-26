package com.sitdh.thesis.core.denim.analysis.collector;

import com.sitdh.thesis.core.denim.database.entity.FileInformation;

public interface MethodCollection {
	
	public void accept(FileInformation fileInfo);

}
