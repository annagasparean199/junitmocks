//package org.example.hibernate;
//
//import org.example.entity.Delivery;
//import org.example.entity.Discount;
//import org.example.entity.Sales;
//import org.example.entity.User;
//import org.example.DAO.GenericDao;
//import org.hibernate.Session;
//import org.junit.jupiter.api.Test;
//
//import java.util.List;
//import java.util.Objects;
//
//public class DiscountDao implements GenericDao<Discount> {
//    private static DiscountDao instance;
//
//    public static synchronized DiscountDao getDiscountDaoInstance() {
//        if (instance == null) {
//            instance = new DiscountDao();
//        }
//        return instance;
//    }
//
//    @Override
//    public Discount findById(Long id, Class<Discount> entityClass) {
//        return GenericDao.super.findById(id, entityClass);
//    }
//
//    @Override
//    public List<Discount> getAllEntities(Class<Discount> entityClass) {
//        return GenericDao.super.getAllEntities(entityClass);
//    }
//
//    @Override
//    public void delete(Discount entity) {
//        GenericDao.super.delete(entity);
//    }
//
//    @Override
//    public Class<Discount> getEntityClass() {
//        return Discount.class;
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        GenericDao.super.deleteById(id);
//    }
//
//    @Override
//    public void save(Discount entity) {
//        GenericDao.super.save(entity);
//    }
//
//    @Override
//    public void updateEntity(Discount entity) {
//        GenericDao.super.updateEntity(entity);
//    }
//
//    //    public double getCalculatedPercentageForUser(Long userId) {
////        User user = UserDao.getUserDaoInstance().findById(userId, User.class);
////        double percentage = 0;
////        double sumOfPurchases = 0;
////        if(user.getCompany().equals("Maximum")){
////            percentage += 5;
////        }
////        for(Sales sale : SalesDao.getSalesDaoInstance().getAllEntities(Sales.class)){
////            if(sale.getUser().getId().equals(userId)){
////                sumOfPurchases+=sale.getPaidAmount().doubleValue();
////            }
////        }
////        if(sumOfPurchases>=10000){
////            percentage+=5;
////        }
////        if(user.getLegalEntity()){
////            percentage+=3;
////        }
////        return percentage;
////    }
//
//
//    //    public double getPercentageByUserId(Long userId) {
////        User user = UserDao.getUserDaoInstance().findById(userId, User.class);
////        List<Discount> discounts = DiscountDao.getDiscountDaoInstance().getAllEntities(Discount.class);
////        for (Discount discount : discounts) {
////            if (Objects.equals(discount.getUser().getId(), userId)) {
////                return discount.getPercentage();
////            }
////        }
////        return 0;
////    }
//
//    public double getPercentageByUserId(Long userId) {
//        return DiscountDao.getDiscountDaoInstance()
//                .getAllEntities(Discount.class)
//                .stream()
//                .filter(discount -> Objects.equals(discount.getUser().getId(), userId))
//                .findFirst()
//                .map(Discount::getPercentage)
//                .orElse(0.0);
//    }
//}