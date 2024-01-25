package daotests.crudtests;

import org.example.dao.impl.SalesDao;
import org.example.entities.Sales;
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
import static utils.Utils.microwaveSales;
import static utils.Utils.phoneSales;

@RunWith(MockitoJUnitRunner.class)
public class SalesTests {

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
    private SalesDao salesDao;

    @Test
    public void testFindByIdEntityFound() {
        doReturn(session).when(salesDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(microwaveSales).when(session).get(eq(Sales.class), eq(microwaveSales.getId()));
        Sales result = salesDao.findById(microwaveSales.getId());
        assertNotNull(result);
        Assertions.assertEquals(microwaveSales, result);
    }

    @Test
    public void testFindAllEntities() {
        Query<Sales> query = mock(Query.class);
        doReturn(session).when(salesDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        List<Sales> sales = Arrays.asList(microwaveSales, phoneSales);
        doReturn(query).when(session).createQuery(anyString(), eq(Sales.class));
        doReturn(sales).when(query).list();
        List<Sales> result = salesDao.getAllEntities();
        assertNotNull(result);
        Assertions.assertEquals(sales, result);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteDeliveryEntity() {
        doReturn(session).when(salesDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        salesDao.delete(microwaveSales);
        verify(session).delete(microwaveSales);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteById() {
        doReturn(session).when(salesDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(microwaveSales).when(session).get(eq(Sales.class), eq(microwaveSales.getId()));
        salesDao.deleteById(microwaveSales.getId());
        verify(session).delete(microwaveSales);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testSaveDeliveryEntity() {
        doReturn(session).when(salesDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        salesDao.save(microwaveSales);
        verify(session).save(microwaveSales);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void updateDeliveryEntity() {
        doReturn(session).when(salesDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        salesDao.updateEntity(microwaveSales);
        verify(session).update(microwaveSales);
        verify(transaction).commit();
        verify(session).close();
    }
}