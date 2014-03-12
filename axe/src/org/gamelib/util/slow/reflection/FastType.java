/**
 * 
 */
package org.gamelib.util.slow.reflection;

import com.sun.xml.internal.ws.org.objectweb.asm.ClassWriter;
import com.sun.xml.internal.ws.org.objectweb.asm.MethodVisitor;
import com.sun.xml.internal.ws.org.objectweb.asm.Opcodes;

/**
 * @author Axel
 */
public class FastType<T> extends NativeType<T> implements Type<T>, Opcodes {
	public FastType(Class<T> type) {
		super(type);
	}

	public FastType(T instance) {
		super(instance);
	}

	@Override
	public org.gamelib.util.slow.reflection.Type.Field field(String name)
			throws ReflectiveOperationException {
		// ClassLoader classLoader = getClass().getClassLoader();
		ASMClassLoader classLoader = new ASMClassLoader();

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
		String className = type.getName();
		String classNameInternal = className.replace('.', '/');
		String accessClassName = className + "FastField";
		if (accessClassName.startsWith("java.")) accessClassName = "gamelib." + accessClassName;
		String accessClassNameInternal = accessClassName.replace('.', '/');
		cw.visit(V1_6, ACC_PUBLIC + ACC_FINAL, accessClassNameInternal, null, "org/gamelib/util/slow/reflection/FastType$FastField", null);
		insertConstructor(cw);
		insertSetObject(cw, classNameInternal, type.getDeclaredField(name));
		insertGetObject(cw, classNameInternal, type.getDeclaredField(name));
		cw.visitEnd();

		try {
			return (Field) classLoader.defineClass(accessClassName, cw.toByteArray()).getConstructor(Object.class).newInstance(instance);
		} catch (InstantiationException | IllegalAccessException e) {
			return super.field(name); // slower fallback
		}
	}

	static private void insertConstructor(ClassWriter cw) {
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/Object;)V", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitVarInsn(ALOAD, 1);
		mv.visitMethodInsn(INVOKESPECIAL, "org/gamelib/util/slow/reflection/FastType$FastField", "<init>", "(Ljava/lang/Object;)V");
		mv.visitInsn(RETURN);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
	}

	private static void insertSetObject(ClassWriter cw, String classNameInternal, java.lang.reflect.Field field) {
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "set", "(Ljava/lang/Object;)V", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, "org/gamelib/util/slow/reflection/FastType$FastField", "instance", "Ljava/lang/Object;");
		mv.visitTypeInsn(CHECKCAST, classNameInternal);
		mv.visitVarInsn(ALOAD, 1); // arg
		com.sun.xml.internal.ws.org.objectweb.asm.Type fieldType = com.sun.xml.internal.ws.org.objectweb.asm.Type.getType(field.getType());
		switch (fieldType.getSort()) {
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.BOOLEAN:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Boolean");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Boolean", "booleanValue", "()Z");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.BYTE:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Byte");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Byte", "byteValue", "()B");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.CHAR:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Character");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Character", "charValue", "()C");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.SHORT:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Short");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Short", "shortValue", "()S");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.INT:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Integer");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Integer", "intValue", "()I");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.FLOAT:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Float");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Float", "floatValue", "()F");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.LONG:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Long");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Long", "longValue", "()J");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.DOUBLE:
			mv.visitTypeInsn(CHECKCAST, "java/lang/Double");
			mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Double", "doubleValue", "()D");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.ARRAY:
			mv.visitTypeInsn(CHECKCAST, fieldType.getDescriptor());
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.OBJECT:
			mv.visitTypeInsn(CHECKCAST, fieldType.getInternalName());
			break;
		}
		mv.visitFieldInsn(PUTFIELD, classNameInternal, field.getName(), fieldType.getDescriptor());
		mv.visitInsn(RETURN);
		insertThrowExceptionForFieldNotFound(mv);
		mv.visitMaxs(2, 1);
		mv.visitEnd();
	}

	private static void insertGetObject(ClassWriter cw, String classNameInternal, java.lang.reflect.Field field) {
		MethodVisitor mv = cw.visitMethod(ACC_PUBLIC, "get", "()Ljava/lang/Object;", null, null);
		mv.visitCode();
		mv.visitVarInsn(ALOAD, 0);
		mv.visitFieldInsn(GETFIELD, "org/gamelib/util/slow/reflection/FastType$FastField", "instance", "Ljava/lang/Object;");
		mv.visitTypeInsn(CHECKCAST, classNameInternal);
		mv.visitFieldInsn(GETFIELD, classNameInternal, field.getName(), com.sun.xml.internal.ws.org.objectweb.asm.Type.getDescriptor(field.getType()));
		com.sun.xml.internal.ws.org.objectweb.asm.Type fieldType = com.sun.xml.internal.ws.org.objectweb.asm.Type.getType(field.getType());
		switch (fieldType.getSort()) {
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.BOOLEAN:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.BYTE:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.CHAR:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.SHORT:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.INT:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.FLOAT:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.LONG:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
			break;
		case com.sun.xml.internal.ws.org.objectweb.asm.Type.DOUBLE:
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
			break;
		}
		mv.visitInsn(ARETURN);
		insertThrowExceptionForFieldNotFound(mv);
		mv.visitMaxs(1, 1);
		mv.visitEnd();
	}

	static private MethodVisitor insertThrowExceptionForFieldNotFound(MethodVisitor mv) {
		mv.visitTypeInsn(NEW, "java/lang/IllegalArgumentException");
		mv.visitInsn(DUP);
		mv.visitTypeInsn(NEW, "java/lang/StringBuilder");
		mv.visitInsn(DUP);
		mv.visitLdcInsn("Field not found: ");
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/StringBuilder", "<init>", "(Ljava/lang/String;)V");
		mv.visitVarInsn(ILOAD, 2);
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "append", "(I)Ljava/lang/StringBuilder;");
		mv.visitMethodInsn(INVOKEVIRTUAL, "java/lang/StringBuilder", "toString", "()Ljava/lang/String;");
		mv.visitMethodInsn(INVOKESPECIAL, "java/lang/IllegalArgumentException", "<init>", "(Ljava/lang/String;)V");
		mv.visitInsn(ATHROW);
		return mv;
	}

	public static abstract class FastField implements Field {
		protected Object instance;

		public FastField(Object instance) {
			this.instance = instance;
		}
	}
}
