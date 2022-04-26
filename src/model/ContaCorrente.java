package model;

import dao.ContaCorrenteDAO;


public class ContaCorrente extends Conta {

    private double limite;

    public ContaCorrente() {
    }

    public ContaCorrente(double limite, Cliente dono) {
        super(dono);
        this.limite = limite;
    }

    public double getLimite() {
        return limite;
    }

    public void setLimite(double limite) {
        this.limite = limite;
    }

    @Override
    public boolean saca(double valor) {
        System.out.println("saldo: " + getSaldo());
        System.out.println("limite: " + this.limite);
        System.out.println("valor: " + valor);
        if ((getSaldo() + this.limite) > valor) {
            System.out.println("Entrou primeiro if corrente");
            double oldSaldo = this.getSaldo();
            if (super.saca(valor)) {
                System.out.println("Entrou segundo if corrente");
                try {
                    new ContaCorrenteDAO().atualizarSaldo(this);
                    return true;
                } catch (Exception e) {
                    System.out.println("Caiu no catch corrente");
                    System.out.println("Mensagem erro: " + e.getMessage());
                    super.setSaldo(oldSaldo);
                }
            }
        }
        return false;
    }

    @Override
    public boolean deposita(double valor) {
        double oldSaldo = this.getSaldo();
        if (super.deposita(valor)) {
            try {
                new ContaCorrenteDAO().atualizarSaldo(this);
                return true;
            } catch (Exception e) {
                super.setSaldo(oldSaldo);
            }
        }
        return false;
    }

    @Override
    public void remunera() {
        this.setSaldo(getSaldo() + (0.01 * getSaldo()));
    }

    @Override
    public void setSaldo(double saldo) {
        double oldSaldo = this.getSaldo();
        super.setSaldo(saldo);

        try {
            new ContaCorrenteDAO().atualizarSaldo(this);
        } catch (Exception e) {
            super.setSaldo(oldSaldo);
        }
    }

}
