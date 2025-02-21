package com.JBFinancial.JBFinancial_backend.Base_test;

import com.JBFinancial.JBFinancial_backend.controller.BaseController;
import com.JBFinancial.JBFinancial_backend.domain.base.BaseRequestDTO;
import com.JBFinancial.JBFinancial_backend.repositories.BaseRepository;
import com.JBFinancial.JBFinancial_backend.repositories.ContaRepository;
import com.JBFinancial.JBFinancial_backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BaseController.class)
public class POST_Base {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BaseRepository baseRepository;

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private BaseController baseController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testSaveBase() throws Exception {
        BaseRequestDTO request = new BaseRequestDTO(
                UUID.randomUUID(),
                100.0,
                true,
                true,
                "Test Description",
                true,
                null
        );

        String jsonRequest = """
                {
                    "contaId": "%s",
                    "valor": 100.0,
                    "impactaCaixa": true,
                    "impactaDre": true,
                    "descricao": "Test Description",
                    "debtCred": true
                }
                """.formatted(request.contaId());

        mockMvc.perform(post("/base")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk());
    }
}