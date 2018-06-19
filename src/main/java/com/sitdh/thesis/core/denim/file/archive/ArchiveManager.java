package com.sitdh.thesis.core.denim.file.archive;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@PropertySource("classpath:/graph.properties")
public class ArchiveManager {
		
	private File workspace;
	
	private String storageLocation;
	
	public ArchiveManager(@Value("${denim.project.workspace}") String storageLocation) {
		
		this.storageLocation = storageLocation;
		
		try {
			workspace = new File(storageLocation);
			FileUtils.forceMkdir(workspace);
			log.debug("Create workspace: " + storageLocation);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			workspace = null;
			log.debug("Failure to crate workspace");
		}
	}
	
	public Optional<String> extract(MultipartFile mf, String projectname) {
		Optional<String> location = Optional.ofNullable(null);
		
		try {
			location = this.prepareTempFile(mf.getInputStream(), mf.getOriginalFilename());
			if (location.isPresent()) {
				String tempfile = location.get();
				location = this.extract(tempfile, projectname);
				this.cleanup(tempfile);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return location;
	}
	
	public Optional<String> extract(InputStream input, String filename) {
		Optional<String> location = this.prepareTempFile(input, filename);
		
		if (location.isPresent()) {
			String tempfile = location.get();
			location = this.extract(tempfile, filename);
			this.cleanup(tempfile);
		}
		
		return location;
	}
	
	public Optional<String> extract(String location, String filename) {
		
		String destLocation = null;
		
		try {
			String tempDir = FileUtils.getTempDirectoryPath() + filename;
			FileUtils.forceMkdir(new File(tempDir));

			log.debug("Temp dir was created: " + tempDir);

			ZipFile zipFile = new ZipFile(location);
			ZipInputStream zis = new ZipInputStream(new FileInputStream(location));
			ZipEntry zipEntry = null;
			
			while(null != (zipEntry = (ZipEntry) zis.getNextEntry())) {
				String name = zipEntry.getName();
				if (name.endsWith("/")) {
					new File(name).mkdirs();
					continue;
				}
				
				File f = new File(tempDir + File.separator + name);
				FileUtils.forceMkdirParent(f);
				
				InputStream is = zipFile.getInputStream(zipEntry);
				
				FileOutputStream fos = new FileOutputStream(f);
				byte[] bytes = new byte[1024];
				int length;
				while ((length = is.read(bytes)) >= 0) {
					fos.write(bytes, 0, length);
				}
				
				is.close();
				fos.close();
				
			}
			
			zipFile.close();
			zis.close();
			
			File tempFileLocation = new File(tempDir);
			File destFileLocation = new File(this.storageLocation + File.separator + this.uniqueName(filename));
			
			log.debug(destFileLocation.getPath());
			
			FileUtils.moveToDirectory(tempFileLocation, destFileLocation, true);
			destLocation = destFileLocation.getAbsolutePath() + File.separator + filename;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Optional.ofNullable(destLocation);
	}
	
	private String uniqueName(String originFileName) {
		return originFileName + "-" + UUID.randomUUID().toString();
	}
	
	private Optional<String> prepareTempFile(InputStream inputstream, String filename) {
		
		String tempfilelocation = FileUtils.getTempDirectory() + this.uniqueName(filename) + ".zip";
		
		try {
			File tempfile = new File(tempfilelocation);
			OutputStream os = new FileOutputStream(tempfile);
			
			IOUtils.copy(inputstream, os);
			
			log.info("Prepare tempfile: " + tempfilelocation);
			
			os.close();
			inputstream.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			tempfilelocation = null;
			log.debug("Temp file failure");
		}
		
		return Optional.ofNullable(tempfilelocation);
	}
	
	private void cleanup(String tempfile) {
		try {
			FileUtils.forceDelete(new File(tempfile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}