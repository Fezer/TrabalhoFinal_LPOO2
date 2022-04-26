/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Cliente;
import exceptions.DAOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.ContaInvestimento;
import model.ContaCorrente;



public class ClienteDAO implements DAO<Cliente> {

    private static ContaCorrenteDAO correnteDao = new ContaCorrenteDAO();
    private static ContaInvestimentoDAO investimentoDao = new ContaInvestimentoDAO();

    private static final String table = "Cliente";

    private static final String QUERY_INSERIR
            = "INSERT INTO " + table + " ("
            + "cpf, "
            + "nome, "
            + "sobrenome, "
            + "rg, "
            + "endereco) "
            + "VALUES(?, ?, ?, ?, ?)";

    private static final String QUERY_BUSCAR_TODOS = "SELECT * FROM " + table;

    private static final String QUERY_BUSCA_CPF = "SELECT * FROM " + table + " WHERE cpf=?";

    private static final String QUERY_BUSCAR_CORRENTE = "SELECT * FROM ContaCorrente WHERE cpfCliente=?";

    private static final String QUERY_BUSCAR_INVESTIMENTO = "SELECT * FROM ContaInvestimento WHERE cpfCliente=?";

    private static final String QUERY_ATUALIZAR_CPF
            = "UPDATE " + table + " SET "
            + "cpf=?, "
            + "nome=?, "
            + "sobrenome=?, "
            + "rg=?, "
            + "endereco=? "
            + "WHERE cpf=?";

    private static final String QUERY_REMOVER = "DELETE FROM " + table + " WHERE cpf=?";

    private ConnectionFactory connectionFactory = new ConnectionFactory();
    private Connection con;
    
    public ClienteDAO(ConnectionFactory conFactory) {
        this.connectionFactory = conFactory;
    }

    public ClienteDAO() {
        try {
            this.con = this.connectionFactory.getConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente buscar(String cpf) throws DAOException {
        Cliente c = new Cliente();
        try {
            PreparedStatement st = con.prepareStatement(QUERY_BUSCA_CPF);
            st.setString(1, cpf);
            ResultSet rs = st.executeQuery();
            rs.next();
            if (rs != null) {
                c.setCpf(rs.getString("cpf"));
                c.setNome(rs.getString("nome"));
                c.setSobrenome(rs.getString("sobrenome"));
                c.setRg(rs.getString("rg"));
                c.setEndereco(rs.getString("endereco"));

                if (this.buscarInvestimento(rs.getString("cpf")) || this.buscarCorrente(rs.getString("cpf"))) {
                    c.setPossuiConta(true);
                } else {
                    c.setPossuiConta(false);
                }

            }
            return c;
        } catch (SQLException e) {
            throw new DAOException("Erro buscando cliente de cpf " + cpf + ": " + QUERY_BUSCAR_TODOS, e);
        }
    }

    @Override
    public List<Cliente> buscarTodos() throws DAOException {
        List<Cliente> lista = new ArrayList<>();
        try {
            PreparedStatement st = con.prepareStatement(QUERY_BUSCAR_TODOS);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setCpf(rs.getString("cpf"));
                c.setNome(rs.getString("nome"));
                c.setSobrenome(rs.getString("sobrenome"));
                c.setRg(rs.getString("rg"));
                c.setEndereco(rs.getString("endereco"));

                if (this.buscarInvestimento(rs.getString("cpf")) || this.buscarCorrente(rs.getString("cpf"))) {
                    c.setPossuiConta(true);
                } else {
                    c.setPossuiConta(false);
                }

                lista.add(c);
            }
            return lista;
        } catch (SQLException e) {
            throw new DAOException("Erro buscando todos os clientes: " + QUERY_BUSCAR_TODOS, e);
        }
    }

    public boolean buscarCorrente(String cpf) throws DAOException {
        try {
            PreparedStatement st = con.prepareStatement(QUERY_BUSCAR_CORRENTE);
            st.setString(1, cpf);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar Conta Corrente do cpf " + cpf + ": " + QUERY_INSERIR, e);
        }

        return false;
    }

    public boolean buscarInvestimento(String cpf) throws DAOException {
        try {
            PreparedStatement st = con.prepareStatement(QUERY_BUSCAR_INVESTIMENTO);
            st.setString(1, cpf);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new DAOException("Erro ao buscar Conta Investimento do cpf " + cpf + ": " + QUERY_INSERIR, e);
        }

        return false;
    }

    @Override
    public void inserir(Cliente c) throws DAOException {
        try {
            PreparedStatement st = con.prepareStatement(QUERY_INSERIR);
            st.setString(1, c.getCpf());
            st.setString(2, c.getNome());
            st.setString(3, c.getSobrenome());
            st.setString(4, c.getRg());
            st.setString(5, c.getEndereco());
            st.execute();
        } catch (SQLException e) {
            throw new DAOException("Erro ao inserir cliente de cpf " + c.getCpf() + ": " + QUERY_INSERIR, e);
        }
    }

    @Override
    public void atualizar(Cliente c) throws DAOException {
        try {
            PreparedStatement st = con.prepareStatement(QUERY_ATUALIZAR_CPF);
            st.setString(1, c.getCpf());
            st.setString(2, c.getNome());
            st.setString(3, c.getSobrenome());
            st.setString(4, c.getRg());
            st.setString(5, c.getEndereco());
            st.setString(6, c.getCpf());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao atualizar cliente de cpf " + c.getCpf() + ": " + QUERY_ATUALIZAR_CPF, e);
        }
    }

    @Override
    public void remover(Cliente c) throws DAOException {
        String cpfCliente = c.getCpf();
        try {
            PreparedStatement st = con.prepareStatement(QUERY_REMOVER);
            st.setString(1, cpfCliente);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Erro ao remover cliente de cpf " + cpfCliente + ": " + QUERY_REMOVER, e);
        }
    }

}
