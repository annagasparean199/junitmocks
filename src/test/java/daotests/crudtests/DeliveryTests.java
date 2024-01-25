package daotests.crudtests;

import org.example.dao.impl.DeliveryDao;
import org.example.entities.Delivery;
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
import static utils.Utils.microwaveDelivery;
import static utils.Utils.phoneDelivery;

@RunWith(MockitoJUnitRunner.class)
public class DeliveryTests {

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    private Session session;
    @Mock
    private Transaction transaction;
    @InjectMocks
    @Spy
    private DeliveryDao deliveryDao;

    @Test
    public void testFindByIdEntityFound() {
        doReturn(session).when(deliveryDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(microwaveDelivery).when(session).get(eq(Delivery.class), eq(1L));

        Delivery result = deliveryDao.findById(1L);
        assertNotNull(result);
        Assertions.assertEquals(microwaveDelivery, result);
    }

    @Test
    public void testFindAllEntities() {
        doReturn(session).when(deliveryDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        List<Delivery> deliveries = Arrays.asList(microwaveDelivery, phoneDelivery);
        Query<Delivery> query = mock(Query.class);
        doReturn(query).when(session).createQuery(anyString(), eq(Delivery.class));
        doReturn(deliveries).when(query).list();

        List<Delivery> result = deliveryDao.getAllEntities();

        assertNotNull(result);
        Assertions.assertEquals(deliveries, result);

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteDeliveryEntity() {
        doReturn(session).when(deliveryDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        deliveryDao.delete(microwaveDelivery);

        verify(session).delete(microwaveDelivery);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteById() {
        doReturn(session).when(deliveryDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(microwaveDelivery).when(session).get(eq(Delivery.class), eq(microwaveDelivery.getId()));

        deliveryDao.deleteById(microwaveDelivery.getId());

        verify(session).delete(microwaveDelivery);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testSaveDeliveryEntity() {
        doReturn(session).when(deliveryDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        deliveryDao.save(microwaveDelivery);

        verify(session).save(microwaveDelivery);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void updateDeliveryEntity() {
        doReturn(session).when(deliveryDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        deliveryDao.updateEntity(microwaveDelivery);

        verify(session).update(microwaveDelivery);
        verify(transaction).commit();
        verify(session).close();
    }
}