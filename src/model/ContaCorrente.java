package model;

/**
 *
 * @authors nickolas & albano
 */
public class ContaCorrente extends Conta{
    private double limite;

    public ContaCorrente() {
    }

    public ContaCorrente(double limite, int numero, Cliente dono) {
        super(numero, dono);
        this.limite = limite;
    }
    
    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }
    
    @Override
    public boolean saca(double valor){
        if((getSaldo()+this.limite) < valor)
            return false;
        return(super.saca(valor));
    }
    
    @Override
    public void remunera() {
       super.setSaldo(getSaldo()+(0.01*getSaldo()));
    }
    
}
