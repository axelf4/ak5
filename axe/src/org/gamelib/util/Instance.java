/**
 * 
 */
package org.gamelib.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author pwnedary
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ ElementType.FIELD, ElementType.LOCAL_VARIABLE })
public @interface Instance {
	Class<?> type() default Object.class;

	CreationalPattern pattern() default CreationalPattern.CONSTRUCTOR;

	public enum CreationalPattern {
		ABSTRACT_FACTORY, BUILDER, CONSTRUCTOR, FACTORY_METHOD, LAZY, MULTITON, OBJECT_POOL, PROTOTYPE, SINGLETON
	}
}
