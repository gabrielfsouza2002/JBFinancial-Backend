package com.JBFinancial.JBFinancial_backend.controller;

import com.JBFinancial.JBFinancial_backend.domain.cliente.*;
import com.JBFinancial.JBFinancial_backend.domain.segmento.Segmento;
import com.JBFinancial.JBFinancial_backend.repositories.ClienteRepository;
import com.JBFinancial.JBFinancial_backend.repositories.SegmentoRepository;
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
@RequestMapping("cliente")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SegmentoRepository segmentoRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping
    public void saveCliente(@Valid @RequestBody ClienteRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        if (clienteRepository.existsByNomeClienteAndTipoPessoaAndDescricaoAndUserId(
                data.nome_cliente().toUpperCase(),
                data.tipo_pessoa().toUpperCase(),
                data.descricao(),
                userId)) {
            throw new RuntimeException("Cliente com esta combinação de nome, tipo de pessoa e descrição já existe.");
        }

        Cliente cliente = new Cliente(data);
        cliente.setUserId(userId);

        if (data.id_segmento() != null) {
            Segmento segmento = segmentoRepository.findById(data.id_segmento())
                .orElseThrow(() -> new RuntimeException("Segmento não encontrado"));
            if (!segmento.getUserId().equals(userId)) {
                throw new RuntimeException("Usuário não autorizado a usar este segmento");
            }
            cliente.setSegmento(segmento);
        }

        clienteRepository.save(cliente);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping
    public List<ClienteResponseDTO> getAll(){
        return clienteRepository.findAll().stream().map(ClienteResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/user")
    public List<ClienteResponseDTO> getClientesByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();
        return clienteRepository.findByUserId(userId).stream().map(ClienteResponseDTO::new).toList();
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PutMapping("/{id}")
    public void updateCliente(@PathVariable UUID id, @Valid @RequestBody ClienteRequestDTO data){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        if (!cliente.getUserId().equals(userId)) {
            throw new RuntimeException("Usuário não autorizado a atualizar este cliente");
        }

        if (clienteRepository.existsByNomeClienteAndTipoPessoaAndDescricaoAndUserIdAndIdNot(
                data.nome_cliente().toUpperCase(),
                data.tipo_pessoa().toUpperCase(),
                data.descricao(),
                userId,
                id)) {
            throw new RuntimeException("Cliente com esta combinação de nome, tipo de pessoa e descrição já existe.");
        }

        cliente.setNome_cliente(data.nome_cliente());
        cliente.setDescricao(data.descricao());
        cliente.setTipo_pessoa(data.tipo_pessoa());

        if (data.id_segmento() != null) {
            Segmento segmento = segmentoRepository.findById(data.id_segmento())
                .orElseThrow(() -> new RuntimeException("Segmento não encontrado"));
            if (!segmento.getUserId().equals(userId)) {
                throw new RuntimeException("Usuário não autorizado a usar este segmento");
            }
            cliente.setSegmento(segmento);
        } else {
            cliente.setSegmento(null);
        }

        clienteRepository.save(cliente);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @DeleteMapping("/{id}")
    public void deleteCliente(@PathVariable UUID id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userId = userRepository.findByLogin(userDetails.getUsername()).getId();

        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Cliente não encontrado"));
        if (!cliente.getUserId().equals(userId)) {
            throw new RuntimeException("Usuário não autorizado a deletar este cliente");
        }
        clienteRepository.deleteById(id);
    }
}
