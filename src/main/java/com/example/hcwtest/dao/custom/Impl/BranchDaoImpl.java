package com.example.hcwtest.dao.custom.Impl;

import com.example.hcwtest.config.FactoryConfiguration;
import com.example.hcwtest.dao.custom.BranchDao;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class BranchDaoImpl implements BranchDao {


    @Override
    public String getNextId() {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hqlQuery = "SELECT b.branchId FROM Branch b ORDER BY b.branchId DESC";
        Query query = session.createQuery(hqlQuery);
        query.setMaxResults(1);
        String lastUId = (String) query.uniqueResult();
        System.out.println("lastBranchId : "+lastUId);

        String newId = splitId(lastUId);
        transaction.commit();
        session.close();

        return newId;
    }
    private static String splitId(String userId) {
        if (userId != null){
            String[] splint = userId.split("BR0");

            int id = Integer.parseInt(splint[1]);
            id++;
            if (id > 9){
                return "BR0"+id;
            }
            return "BR00"+id;
        }
        return "BR001";
    }
}
