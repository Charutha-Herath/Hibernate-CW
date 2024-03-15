package com.example.hcwtest.bo.custom;

import com.example.hcwtest.bo.SuperBo;
import com.example.hcwtest.dao.SuperDao;
import com.example.hcwtest.dto.BranchDto;

import java.util.List;

public interface BranchBo extends SuperBo {


    String generateId();

    void saveBranch(BranchDto branchDto);

    List<String> getBranchName();

    List<BranchDto> getAllBranches();
}
