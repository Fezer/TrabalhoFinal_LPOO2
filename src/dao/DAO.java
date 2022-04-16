/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package dao;

import exceptions.DAOException;
import java.util.List;

/**
 *
 * @author Felipe
 */
public interface DAO<T> {
    T buscar(String cpf) throws DAOException;
    List<T> buscarTodos() throws DAOException;
    void inserir(T u) throws DAOException;
    void atualizar(T u) throws DAOException;
    void remover(T u) throws DAOException;
}
