package drdm.school.pia.manager;

import drdm.school.pia.dao.RoleDao;
import drdm.school.pia.dao.UserDao;

import drdm.school.pia.domain.Role;
import drdm.school.pia.domain.User;
import drdm.school.pia.manager.implementation.DefaultAccountManager;
import drdm.school.pia.manager.implementation.DefaultCardManager;
import drdm.school.pia.manager.implementation.DefaultRoleManager;
import drdm.school.pia.manager.implementation.DefaultUserManager;
import drdm.school.pia.utils.Encoder;
import drdm.school.pia.utils.StringGenerator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DefaultUserManagerTest {

    @Mock
    private Encoder encoder;
    @Mock
    private UserDao userDao;
    @Mock
    private StringGenerator stringGenerator;
    @Mock
    private DefaultRoleManager roleManager;
    @Mock
    private DefaultAccountManager accountManager;
    @Mock
    private DefaultCardManager cardManager;
    @Mock
    private User user;

    @InjectMocks
    private DefaultUserManager userManager;

    public DefaultUserManagerTest() {
    }

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUserRegistration() throws Exception {
        final String username = "username";
        final String hashed = "Hash";
        final String password = "Password";
        final String role = "Role";
        final String firstname = "Name";
        final String lastname = "Surname";
        final String email = "Email";
        final String address = "Address";
        final String city = "City";
        final String zip = "ZipCode";
        final String birthid = "BirthId";
        final String gender = "Gender";

        // Need to put actual password here, because of validation of input, and hashing this password afterwards.
        User src = new User(password, role, firstname, lastname, email, address, city, zip, birthid, gender);

        when(userDao.save(src)).thenReturn(src);
        when(userDao.findByUsername(username)).thenReturn(null);
        when(encoder.encode(password)).thenReturn(hashed);
        when(stringGenerator.generate(8)).thenReturn(username);

        userManager.register(src);

        verify(userDao, times(1)).save(any(User.class));
        verify(userDao, times(1)).findByUsername(username);
        verify(encoder, times(1)).encode(password);
        verify(stringGenerator, times(1)).generate(8);
        assertEquals(hashed, src.getPassword());
    }

    @Test
    public void testUserAuth() throws Exception {
        final String hashed = "Hash";
        final String username = "Username";
        final String password = "Password";
        final String role = "Role";
        final String firstname = "Name";
        final String lastname = "Surname";
        final String email = "Email";
        final String address = "Address";
        final String city = "City";
        final String zip = "ZipCode";
        final String birthid = "BirthId";
        final String gender = "Gender";

        User src = new User(hashed, role, firstname, lastname, email, address, city, zip, birthid, gender);

        when(userDao.findByUsername(username)).thenReturn(src);
        when (encoder.validate(password, hashed)).thenReturn(true);

        assertTrue(userManager.authenticate(username,password));

        verify(userDao, times(1)).findByUsername(username);
        verify(encoder,times(1)).validate(password, hashed);
    }

    @Test
    public void testUserAuthFailValidation() throws Exception {
        final String hashed = "Hash";
        final String username = "Username";
        final String password = "Password";
        final String role = "Role";
        final String firstname = "Name";
        final String lastname = "Surname";
        final String email = "Email";
        final String address = "Address";
        final String city = "City";
        final String zip = "ZipCode";
        final String birthid = "BirthId";
        final String gender = "Gender";

        User src = new User(hashed, role, firstname, lastname, email, address, city, zip, birthid, gender);

        when(userDao.findByUsername(username)).thenReturn(src);
        when (encoder.validate(password, hashed)).thenReturn(false);

        assertFalse(userManager.authenticate(username,password));

        verify(userDao, times(1)).findByUsername(username);
        verify(encoder,times(1)).validate(password, hashed);
    }

    @Test
    public void testUserAuthFailFindByUsername() throws Exception {
        final String hashed = "Hash";
        final String username = "Username";
        final String password = "Password";

        // No need to create a user because we still return null.
        when(userDao.findByUsername(username)).thenReturn(null);
        when (encoder.validate(password, hashed)).thenReturn(true);

        assertFalse(userManager.authenticate(username,password));

        verify(userDao, times(1)).findByUsername(username);
        verify(encoder,times(0)).validate(password, hashed);
    }

}
