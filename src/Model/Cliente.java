package Model;
/**
 *
 */
public class Cliente implements Comparable{
    private String nome;
    private String sobrenome;
    private String rg;
    private String cpf;
    private String endereço;
    private boolean possuiConta = false;

    public Cliente(String nome, String sobrenome, String rg, String cpf, String endereço) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.rg = rg;
        this.cpf = cpf;
        this.endereço = endereço;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereço() {
        return endereço;
    }

    public void setEndereço(String endereço) {
        this.endereço = endereço;
    }

    public boolean isPossuiConta() {
        return possuiConta;
    }

    public void setPossuiConta(boolean possuiConta) {
        this.possuiConta = possuiConta;
    }
    
    @Override
    public int compareTo(Object o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }   
    
}
