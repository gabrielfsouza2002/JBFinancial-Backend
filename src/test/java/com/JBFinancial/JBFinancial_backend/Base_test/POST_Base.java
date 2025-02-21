package com.JBFinancial.JBFinancial_backend.Base_test;

import com.JBFinancial.JBFinancial_backend.controller.BaseController;
import com.JBFinancial.JBFinancial_backend.Infra.security.TokenService;
import com.JBFinancial.JBFinancial_backend.Services.BaseService;
import com.JBFinancial.JBFinancial_backend.Services.DreService;
import com.JBFinancial.JBFinancial_backend.Services.FinancialSummaryService;
import com.JBFinancial.JBFinancial_backend.repositories.BaseRepository;
import com.JBFinancial.JBFinancial_backend.repositories.ContaRepository;
import com.JBFinancial.JBFinancial_backend.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BaseController.class)
public class POST_Base {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BaseRepository baseRepository;

    @MockBean
    private ContaRepository contaRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private DreService dreService;

    @MockBean
    private BaseService baseService;

    @MockBean
    private TokenService tokenService;

    @MockBean
    private FinancialSummaryService financialSummaryService;

    @Test
    @WithMockUser
    public void testGetAllBases() throws Exception {
        mockMvc.perform(get("/base"))
                .andExpect(status().isOk());
    }
}