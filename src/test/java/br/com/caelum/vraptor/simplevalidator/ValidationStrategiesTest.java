package br.com.caelum.vraptor.simplevalidator;

import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.biggerThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.lessThan;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.matches;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.notEmpty;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.notEmptyNorNull;
import static br.com.caelum.vraptor.simplevalidator.ValidationStrategies.notNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ValidationStrategiesTest extends SimpleValidatorTestBase{

	protected static final String NUMBER_ERROR_KEY = "error.key";
	
	@Test
	public void should_verify_if_list_is_empty() {
		validator.validate(new ArrayList<String>(), notEmpty().key(NUMBER_ERROR_KEY));
		verify(validationStrategyHelper).addError(NUMBER_ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_string_is_null() {
		validator.validate(null, notEmptyNorNull().key(NUMBER_ERROR_KEY));
		verify(validationStrategyHelper).addError(NUMBER_ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_string_is_empty() {
		validator.validate("", notEmptyNorNull().key(NUMBER_ERROR_KEY));
		verify(validationStrategyHelper).addError(NUMBER_ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_something_is_null() {
		validator.validate(null, notNull().key(NUMBER_ERROR_KEY));
		verify(validationStrategyHelper).addError(NUMBER_ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_string_matches() {
		validator.validate("my_test", matches("my.test").key(NUMBER_ERROR_KEY));
		verify(validationStrategyHelper).addError(NUMBER_ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_number_is_bigger_than_other() {
		validator.validate(0l, biggerThan(1l).key(NUMBER_ERROR_KEY));
		verify(validationStrategyHelper).addError(NUMBER_ERROR_KEY);
	}
	
	@Test
	public void should_verify_if_number_is_less_than_other() {
		validator.validate(1l, lessThan(0l).key(NUMBER_ERROR_KEY));
		verify(validationStrategyHelper).addError(NUMBER_ERROR_KEY);
	}
	
	@Test
	public void should_not_add_error_if_string_matches_actually() {
		String matchingString = "my_test";
		validator.validate(matchingString, matches(matchingString).key(NUMBER_ERROR_KEY));
		verify(validationStrategyHelper, never()).addError(NUMBER_ERROR_KEY);
	}
	
	@Test(expected=IllegalStateException.class)
	public void should_throw_exception_if_key_is_not_present() {
		validator.validate("", notEmptyNorNull());
	}

}
