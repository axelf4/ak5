/**
 * 
 */
package org.gamelib.util.slow.reflection;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author pwnedary
 */
public class Reflect<T> {
	private Class<T> type;
	private T instance;
	private Member current;

	private Reflect() {}

	public static <T> Reflect<T> on(Class<T> type) {
		Reflect<T> reflect = new Reflect<>();
		reflect.type = type;
		try {
			reflect.instance = type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return reflect;
	}

	public static Reflect<?> on(String name) throws ClassNotFoundException {
		return on(Class.forName(name));
	}

	@SuppressWarnings("unchecked")
	public static <T> Reflect<T> on(T instance) {
		Reflect<T> reflect = new Reflect<>();
		reflect.type = (Class<T>) instance.getClass();
		reflect.instance = instance;
		return reflect;
	}

	public Reflect<T> method(String name, Class<?>... parameters) {
		try {
			current = type.getDeclaredMethod(name, parameters);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return this;
	}

	public Object call(Object... parameters) {
		try {
			return ((Method) current).invoke(Modifier.isStatic(current.getModifiers()) ? null : instance, parameters);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Reflect<T> field(String name) {
		try {
			current = type.getDeclaredField(name);
			setAccessible((AccessibleObject) current, true);
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return this;
	}

	public Object get() {
		try {
			return ((Field) current).get(instance);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	public Reflect<T> set(Object value) {
		Field field = (Field) current;
		setFinal(field, false);
		try {
			field.set(instance, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return this;
	}

	public static void setAccessible(AccessibleObject object, boolean accessible) {
		object.setAccessible(accessible);
	}

	public static void setFinal(Member member, boolean setFinal) {
		try {
			Field modifiersField = member.getClass().getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			if (setFinal) modifiersField.setInt(member, member.getModifiers() & ~Modifier.FINAL);
			else modifiersField.setInt(member, member.getModifiers() | Modifier.FINAL);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
