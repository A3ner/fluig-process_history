package com.fluig.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fluig.jdbc.ConnectionFactory;
import com.fluig.model.DefProces;

public class DatabaseManagement {

    private Logger log = LoggerFactory.getLogger(DatabaseManagement.class);

    private Connection con = null;

    private PreparedStatement statement;

    private ResultSet rs;


    public DatabaseManagement(){
        ConnectionFactory cf = new ConnectionFactory();
        this.con = cf.getConnection();
    }


    public List<DefProces> findDefProces(long cod_empresa){
    	
    	List<DefProces> lista = new ArrayList<DefProces>();

        try {
            this.statement = this.con.prepareStatement("select COD_DEF_PROCES,  DES_DEF_PROCES from def_proces where cod_empresa = "+cod_empresa);

            if (this.statement != null) {

                this.rs = this.statement.executeQuery();

                while (rs.next()){
                	lista.add(new DefProces(rs.getString("COD_DEF_PROCES"), rs.getString("DES_DEF_PROCES")));
                }

            }

        } catch (SQLException e){
            log.error(e.getMessage());
            e.printStackTrace();
        } finally {
            log.info("#close connections...");
            this.closeConnection(this.rs, this.statement, this.con);
        }
		return lista;
    }

    private void closeConnection(ResultSet rs, PreparedStatement stmt, Connection con){
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            log.error("error on close connections: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
