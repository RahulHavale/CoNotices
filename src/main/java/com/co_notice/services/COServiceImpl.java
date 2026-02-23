package com.co_notice.services;

import com.co_notice.entities.COEntity;
import com.co_notice.entities.CoTriggerEntity;
import com.co_notice.entities.EDEntity;
import com.co_notice.repo.CORepo;
import com.co_notice.repo.CoTriggerRepo;
import com.co_notice.repo.EDRepo;
import com.co_notice.utils.EmailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class COServiceImpl implements COService {

    @Autowired
    private CoTriggerRepo coTRepo;

    @Autowired
    private EDRepo edRepo;

    @Autowired
    private EmailUtils emailUtils;
    
    @Override
    public void processPendingTriggers() {
        List<CoTriggerEntity> pendingTrg = coTRepo.findNoticesByNoticeStatus("PENDING");
        for (CoTriggerEntity triggers : pendingTrg) {
            processEachRecord(triggers);
        }
    }

    private void processEachRecord(CoTriggerEntity coTEntity) {

        Long caseNum = coTEntity.getCaseNum();
        EDEntity edEntity = edRepo.findByCaseNum(caseNum);
        String planStatus = edEntity.getPlanStatus();

        File pdfFile = null;
        if("APPROVED".equals(planStatus)){
            pdfFile = generateApprovedNotices(edEntity);
        }else if("DENIED".equals(planStatus)){
            pdfFile = generateDeniedNotices(edEntity);
        }
        String fileUrl = storPdfInS3(pdfFile);

        boolean isUpdated = updateProcessedRecord(coTEntity,fileUrl);

        if(isUpdated){
            emailUtils.sendEmail("","","",pdfFile);
        }
    }

    private boolean updateProcessedRecord(CoTriggerEntity coTEntity, String fileUrl) {
        coTEntity.setNoticeStatus("H");
        coTEntity.setNoticeUrl(fileUrl);
        coTRepo.save(coTEntity);
        return false;
    }

    private String storPdfInS3(File pdfFile) {
        return null;
    }

    private File generateDeniedNotices(EDEntity entity) {
        return null;
    }

    private File generateApprovedNotices(EDEntity entity) {
        return null;
    }
}
