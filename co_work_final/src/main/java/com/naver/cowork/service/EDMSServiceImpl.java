package com.naver.cowork.service;

import com.naver.cowork.domain.ChatVO;
import com.naver.cowork.domain.EDMS;
import com.naver.cowork.mybatis.mapper.ChatMapper;
import com.naver.cowork.mybatis.mapper.EDMSMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EDMSServiceImpl implements EDMSService {

    private EDMSMapper dao;

    public EDMSServiceImpl(EDMSMapper dao) {
        this.dao = dao;
    }

    public List<EDMS> getMyDoc(String user_id) {
        List<EDMS> docList = dao.getMyDoc(user_id);
        for (EDMS e : docList) {
            if (e.getDOCUMENT_STATE() == edmsResultEnum.WAIT.getValue()) {
                e.setSTATE_RESULT("대기");
            } else if (e.getDOCUMENT_STATE() == edmsResultEnum.ING.getValue()) {
                e.setSTATE_RESULT("진행중");
            } else if (e.getDOCUMENT_STATE() == edmsResultEnum.REJECT.getValue()) {
                e.setSTATE_RESULT("반려");
            } else {
                e.setSTATE_RESULT(e.getAPPROVAL_DATE());
            }
            
            if(e.getDOCUMENT_CATEGORY_NUM() == edmsCategoryEnum.USECAR.getValue()){
                e.setCATEGORY_RESULT("차량신청서");
            }
        }
        return docList;
    }

    public List<EDMS> getMyDocApp(String user_id) {
        List<EDMS> docAppList = dao.getMyDocApp(user_id);
        for (EDMS e : docAppList) {
            if (e.getAPPROVAL_DATE() == null || e.getAPPROVAL_DATE() == "") {
                e.setAPPROVAL_DATE_RESULT("결재");
            } else {
                e.setAPPROVAL_DATE_RESULT("완료");
            }

            if(e.getDOCUMENT_CATEGORY_NUM() == edmsCategoryEnum.USECAR.getValue()){
                e.setCATEGORY_RESULT("차량신청서");
            }
        }
        return docAppList;
    }

    public int getCountDoc(String user_id){
        return dao.getCountDoc(user_id);
    }

    public int getCountDocApp(String user_id){
        return dao.getCountDocApp(user_id);
    }

}