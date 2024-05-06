package com.hounter.backend.business_logic.services;

import com.hounter.backend.application.DTO.AdminDTO.StaffResDTO;
import com.hounter.backend.application.DTO.ReportDTO.ReportResDTO;
import com.hounter.backend.business_logic.entities.Account;
import com.hounter.backend.business_logic.entities.Post;
import com.hounter.backend.business_logic.entities.Report;
import com.hounter.backend.business_logic.entities.Staff;
import com.hounter.backend.business_logic.interfaces.StaffService;
import com.hounter.backend.business_logic.mapper.ReportMapping;
import com.hounter.backend.data_access.repositories.ReportRepository;
import com.hounter.backend.data_access.repositories.StaffRepository;
import com.hounter.backend.shared.exceptions.NotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private ReportRepository reportRepository;

    @PersistenceContext
    protected EntityManager em;

    @Autowired
    private NotifyService notifyService;

    @Override
    public StaffResDTO getStaffInfoById(Long staffId) {
        Optional<Staff> optionalStaff = this.staffRepository.findById(staffId);
        if (optionalStaff.isEmpty()) {
            throw new NotFoundException("Staff not found.", HttpStatus.OK);
        }
        Staff staff = optionalStaff.get();
        return new StaffResDTO(staff.getId(), staff.getFull_name(), staff.getUsername(),
                staff.getPhoneNumber(), staff.getEmail(), staff.getAddress(), staff.getStartDate(),
                staff.getIsActive() ? "Hoạt động" : "Không hoạt động");
    }

    @Override
    public List<ReportResDTO> getStaffReports(Long staffId, String fromDate, String toDate, Boolean isResolved,
            Integer pageSize, Integer pageNo) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Report> cq = cb.createQuery(Report.class);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Root<Report> reportRoot = cq.from(Report.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(reportRoot.get("staff").get("id"), staffId));
        if (fromDate != null) {
            LocalDate from = LocalDate.parse(fromDate, formatter);
            predicates.add(cb.greaterThanOrEqualTo(reportRoot.get("createDate"), from));
        }
        if (toDate != null) {
            LocalDate to = LocalDate.parse(toDate, formatter);
            predicates.add(cb.lessThanOrEqualTo(reportRoot.get("createDate"), to));
        }
        if (isResolved != null) {
            predicates.add(cb.equal(reportRoot.get("status"), isResolved));
        }
        cq.where(predicates.toArray(new Predicate[0]));
        List<Report> reports = em.createQuery(cq).setFirstResult(pageNo * pageSize).setMaxResults(pageSize)
                .getResultList();
        List<ReportResDTO> reportResDTOS = new ArrayList<>();
        for (Report report : reports) {
            reportResDTOS.add(ReportMapping.mapToReportResDTO(report));
        }
        return reportResDTOS;
    }

    @Override
    public ReportResDTO getStaffReportById(Long staffId, Long reportId) {
        return null;
    }

    @Override
    public void requestDeleteAccount(Account account) {
        // handle more if necessary
        this.notifyService.createNotifyDeleteAccount(account);
    }

    @Override
    public void requestDeletePost(Post post) {
        // handle more if necessary
        this.notifyService.createNotifyDeletePost(post);
    }

    @Override
    public Staff getStaffById(Long staffId) {
        Optional<Staff> optionalStaff = this.staffRepository.findById(staffId);
        if (optionalStaff.isEmpty()) {
            throw new NotFoundException("Staff not found.", HttpStatus.OK);
        }
        return optionalStaff.get();
    }
}
