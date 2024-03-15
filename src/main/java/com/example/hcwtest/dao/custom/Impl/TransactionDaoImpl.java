package com.example.hcwtest.dao.custom.Impl;

import com.example.hcwtest.config.FactoryConfiguration;
import com.example.hcwtest.dao.custom.TransactionDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

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


}
