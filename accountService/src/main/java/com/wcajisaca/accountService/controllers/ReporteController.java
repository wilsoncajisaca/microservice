package com.wcajisaca.accountService.controllers;

import com.wcajisaca.accountService.dtos.AccountStatementReportDTO;
import com.wcajisaca.accountService.dtos.response.BaseResponse;
import com.wcajisaca.accountService.services.IMovementsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/reportes")
@Slf4j
@RequiredArgsConstructor
public class ReporteController {
    private final IMovementsService movementsService;
    @GetMapping
    public ResponseEntity<?> getReporte(@RequestParam UUID clientId,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity
                .ok(BaseResponse.builder()
                        .data(movementsService.generateAccountStatement(clientId, startDate, endDate)).build());
    }
}
