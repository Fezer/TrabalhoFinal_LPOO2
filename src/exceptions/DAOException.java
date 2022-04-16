/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package exceptions;

import java.sql.SQLException;

/**
 *
 * @author Felipe
 */
public class DAOException extends Exception {

    public DAOException(){
        super("Causa do Erro Desconhecida");
    }
    
    public DAOException(String msg, SQLException e) {
        super(msg+" / "+e.getMessage());
    }

    public DAOException(String msg, ClassNotFoundException e) {
        super(msg+" / "+e.getMessage());
    }

    public DAOException(String msg) {
        super(msg);
    }
    
}
