//package org.example.entity;
//
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//import org.hibernate.annotations.Formula;
//
//import javax.persistence.*;
//
//@Data
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "Discount")
//public class Discount {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
//    private Long id;
//
//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
//
////    @Column(name = "percentage")
////    private Double percentage;
//
//    @Column(name = "user_id", insertable = false, updatable = false)
//    @Formula("(CASE " +
//            "WHEN company = 'Maximum' THEN 5 " +
//            "ELSE 0 END) + " +
//            "(CASE " +
//            "WHEN (SELECT SUM(s.paid_amount) FROM Sales s WHERE s.user_id = id) >= 10000 THEN 5 " +
//            "ELSE 0 END) + " +
//            "(CASE " +
//            "WHEN legal_entity = true THEN 3 " +
//            "ELSE 0 END)")
//    private Double percentage;
//}