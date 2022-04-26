package model;

import dao.ContaInvestimentoDAO;


public class ContaInvestimento extends Conta {

    private double depositoMin;
    private double montanteMin;

    public ContaInvestimento() {
    }

    public ContaInvestimento(double depositoMin, double montanteMin, Cliente dono) {
        super(dono);
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
        if ((getSaldo() - valor) >= this.montanteMin) {
            double oldSaldo = this.getSaldo();
            if (super.saca(valor)) {
                try {
                    new ContaInvestimentoDAO().atualizarSaldo(this);
                    return true;
                } catch (Exception e) {
                    super.setSaldo(oldSaldo);
                }
            }
        }
        return false;
    }

    @Override
    public boolean deposita(double valor) {
        if (valor >= this.depositoMin) {
            double oldSaldo = this.getSaldo();
            if (super.deposita(valor)) {
                try {
                    new ContaInvestimentoDAO().atualizarSaldo(this);
                    return true;
                } catch (Exception e) {
                    super.setSaldo(oldSaldo);
                }
            }
        }
        return false;
    }

    @Override
    public void remunera() {
        this.setSaldo(getSaldo() + (getSaldo() * 0.02));
    }

    @Override
    public void setSaldo(double saldo) {
        double oldSaldo = this.getSaldo();
        super.setSaldo(saldo);

        try {
            new ContaInvestimentoDAO().atualizarSaldo(this);
        } catch (Exception e) {
            super.setSaldo(oldSaldo);
        }
    }
}
