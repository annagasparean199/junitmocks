package daotests.crudtests;

import org.example.dao.impl.ProductDao;
import org.example.entities.Product;
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
import static utils.Utils.productMicrowave;
import static utils.Utils.productPhone;

@RunWith(MockitoJUnitRunner.class)
public class ProductTests {

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
    private ProductDao productDao;

    @Test
    public void testFindByIdEntityFound() {
        doReturn(session).when(productDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(productMicrowave).when(session).get(eq(Product.class), eq(productMicrowave.getId()));

        Product result = productDao.findById(productMicrowave.getId());
        assertNotNull(result);
        Assertions.assertEquals(productMicrowave, result);
    }

    @Test
    public void testFindAllEntities() {
        doReturn(session).when(productDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        List<Product> products = Arrays.asList(productMicrowave, productPhone);
        Query<Product> query = mock(Query.class);
        doReturn(query).when(session).createQuery(anyString(), eq(Product.class));
        doReturn(products).when(query).list();

        List<Product> result = productDao.getAllEntities();

        assertNotNull(result);
        Assertions.assertEquals(products, result);

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteDeliveryEntity() {
        doReturn(session).when(productDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        productDao.delete(productMicrowave);

        verify(session).delete(productMicrowave);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteById() {
        doReturn(session).when(productDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(productMicrowave).when(session).get(eq(Product.class), eq(productMicrowave.getId()));

        productDao.deleteById(productMicrowave.getId());

        verify(session).delete(productMicrowave);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testSaveDeliveryEntity() {
        doReturn(session).when(productDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        productDao.save(productMicrowave);

        verify(session).save(productMicrowave);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void updateDeliveryEntity() {
        doReturn(session).when(productDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        productDao.updateEntity(productMicrowave);

        verify(session).update(productMicrowave);
        verify(transaction).commit();
        verify(session).close();
    }
}