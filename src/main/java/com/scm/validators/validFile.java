package com.scm.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import jakarta.validation.Payload;

import jakarta.validation.Constraint;

@Documented
@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE, ElementType.METHOD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FileValidator.class)
public @interface validFile {

	String message()default "Invalid file";
	Class<?>[]groups()default{};
	Class<? extends Payload>[]payload()default{};
}
