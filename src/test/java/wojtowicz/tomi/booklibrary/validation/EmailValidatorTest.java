package wojtowicz.tomi.booklibrary.validation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidatorContext;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by tommy on 7/17/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class EmailValidatorTest {

    private EmailValidator emailValidator;

    @Before
    public void setUp() {
        emailValidator = new EmailValidator();
    }

    @Test
    public void testForValidEmail() {
        String email = "imie.nazwisko@gmail.com";
        assertTrue(emailValidator.validateEmail(email));
    }

    @Test
    public void testForInvalidEmailWithoutAtSymbol() {
        String email = "imie.nazwisko$gmail.com";
        assertFalse(emailValidator.validateEmail(email));
    }

    @Test
    public void testForInvalidEmailWithInvalidCharacter() {
        String email = "imie.nazw$isko@gmail.com";
        assertFalse(emailValidator.validateEmail(email));
    }

    @Test
    public void testForValidEmailWithPlus() {
        String email = "imie_nazwisko+costam@gmail.com";
        assertTrue(emailValidator.validateEmail(email));
    }

    @Test
    public void testInvalidEmailWithTooShortDomain() {
        String email = "imiecostam@onet.m";
        assertFalse(emailValidator.validateEmail(email));
    }


}
