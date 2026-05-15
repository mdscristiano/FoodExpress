package util;

/**
 * Interface para classes que precisam registrar histórico de ações.
 * Requisito 
 * da 
 * CP4: Implementar interfaces.
 */
public interface Auditavel {
    void registrarLog(String acao);
    String obterHistorico();
}