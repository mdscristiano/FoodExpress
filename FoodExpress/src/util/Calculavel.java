package util;

/**
 * Interface para padronizar cálculos financeiros no sistema.
 * Requisito 
 * da
 * CP4: Regra de negócio complexa.
 */
public interface Calculavel {
    double calcularDesconto();
    double calcularTotal();
}