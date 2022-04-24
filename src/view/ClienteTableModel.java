package view;

import controller.BancoController;
import model.Cliente;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import dao.ClienteDAO;
import exceptions.DAOException;

public class ClienteTableModel extends AbstractTableModel {
    private final String[] colunas=new String[]{"Nome", "Sobrenome","Endereço" ,"CPF","RG", "Possui Conta"};

    private List<Cliente> lista = BancoController.clientes;
	private ClienteDAO clienteDao = new ClienteDAO();

	public ClienteTableModel(){
		try {
			List<Cliente> clientes = clienteDao.buscarTodos();
			this.atualizarTabela(clientes);
		} catch (Exception e) {
		}
	}

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return this.colunas.length;
    }

    @Override
    public String getColumnName(int index) {
        return this.colunas[index];
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
        //if(column==0)
        //    return false;
        //return true;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cliente customer = lista.get(rowIndex);
        switch (columnIndex) {
            case 0: return customer.getNome();//if column 1 (name)
            case 1: return customer.getSobrenome();//if column 2 (birthday)
            case 2: return customer.getEndereco();
            case 3: return customer.getCpf();
            case 4: return customer.getRg();
            case 5: return (customer.isPossuiConta() ?  "Sim":"Não");
            default : return null;
        }
    }

    public void adicionaCliente(Cliente customer){
		try {
			this.clienteDao.inserir(customer);
			this.lista.add(customer);
			this.fireTableRowsInserted(lista.size()-1,lista.size()-1);//update JTable
			System.out.println(this.lista.size());
		} catch (Exception e) {
		}
    }
    
    public boolean removeCliente(Cliente customer){
		try {
			this.clienteDao.remover(customer);
			int linha = this.lista.indexOf(customer);
			boolean result = this.lista.remove(customer);
			this.fireTableRowsDeleted(linha,linha);//update JTable
			return result;
		} catch (Exception e) {
			return false;
		}
    }
    
    public void atualizaCliente(int linha, Cliente c){
		try {
			this.clienteDao.atualizar(c);
			this.lista.set(linha, c);
			this.fireTableDataChanged();
		} catch (Exception e) {
		}
    }

    public void atualizarTabela(List<Cliente> lista){
        this.lista = new ArrayList();
        this.lista.addAll(lista);
        this.fireTableDataChanged();
    }

    public void limpaTabela() {
        int indice = lista.size()-1;
        if(indice<0)
            indice=0;
        this.lista = new ArrayList();
        this.fireTableRowsDeleted(0,indice);//update JTable
    }

    public Cliente getCliente(int linha){
        return lista.get(linha);
    }
}
