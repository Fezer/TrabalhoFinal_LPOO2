package view;

import controller.Usability;
import model.Cliente;
import model.Conta;
import model.ContaCorrente;
import model.ContaInvestimento;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import javax.swing.table.AbstractTableModel;
import dao.*;

public class ContasTableModel extends AbstractTableModel {
    private final String[] colunas=new String[]{"Número","Tipo conta", "Nome", "CPF","Depósito minimo", "Montante minimo"};

    private List<Conta> lista = Usability.contas;
	private ClienteDAO clienteDao = new ClienteDAO();
	private ContaCorrenteDAO correnteDao = new ContaCorrenteDAO();
	private ContaInvestimentoDAO investimentoDao = new ContaInvestimentoDAO();

	public ContasTableModel() {
		try {
			List<ContaCorrente> correntes = correnteDao.buscarTodos();
			List<ContaInvestimento> investimentos = investimentoDao.buscarTodos();
			List<Conta> contas = new ArrayList<Conta>();
			contas.addAll(correntes);
			contas.addAll(investimentos);
			this.atualizarTabela(contas);
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
        Conta customer = lista.get(rowIndex);
		
        ContaInvestimento customerI= null;
        if(customer instanceof ContaInvestimento){
            customerI = (ContaInvestimento)customer;
			try {
				customer = investimentoDao.buscar(customer.getNumero());
			} catch(Exception e) {
			}
			
        } else {
			try {
				customer = correnteDao.buscar(customer.getNumero());
			} catch(Exception e) {
			}
		}
            
        switch (columnIndex) {
            case 0: return customer.getNumero();//if column 1 (name)
            case 1: return (customer instanceof ContaCorrente ? "Conta corrente": "Conta investimento");//if column 2 (birthday)
            case 2: return (customer.getDono().getNome()+" "+customer.getDono().getSobrenome());
            case 3: return customer.getDono().getCpf();
            case 4: return (customer instanceof ContaInvestimento ? customerI.getDepositoMin() : "-");
            case 5: return (customer instanceof ContaInvestimento ? customerI.getMontanteMin(): "-");
            default : return null;
        }
    }

    public void adicionaContaCorrente(Conta customer) {
		try {
			this.correnteDao.inserir((ContaCorrente) customer);
			this.lista.add(customer);
			this.fireTableRowsInserted(lista.size()-1,lista.size()-1);//update JTable
			System.out.println(this.lista.size());
		} catch (Exception e) {
		}
    }

	public void adicionaContaInvestimento(Conta customer) {
        try {
			this.investimentoDao.inserir((ContaInvestimento) customer);
			this.lista.add(customer);
			this.fireTableRowsInserted(lista.size()-1,lista.size()-1);//update JTable
			System.out.println(this.lista.size());
		} catch (Exception e) {
		}
    }
    
    public boolean removeConta(Cliente customer) {       
        int linha = IntStream.range(0, lista.size())
                    .filter(i -> lista.get(i).getDono().equals(customer))
                    .findFirst()
                    .orElse(-1);
        boolean result = this.lista.remove(lista.get(linha));
        this.fireTableRowsDeleted(linha,linha);//update JTable
        return result;
    }
    
    public void atualizaConta(int linha, Conta c) {
        this.lista.set(linha, c);
        this.fireTableDataChanged();
    }

    public void atualizarTabela(List<Conta> lista){
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

    public Conta getConta(int linha){
        return lista.get(linha);
    }
}
