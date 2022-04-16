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

/**
 *
 * @author Felipe
 */
public class ClienteDAO implements DAO<Cliente> {
    
    private static final String QUERY_INSERIR = 
            "INSERT INTO cliente ("
            + "cpf, "
            + "nome, "
            + "sobrenome, "
            + "rg, "
            + "endereco, "
            + "VALUES(?, ?, ?, ?, ?)";
    private static final String QUERY_BUSCAR_TODOS =
            "SELECT "
            + "cpf, "
            + "nome, "
            + "sobrenome, "
            + "rg, "
            + "endereco, "
            + "FROM cliente";
    private static final String QUERY_BUSCA_CPF =
            "SELECT "
            + "cpf, "
            + "nome, "
            + "sobrenome, "
            + "rg, "
            + "endereco, "
            + "FROM cliente "
            + "WHERE cpf=?";
    private static final String QUERY_ATUALIZAR_CPF = 
            "UPDATE cliente SET "
            + "cpf, "
            + "nome, "
            + "sobrenome, "
            + "rg, "
            + "endereco, "
            + "WHERE cpf=?";
    private static final String QUERY_REMOVER = 
            "DELETE FROM cliente "
            + "WHERE cpf=?";
            
    private Connection con = null;
    
    
    public ClienteDAO(Connection con) throws DAOException{
        if(con == null){
            throw new DAOException("Conexão nula ao criar PessoaDAO.");
        }
        this.con = con;
    }


    @Override
    public Cliente buscar(String cpf) throws DAOException {
        Cliente c = new Cliente();
        try{
            PreparedStatement st = con.prepareStatement(QUERY_BUSCA_CPF);
            st.setString(1, cpf);
            ResultSet rs = st.executeQuery();
            rs.next();
            if(rs!=null){
                c.setCpf(rs.getString("cpf"));
                c.setNome(rs.getString("nome"));
                c.setSobrenome(rs.getString("sobrenome"));
                c.setRg(rs.getString("rg"));
                c.setEndereco(rs.getString("endereco"));
            }
            return c;
        }catch(SQLException e){
            throw new DAOException("Erro buscando cliente de cpf "+ cpf+": " + QUERY_BUSCAR_TODOS, e);
        }
    }

    @Override
    public List<Cliente> buscarTodos() throws DAOException {
        List<Cliente> lista = new ArrayList<>();
        try{
            PreparedStatement st = con.prepareStatement(QUERY_BUSCAR_TODOS);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Cliente c = new Cliente();
                c.setCpf(rs.getString("cpf"));
                c.setNome(rs.getString("nome"));
                c.setSobrenome(rs.getString("sobrenome"));
                c.setRg(rs.getString("rg"));
                c.setEndereco(rs.getString("endereco"));
                lista.add(c);
            }
            return lista;
        }catch(SQLException e){
            throw new DAOException("Erro buscando todos os clientes: " + QUERY_BUSCAR_TODOS, e);
        }
    }

    @Override
    public void inserir(Cliente c) throws DAOException {
        try{
            PreparedStatement st = con.prepareStatement(QUERY_INSERIR);
            st.setString(1, c.getCpf());
            st.setString(2, c.getNome());
            st.setString(3, c.getSobrenome());
            st.setString(4, c.getRg());
            st.setString(5, c.getEndereco());
            st.execute();
        } catch(SQLException e){
            throw new DAOException("Erro ao atualizar cliente de cpf "+c.getCpf()+": " + QUERY_INSERIR, e);
        }
    }

    @Override
    public void atualizar(Cliente c) throws DAOException {
        try{
            PreparedStatement st = con.prepareStatement(QUERY_ATUALIZAR_CPF);
            st.setString(1, c.getCpf());
            st.setString(2, c.getNome());
            st.setString(3, c.getSobrenome());
            st.setString(4, c.getRg());
            st.setString(5, c.getEndereco());
            st.executeUpdate();
        } catch(SQLException e){
            throw new DAOException("Erro ao atualizar cliente de cpf "+c.getCpf()+": " + QUERY_ATUALIZAR_CPF, e);
        }
    }

    @Override
    public void remover(Cliente c) throws DAOException {
        String cpfCliente = c.getCpf();
        try{
            PreparedStatement st = con.prepareStatement(QUERY_REMOVER);
            st.setString(1, cpfCliente);
            st.executeUpdate();
        }catch(SQLException e){
            throw new DAOException("Erro ao remover cliente de cpf "+cpfCliente+": " + QUERY_REMOVER, e);
        }
    }
    
}
