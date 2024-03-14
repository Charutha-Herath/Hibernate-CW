package com.example.hcwtest.dao.custom.Impl;

import com.example.hcwtest.config.FactoryConfiguration;
import com.example.hcwtest.dao.custom.BookDao;
import com.example.hcwtest.dto.BookDto;
import com.example.hcwtest.entity.Book;
import com.example.hcwtest.entity.User;
import jakarta.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

import static com.example.hcwtest.config.FactoryConfiguration.sessionFactory;

public class BookDaoImpl implements BookDao {

    @Override
    public void save(Book entity) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
    }

    @Override
    public ArrayList<Book> getAll() {
        /*Session session = FactoryConfiguration.getInstance().getSession(); // assuming you have sessionFactory available

        // HQL query to retrieve all data from the coach table
        String hql = "FROM Book";
        TypedQuery<Book> query = session.createQuery(hql);

        // Execute query and return the result list
        return (ArrayList<Book>) query.getResultList();*/


        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        ArrayList<Book> list = (ArrayList<Book>) session.createQuery("FROM Book").list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public String getNextId() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        String hqlQuery = "SELECT b.bookId FROM Book b ORDER BY b.bookId DESC";
        Query query = session.createQuery(hqlQuery);
        query.setMaxResults(1);
        String lastUId = (String) query.uniqueResult();
        System.out.println("lastBookId : "+lastUId);

        String newId = splitUserId(lastUId);
        transaction.commit();
        session.close();

        return newId;
    }

    @Override
    public boolean update(Book entity) {
        /*Session session = sessionFactory.openSession();
        session.beginTransaction();

        // Merge the detached book object into the persistent context
        session.merge(entity);

        session.getTransaction().commit();
        session.close();*/

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();

            Book book = session.get(Book.class, entity.getBookId());



            if (book != null ) {
                // Update the user entity with the new values

                book.setBookId(entity.getBookId());
                book.setTitle(entity.getTitle());
                book.setAuthor(entity.getAuthor());
                book.setGenre(entity.getGenre());
                book.setStatus(entity.getStatus());

                session.update(book);


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
    }

    @Override
    public boolean delete(String bookId) {

        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.load(Book.class, bookId));
        transaction.commit();
        session.close();
        return true;
    }

    private static String splitUserId(String userId) {
        if (userId != null){
            String[] splint = userId.split("B0");

            int id = Integer.parseInt(splint[1]);
            id++;
            if (id > 9){
                return "B0"+id;
            }
            return "B00"+id;
        }
        return "B001";
    }
}
