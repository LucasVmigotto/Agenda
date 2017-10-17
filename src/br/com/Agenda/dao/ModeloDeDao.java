package br.com.Agenda.dao;

import java.util.List;

public interface ModeloDeDao<T> {
	public void criar(T t);
	public T buscar(Long id);
	public void atualizar(T t);
	public void deletar(Long id);
	public List<T> listarTodos();
}
