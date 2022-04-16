package model;

import interfaces.ContaI;

/**
 *
 * @authors nickolas & albano
 */
public abstract class Conta implements ContaI{
    private int numero;
    private double saldo;
    private Cliente dono;

    public Conta() {
    }

    public Conta(int numero, Cliente dono) {
        this.dono = dono;
        this.numero = numero;
        this.saldo = 0;
    }
   
    @Override
    public boolean deposita(double valor) {
        if(valor  < 0.01)
            return false;
        
        this.saldo += valor;
        return true;
    }

    @Override
    public boolean saca(double valor) {
        if(valor  < 0.01)
            return false;
        this.saldo -= valor;
        return true;
    }

    public void setDono(Cliente dono) {
        this.dono = dono;
    }
    
    @Override
    public Cliente getDono() {     
        return this.dono;     
    }

    @Override
    public int getNumero() {
        return this.numero;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    @Override
    public double getSaldo() {
        return this.saldo;
    }
}
