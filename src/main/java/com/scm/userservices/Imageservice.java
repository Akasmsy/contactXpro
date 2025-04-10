package com.scm.userservices;

import org.springframework.web.multipart.MultipartFile;

public interface Imageservice {

	String uploadImage(MultipartFile image , String filename);
	
	String getUrlFromPublicId(String publicId);
}
