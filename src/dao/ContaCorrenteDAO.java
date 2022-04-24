/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ContaCorrente;
import model.Cliente;
import model.Conta;

/**
 *
 * @author Felipe
 */
public class ContaCorrenteDAO {

    private static final String table = "ContaCorrente";

    private static final String QUERY_INSERIR
            = "INSERT INTO " + table + " ("
            + "numero, "
            + "limite, "
            + "saldo, "
            + "cpfCliente) "
            + "VALUES(?, ?, ?, ?)";

    private static final String QUERY_BUSCAR_TODOS = "SELECT * FROM " + table;

    private static final String QUERY_BUSCA_NUMERO = "SELECT * FROM " + table + " WHERE numero=?";
    
    private static final String QUERY_BUSCA_CPF = "SELECT * FROM " + table + " WHERE cpfCliente=?";

    private static final String QUERY_ATUALIZAR_NUMERO
            = "UPDATE " + table + " SET "
            + "numero=?, "
            + "limite=?, "
            + "saldo=?, "
            + "cpfCliente=? "
            + "WHERE numero=?";

    private static final String QUERY_ATUALIZAR_SALDO
            = "UPDATE " + table + " SET "
            + "saldo=? "
            + "WHERE numero=?";

    private static final String QUERY_REMOVER = "DELETE FROM " + table + " WHERE numero=?";

    private Connection con = null;
    private ConnectionFactory connectionFactory = new ConnectionFactory();
    
    public ContaCorrenteDAO(ConnectionFactory conFactory) {
        this.connectionFactory = conFactory;
    }

    public ContaCorrenteDAO() {
        try {
            this.con = this.connectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //    TO-DO: Entender como correlacionar cliente com conta. Model conta correlaciona-se com cliente através do objeto e BD a correlação é pelo cpf.
    public ContaCorrente buscar(int numero) throws DAOException {
        ContaCorrente c = new ContaCorrente();
        try {
            PreparedStatement st = con.prepareStatement(QUERY_BUSCA_NUMERO);
            st.setInt(1, numero);
            ResultSet rs = st.executeQuery();
            rs.next();
            if (rs != null) {
                c.setNumero(rs.getInt("numero"));
                c.setLimite(rs.getDouble("limite"));
                c.setSaldo(rs.getDouble("saldo"));

                String cpf = rs.getString("cpfCliente");
                ClienteDAO clienteDao = new ClienteDAO();
                Cliente cliente = clienteDao.buscar(cpf);
                c.setDono(cliente);
            }
            return c;
        } catch (SQLException e) {
            throw new DAOException("Erro buscando conta de numero " + numero + ": " + QUERY_BUSCAR_TODOS, e);
        }
    }
    
    public ContaCorrente buscarPorCpf(String cpf) throws DAOException {
        ContaCorrente c = new ContaCorrente();
        try {
            PreparedStatement st = con.prepareStatement(QUERY_BUSCA_CPF);
            st.setString(1, cpf);
            ResultSet rs = st.executeQuery();
            rs.next();
            if (rs != null) {
                c.setNumero(rs.getInt("numero"));
                c.setLimite(rs.getDouble("limite"));
                c.setSaldo(rs.getDouble("saldo"));
                ClienteDAO clienteDao = new ClienteDAO();
                Cliente cliente = clienteDao.buscar(cpf);
                c.setDono(cliente);
            }
            return c;
        } catch (SQLException e) {
            throw new DAOException("Erro buscando conta associada ao cpf " + cpf + ": " + QUERY_BUSCA_CPF, e);
        }
    }

    //    TO-DO: Entender como correlacionar cliente com conta. Model conta correlaciona-se com cliente através do objeto e BD a correlação é pelo cpf.
    public List<ContaCorrente> buscarTodos() throws DAOException {
        List<ContaCorrente> lista = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement(QUERY_BUSCAR_TODOS);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                ContaCorrente c = new ContaCorrente();
                c.setNumero(rs.getInt("numero"));
                c.setLimite(rs.getDouble("limite"));
                c.setSaldo(rs.getDouble("saldo"));

                String cpf = rs.getString("cpfCliente");
                ClienteDAO clienteDao = new ClienteDAO();
                Cliente cliente = clienteDao.buscar(cpf);
                c.setDono(cliente);

                lista.add(c);
            }
            return lista;
        } catch (SQLException e) {
            throw new DAOException("Erro buscando todos as contas correntes: " + QUERY_BUSCAR_TODOS, e);
        }
    }

    public void inserir(ContaCorrente conta) throws DAOException {
        try {
            PreparedStatement st = con.prepareStatement(QUERY_INSERIR);
            st.setInt(1, conta.getNumero());
            st.setDouble(2, conta.getLimite());
            st.setDouble(3, conta.getSaldo());
            st.setString(4, conta.getDono().getCpf());
            st.execute();
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir conta de numero " + conta.getNumero() + ": " + QUERY_INSERIR, e);
        }
    }

    public void atualizar(ContaCorrente conta) throws DAOException {
        try {
            PreparedStatement st = con.prepareStatement(QUERY_ATUALIZAR_NUMERO);
            st.setInt(1, conta.getNumero());
            st.setDouble(2, conta.getLimite());
            st.setDouble(3, conta.getSaldo());
            st.setString(4, conta.getDono().getCpf());
            st.setInt(5, conta.getNumero());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar conta de numero " + conta.getNumero() + ": " + QUERY_ATUALIZAR_NUMERO, e);
        }
    }

    public void atualizarSaldo(Conta conta) throws DAOException {
        try {
            PreparedStatement st = con.prepareStatement(QUERY_ATUALIZAR_SALDO);
            st.setDouble(1, conta.getSaldo());
            st.setInt(2, conta.getNumero());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar o saldo da conta de numero " + conta.getNumero() + ": " + QUERY_ATUALIZAR_SALDO, e);
        }
    }

    public void remover(ContaCorrente conta) throws DAOException {
        int numero = conta.getNumero();
        try {
            PreparedStatement st = con.prepareStatement(QUERY_REMOVER);
            st.setInt(1, numero);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao remover conta de numero " + numero + ": " + QUERY_REMOVER, e);
        }
    }

}
