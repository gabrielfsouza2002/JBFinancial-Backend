package com.JBFinancial.JBFinancial_backend.Infra.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        Map<String, String> errorResponse = new HashMap<>();

        String message = ex.getMessage();
        String errorMessage;

        // Detectar o tipo de erro e retornar mensagem apropriada
        if (message != null) {
            // Verificações mais específicas primeiro
            if (message.contains("fk_cliente_segmento") || message.contains("table \"segmento\"")) {
                errorMessage = "O segmento não pode ser excluído pois há uso em clientes. Se desejar, é possível atualizar os dados.";
            } else if (message.contains("fk_produto_categoria") || message.contains("table \"categoria_produto\"")) {
                errorMessage = "A categoria não pode ser excluída pois há uso em produtos. Se desejar, é possível atualizar os dados.";
            } else if (message.contains("fk_conta") || message.contains("table \"contas\"")) {
                errorMessage = "A conta não pode ser excluída pois há uso em lançamentos. Se desejar, é possível atualizar os dados.";
            } else if (message.contains("fk_base_cliente") || message.contains("fk_cliente") || message.contains("table \"cliente\"")) {
                errorMessage = "O cliente não pode ser excluído pois há uso em lançamentos. Se desejar, é possível atualizar os dados.";
            } else if (message.contains("fk_base_fornecedor") || message.contains("fk_fornecedor") || message.contains("table \"fornecedor\"")) {
                errorMessage = "O fornecedor não pode ser excluído pois há uso em lançamentos. Se desejar, é possível atualizar os dados.";
            } else if (message.contains("fk_base_produto") || message.contains("fk_produto") || message.contains("table \"produto\"")) {
                errorMessage = "O produto não pode ser excluído pois há uso em lançamentos. Se desejar, é possível atualizar os dados.";
            } else if (message.contains("fk_subgrupo")) {
                errorMessage = "O subgrupo não pode ser excluído pois há uso em contas. Se desejar, é possível atualizar os dados.";
            } else if (message.contains("fk_grupo")) {
                errorMessage = "O grupo não pode ser excluído pois há uso em subgrupos ou contas. Se desejar, é possível atualizar os dados.";
            } else if (message.contains("foreign key constraint") || message.contains("violates")) {
                errorMessage = "Este registro não pode ser excluído pois há uso em outros registros. Se desejar, é possível atualizar os dados.";
            } else {
                errorMessage = "Não foi possível realizar a operação devido a restrições de integridade. Se desejar, é possível atualizar os dados.";
            }
        } else {
            errorMessage = "Não foi possível realizar a operação devido a restrições de integridade. Se desejar, é possível atualizar os dados.";
        }

        // IMPORTANTE: O campo deve ser "message" e não "error" para o frontend capturar corretamente
        errorResponse.put("message", errorMessage);
        errorResponse.put("error", "DataIntegrityViolationException");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("message", "Erro ao processar requisição: " + ex.getMessage());
        errorResponse.put("error", ex.getClass().getSimpleName());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
