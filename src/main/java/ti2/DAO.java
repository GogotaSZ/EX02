package ti2;

import java.sql.*;

public class DAO {
    private Connection conexao;
    
    public DAO() {
        conexao = null;
    }
    
    public boolean conectar() {
        String driverName = "org.postgresql.Driver";                    
        String serverName = "localhost";
        String mydatabase = "postgres";
        int porta = 5432;
        String url = "jdbc:postgresql://" + serverName + ":" + porta +"/" + mydatabase;
        String username = "postgres";
        String password = "guizagg12RD$";
        boolean status = false;

        try {
            Class.forName(driverName);
            conexao = DriverManager.getConnection(url, username, password);
            status = (conexao == null);
            System.out.println("Conexão efetuada com o postgres!");
        } catch (ClassNotFoundException e) { 
            System.err.println("Conexão NÃO efetuada com o postgres -- Driver não encontrado -- " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Conexão NÃO efetuada com o postgres -- " + e.getMessage());
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
    
    public boolean inserirProduto(Produto produto) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            st.executeUpdate("INSERT INTO farmacia (id, nome, preco) "
                           + "VALUES ("+produto.getId()+ ", '" + produto.getNome() + "', "  
                           + produto.getPreco() + ");");
            st.close();
            status = true;
        } catch (SQLException u) {  
            System.err.println("Erro ao inserir produto: " + u.getMessage());
        }
        return status;
    }
    
    public boolean atualizarProduto(Produto produto) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            String sql = "UPDATE farmacia SET nome = '" + produto.getNome() + "', preco = "  
                       + produto.getPreco() 
                       + " WHERE id = " + produto.getId();
            st.executeUpdate(sql);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public boolean excluirProduto(int id) {
        boolean status = false;
        try {  
            Statement st = conexao.createStatement();
            st.executeUpdate("DELETE FROM farmacia WHERE id = " + id);
            st.close();
            status = true;
        } catch (SQLException u) {  
            throw new RuntimeException(u);
        }
        return status;
    }
    
    public Produto[] getProdutos() {
        Produto[] produtos = null;
        
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM farmacia");        
             if(rs.next()){
                 rs.last();
                 produtos = new Produto[rs.getRow()];
                 rs.beforeFirst();

                 for(int i = 0; rs.next(); i++) {
                    produtos[i] = new Produto(rs.getInt("id"), rs.getString("nome"), 
                                              rs.getDouble("preco"));
                 }
              }
              st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return produtos;
    }

    public Produto[] getProdutosPorPreco(double precoMinimo) {
        Produto[] produtos = null;
        
        try {
            Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = st.executeQuery("SELECT * FROM farmacia WHERE preco >= " + precoMinimo);        
             if(rs.next()){
                 rs.last();
                 produtos = new Produto[rs.getRow()];
                 rs.beforeFirst();

                 for(int i = 0; rs.next(); i++) {
                    produtos[i] = new Produto(rs.getInt("id"), rs.getString("nome"), 
                                              rs.getDouble("preco"));
                 }
              }
              st.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return produtos;
    }
}
