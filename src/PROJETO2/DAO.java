package PROJETO2;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAO {
	private Connection conexao;
	
	public DAO() {
		conexao = null;
	}
	
	public boolean createTable() {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("CREATE TABLE IF NOT EXISTS X (descricao VARCHAR, id int, quantidade int, validade VARCHAR, fabricacao VARCHAR)");
		st.close();
		status = true;
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean conectar() {
		String driverName = "org.postgresql.Driver";
		String ServerName = "localhost";
		String myDataBase = "teste";
		int porta = 5432;
		String url = "jdbc:postgresql://" + ServerName + ":" + porta +"/" + myDataBase;
		String username = "postgres";
		String password = "root";
		boolean status = false;
		
		try {
			Class.forName(driverName);
			conexao = DriverManager.getConnection(url, username, password);
			status = (conexao == null);
			System.out.println("Conexão efeutada.");
		} catch (ClassNotFoundException e) {
			System.err.println("Driver Não encontrado");
		} catch (SQLException e) {
			System.err.println(e);
		}
		
		return status;
	}
	
	public boolean close() {
		boolean status = false;
		
		try { 
			conexao.close();
			status = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		return status;
	}
	
	public boolean insertX(X x) {
		boolean status = false;
		try {
			Statement st = conexao.createStatement();
			st.executeUpdate("INSERT INTO X (nome, id) VALUES (" + x.descricao + ',' + x.id + ");");
			st.close();
			status = true;
			
		} catch (SQLException u) {
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public X get(int id) {
		X x = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM X WHERE id=" + id;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 x = new X();
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return x;
	}
	
	
	public List<X> get() {
		return get("");
	}

	
	public List<X> getOrderByNome() {
		return get("nome");		
	}
	
	
	public List<X> getOrderByLogin() {
		return get("login");		
	}
	
	
	public List<X> getOrderByID() {
		return get("id");		
	}
	
	
	private List<X> get(String orderBy) {	
	
		List<X> x = new ArrayList<X>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM x" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	X u = new X();
	            x.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return x;
	}
	
	
	public boolean update(X x) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE X SET nome = '" + x.nome
					   + " WHERE id = " + x.id;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM X WHERE id = " + codigo;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
}
