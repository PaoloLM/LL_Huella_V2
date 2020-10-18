package com.desarrollo.ll_huella;

public class ClsPromedio {
    private int indice;
    private float promedio;

    public ClsPromedio(int indice, float promedio) {
        this.indice = indice;
        this.promedio = promedio;
    }

    public int getIndice() {
        return indice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public float getPromedio() {
        return promedio;
    }

    public void setPromedio(float promedio) {
        this.promedio = promedio;
    }
}
