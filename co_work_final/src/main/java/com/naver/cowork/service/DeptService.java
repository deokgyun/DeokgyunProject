package com.naver.cowork.service;

import com.naver.cowork.domain.Dept;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeptService {

    public List<Dept> deptAll();

    public int dmaxNo();

    public int delete(int dept_no);

    public int insert(Dept dept);

}
