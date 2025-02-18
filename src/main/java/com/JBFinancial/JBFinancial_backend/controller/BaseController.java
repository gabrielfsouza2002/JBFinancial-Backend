// src/main/java/com/JBFinancial/JBFinancial_backend/controller/BaseController.java

package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.Services.BaseService;
import com.JBFinancial.JBFinancial_backend.Services.DreService;
import com.JBFinancial.JBFinancial_backend.Services.FinancialSummaryDTO;
import com.JBFinancial.JBFinancial_backend.Services.FinancialSummaryService;
import com.JBFinancial.JBFinancial_backend.domain.base.*;
import com.JBFinancial.JBFinancial_backend.domain.conta.Conta;
import com.JBFinancial.JBFinancial_backend.repositories.BaseRepository;
import com.JBFinancial.JBFinancial_backend.repositories.ContaRepository;
import com.JBFinancial.JBFinancial_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("base")
public class BaseController {

    @Autowired
    private BaseRepository repository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private DreService dreService;

    @Autowired
    private BaseService baseService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FinancialSummaryService financialSummaryService;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveBase(@RequestBody BaseRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        Conta conta = contaRepository.findById(data.contaId())
                .orElseThrow(() -> new RuntimeException("Conta not found"));
        if (!conta.getUserId().equals(userId)) {
            throw new RuntimeException("User not authorized to use this account");
        }

        Base baseData = new Base(data);
        baseData.setUserId(userId);
        repository.save(baseData);
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<BaseResponseDTO> getAll(){
        return repository.findAll().stream().map(BaseResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user")
    public List<BaseResponseDTO> getBasesByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return repository.findByUserId(userId).stream().map(BaseResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateBase(@PathVariable UUID id, @RequestBody BaseRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Base baseData = repository.findById(id).orElseThrow(() -> new RuntimeException("Base not found"));
        Conta conta = contaRepository.findById(data.contaId())
                .orElseThrow(() -> new RuntimeException("Conta not found"));
        if (!conta.getUserId().equals(userId)) {
            throw new RuntimeException("User not authorized to use this account");
        }

        baseData.setContaId(data.contaId());
        baseData.setValor(data.valor());
        baseData.setImpactaCaixa(data.impactaCaixa());
        baseData.setImpactaDre(data.impactaDre());
        baseData.setDescricao(data.descricao());
        baseData.setDebtCred(data.debtCred());
        repository.save(baseData);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteBase(@PathVariable UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Base baseData = repository.findById(id).orElseThrow(() -> new RuntimeException("Base not found"));
        Conta conta = contaRepository.findById(baseData.getContaId())
                .orElseThrow(() -> new RuntimeException("Conta not found"));
        if (!conta.getUserId().equals(userId)) {
            throw new RuntimeException("User not authorized to use this account");
        }

        repository.deleteById(id);
    }


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/financial-summary")
    public FinancialSummaryDTO getFinancialSummary() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return financialSummaryService.calculateFinancialSummary(userId);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/years")
    public List<Integer> getDistinctYearsByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return repository.findDistinctYearsByUserId(userId);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/base-today")
    public List<BaseResponseDTO> getBasesTodayByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return repository.findByUserIdAndToday(userId).stream().map(BaseResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/matrix")
    public List<BaseMatrixResponseDTO> getBaseMatrix() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return baseService.getBaseMatrix(userId);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/summary-mes/{year}/{ic}")
    public List<List<Object>> getMonthlySummaryByUser(@PathVariable int year, @PathVariable boolean ic) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return baseService.getMonthlySummaryByUser(year, ic, userId);
    }
}