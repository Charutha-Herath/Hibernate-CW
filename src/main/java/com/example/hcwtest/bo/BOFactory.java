package com.example.hcwtest.bo;

import com.example.hcwtest.bo.custom.impl.BookBoImpl;
import com.example.hcwtest.bo.custom.impl.BranchBoImpl;
import com.example.hcwtest.bo.custom.impl.UserBoImpl;


public class BOFactory {
    private static BOFactory boFactory;
    private BOFactory(){

    }
    public static BOFactory getBOFactory(){
        return (boFactory==null)?boFactory=new BOFactory():boFactory;
    }
    public enum BOTypes{
        USER,BOOK,BRANCH
    }
    public SuperBo getBO(BOTypes boTypes){
        switch (boTypes){
            case USER:
                return new UserBoImpl();
            case BOOK:
                return new BookBoImpl();
            case BRANCH:
                return new BranchBoImpl();
            default:
                return null;
        }
    }

}
