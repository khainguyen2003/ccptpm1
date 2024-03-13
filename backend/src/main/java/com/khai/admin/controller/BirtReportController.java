//package com.khai.admin.controller;
//
//import com.khai.admin.dto.OutputType;
//import com.khai.admin.dto.Report;
//import com.khai.admin.service.BirtReportService;
//import lombok.extern.slf4j.Slf4j;
//import org.eclipse.birt.report.engine.api.EngineException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.util.List;
//
//@Controller
//@Slf4j
//public class BirtReportController {
//
//    @Autowired
//    private BirtReportService reportService;
//
//    @RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "/report")
//    @ResponseBody
//    public List<Report> listReports() {
//        return reportService.getReports();
//    }
//
//    @RequestMapping(produces = "application/json", method = RequestMethod.GET, value = "/report/reload")
//    @ResponseBody
//    public ResponseEntity reloadReports(HttpServletResponse response) {
//        try {
//            log.info("Reloading reports");
//            reportService.loadReports();
//        } catch (EngineException e) {
//            log.error("There was an error reloading the reports in memory: ", e);
//            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).build();
//        }
//        return ResponseEntity.ok().build();
//    }
//
//    @RequestMapping(method = RequestMethod.GET, value = "/report/{name}")
//    @ResponseBody
//    public void generateFullReport(HttpServletResponse response, HttpServletRequest request,
//                                   @PathVariable("name") String name, @RequestParam("output") String output) {
//        log.info("Generating full report: " + name + "; format: " + output);
//        OutputType format = OutputType.from(output);
//        reportService.generateMainReport(name, format, response, request);
//    }
//}