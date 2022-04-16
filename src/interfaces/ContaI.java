package interfaces;

import model.Cliente;

/**
 * 
 * @author albano & nickolas
 */
public interface ContaI {
    public boolean deposita(double valor);
    public boolean saca(double valor);
    public Cliente getDono();
    public int getNumero();
    public double getSaldo();
    public void remunera();
}
