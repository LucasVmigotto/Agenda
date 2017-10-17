package br.com.Agenda.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.Agenda.model.Contato;

public class DaoContato implements ModeloDeDao<Contato>{
	private final Connection conexao;
	
	public DaoContato() {
		conexao=FabricaDeConexao.gerarConexao();
	}

	@Override
	public void criar(Contato t) {
		try{
			PreparedStatement comando=conexao.prepareStatement("INSERT INTO contato (nome, numeroTelefone) VALUES (?,?)");
			comando.setString(1, t.getNome());
			comando.setString(2, t.getNumeroTelefone());
			comando.execute();
			comando.close();
			conexao.close();
		}catch(SQLException e){
			throw new RuntimeException("Erro no DaoContato(Criar): "+e.getMessage());
		}
	}

	@Override
	public Contato buscar(Long id) {
		try{
			PreparedStatement comando=conexao.prepareStatement("SELECT * FROM contato WHERE id=?");
			comando.setLong(1, id);
			ResultSet rs=comando.executeQuery();
			Contato c=recuperarInformacao(rs);
			rs.close();
			comando.close();
			conexao.close();
			return c;
		}catch(SQLException e){
			throw new RuntimeException("Erro no DaoContato(Buscar): "+e.getMessage());
		}
	}

	private Contato recuperarInformacao(ResultSet rs){
		try{
			Contato c=new Contato();
			c.setId(rs.getLong("id"));
			c.setNome(rs.getString("nome"));
			c.setNumeroTelefone(rs.getString("numeroTelefone"));
			c.setFavorito(rs.getBoolean("favorito"));
			return c;
		}catch(SQLException e){
			throw new RuntimeException("Erro no DaoContato(Recuperar Informação): "+e.getMessage());
		}
	}
	
	@Override
	public void atualizar(Contato t) {
		try{
			PreparedStatement comando=conexao.prepareStatement("UPDATE contato SET nome=?, numeroTelefone=? WHERE id=?");
			comando.setString(1, t.getNome());
			comando.setString(2, t.getNumeroTelefone());
			comando.setLong(3, t.getId());
			comando.execute();
			conexao.close();
		}catch(SQLException e){
			throw new RuntimeException("Erro no DaoContato(Atualizar): "+e.getMessage());
		}
	}

	public void favoritarContato(Long id, boolean favorito){
		try {
			PreparedStatement comando=conexao.prepareStatement(favorito==true?"UPDATE contato SET favorito=1 WHERE ":"UPDATE contato SET favorito=0 WHERE id=?");
			comando.setLong(1, id);
			comando.execute();
			comando.close();
			conexao.close();
		}catch(SQLException e){
			throw new RuntimeException("Erro no DaoContato(Favoritar contato): "+e.getMessage());
		}
	}
	
	@Override
	public void deletar(Long id) {
		try{
			PreparedStatement comando=conexao.prepareStatement("DELETE FROM contato WHERE id=?");
			comando.setLong(1, id);
			comando.execute();
			comando.close();
			conexao.close();
		}catch(SQLException e){
			throw new RuntimeException("Erro no DaoContato(Deletar): "+e.getMessage());
		}
	}

	@Override
	public List<Contato> listarTodos() {
		try{
			List<Contato> lista=new ArrayList<>();
			PreparedStatement comando=conexao.prepareStatement("SELECT * FROM contato");
			ResultSet rs=comando.executeQuery();
			while(rs.next()){
				lista.add(recuperarInformacao(rs));
			}
			rs.close();
			comando.close();
			conexao.close();
			return lista;
		}catch(SQLException e){
			throw new RuntimeException("Erro no DaoContato(Listar Todos): "+e.getMessage());
		}
	}
	
	
}