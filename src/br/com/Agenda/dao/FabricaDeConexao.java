package br.com.Agenda.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class FabricaDeConexao {
	public static Connection gerarConexao(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			return DriverManager.getConnection("jdbc:mysql://localhost/agenda","root","root");
		}catch(ClassNotFoundException e){
			throw new RuntimeException("Erro na FabricaDeConexao(Gerar Conexao - Erro carregando o drive): "+e.getMessage());
		}catch(SQLException e){
			throw new RuntimeException("Erro na FabricaDeConexao(Gerar Conexao - Erro de Conexao): "+e.getMessage());
		}
	}
}
