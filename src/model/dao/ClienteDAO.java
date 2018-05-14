/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import conexao.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import objetos.Cliente;

/**
 *
 * @author Igor
 */
public class ClienteDAO {
    
    //começando o crud
    public void create(Cliente c){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("INSERT INTO clientes (nome,cpf,pagamento,parcelas) VALUES (?,?,?,?)");
            stmt.setString(1,c.getNome());
            stmt.setString(2,c.getCpf());
            stmt.setString(3,c.getPagamento());
            stmt.setString(4,c.getParcelas());
            
            //executa a query de insert
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Cadastrado com sucesso!");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao cadastrar: " + ex);
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            //fecha a conexão
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
    
    //método para consulta
    public List<Cliente> read(){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        List<Cliente> clientes = new ArrayList<>();
        
        try {
            stmt = con.prepareStatement("SELECT * FROM clientes");
            rs = stmt.executeQuery();
            
            while(rs.next()){
                Cliente cliente = new Cliente();
                
                cliente.setCodigo(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                cliente.setPagamento(rs.getString("pagamento"));
                cliente.setParcelas(rs.getString("parcelas"));
                clientes.add(cliente);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        
        return clientes;
    }
    
    
    public void update (Cliente c){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        try {
            stmt = con.prepareStatement("UPDATE clientes SET nome = ?,cpf = ?,pagamento = ?,parcelas = ? WHERE id = ?");
            stmt.setString(1,c.getNome());
            stmt.setString(2,c.getCpf());
            stmt.setString(3,c.getPagamento());
            stmt.setString(4,c.getParcelas());
            stmt.setInt(5,c.getCodigo());
            
            //executa a query de insert
            stmt.executeUpdate();
            
            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
            Logger.getLogger(ClienteDAO.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            //fecha a conexão
            ConnectionFactory.closeConnection(con, stmt);
        }
    }
}
