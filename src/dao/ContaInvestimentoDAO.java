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
import model.ContaInvestimento;

/**
 *
 * @author Felipe
 */
public class ContaInvestimentoDAO {
    
    private static final String QUERY_INSERIR = 
            "INSERT INTO ContaInvestimento ("
            + "numero, "
            + "depositoMinimo, "
            + "montanteMinimo, "
            + "saldo, "
            + "cpfCliente, "
            + "VALUES(?, ?, ?, ?, ?)";
    private static final String QUERY_BUSCAR_TODOS =
            "SELECT "
            + "numero, "
            + "depositoMinimo, "
            + "montanteMinimo, "
            + "saldo, "
            + "cpfCliente, "
            + "FROM ContaInvestimento";
    private static final String QUERY_BUSCA_NUMERO =
            "SELECT "
            + "numero, "
            + "depositoMinimo, "
            + "montanteMinimo, "
            + "saldo, "
            + "cpfCliente, "
            + "FROM ContaInvestimento"
            + "WHERE numero=?";
    private static final String QUERY_ATUALIZAR_NUMERO = 
            "UPDATE ContaInvestimento SET "
            + "numero=?, "
            + "depositoMinimo=?, "
            + "montanteMinimo=?, "
            + "saldo=?, "
            + "cpfCliente=?, "
            + "WHERE numero=?";
    private static final String QUERY_REMOVER = 
            "DELETE FROM ContaInvestimento "
            + "WHERE numero=?";
    
    private Connection con = null;
    
    public ContaInvestimentoDAO(Connection con) throws DAOException{
        if(con == null){
            throw new DAOException("Conexão nula ao criar ContaCorrenteDAO.");
        }
        this.con = con;
    }

    //    TO-DO: Entender como correlacionar cliente com conta. Model conta correlaciona-se com cliente através do objeto e BD a correlação é pelo cpf.
    public ContaInvestimento buscar(int numero) throws DAOException {
        ContaInvestimento c = new ContaInvestimento();
        try{
            PreparedStatement st = con.prepareStatement(QUERY_BUSCA_NUMERO);
            st.setInt(1, numero);
            ResultSet rs = st.executeQuery();
            rs.next();
            if(rs!=null){
                c.setNumero(rs.getInt("numero"));
                c.setDepositoMin(rs.getDouble("depositoMinimo"));
                c.setMontanteMin(rs.getDouble("montanteMinimo"));
                c.setSaldo(rs.getDouble("saldo"));
            }
            return c;
        }catch(SQLException e){
            throw new DAOException("Erro buscando conta de numero "+ numero+": " + QUERY_BUSCAR_TODOS, e);
        }
    }

    //    TO-DO: Entender como correlacionar cliente com conta. Model conta correlaciona-se com cliente através do objeto e BD a correlação é pelo cpf.
    public List<ContaInvestimento> buscarTodos() throws DAOException {
        List<ContaInvestimento> lista = new ArrayList<>();
        try{
            PreparedStatement st = con.prepareStatement(QUERY_BUSCAR_TODOS);
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                ContaInvestimento c = new ContaInvestimento();
                c.setNumero(rs.getInt("numero"));
                c.setDepositoMin(rs.getDouble("depositoMinimo"));
                c.setMontanteMin(rs.getDouble("montanteMinimo"));
                c.setSaldo(rs.getDouble("saldo"));
                lista.add(c);
            }
            return lista;
        }catch(SQLException e){
            throw new DAOException("Erro buscando todos as contas correntes: " + QUERY_BUSCAR_TODOS, e);
        }
    }

    public void inserir(ContaInvestimento conta) throws DAOException {
        try{
            PreparedStatement st = con.prepareStatement(QUERY_INSERIR);
            st.setInt(1, conta.getNumero());
            st.setDouble(2, conta.getDepositoMin());
            st.setDouble(3, conta.getMontanteMin());
            st.setDouble(4, conta.getSaldo());
            st.setString(5, conta.getDono().getCpf());
            st.execute();
        } catch(SQLException e){
            throw new DAOException("Erro ao inserir conta de numero "+conta.getNumero()+": " + QUERY_INSERIR, e);
        }
    }

    public void atualizar(ContaInvestimento conta) throws DAOException {
        try{
            PreparedStatement st = con.prepareStatement(QUERY_ATUALIZAR_NUMERO);
            st.setInt(1, conta.getNumero());
            st.setDouble(2, conta.getDepositoMin());
            st.setDouble(3, conta.getMontanteMin());
            st.setDouble(4, conta.getSaldo());
            st.setString(5, conta.getDono().getCpf());
            st.setInt(6, conta.getNumero());
            st.executeUpdate();
        } catch(SQLException e){
            throw new DAOException("Erro ao atualizar conta de numero "+conta.getNumero()+": " + QUERY_ATUALIZAR_NUMERO, e);
        }
    }

    public void remover(ContaInvestimento conta) throws DAOException {
        int numero = conta.getNumero();
        try{
            PreparedStatement st = con.prepareStatement(QUERY_REMOVER);
            st.setInt(1, numero);
            st.executeUpdate();
        }catch(SQLException e){
            throw new DAOException("Erro ao remover conta de numero "+numero+": " + QUERY_REMOVER, e);
        }
    }
    
}