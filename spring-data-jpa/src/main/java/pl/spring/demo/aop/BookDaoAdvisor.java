package pl.spring.demo.aop;

import java.lang.reflect.Method;

import org.springframework.aop.MethodBeforeAdvice;
import org.springframework.beans.factory.annotation.Autowired;

import pl.spring.demo.annotation.GenerateId;
import pl.spring.demo.annotation.NullableId;
import pl.spring.demo.common.Sequence;
import pl.spring.demo.dao.impl.BookDaoImpl;
import pl.spring.demo.exception.BookNotNullIdException;
import pl.spring.demo.to.BookTo;
import pl.spring.demo.to.IdAware;

public class BookDaoAdvisor implements MethodBeforeAdvice {

	private Sequence sequence;

	@Override
	public void before(Method method, Object[] methodArgs, Object methodOwner) throws Throwable {

		if (hasAnnotation(method, methodOwner, NullableId.class)) {
			checkNotNullId(methodArgs[0]);
		}
		if (hasAnnotation(method, methodOwner, GenerateId.class)) {
			generateBookId(methodArgs[0], methodOwner);
		}

	}

	private void generateBookId(Object book, Object dao) {
		if (dao instanceof BookDaoImpl && book instanceof BookTo) {
			((BookTo) book).setId(sequence.nextValue(((BookDaoImpl) dao).getALL_BOOKS()) + 1);
		}
	}

	private void checkNotNullId(Object o) {
		if (o instanceof IdAware && ((IdAware) o).getId() != null) {
			throw new BookNotNullIdException();
		}
	}

	private boolean hasAnnotation(Method method, Object o, Class annotationClazz) throws NoSuchMethodException {
		boolean hasAnnotation = method.getAnnotation(annotationClazz) != null;

		if (!hasAnnotation && o != null) {
			hasAnnotation = o.getClass().getMethod(method.getName(), method.getParameterTypes())
					.getAnnotation(annotationClazz) != null;
		}
		return hasAnnotation;
	}

	public void setSequence(Sequence sequence) {
		this.sequence = sequence;
	}
}
