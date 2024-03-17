package com.example.hcwtest.dao.custom.Impl;

import com.example.hcwtest.config.FactoryConfiguration;
import com.example.hcwtest.dao.custom.BookDao;
import com.example.hcwtest.dto.BookDto;
import com.example.hcwtest.entity.Book;
import com.example.hcwtest.entity.User;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import javafx.scene.control.Alert;
import org.hibernate.HibernateException;
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

        if (lastUId==null){
            return "B001";
        }


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

    @Override
    public Book search(String bookId) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Book load = session.load(Book.class, bookId);
        transaction.commit();
        session.close();
        return load;
    }

    @Override
    public void updateStatusNo(Book search) {
        Transaction transaction = null;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            transaction = session.beginTransaction();
            Book load = session.load(Book.class, search.getBookId());
            load.setStatus("NO");
            session.update(load);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public List<Book> searchByName(String search) {
        /*Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Book singleResult = null;
        try {
            session = FactoryConfiguration.getInstance().getSession();
            transaction = session.beginTransaction();
            Query query = session.createQuery("from Book where title = ?1");
            query.setParameter(1, search);
            singleResult = (Book) query.uniqueResult();
            transaction.commit();
            System.out.println(singleResult);
        } catch (NoResultException e) {

            new Alert(Alert.AlertType.WARNING,"No result found").show();

        } catch (HibernateException e) {
            System.out.println("Error occurred during Hibernate operation: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return singleResult;*/

        /*Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Book singleResult;
        try {
            Query query = session.createQuery("from Book where title = ?1");
            query.setParameter(1, search);
            singleResult = (Book) query.getSingleResult();
            transaction.commit();

        }catch (Exception e) {
            e.printStackTrace();
            singleResult = new Book();
        }

        System.out.println(singleResult);
        return singleResult;*/

        List<com.example.hcwtest.entity.Book> singleResult;
        try (Session session = FactoryConfiguration.getInstance().getSession()) {
            Transaction transaction = session.beginTransaction();

            String hql = "FROM Book WHERE title = ?1";
            Query<com.example.hcwtest.entity.Book> query = session.createQuery(hql, com.example.hcwtest.entity.Book.class);
            query.setParameter(1, search);
            singleResult = query.list();

            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            singleResult = new ArrayList<>();
        }
        return singleResult;


        /*List<com.example.hcwtest.entity.Transaction> logList;
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
        return logList;*/



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
