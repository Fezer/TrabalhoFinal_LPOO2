package model;

/**
 *
 * @authors nickolas & albano
 */
public class ContaInvestimento extends Conta{
    private double depositoMin;
    private double montanteMin;

    public ContaInvestimento() {
    }

    public ContaInvestimento(double depositoMin, double montanteMin, int numero, Cliente dono) {
        super(numero, dono);
        this.depositoMin = depositoMin;
        this.montanteMin = montanteMin;
    }
    
    public double getDepositoMin() {
        return depositoMin;
    }

    public void setDepositoMin(double depositoMin) {
        this.depositoMin = depositoMin;
    }

    public double getMontanteMin() {
        return montanteMin;
    }

    public void setMontanteMin(double montanteMin) {
        this.montanteMin = montanteMin;
    }
    
    @Override
    public boolean saca(double valor) {
       if((getSaldo()-valor) >= this.montanteMin)
           return(super.saca(valor));
       return false;
    }    
    
    @Override
    public boolean deposita(double valor) {
        if(valor >= this.depositoMin)
            return (super.deposita(valor));
        return false;
    }
    
    @Override
    public void remunera() {       
       super.setSaldo(getSaldo()+(getSaldo()*0.02));
    }
    
    
}
