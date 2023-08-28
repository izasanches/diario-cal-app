package com.projeto.diariocal;

public enum UnidadeMedida {
    GRAMA(0),
    MILILITRO(1);

    public final int value;

    UnidadeMedida(int value) {
        this.value = value;
    }
}
