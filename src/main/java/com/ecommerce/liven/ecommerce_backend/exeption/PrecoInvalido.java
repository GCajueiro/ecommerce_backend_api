package com.ecommerce.liven.ecommerce_backend.exeption;

public class PrecoInvalido extends RuntimeException{
    public String InvalidPrice(){
        return "Preço inválido";
    }
}
