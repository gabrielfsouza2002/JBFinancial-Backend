package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.domain.base.*;
import com.JBFinancial.JBFinancial_backend.repositories.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("base")
public class BaseController {

    @Autowired
    private BaseRepository repository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveBase(@RequestBody BaseRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userId = authentication.getName();
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
        String userId = authentication.getName();
        return repository.findByUserId(userId).stream().map(BaseResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateBase(@PathVariable Long id, @RequestBody BaseRequestDTO data){
        Base baseData = repository.findById(id).orElseThrow(() -> new RuntimeException("Base not found"));
        baseData.setContaId(data.contaId());
        baseData.setValor(data.valor());
        baseData.setImpactaCaixa(data.impactaCaixa());
        baseData.setImpactaDre(data.impactaDre());
        baseData.setDescricao(data.descricao());
        repository.save(baseData);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteBase(@PathVariable Long id){
        repository.deleteById(id);
    }
}