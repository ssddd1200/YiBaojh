package com.yjs.yibaofw.helper;

import com.yjs.yibaofw.entity.DBModelPO;
import com.yjs.yibaofw.entity.ReturnPageVO;

import java.io.File;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SQLiteConfigHelper {

    private static final String BASE_DIR = "db";
    private static final String CONFID_BD = "/SQLite3.db";

    public static void createEmptyConfig(){

        File file = new File(BASE_DIR);
        if (!file.exists()){
            file.mkdir();
        }
        File dbFile = new File(BASE_DIR + CONFID_BD);
        if(!dbFile.exists()){
            file.mkdir();
            createEmptyDB();
        }
    }

    private static void createEmptyDB() {
        Connection conn = getSQLiteConn();
        Statement stat = null;
        try {
            stat = conn.createStatement();
            String createSQL = "CREATE TABLE operatelog("+
                    "id INTEGER primary key,"+
                    "postIP char(100),"+
                    "postJson char(500),"+
                    "result char(400),"+
                    "jiaoyih char(3),"+
                    "jigousbh char(4),"+
                    "tianbiaorq char(8),"+
                    "tianbiaosj char(6))";
            stat.execute(createSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static Connection getSQLiteConn() {

        Connection conn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbURL = "jdbc:sqlite:./"+BASE_DIR+CONFID_BD;
            conn = DriverManager.getConnection(dbURL);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static ReturnPageVO getsLogInfo(String riqi1, String riqi2, int page, int rows) throws Exception{
        ReturnPageVO retPage = new ReturnPageVO();
        retPage.setAppcode("0");
        retPage.setDatabuffer("成功");
        retPage.setPage(Long.valueOf(page));
        Connection conn = getSQLiteConn();
        Statement stat = null;
        ResultSet rs = null;
        ResultSet rs1 = null;
        List<DBModelPO> list = new ArrayList<>();
        try {
            stat = conn.createStatement();
            if (page == 1){
                rs = stat.executeQuery("SELECT COUNT(1) AS ZS FROM operatelog WHERE tianbiaorq BETWEEN "+riqi1+" AND "+riqi2);
                double total = rs.getDouble("ZS");
                int recode = (int) Math.ceil(total/rows);
                retPage.setTotal(Long.valueOf((int) total));
                retPage.setRecords(Long.valueOf(recode));
            }

            String sql = String.format("SELECT * FROM operatelog WHERE tianbiaorq BETWEEN %s and %s order by tianbiaorq desc limit %d,%d",riqi1,riqi2,(page-1)*rows,rows);
            rs1 = stat.executeQuery(sql);
            while (rs1.next()){
                DBModelPO po = new DBModelPO();
                po.setId(rs1.getInt("id"));
                po.setPostIP(rs1.getString("postIP"));
                po.setPostJson(rs1.getString("postJson"));
                po.setResult(rs1.getString("result"));
                po.setJiaoyih(rs1.getString("jiaoyih"));
                po.setJigousbh(rs1.getString("jigousbh"));
                po.setTianbiaorq(rs1.getString("tianbiaorq"));
                po.setTianbiaosj(rs1.getString("tianbiaosj"));
                list.add(po);
            }
            retPage.setResultlist(list);
        } finally {
            if (rs != null) rs.close();
            if (rs1 != null) rs1.close();
            if (stat != null) stat.close();
            if (conn != null) conn.close();
        }
        return retPage;
    }

    public static DBModelPO getDBModelPO(int id) throws Exception{
        Connection conn = getSQLiteConn();
        Statement state = null;
        ResultSet rs = null;
        DBModelPO model = new DBModelPO();
        try {
            state = conn.createStatement();
            rs = state.executeQuery("SELECT * FROM operatelog WHERE id = "+id);
            if (rs.next()){
                model.setId(rs.getInt("id"));
                model.setPostIP(rs.getString("postIP"));
                model.setPostJson(rs.getString("postJson"));
                model.setResult(rs.getString("result"));
                model.setJiaoyih(rs.getString("jiaoyih"));
                model.setJigousbh(rs.getString("jigousbh"));
                model.setTianbiaorq(rs.getString("tianbiaorq"));
                model.setTianbiaosj(rs.getString("tianbiaosj"));
            }
        }finally {
            if (rs != null) rs.close();
            if (state != null) state.close();
            if (conn != null) conn.close();
        }
        return model;
    }

    public static void saveDBModel(DBModelPO model) throws Exception{
        Connection conn = getSQLiteConn();
        int id = model.getId();
        Statement state = null;
        ResultSet rs = null;
        String sql = null;
        Date date = new Date();
        try {
            state = conn.createStatement();
            ResultSet rs1 = state.executeQuery("SELECT * FROM operatelog WHERE id="+id);
            if (rs1.next()){
                sql = String.format("UPDATE operatelog SET postIP='%s',postJson='%s',result='%s',jiaoyih='%s'," +
                                "jigousbh='%s' WHERE id = '%d'", model.getPostIP(), model.getPostJson(),
                        model.getResult(), model.getJiaoyih(), model.getJigousbh(), id);
            }else {
                sql = String.format("INSERT INTO operatelog(id, postIP, postJson,result,jiaoyih,jigousbh,tianbiaorq,tianbiaosj) " +
                                "VALUES (null,'%s','%s','%s','%s','%s','%s','%s')",model.getPostIP(), model.getPostJson(),
                        model.getResult(),model.getJiaoyih(),model.getJigousbh(), new SimpleDateFormat("yyyyMMdd").format(date),new SimpleDateFormat("HHmmss").format(date));
            }
            state.executeUpdate(sql);
        } finally {
            if (rs != null) rs.close();
            if (state != null) state.close();
            if (conn != null) conn.close();
        }
    }
}
