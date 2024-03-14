package com.example.hcwtest.bo.custom.impl;

import com.example.hcwtest.bo.custom.BranchBo;
import com.example.hcwtest.dao.DAOFactory;
import com.example.hcwtest.dao.custom.BranchDao;

public class BranchBoImpl implements BranchBo {

    BranchDao branchDao = (BranchDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BRANCH);


    @Override
    public String generateId() {

        return branchDao.getNextId();
    }
}
