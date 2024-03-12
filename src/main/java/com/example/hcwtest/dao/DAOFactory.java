package com.example.hcwtest.dao;


import com.example.hcwtest.dao.custom.Impl.UserDaoImpl;

public class DAOFactory {
    private static DAOFactory daoFactory;
    private DAOFactory(){
    }
    public static DAOFactory getDaoFactory(){
        return (daoFactory==null)?daoFactory=new DAOFactory():daoFactory;
    }
    public enum DAOTypes{
        USER
    }
    public SuperDao getDAO(DAOTypes daoTypes){
        switch (daoTypes){
            case USER:
                return new UserDaoImpl();
            default:
                return null;
        }
    }
}
