package com.synaptiklabs.javaee.embedded;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

@Stateless
@Path("/")
public class MyService {
    @Resource(lookup="java:/datasources/MyDS")
    DataSource ds;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() throws Exception {
        String result = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;

        try {
            conn = ds.getConnection();
            stmt = conn.createStatement();
            rset = stmt.executeQuery("SELECT * FROM version");
            if (rset.next()) {
                result = rset.getString(1);
            }
        } finally {
            if (conn != null) {
                conn.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (rset != null) {
                rset.close();
            }
        }
        return result;
    }
}
