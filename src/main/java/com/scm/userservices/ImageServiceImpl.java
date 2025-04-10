package com.scm.userservices;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.scm.helper.appConstaints;

@Service
public class ImageServiceImpl implements Imageservice{

	@Autowired
	private Cloudinary cloudinary;
	
	
	
	@Override
	public String uploadImage(MultipartFile contactImage, String filename) {
		
		try {
			byte[] data = new byte[contactImage.getInputStream().available()];
			             contactImage.getInputStream().read(data);
			             cloudinary.uploader()
			            .upload(data,ObjectUtils.asMap("public_id",filename));
			             return this.getUrlFromPublicId(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getUrlFromPublicId(String publicId) {
		
		return cloudinary.url()
				.transformation(new Transformation<>()
						.width(appConstaints.IMAGE_WIDTH)
						.height(appConstaints.IMAGE_HIGHT)
						.crop(appConstaints.IMAGE_CROP))
				.generate(publicId);
	}
  
	
}
