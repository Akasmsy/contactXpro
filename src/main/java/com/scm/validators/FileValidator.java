package com.scm.validators;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FileValidator implements ConstraintValidator<validFile, MultipartFile>{

	public static final long  IMAGE_MAX_SIZE=1024*1024*2;//10mb
	
	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		
		if(file!=null&&file.isEmpty())
		{
//			context.disableDefaultConstraintViolation();
//			context.buildConstraintViolationWithTemplate("File Cannot be Empty").addConstraintViolation();
//			
			return true;
		}
		if(file.getSize()>IMAGE_MAX_SIZE)
		{
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate("File size should be less than 10 MB").addConstraintViolation();
			return false;
		}
		return  true;
	}

}
