package com.projeto.diariocal;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "alimentos")
public class Alimento {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String nome;
    @NonNull
    private double quantidadeCal;
    @NonNull
    private int unidadeMedida;
    @NonNull
    private String categoria;
    @NonNull
    private boolean alimentoFresco;

    public Alimento(String nome, double quantidadeCal, int unidadeMedida,
                    String categoria, boolean alimentoFresco) {
        this.nome = nome;
        this.quantidadeCal = quantidadeCal;
        this.unidadeMedida = unidadeMedida;
        this.categoria = categoria;
        this.alimentoFresco = alimentoFresco;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    @NonNull
    public double getQuantidadeCal() {
        return quantidadeCal;
    }

    public void setQuantidadeCal(@NonNull double quantidadeCal) {
        this.quantidadeCal = quantidadeCal;
    }

    @NonNull
    public int getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(@NonNull int unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    @NonNull
    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NonNull String categoria) {
        this.categoria = categoria;
    }

    @NonNull
    public boolean isAlimentoFresco() {
        return alimentoFresco;
    }

    public void setAlimentoFresco(@NonNull boolean alimentoFresco) {
        this.alimentoFresco = alimentoFresco;
    }

    @Override
    public String toString() {
        return nome + " - " + quantidadeCal + " calorias a cada 100 " + unidadeMedida;
    }
}
