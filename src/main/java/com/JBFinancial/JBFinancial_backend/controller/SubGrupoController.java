package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.domain.subgrupo.Subgrupo;
import com.JBFinancial.JBFinancial_backend.domain.subgrupo.SubgrupoRequestDTO;
import com.JBFinancial.JBFinancial_backend.domain.subgrupo.SubgrupoResponseDTO;
import com.JBFinancial.JBFinancial_backend.repositories.SubgrupoRepository;
import com.JBFinancial.JBFinancial_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("subgrupo")
public class SubGrupoController {

    @Autowired
    private SubgrupoRepository subgrupoRepository;

    @Autowired
    private UserRepository userRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveSubgrupo(@Valid @RequestBody SubgrupoRequestDTO data) {
        // Obtém o usuário autenticado do token JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Subgrupo subgrupo = new Subgrupo();
        subgrupo.setIdUser(userId); // Usa o userId do token, não do request
        subgrupo.setNome(data.nome());
        subgrupo.setIdGrupo(data.idGrupo());
        subgrupo.setDigitoSubgrupo(generateNextSubgrupoDigit(userId, data.idGrupo()));
        subgrupoRepository.save(subgrupo);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<SubgrupoResponseDTO> getAll() {
        return subgrupoRepository.findAll().stream().map(SubgrupoResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user")
    public List<SubgrupoResponseDTO> getSubgruposByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return subgrupoRepository.findByIdUserOrIdUserIsNull(userId).stream().map(SubgrupoResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateSubgrupo(@PathVariable UUID id, @Valid @RequestBody SubgrupoRequestDTO data) {
        // Obtém o usuário autenticado do token JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Subgrupo subgrupo = subgrupoRepository.findById(id).orElseThrow(() -> new RuntimeException("Subgrupo not found"));

        // Validação: apenas o dono do subgrupo pode atualizá-lo
        if (!subgrupo.getIdUser().equals(userId)) {
            throw new RuntimeException("Você não tem permissão para atualizar este subgrupo");
        }

        subgrupo.setNome(data.nome());
        subgrupo.setIdGrupo(data.idGrupo());
        subgrupoRepository.save(subgrupo);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteSubgrupo(@PathVariable UUID id) {
        // Obtém o usuário autenticado do token JWT
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Subgrupo subgrupo = subgrupoRepository.findById(id).orElseThrow(() -> new RuntimeException("Subgrupo not found"));

        // Validação: apenas o dono do subgrupo pode deletá-lo
        if (!subgrupo.getIdUser().equals(userId)) {
            throw new RuntimeException("Você não tem permissão para deletar este subgrupo");
        }

        subgrupoRepository.deleteById(id);
    }

    private String generateNextSubgrupoDigit(String userId, UUID idGrupo) {
        long count = subgrupoRepository.countByIdUserAndIdGrupo(userId, idGrupo);
        return String.valueOf((count + 1) % 10);
    }
}