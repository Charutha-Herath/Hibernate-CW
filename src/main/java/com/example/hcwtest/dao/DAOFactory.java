package com.example.hcwtest.dao;


import com.example.hcwtest.bo.custom.impl.BranchBoImpl;
import com.example.hcwtest.dao.custom.Impl.BookDaoImpl;
import com.example.hcwtest.dao.custom.Impl.BranchDaoImpl;
import com.example.hcwtest.dao.custom.Impl.UserDaoImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){
    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOTypes{
        USER,BOOK,BRANCH
    }
    public SuperDao getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case USER:
                return new UserDaoImpl();
            case BOOK:
                return new BookDaoImpl();
            case BRANCH:
                return new BranchDaoImpl();
            default:
                return null;
        }
    }
}
