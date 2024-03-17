package com.example.hcwtest.dao.custom.Impl;

import com.example.hcwtest.config.FactoryConfiguration;
import com.example.hcwtest.dao.custom.TransactionDao;
import com.example.hcwtest.entity.User;
import javafx.scene.control.Alert;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class TransactionDaoImpl implements TransactionDao {
    @Override
    public String getNextTId() {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        String logTid = "";
        Query query = session.createQuery("select transactionId  from Transaction  where transactionId  like ?1 order by transactionId  desc limit 1");
        query.setParameter(1, "T%");
        Object o = query.uniqueResult();
        if (o == null) {
            logTid = "T001";
        } else {
            String user = o.toString();
            System.out.println(user);
            String[] userIds = user.split("T");
            int id = Integer.parseInt(userIds[1]);
            id++;
            logTid = String.format("T%03d", id);
        }
        transaction.commit();
        session.close();
        return logTid;
    }

    @Override
    public void save(com.example.hcwtest.entity.Transaction newTransaction) {
        /*Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.persist(newTransaction);
        transaction.commit();
        session.close();*/
        Session session = null;
        Transaction transaction = null;
        try {
            session = FactoryConfiguration.getInstance().getSession();
            transaction = session.beginTransaction();

            // Perform database operations
            session.persist(newTransaction);

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace(); // Handle exception appropriately
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<com.example.hcwtest.entity.Transaction> getAll(String text) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        List<com.example.hcwtest.entity.Transaction> logList;
        try {
            Query<com.example.hcwtest.entity.Transaction> query = session.createQuery("FROM Transaction where user.userId = ?1", com.example.hcwtest.entity.Transaction.class);
            query.setParameter(1,text);
            logList = query.list();
        }
        catch (Exception exception){
            new Alert(Alert.AlertType.WARNING,"NO data in Logs").showAndWait();
            logList = new ArrayList<>();
        }

        transaction.commit();
        session.close();

        return logList;

    }

    @Override
    public List<com.example.hcwtest.entity.Transaction> getAllTrans() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<com.example.hcwtest.entity.Transaction> list = (ArrayList<com.example.hcwtest.entity.Transaction>)session.createQuery("FROM Transaction ").list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public List<com.example.hcwtest.entity.Transaction> getOverDues() {

        List<com.example.hcwtest.entity.Transaction> logList;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM Transaction WHERE return_date < CURRENT_DATE";
            Query<com.example.hcwtest.entity.Transaction> query = session.createQuery(hql, com.example.hcwtest.entity.Transaction.class);
            logList = query.list();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            logList = new ArrayList<>();
        }
        return logList;
    }


}
