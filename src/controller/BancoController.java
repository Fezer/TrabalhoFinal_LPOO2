package controller;

import model.Cliente;
import model.Conta;
import java.util.ArrayList;
import java.util.List;
import dao.ClienteDAO;
import dao.ContaCorrenteDAO;
import dao.ContaInvestimentoDAO;
import view.ClientesView;

/**
 *
 * @author albano & nickolas
 */
public class BancoController {

    public static List<Conta> contas = new ArrayList<>();
    public static List<Cliente> clientes = new ArrayList<>();
    
    private ClientesView view;
    private ClienteDAO clienteDAO;
    private ContaCorrenteDAO contaCorrenteDAO;
    private ContaInvestimentoDAO contaInvestimentoDAO;
    
    public BancoController(ClientesView view, ClienteDAO clienteDAO, ContaCorrenteDAO contaCorrenteDAO, ContaInvestimentoDAO contaInvestimentoDAO) {
        this.view = view;
        this.clienteDAO = clienteDAO;
        this.contaCorrenteDAO = contaCorrenteDAO;
        this.contaInvestimentoDAO = contaInvestimentoDAO;
        initController();
    }
    
    private void initController(){
        this.view.setController(this);
        //this.view.initView();
    }
    
}
