package com.projeto.diariocal;

public class Alimento {
    private String nome;
    private double quantidadeCal;
    private UnidadeMedida unidadeMedida;
    private String categoria;
    private boolean alimentoFresco;

    public Alimento(String nome, double quantidadeCal, UnidadeMedida unidadeMedida,
                    String categoria, boolean alimentoFresco) {
        this.nome = nome;
        this.quantidadeCal = quantidadeCal;
        this.unidadeMedida = unidadeMedida;
        this.categoria = categoria;
        this.alimentoFresco = alimentoFresco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getQuantidadeCal() {
        return quantidadeCal;
    }

    public void setQuantidadeCal(double quantidadeCal) {
        this.quantidadeCal = quantidadeCal;
    }

    public UnidadeMedida getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(UnidadeMedida unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isAlimentoFresco() {
        return alimentoFresco;
    }

    public void setAlimentoFresco(boolean alimentoFresco) {
        this.alimentoFresco = alimentoFresco;
    }

    @Override
    public String toString() {
        return nome + " - " + quantidadeCal + " calorias a cada 100 " + unidadeMedida;
    }
}
