package com.co_notice.services;

import com.co_notice.entities.*;
import com.co_notice.repo.*;
import com.co_notice.utils.EmailUtils;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class COServiceImpl implements COService {

    private static final Logger log =
            LogManager.getLogger(COServiceImpl.class);

    @Autowired
    private CoTriggerRepo triggerRepo;

    @Autowired
    private EDRepo edRepo;

    @Autowired
    private DcCaseRepo caseRepo;

    @Autowired
    private CitizenAppRepo appRepo;

    @Autowired
    private CORepo coRepo;

    @Autowired
    private EmailUtils emailUtils;

    @Override
    public void processPendingTriggers() {

        log.info("CO Batch process started");

        List<CoTriggerEntity> triggers =
                triggerRepo.findByTriggerStatus("P");

        log.info("Total pending triggers found: {}", triggers.size());

        ExecutorService executor =
                Executors.newFixedThreadPool(10);

        for (CoTriggerEntity trigger : triggers) {

            log.debug("Submitting trigger for case: {}",
                    trigger.getCaseNum());

            executor.submit(() -> {
                try {
                    processEachTrigger(trigger);
                } catch (Exception e) {
                    log.error("Error processing case: {}",
                            trigger.getCaseNum(), e);
                }
            });
        }
    }
//abc
    @Transactional
    private void processEachTrigger(CoTriggerEntity trigger) {

        Long caseNum = trigger.getCaseNum();
        log.info("Processing trigger for case: {}", caseNum);

        try {
//
            EDEntity ed = edRepo.findByCaseNum(caseNum);
            if (ed == null) {
                log.warn("Eligibility record not found for case: {}", caseNum);
                return;
            }
            DcCaseEntity dcCase =
                    caseRepo.findById(caseNum).orElse(null);
            if (dcCase == null) {
                log.warn("DC Case not found for case: {}", caseNum);
                return;
            }

            CitizenAppEntity app =
                    appRepo.findById(dcCase.getAppId()).orElse(null);
            if (app == null) {
                log.warn("Citizen application not found for case: {}", caseNum);
                return;
            }

            File file = null;

            if ("AP".equals(ed.getPlanStatus())) {
                log.info("Generating APPROVED notice for case: {}", caseNum);
                file = generatePdf(ed, app, "APPROVED");
            }
            else if ("DN".equals(ed.getPlanStatus())) {
                log.info("Generating DENIED notice for case: {}", caseNum);
                file = generatePdf(ed, app, "DENIED");
            }

            if (file == null) {
                log.warn("PDF generation failed for case: {}", caseNum);
                return;
            }

            log.info("PDF generated successfully: {}", file.getName());

            trigger.setTriggerStatus("C");
            triggerRepo.save(trigger);

            log.info("Trigger marked as completed for case: {}", caseNum);

            emailUtils.sendEmail(
                    app.getEmail(),
                    "IES Eligibility Info",
                    "Please find attached eligibility notice",
                    file
            );

            log.info("Email sent successfully to: {}", app.getEmail());

        } catch (Exception e) {
            log.error("Exception while processing case: {}", caseNum, e);
        }
    }

    // =====================================================
    // SAVE NOTICE
    // =====================================================
    private void saveNotice(EDEntity ed,
                            String url,
                            String pdfName) {

        log.debug("Saving CO notice for case: {}", ed.getCaseNum());

        COEntity co = new COEntity();

        co.setCaseNum(ed.getCaseNum());
        co.setEdId(ed.getEdId());
        co.setNoticeUrl(url);
        co.setPdfName(pdfName);
        co.setNoticeStatus(ed.getPlanStatus());
        co.setPrintDate(LocalDate.now());
        co.setCreatedDate(LocalDate.now());

        coRepo.save(co);

        log.info("CO notice saved successfully for case: {}", ed.getCaseNum());
    }

    private File generatePdf(EDEntity ed,
                             CitizenAppEntity app,
                             String type) throws Exception {

        Long caseNum = ed.getCaseNum();
        log.debug("Starting PDF generation for case: {} type: {}", caseNum, type);

        Document document = new Document(PageSize.A4);

        File file = new File(caseNum + "_" + type + ".pdf");

        PdfWriter.getInstance(
                document,
                new FileOutputStream(file));

        document.open();

        Font titleFont =
                FontFactory.getFont(
                        FontFactory.HELVETICA_BOLD, 18);

        Paragraph title =
                new Paragraph("Eligibility Report", titleFont);

        title.setAlignment(Element.ALIGN_CENTER);

        document.add(title);
        document.add(new Paragraph("\n"));

        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(100);
        table.setWidths(new float[]{2f,2f,2f,2f,2f,2f});

        addHeader(table, "Citizen Name");
        addHeader(table, "Plan Name");
        addHeader(table, "Plan Status");
        addHeader(table, "Start Date");
        addHeader(table, "End Date");
        addHeader(table, "Benefit");

        table.addCell(app.getFullName());
        table.addCell(ed.getPlanName());
        table.addCell(ed.getPlanStatus());
        table.addCell("NA");
        table.addCell("NA");
        table.addCell("0");

        document.add(table);
        document.close();

        log.info("PDF created successfully at: {}",
                file.getAbsolutePath());

        return file;
    }
    private void addHeader(PdfPTable table, String text) {

        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);

        Font font =
                FontFactory.getFont(FontFactory.HELVETICA);

        cell.setPhrase(new Phrase(text, font));
        table.addCell(cell);
    }
}