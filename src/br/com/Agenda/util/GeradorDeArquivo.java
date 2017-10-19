package br.com.Agenda.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.JOptionPane;

import br.com.Agenda.model.Contato;

public class GeradorDeArquivo {
	private final static String NOME_DO_ARQUIVO="contatos.txt";
	
	public static void exportarDados(List<Contato> lista){
		FileWriter escritor=null;
			System.out.println(lista);
			try{
				escritor=new FileWriter(NOME_DO_ARQUIVO);
				for(Contato c : lista){
					escritor.write(String.format("| Id: %10d | Nome: %50s | Numero: %16s | Favorito %10b\n", c.getId(), c.getNome(), c.getNumeroTelefone(), c.isFavorito()));
				}
			}catch(IOException e){
				JOptionPane.showMessageDialog(null, "Erro ao salvar o arquivo!");
				throw new RuntimeException("Erro no Gerador de Arquivo(Exportar dados): "+e.getMessage());
			}finally{
				try{
					escritor.close();
				}catch(IOException e){
					throw new RuntimeException("Erro no GeradorDeArquivo(Exportar dados - fechando o FileWriter): "+e.getMessage());
				}
			}
	}
}