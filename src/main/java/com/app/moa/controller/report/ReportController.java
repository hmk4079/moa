package com.app.moa.controller.report;


import com.app.moa.domain.report.Pagination;
import com.app.moa.domain.report.ReportDTO;
import com.app.moa.domain.report.ReportVO;
import com.app.moa.domain.thesis_post.ThesisPostDTO;
import com.app.moa.domain.thesis_post.ThesisPostVO;
import com.app.moa.service.report.ReportService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/report/*")
@RequiredArgsConstructor
@Slf4j

public class ReportController {
    private final ReportService reportService;
    private final ReportDTO reportDTO;

//    신고된 게시글 목록 조회
    @GetMapping("list")
    public String getList(Pagination pagination, Model model,@RequestParam(defaultValue = "recent") String view) {

        reportDTO.setMemberId(21L);
        reportDTO.setPostId(146L);
        reportDTO.setPostContent("우리집 부자다");
        pagination.setTotal(reportService.getTotal());
        pagination.progress();
        List<ReportDTO> reports = reportService.getList(pagination);

        if (reports.isEmpty()) {
            log.info("포스트 없음");
        }

        model.addAttribute("pagination", pagination);
        model.addAttribute("reports", reports);

        return "admin-page-html/admin-report/admin-report-list";
    }

    //  신고된 게시글 조회
    @GetMapping("inquiry")
    public String getReportInquiry(@RequestParam("Id") Long Id, Model model) {
        Optional<ReportVO> report = reportService.getById(Id);
        if (report.isPresent()) {
            model.addAttribute("report", report.get());
        } else {
            return "forward:admin-page-html/admin-report/admin-report-Inquiry";
        }
        log.info(String.valueOf(report.get()));
        return "admin-page-html/admin-report/admin-report-Inquiry";
    }

//    @GetMapping("Inquiry")
//    public String () {
//
//    }
}
