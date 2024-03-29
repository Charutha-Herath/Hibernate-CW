package com.example.hcwtest.dao.custom.Impl;

import com.example.hcwtest.config.FactoryConfiguration;
import com.example.hcwtest.dao.custom.UserDao;
import com.example.hcwtest.entity.Branch;
import com.example.hcwtest.entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
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

    @Override
    public boolean update(String id, String password, User entity) {
        /*Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();*/
        System.out.println();

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            User user = session.get(User.class, id);

            System.out.println("user.getPassword() : "+ user.getPassword());

            if (user != null && user.getPassword().equals(password)) {
                // Update the user entity with the new values
                System.out.println("entity.getUsername() : "+ entity.getUsername());
                System.out.println("entity.getPassword() : "+ entity.getPassword());

                user.setUsername(entity.getUsername());
                user.setPassword(entity.getPassword());

                session.update(user);


                transaction.commit();

                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }



        /*Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();


            String hqlQuery = "UPDATE User SET username = :newUsername, password = :newPassword " +
                    "WHERE userId = :id AND password = :password";
            Query query = session.createQuery(hqlQuery);
            query.setParameter("newUsername", entity.getUsername());
            query.setParameter("newPassword", entity.getPassword());
            query.setParameter("id", id);
            query.setParameter("password", password);

            int updatedRows = query.executeUpdate();
            transaction.commit();
            session.close();
        System.out.println("updateRows : "+ updatedRows);
            return updatedRows > 0;*/
        /*catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            session.close();
        }*/
    }

    @Override
    public String getNextId() {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hqlQuery = "SELECT u.userId FROM User u WHERE u.userId LIKE 'U%' ORDER BY u.userId DESC";
        Query query = session.createQuery(hqlQuery);
        query.setMaxResults(1);
        String lastUId = (String) query.uniqueResult();
        System.out.println("lastId : "+lastUId);


        if (lastUId==null){
            return "U001";
        }


        String newId = splitUserId(lastUId);
        transaction.commit();
        session.close();

        return newId;
       /* List<Object[]> list = session.createQuery("SELECT username FROM User").list();
        transaction.commit();
        session.close();
        HashMap<String, String> userMap = new HashMap<>();
        list.stream().forEach(o -> userMap.put((String)o[0],(String)o[1]));
        return userMap;*/
    }

    @Override
    public String getUserId(String uname, String password) {

        Session session = FactoryConfiguration.getInstance().getSession();
        try {
            String hqlQuery = "SELECT u.userId FROM User u WHERE u.username = ?1 ";
            Query<String> query = session.createQuery(hqlQuery);
            query.setParameter(1, uname);
            /*query.setParameter(2, password);*/
            query.setMaxResults(1);
            String userId = query.uniqueResult();
            System.out.println("UserId: " + userId);

            return userId;
        } finally {
            session.close();
        }

        /*Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hqlQuery = "SELECT u.userId FROM User u WHERE u.username;
        Query query = session.createQuery(hqlQuery);
        query.setMaxResults(1);
        String lastUId = (String) query.uniqueResult();
        System.out.println("lastId : "+lastUId);

        String newId = splitUserId(lastUId);
        transaction.commit();
        session.close();*/
    }

    @Override
    public User search(String username) {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("from User where username = ?1");
        query.setParameter(1, username);
        Object o = query.getSingleResult();
        transaction.commit();
        session.close();
        return (User) o;

    }

    @Override
    public ArrayList<User> getAll() {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<User> list = (ArrayList<User>)session.createQuery("FROM User ").list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public String getNextAdminId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hqlQuery = "SELECT u.userId FROM User u WHERE u.userId LIKE 'A%' ORDER BY u.userId DESC";
        Query query = session.createQuery(hqlQuery);
        query.setMaxResults(1);
        String lastAId = (String) query.uniqueResult();
        System.out.println("lastAdminId : "+lastAId);
        if (lastAId==null){
            return "A001";
        }
        String newId = splitAdminId(lastAId);
        transaction.commit();
        session.close();

        return newId;

    }

    @Override
    public void saveAdmin(User entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(entity);
        transaction.commit();
        session.close();
    }

    private static String splitAdminId(String userId) {
        if (userId != null){
            String[] splint = userId.split("A0");

            int id = Integer.parseInt(splint[1]);
            id++;
            if (id > 9){
                return "A0"+id;
            }
            return "A00"+id;
        }
        return null;
    }

    private static String splitUserId(String userId) {
        if (userId != null){
            String[] splint = userId.split("U0");

            int id = Integer.parseInt(splint[1]);
            id++;
            if (id > 9){
                return "U0"+id;
            }
            return "U00"+id;
        }
        return null;
    }
}
