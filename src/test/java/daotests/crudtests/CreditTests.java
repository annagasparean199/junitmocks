package daotests.crudtests;

import lombok.extern.log4j.Log4j;
import org.example.dao.impl.CreditDao;
import org.example.entities.Credit;
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
import static utils.Utils.microwaveSalesCredit;
import static utils.Utils.phoneSalesCredit;

@Log4j
@RunWith(MockitoJUnitRunner.class)
public class CreditTests {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    Session session;

    @Mock
    Transaction transaction;

    @Spy
    @InjectMocks
    CreditDao creditDao;

    @Test
    public void testFindByIdEntityFound() {
        doReturn(session).when(creditDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(microwaveSalesCredit).when(session).get(eq(Credit.class), eq(microwaveSalesCredit.getId()));

        Credit result = creditDao.findById(microwaveSalesCredit.getId());
        assertNotNull(result);
        Assertions.assertEquals(microwaveSalesCredit, result);
    }

    @Test
    public void testFindAllEntities() {
        doReturn(session).when(creditDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        List<Credit> credits = Arrays.asList(microwaveSalesCredit, phoneSalesCredit);
        Query<Credit> query = mock(Query.class);
        doReturn(query).when(session).createQuery(anyString(), eq(Credit.class));
        doReturn(credits).when(query).list();

        List<Credit> result = creditDao.getAllEntities();

        assertNotNull(result);
        Assertions.assertEquals(credits, result);

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteDeliveryEntity() {
        doReturn(session).when(creditDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        creditDao.delete(microwaveSalesCredit);

        verify(session).delete(microwaveSalesCredit);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteById() {
        doReturn(session).when(creditDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(microwaveSalesCredit).when(session).get(eq(Credit.class), eq(microwaveSalesCredit.getId()));

        creditDao.deleteById(microwaveSalesCredit.getId());

        verify(session).delete(microwaveSalesCredit);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testSaveDeliveryEntity() {
        doReturn(session).when(creditDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        creditDao.save(microwaveSalesCredit);

        verify(session).save(microwaveSalesCredit);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void updateDeliveryEntity() {
        doReturn(session).when(creditDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        creditDao.updateEntity(microwaveSalesCredit);

        verify(session).update(microwaveSalesCredit);
        verify(transaction).commit();
        verify(session).close();
    }
}