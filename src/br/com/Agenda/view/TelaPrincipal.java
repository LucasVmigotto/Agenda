package br.com.Agenda.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.Agenda.dao.DaoContato;
import br.com.Agenda.model.Contato;
import br.com.Agenda.model.ContatoTableModel;
import br.com.Agenda.util.GeradorDeArquivo;

public class TelaPrincipal {
	private JFrame telaPrincipal;
	private ContatoTableModel tabelaDeContatos;
	private JTable tabela;
	private JLabel lblNome, lblNumeroTelefone, lblTitulo, lblFavorito, lblApenasFavoritos;
	private JTextField txtNome, txtNumeroTelefone;
	private JScrollPane pnlRolagem;
	private JButton btnSalvar, btnExcluir, btnExportarContatos;
	private JCheckBox ckFavorito, ckApenasFavoritos;
	private Contato contato;
	
	public TelaPrincipal() {
		iniciarComponentes();
		iniciarAcoes();
	}
	
	private void iniciarComponentes(){
		lblTitulo=new JLabel(new ImageIcon("titulo.png"));
		lblTitulo.setBounds(20, 40, 210, 40);
		
		lblNome=new JLabel("Nome:");
		lblNome.setBounds(20, 110, 90, 25);
		
		txtNome=new JTextField();
		txtNome.setBounds(90, 110, 150, 25);
		
		lblNumeroTelefone=new JLabel("Telefone:");
		lblNumeroTelefone.setBounds(20, 160, 90, 25);
		
		txtNumeroTelefone=new JTextField();
		txtNumeroTelefone.setBounds(90, 160, 150, 25);
		
		tabelaDeContatos=new ContatoTableModel();
		tabela=new JTable(tabelaDeContatos);
		tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		pnlRolagem=new JScrollPane(tabela);
		pnlRolagem.setBounds(270, 30, 280, 260);
		
		lblApenasFavoritos=new JLabel("Mostrar favoritos primeiro");
		lblApenasFavoritos.setBounds(300, 302, 160, 25);
		
		ckApenasFavoritos=new JCheckBox();
		ckApenasFavoritos.setBounds(270, 305, 18, 18);
		ckApenasFavoritos.setSelected(false);
		
		lblFavorito=new JLabel("Favorito");
		lblFavorito.setBounds(20, 200, 90, 25);
		
		ckFavorito=new JCheckBox();
		ckFavorito.setBounds(90, 205, 18, 18);
		ckFavorito.setOpaque(true);
		
		btnSalvar=new JButton("Salvar");
		btnSalvar.setBounds(20, 240, 110, 40);
		
		btnExcluir=new JButton("Excluir");
		btnExcluir.setBounds(130, 240, 110, 40);
		
		btnExportarContatos=new JButton("Exportar Contatos");
		btnExportarContatos.setBounds(20, 290, 220, 40);
		
		telaPrincipal=new JFrame("Agenda");
		telaPrincipal.setSize(600, 400);
		telaPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		telaPrincipal.setLocationRelativeTo(null);
		telaPrincipal.setResizable(false);
		telaPrincipal.setLayout(null);
		telaPrincipal.getContentPane().setBackground(Color.LIGHT_GRAY);
		telaPrincipal.add(lblTitulo);
		telaPrincipal.add(lblNome);
		telaPrincipal.add(lblNumeroTelefone);
		telaPrincipal.add(txtNome);
		telaPrincipal.add(txtNumeroTelefone);
		telaPrincipal.add(pnlRolagem);
		telaPrincipal.add(lblApenasFavoritos);
		telaPrincipal.add(ckApenasFavoritos);
		telaPrincipal.add(lblFavorito);
		telaPrincipal.add(ckFavorito);
		telaPrincipal.add(btnSalvar);
		telaPrincipal.add(btnExcluir);
		telaPrincipal.add(btnExportarContatos);
		telaPrincipal.setVisible(true);
	}
	
	private void limparFormulario(){
		txtNome.setText("");
		txtNumeroTelefone.setText("");
		ckFavorito.setSelected(false);
	}
	
	private void atualizarTabela(){
		tabelaDeContatos.atualizarTabela();
	}
	
	private boolean validarFormulario(){
		if(txtNome.getText().trim().isEmpty()){
			JOptionPane.showMessageDialog(telaPrincipal, "Campo Obrigatorio não preenchido!");
			txtNome.requestFocus();
			return false;
		}else{
			return true;
		}
	}
	
	private void apenasFavoritos(){
		if(ckApenasFavoritos.isSelected()){
			tabelaDeContatos.apenasFavoritos();
		}else{
			atualizarTabela();
		}
	}
	
	private void iniciarAcoes(){
		btnSalvar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(validarFormulario()){
					Contato c=new Contato();
					c.setNome(txtNome.getText().trim());
					c.setNumeroTelefone(txtNumeroTelefone.getText().trim());
					c.setFavorito(ckFavorito.isSelected());
					if(contato==null){
						new DaoContato().criar(c);
					}else{
						c.setId(contato.getId());
						new DaoContato().atualizar(c);
					}
					limparFormulario();
					atualizarTabela();
				}
			}
		});
		
		btnExcluir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(contato!=null){
					new DaoContato().deletar(contato.getId());
					limparFormulario();
					atualizarTabela();
				}else{
					JOptionPane.showMessageDialog(telaPrincipal, "Selecione um contato primeiro!");
				}
			}
		});
		
		btnExportarContatos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				GeradorDeArquivo.exportarDados(new DaoContato().listarTodos());
				JOptionPane.showMessageDialog(telaPrincipal, "Contatos exportados com sucesso!");
			}
		});
		
		tabela.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if(tabela.getSelectedRow()>-1){
					contato=tabelaDeContatos.getModel(tabela.getSelectedRow());
					txtNome.setText(contato.getNome());
					txtNumeroTelefone.setText(contato.getNumeroTelefone());
					ckFavorito.setSelected(contato.isFavorito());
				}else{
					contato=null;
				}
			}
		});
		
		ckApenasFavoritos.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				apenasFavoritos();
			}
		});
	}
}
