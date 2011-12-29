package org.lastfm.util;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class MethodWrapper<T> {
	private static final Log LOG = LogFactory.getLog(MethodWrapper.class);
	private final Method method;
	private final Class<?> returnType;

	public static MethodWrapperBuilderPhase1 forClass(String className) {
		return new MethodWrapperBuilder(className);
	}

	public static MethodWrapperBuilderPhase1 forClass(Class<?> clazz) {
		return new MethodWrapperBuilder(clazz);
	}

	private MethodWrapper(Method method, Class<?> returnType) {
		this.method = method;
		this.returnType = returnType;
	}

	private MethodWrapper<T> check() {
		return this;
	}

	public static Class<?> getClass(String string) {
		try {
			return Class.forName(string);
		} catch (ClassNotFoundException e) {
			LOG.error(e, e);
		}
		return null;
	}

	public T invoke(Object... param) {
		return new UsingMethodWrapper<T>(null).invoke(param);
	}

	public UsingMethodWrapper<T> using(Object object) {
		return new UsingMethodWrapper<T>(object);
	}

	public class UsingMethodWrapper<X> {
		private final Object object;

		private UsingMethodWrapper(Object object) {
			this.object = object;
		}

		@SuppressWarnings("unchecked")
		public X invoke(Object... param) {
			try {
				if (returnType == null) {
					method.invoke(object, param);
					return null;
				}
				return (X) method.invoke(object, param);
			} catch (Exception e) {
				LOG.error(e, new RuntimeException("The method has just exploded in your face", e));
				return null;
			}
		}

	}

	public static interface MethodWrapperBuilderPhase1 {
		MethodWrapperBuilderPhase2 method(String methodName);
	}

	public static interface MethodWrapperBuilderPhase2 {
		MethodWrapperBuilderPhase3 withParameters(Class<?>... parameters);
	}

	public static interface MethodWrapperBuilderPhase3 {
		<T> MethodWrapper<T> andReturnType(Class<T> returnType);
	}

	private static class MethodWrapperBuilder implements MethodWrapperBuilderPhase1, MethodWrapperBuilderPhase2,
			MethodWrapperBuilderPhase3 {
		private final Class<?> clazz;
		private String methodName;
		private Class<?>[] parameters;

		private MethodWrapperBuilder(Class<?> clazz) {
			this.clazz = clazz;
		}

		private MethodWrapperBuilder(String className) {
			try {
				clazz = Class.forName(className);
			} catch (ClassNotFoundException e) {
				LOG.error(e, e);
				throw new RuntimeException(className + " does not exist");
			}
		}

		public MethodWrapperBuilder method(String methodName) {
			this.methodName = methodName;
			return this;
		}

		public MethodWrapperBuilder withParameters(Class<?>... parameters) {
			this.parameters = parameters;
			return this;
		}

		public <T> MethodWrapper<T> andReturnType(Class<T> returnType) {
			Method method;
			try {
				method = clazz.getDeclaredMethod(methodName, parameters);
				if (method.getReturnType().equals(returnType)
						|| (returnType == null && method.getReturnType().equals(void.class))) {
					return new MethodWrapper<T>(method, returnType).check();
				}
			} catch (SecurityException e) {
				LOG.error(e, e);
			} catch (NoSuchMethodException e) {
				LOG.error(e, e);
			}
			throw new RuntimeException("Method " + methodName + " does not exist");
		}

	}
}
