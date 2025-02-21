package com.JBFinancial.JBFinancial_backend;

import com.JBFinancial.JBFinancial_backend.Base_test.POST_Base;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(POST_Base.class)
class JbFinancialBackendApplicationTests {
	@Test
	void contextLoads() {
	}
}