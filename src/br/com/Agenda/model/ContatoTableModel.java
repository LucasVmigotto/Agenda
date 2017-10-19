package br.com.Agenda.model;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.com.Agenda.dao.DaoContato;

@SuppressWarnings("serial")
public class ContatoTableModel extends AbstractTableModel{
	String[] colunas;
	List<Contato> lista;
	
	public ContatoTableModel() {
		colunas=new String[]{"Nome","Telefone"};
		lista=new DaoContato().listarTodos();
	}
	
	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	@Override
	public int getRowCount() {
		return lista.size();
	}

	@Override
	public Object getValueAt(int numeroLinha, int numeroColuna) {
		switch (numeroColuna) {
			case 0:
			return lista.get(numeroLinha).getNome();
			case 1:
			return lista.get(numeroLinha).getNumeroTelefone();
		}
		return null;
	}
	
	@Override
	public String getColumnName(int indice) {
		return colunas[indice];
	}
	
	@Override
	public boolean isCellEditable(int linha, int coluna) {
		return false;
	}
	
	public Contato getModel(int linha){
		if(linha!=-1){
			return lista.get(linha);
		}else{
			return null;
		}
	}
	
	public void atualizarTabela(){
		lista=new DaoContato().listarTodos();
		fireTableDataChanged();
	}
	
	public void apenasFavoritos(){
		lista=new DaoContato().listarApenasFavoritos();
		fireTableDataChanged();
	}

}
