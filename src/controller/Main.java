/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

/**
 *
 * @author Felipe
 */
import dao.ConnectionFactory;
import dao.ClienteDAO;
import dao.ContaCorrenteDAO;
import dao.ContaInvestimentoDAO;
import view.ClientesView;

/**
 *
 * @author rafae
 */
public class Main {
    public static void main(String[] args){
        ClientesView view = new ClientesView();
        ClienteDAO clienteDao = new ClienteDAO(new ConnectionFactory());
        ContaCorrenteDAO contaCorrenteDAO = new ContaCorrenteDAO(new ConnectionFactory());
        ContaInvestimentoDAO contaInvestimentoDAO = new ContaInvestimentoDAO(new ConnectionFactory());
        BancoController controller = new BancoController(view, clienteDao, contaCorrenteDAO, contaInvestimentoDAO);
        
    }
    
}
