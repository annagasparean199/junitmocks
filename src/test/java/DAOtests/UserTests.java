package DAOtests;

import org.example.DAO.DAOImpl.UserDao;
import org.example.entity.User;
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
import static utils.Utils.product1;
import static utils.Utils.user;
import static utils.Utils.user2;

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
        doReturn(user).when(session).get(eq(User.class), eq(product1.getId()));

        User result = userDao.findById(user.getId(), User.class);
        assertNotNull(result);
        Assertions.assertEquals(user, result);
    }

    @Test
    public void testFindAllEntities() {
        doReturn(session).when(userDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        List<User> users = Arrays.asList(user,user2);
        Query<User> query = mock(Query.class);
        doReturn(query).when(session).createQuery(anyString(), eq(User.class));
        doReturn(users).when(query).list();

        List<User> result = userDao.getAllEntities(User.class);

        assertNotNull(result);
        Assertions.assertEquals(users, result);

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteDeliveryEntity() {
        doReturn(session).when(userDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        userDao.delete(user);

        verify(session).delete(user);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteById() {
        doReturn(session).when(userDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(user).when(session).get(eq(User.class), eq(user.getId()));

        userDao.deleteById(user.getId());

        verify(session).delete(user);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testSaveDeliveryEntity() {
        doReturn(session).when(userDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        userDao.save(user);

        verify(session).save(user);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void updateDeliveryEntity(){
        doReturn(session).when(userDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        userDao.updateEntity(user);

        verify(session).update(user);
        verify(transaction).commit();
        verify(session).close();
    }
}