package PROJETO2;

import java.util.List;

public class Principal {
	
	public static void main(String[] args) throws Exception {
		
		DAO xDao = new DAO();
		boolean conexao = xDao.conectar();
//		boolean createTable = xDao.createTable();
		System.out.println(conexao);
		
		System.out.println("\n\n==== Inserir x === ");
		X x = new X();
		if(xDao.insertX(x) == true) {
			System.out.println("Inserção com sucesso -> " + x.toString());
		}
			
//		System.out.println("\n\n==== Mostrar x's === ");
//		List<X> xs = xDao.get();
//		for (X u: xs) {
//			System.out.println(u.toString());
//		}
//
//		System.out.println("\n\n==== Atualizar nome (nome (" + x.nome + ") === ");
//		x.nome = "ABC";
//		xDao.update(x);
//		
//		System.out.println("\n\n==== Mostrar usuários ordenados por id === ");
//		x = (X) xDao.getOrderByID();
//		for (X xs1: xs) {
//			System.out.println(xs1.toString());
//		}
//		
//		System.out.println("\n\n==== Excluir usuário (código " + x.id + ") === ");
//		xDao.delete(x.id);
//		
//		System.out.println("\n\n==== Mostrar usuários ordenados por login === ");
//		xs = xDao.getOrderByLogin();
//		for (X u: xs) {
//			System.out.println(u.toString());
//		}
	}
}
