package daotests.crudtests;

import org.example.dao.impl.UserDao;
import org.example.entities.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static utils.Utils.defaultUser;
import static utils.Utils.productMicrowave;
import static utils.Utils.secondaryUser;

@RunWith(MockitoJUnitRunner.class)
public class UserTests {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    Session session = mock(Session.class);
    @Mock
    Transaction transaction = mock(Transaction.class);

    @InjectMocks
    @Spy
    private UserDao userDao;

    @Test
    public void testFindByIdEntityFound() {
        doReturn(session).when(userDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(defaultUser).when(session).get(eq(User.class), eq(productMicrowave.getId()));

        User result = userDao.findById(defaultUser.getId());
        assertNotNull(result);
        Assertions.assertEquals(defaultUser, result);
    }

    @Test
    public void testFindAllEntities() {
        doReturn(session).when(userDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        List<User> users = Arrays.asList(defaultUser, secondaryUser);
        Query<User> query = mock(Query.class);
        doReturn(query).when(session).createQuery(anyString(), eq(User.class));
        doReturn(users).when(query).list();

        List<User> result = userDao.getAllEntities();

        assertNotNull(result);
        Assertions.assertEquals(users, result);

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteDeliveryEntity() {
        doReturn(session).when(userDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        userDao.delete(defaultUser);

        verify(session).delete(defaultUser);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteById() {
        doReturn(session).when(userDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(defaultUser).when(session).get(eq(User.class), eq(defaultUser.getId()));

        userDao.deleteById(defaultUser.getId());

        verify(session).delete(defaultUser);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testSaveDeliveryEntity() {
        doReturn(session).when(userDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        userDao.save(defaultUser);

        verify(session).save(defaultUser);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void updateDeliveryEntity() {
        doReturn(session).when(userDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        userDao.updateEntity(defaultUser);

        verify(session).update(defaultUser);
        verify(transaction).commit();
        verify(session).close();
    }
}