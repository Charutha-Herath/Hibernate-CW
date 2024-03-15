package com.example.hcwtest.config;

import com.example.hcwtest.entity.Book;
import com.example.hcwtest.entity.Branch;
import com.example.hcwtest.entity.Transaction;
import com.example.hcwtest.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.util.Properties;

public class FactoryConfiguration {
    public static  FactoryConfiguration factoryConfiguration;

    public static SessionFactory sessionFactory;

    private FactoryConfiguration() {



        /*StandardServiceRegistryBuilder standardServiceRegistryBuilder = new StandardServiceRegistryBuilder();
        standardServiceRegistryBuilder.loadProperties("hibernate.properties");
        MetadataSources metadataSources = new MetadataSources(standardServiceRegistryBuilder.build());
        metadataSources
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Branch.class)
                .addAnnotatedClass(Transaction.class);

        Metadata metadata = metadataSources.getMetadataBuilder().build();

        sessionFactory = metadata.getSessionFactoryBuilder().build();*/




        Properties properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("hibernate.properties"));
        }catch (IOException e) {
            e.printStackTrace();
        }
        Configuration configuration = new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Branch.class)
                .addAnnotatedClass(Book.class)
                .addAnnotatedClass(Transaction.class);
        sessionFactory = configuration.buildSessionFactory();








        /*Configuration configuration = new Configuration().configure().addAnnotatedClass(User.class);
        sessionFactory = configuration.buildSessionFactory();*/
    }

    public static FactoryConfiguration getInstance() {
        return (factoryConfiguration == null) ? factoryConfiguration = new FactoryConfiguration() : factoryConfiguration;

    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}
