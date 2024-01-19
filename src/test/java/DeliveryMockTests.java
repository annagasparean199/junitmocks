//import org.example.DAO.DAOImpl.*;
//import org.example.DAO.GenericDao;
//import org.example.entity.Credit;
//import org.example.entity.Delivery;
//import org.example.entity.Product;
//import org.example.entity.Sales;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.Transaction;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.time.LocalDate;
//import java.time.ZoneId;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//public class DeliveryMockTests {
//
////    @Mock
////    HibernateUtility hibernateUtility;
////    @InjectMocks
////    private GenericDao<Product> genericDao;
////    @InjectMocks
////    private GenericDao<Sales> genericyDao;
////    @InjectMocks
////    private GenericDao<Credit> genericyyDao;
////    @InjectMocks
////    private GenericDao<Delivery> generiyyycDao;
//
//    @Before
//    public void setUp() {
//        SessionFactory mockedSessionFactory = Mockito.mock(SessionFactory.class);
//        Session mockedSession = Mockito.mock(Session.class);
//        Transaction mockedTransaction = Mockito.mock(Transaction.class);
//        Mockito.when(HibernateUtility.getSessionFactory().openSession()).thenReturn(mockedSession);
//        Mockito.when(mockedSession.beginTransaction()).thenReturn(mockedTransaction);
//    }
//    @Mock
//    private ProductDao productDaoMock;
//
//    @Mock
//    private SalesDao salesDaoMock;
//
//    @Mock
//    private CreditDao creditDaoMock;
//
//    @InjectMocks
//    private DeliveryDao deliveryDao;
//
//    @Test
//    public void testGetStoreLossAmountForMonth() {
//
//        Delivery delivery = new Delivery();
//        delivery.setDeliveryDate(new Date());
//        Product product = new Product();
//        product.setPrice(10.0);
//        delivery.setProduct(product);
//        delivery.setQuantity(5);
//
//        when(deliveryDao.getAllEntities(Delivery.class)).thenReturn(List.of(delivery));
//
//        double result = deliveryDao.getStoreLossAmountForMonth(1); // Assuming January as the target month
//
//        Assertions.assertEquals(50.0, result);
//    }
//}