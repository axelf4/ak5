/**
 * 
 */
package org.gamelib.util.slow;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.gamelib.util.slow.reflection.Reflection;

/**
 * An alternative to {@link java.lang.Enum}, for representing enumerations.
 * @see java.lang.Enum
 * @author pwnedary
 */
public class Enum<E extends Enum<E>> implements Comparable<E> {

	private static boolean constructor = false;
	/** The name of this enum constant, as declared in the enum declaration. Most programmers should use the {@link #toString} method rather than accessing this field. */
	private String name;
	/** The ordinal of this enumeration constant (its position in the enum declaration, where the initial constant is assigned an ordinal of zero). Most programmers will have no use for this field. It is designed for use by sophisticated enum-based data structures, such as {@link java.util.EnumSet} and {@link java.util.EnumMap}. */
	private int ordinal = -1;

	public Enum() {
		if (constructor) return;
		@SuppressWarnings("rawtypes") Class<? extends Enum> type = this.getClass();
		if (type.getSuperclass() != Enum.class) throw new Error("Enums cannot be extended.");

		constructor = true;
		initiate(type);
		constructor = false;
	}

	protected static void initiate(@SuppressWarnings("rawtypes") Class<? extends Enum> type) {
		for (Field field : getFields(type)) {
			try {
				if (field.get(null) == null) field.set(null, type.newInstance());
			} catch (ReflectiveOperationException e) {
				e.printStackTrace();
			}
			Reflection.setFieldFinal(field, true);
		}
	}

	/**
	 * The name of this enum constant, as declared in the enum declaration. Most programmers should use the {@link #toString} method rather than accessing this field.
	 */
	public final String name() {
		if (name == null) {
			for (Field field : getFields(getClass()))
				try {
					if (field.get(null).equals(this)) this.name = field.getName();
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
		}
		return name;
	}

	/**
	 * Returns the ordinal of this enumeration constant (its position in its enum declaration, where the initial constant is assigned an ordinal of zero). Most programmers will have no use for this method. It is designed for use by sophisticated enum-based data structures, such as {@link java.util.EnumSet} and {@link java.util.EnumMap}.
	 * @return the ordinal of this enumeration constant
	 */
	public final int ordinal() {
		if (this.ordinal == -1) {
			E[] values = values();
			for (int i = 0; i < values.length; i++) {
				E t = values[i];
				if (this.equals(t)) this.ordinal = i;
			}
			if (this.ordinal == -1) throw new RuntimeException();
		}
		return this.ordinal;
	}

	@SuppressWarnings("unchecked")
	public final E[] values() {
		List<E> list = new ArrayList<>();
		for (Field field : getFields(getClass()))
			try {
				list.add((E) field.get(null));
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		return (E[]) list.toArray();
	}

	/** {@inheritDoc} */
	@Override
	public String toString() {
		return name();
	}

	/**
	 * Throws CloneNotSupportedException. This guarantees that enums are never cloned, which is necessary to preserve their "singleton" status.
	 * @return (never returns)
	 */
	@Override
	protected final Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}

	/**
	 * Compares this enum with the specified object for order. Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object. Enum constants are only comparable to other enum constants of the same enum type. The natural order implemented by this method is the order in which the constants are declared.
	 */
	@Override
	public final int compareTo(E o) {
		Enum<E> other = o;
		Enum<E> self = this;
		if (self.getClass() != other.getClass()) throw new ClassCastException();
		return self.ordinal - other.ordinal;
	}

	/**
	 * Returns the enum constant of the specified enum type with the specified name. The name must match exactly an identifier used to declare an enum constant in this type. (Extraneous whitespace characters are not permitted.)
	 * <p>
	 * Note that for a particular enum type {@code T}, the implicitly declared {@code public static T valueOf(String)} method on that enum may be used instead of this method to map from a name to the corresponding enum constant. All the constants of an enum type can be obtained by calling the implicit {@code public static T[] values()} method of that type.
	 * @param <T> The enum type whose constant is to be returned
	 * @param enumType the {@code Class} object of the enum type from which to return a constant
	 * @param name the name of the constant to return
	 * @return the enum constant of the specified enum type with the specified name
	 * @throws IllegalArgumentException if the specified enum type has no constant with the specified name, or the specified class object does not represent an enum type
	 * @throws NullPointerException if {@code enumType} or {@code name} is null
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Enum<T>> T valueOf(Class<T> enumType, String name) {
		List<Field> fields = getFields(enumType);
		for (Field field : fields) {
			if (field.getName() == name) try {
				return (T) field.get(null);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * enum classes cannot have finalize methods.
	 */
	protected final void finalize() {}

	private static final List<Field> getFields(@SuppressWarnings("rawtypes") Class<? extends Enum> type) {
		List<Field> list = new ArrayList<>(); // ArrayList because toArray
		for (Field field : type.getDeclaredFields())
			if ((field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC && (field.getModifiers() & Modifier.STATIC) == Modifier.STATIC && field.getType() == type) list.add(field);
		return list;
	}

	public static <E extends java.lang.Enum<E>> E addEnum(Class<E> enumClass, String name, Class<?>[] paramTypes, Object[] paramValues) {
		Field valuesField = null;
		for (Field field : enumClass.getDeclaredFields()) {
			if (field.getName().equals("ENUM$VALUES") || field.getName() == "$VALUES") {
				valuesField = field;
				break;
			}
		}
		if (valuesField == null) throw new RuntimeException();
		valuesField.setAccessible(true);
		try {
			List<E> values = new ArrayList<>(Arrays.asList((E[]) valuesField.get(enumClass)));
			
			// E newValue = (E) makeEnum(enumClass, name, values.size(), paramTypes, paramValues);
			Object[] initargs = new Object[paramValues.length + 2];
			initargs[0] = name;
			initargs[1] = Integer.valueOf(values.size());
			System.arraycopy(paramValues, 0, initargs, 2, paramValues.length);
			E newValue = Reflection.newInstance(enumClass, paramTypes, initargs);
			
			values.add(newValue);
			for (Field field : Class.class.getDeclaredFields()) {
				if (field.getName().contains("enumConstantDirectory") || field.getName().contains("enumConstants")) {
					Reflection.setFieldValue(field, enumClass, null);
				}
			}
			return newValue;
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private static <E extends java.lang.Enum<E>> E makeEnum(Class<E> enumClass, String value, int ordinal, Class<?>[] paramTypes, Object[] paramValues)
			throws Exception {
		Object[] initargs = new Object[paramValues.length + 2];
		initargs[0] = value;
		initargs[1] = Integer.valueOf(ordinal);
		System.arraycopy(paramValues, 0, initargs, 2, paramValues.length);
		return Reflection.newInstance(enumClass, paramTypes, initargs);
	}

}
