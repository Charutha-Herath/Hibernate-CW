package com.example.hcwtest.dao.custom.Impl;

import com.example.hcwtest.config.FactoryConfiguration;
import com.example.hcwtest.dao.custom.BranchDao;
import com.example.hcwtest.entity.Book;
import com.example.hcwtest.entity.Branch;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public void save(Branch entity) {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
    }

    @Override
    public List<String> getBranchNames() {

        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Query<String> query = session.createQuery("SELECT b.name FROM Branch b", String.class);
            return query.list();
        } catch (Exception e) {
            // Handle exceptions appropriately
            e.printStackTrace();
            return null; // Or throw your custom exception
        }
    }

    @Override
    public ArrayList<Branch> getAll() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Branch> list = (ArrayList<Branch>)session.createQuery("FROM Branch ").list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public boolean update(Branch entity) {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();

        return true;
    }

    @Override
    public boolean delete(String branchId) {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.load(Branch.class, branchId));
        transaction.commit();
        session.close();
        return true;
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
