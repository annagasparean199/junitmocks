//package org.example;
//
//
//
//import org.hibernate.boot.Metadata;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.cfg.Configuration;
//import org.hibernate.tool.hbm2ddl.SchemaExport;
//import org.hibernate.tool.schema.TargetType;
//
//import java.util.EnumSet;
//
//public class SchemaGeneration {
//
////    public static void main(String[] args) {
////        Configuration configuration1 = new Configuration();
////        configuration1.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
////        MetadataSources configuration = new MetadataSources();
////
////        configuration.addAnnotatedClass(org.example.entity.Product.class);
////        configuration.addAnnotatedClass(org.example.entity.Credit.class);
////        configuration.addAnnotatedClass(org.example.entity.Sales.class);
////        configuration.addAnnotatedClass(org.example.entity.User.class);
////        configuration.addAnnotatedClass(org.example.entity.Credit.class);
////        configuration.addAnnotatedClass(org.example.entity.Credit.class);
////
////        SchemaExport schemaExport = new SchemaExport(configuration);
////        schemaExport.create(true, true);
////    }
////}
////import org.hibernate.boot.Metadata;
////import org.hibernate.boot.MetadataSources;
////import org.hibernate.boot.registry.StandardServiceRegistry;
////import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
////import org.hibernate.tool.hbm2ddl.SchemaExport;
////import org.hibernate.tool.schema.TargetType;
////
////import java.util.EnumSet;
////
////public class SchemaGeneration {
////
//    public static void main(String[] args) {
//       // StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
//
//        MetadataSources metadataSources = new MetadataSources();
//
//        Configuration configuration = new Configuration();
//        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
//
//        metadataSources.addAnnotatedClass(org.example.entity.Product.class);
//        metadataSources.addAnnotatedClass(org.example.entity.Credit.class);
//        metadataSources.addAnnotatedClass(org.example.entity.Sales.class);
//        metadataSources.addAnnotatedClass(org.example.entity.User.class);
//        metadataSources.addAnnotatedClass(org.example.entity.Credit.class);
//        metadataSources.addAnnotatedClass(org.example.entity.Credit.class);
//
//        Metadata metadata = metadataSources.buildMetadata();
//        SchemaExport schemaExport = new SchemaExport();
//
//        EnumSet<TargetType> targetTypes = EnumSet.of(TargetType.SCRIPT, TargetType.DATABASE);
////        schemaExport.execute(targetTypes, SchemaExport.Action.BOTH, metadata);
//        schemaExport.create(targetTypes,metadata);
//
//       // StandardServiceRegistryBuilder.destroy(registry);
//    }
//}
//
//
