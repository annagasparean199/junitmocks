package DAOtests;

import org.example.DAO.DAOImpl.ProductDao;
import org.example.entity.Product;
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
import static utils.Utils.product2;

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
        doReturn(product1).when(session).get(eq(Product.class), eq(product1.getId()));

        Product result = productDao.findById(product1.getId(), Product.class);
        assertNotNull(result);
        Assertions.assertEquals(product1, result);
    }

    @Test
    public void testFindAllEntities() {
        doReturn(session).when(productDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        List<Product> products = Arrays.asList(product1, product2);
        Query<Product> query = mock(Query.class);
        doReturn(query).when(session).createQuery(anyString(), eq(Product.class));
        doReturn(products).when(query).list();

        List<Product> result = productDao.getAllEntities(Product.class);

        assertNotNull(result);
        Assertions.assertEquals(products, result);

        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteDeliveryEntity() {
        doReturn(session).when(productDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        productDao.delete(product1);

        verify(session).delete(product1);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testDeleteById() {
        doReturn(session).when(productDao).setUp();
        doReturn(transaction).when(session).beginTransaction();
        doReturn(product1).when(session).get(eq(Product.class), eq(product1.getId()));

        productDao.deleteById(product1.getId());

        verify(session).delete(product1);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void testSaveDeliveryEntity() {
        doReturn(session).when(productDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        productDao.save(product1);

        verify(session).save(product1);
        verify(transaction).commit();
        verify(session).close();
    }

    @Test
    public void updateDeliveryEntity(){
        doReturn(session).when(productDao).setUp();
        doReturn(transaction).when(session).beginTransaction();

        productDao.updateEntity(product1);

        verify(session).update(product1);
        verify(transaction).commit();
        verify(session).close();
    }
}
