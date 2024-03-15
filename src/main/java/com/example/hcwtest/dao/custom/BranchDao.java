package com.example.hcwtest.dao.custom;

import com.example.hcwtest.dao.SuperDao;
import com.example.hcwtest.entity.Branch;

import java.util.ArrayList;
import java.util.List;

public interface BranchDao extends SuperDao {

    String getNextId();

    void save(Branch branch);

    List<String> getBranchNames();

    ArrayList<Branch> getAll();
}
