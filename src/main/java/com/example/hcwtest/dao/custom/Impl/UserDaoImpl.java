package com.example.hcwtest.dao.custom.Impl;

import com.example.hcwtest.config.FactoryConfiguration;
import com.example.hcwtest.dao.custom.UserDao;
import com.example.hcwtest.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashMap;
import java.util.List;

public class UserDaoImpl implements UserDao {


    @Override
    public HashMap<String, String> checker(User entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Object[]> list = session.createQuery("SELECT username,password FROM User").list();
        transaction.commit();
        session.close();
        HashMap<String, String> userMap = new HashMap<>();
        list.stream().forEach(o -> userMap.put((String)o[0],(String)o[1]));
        return userMap;

    }

    @Override
    public void save(User entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
    }
}
