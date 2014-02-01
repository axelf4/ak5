/**
 * 
 */
package org.gamelib.util.slow;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Member;

/**
 * TODO better name
 * 
 * @author pwnedary
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ProcessedBy {
	Class<? extends AnnotationProcessor> value();

	public static interface AnnotationProcessor {
		void process(Member member);
	}

	public static class AnnotationsProcessing {
		public static void process(Class<?> clazz) {

		}
	}
}
