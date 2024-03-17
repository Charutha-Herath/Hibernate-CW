package com.example.hcwtest.bo.custom.impl;

import com.example.hcwtest.bo.custom.BranchBo;
import com.example.hcwtest.dao.DAOFactory;
import com.example.hcwtest.dao.custom.BranchDao;
import com.example.hcwtest.dto.BookDto;
import com.example.hcwtest.dto.BranchDto;
import com.example.hcwtest.entity.Book;
import com.example.hcwtest.entity.Branch;

import java.util.ArrayList;
import java.util.List;

public class BranchBoImpl implements BranchBo {

    BranchDao branchDao = (BranchDao) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.BRANCH);


    @Override
    public String generateId() {

        return branchDao.getNextId();
    }

    @Override
    public void saveBranch(BranchDto dto) {

         branchDao.save(new Branch(dto.getBranchId(),dto.getName(),dto.getManager(),dto.getBook_total()));
    }

    @Override
    public List<String> getBranchName() {
        return branchDao.getBranchNames();
    }

    @Override
    public List<BranchDto> getAllBranches() {
        ArrayList<Branch> branches = branchDao.getAll();

        ArrayList<BranchDto> dtoList = new ArrayList<>();

        for (Branch branch: branches) {
            dtoList.add(new BranchDto(branch.getBranchId(),branch.getName(),branch.getManager(), branch.getBook_total()));
        }

        return dtoList;
    }

    @Override
    public boolean branchUpdate(BranchDto dto) {

        return branchDao.update(new Branch(dto.getBranchId(),dto.getName(),dto.getManager(),dto.getBook_total()));
    }

    @Override
    public boolean deleteBranch(String branchId) {

        return branchDao.delete(branchId);
    }
}
