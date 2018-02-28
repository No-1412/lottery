package com.youge.yogee.generate;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 读取mysql某数据库下表的注释信息
 *
 * @author haipeng.ren
 * @date 2017年3月30日13:03:23
 */
public class DB4Mysql {
    private String tablename = "";
    private String[] colnames;
    private String[] colremarks;
    private String[] colTypes;
    private int[] colSizes; // 列名大小
    private int[] colScale; // 列名小数精度
    private boolean importUtil = false;
    private boolean importSql = false;
    private boolean importMath = false;

    /**
     *
     * @param url 数据库地址
     * @param name 用户名
     * @param pwd 密码
     * @return
     * @throws Exception
     */
    public static Connection getConnection(String url, String name, String pwd) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
//        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/beller", "root", "root");
        Connection conn = DriverManager.getConnection(url, name, pwd);
        return conn;
    }

    /**
     * 获取当前数据库下的所有表名称
     *
     * @param conn 数据库连接
     * @return
     * @throws Exception
     */
    public static List<String> getAllTableName(Connection conn) throws Exception {
        List<String> tables = new ArrayList();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SHOW TABLES ");
        while (rs.next()) {
            String tableName = rs.getString(1);
            tables.add(tableName);
        }
        rs.close();
        stmt.close();
//        conn.close();
        return tables;
    }

    /**
     * 获得某表的建表语句
     *
     * @param tableName 表名集合
     * @param conn 数据库连接
     * @return
     * @throws Exception
     */
    public static List<String> getCommentListByTableName(List tableName, Connection conn) throws Exception {
        List<String> comments = new ArrayList();
        Statement stmt = conn.createStatement();
        for (int i = 0; i < tableName.size(); i++) {
            String table = (String) tableName.get(i);
            ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + table);
            if (rs != null && rs.next()) {
                String createDDL = rs.getString(2);
                String comment = parse(createDDL);
                comments.add(comment);
            }
            rs.close();
        }
        stmt.close();
//        conn.close();
        return comments;
    }

    /**
     * 获得某表的建表语句
     *
     * @param tableName 表名集合
     * @param conn 数据库连接
     * @return
     * @throws Exception
     */
    public static Map getCommentMapByTableName(List tableName, Connection conn) throws Exception {
        Map map = new LinkedHashMap();
        Statement stmt = conn.createStatement();
        for (int i = 0; i < tableName.size(); i++) {
            String table = (String) tableName.get(i);
            ResultSet rs = stmt.executeQuery("SHOW CREATE TABLE " + table);
            if (rs != null && rs.next()) {
                String createDDL = rs.getString(2);
                String comment = parse(createDDL);
                map.put(table, comment);
            }
            rs.close();
        }
        stmt.close();
//        conn.close();
        return map;
    }

    /**
     * 返回注释信息
     *
     * @param all
     * @return
     */
    private static String parse(String all) {
        String comment = null;
        int index = all.indexOf("COMMENT='");
        if (index < 0) {
            return "";
        }
        comment = all.substring(index + 9);
        comment = comment.substring(0, comment.length() - 1);
        return comment;
    }


    /**
     * 将数据库表转换为实体类
     *
     * @param tableName 表名
     * @param conn 数据库连接
     */
    public void tableToEntity(String tableName, Connection conn) throws Exception {
        tablename = tableName;
        DatabaseMetaData dbmd = null;
        String strsql = "SELECT * FROM " + tablename;// +" WHERE ROWNUM=1"
        try {
            System.out.println(strsql);
            PreparedStatement pstmt = conn.prepareStatement(strsql);
            pstmt.executeQuery();
            ResultSetMetaData rsmd = pstmt.getMetaData();
            int size = rsmd.getColumnCount(); // 共有多少列
            colnames = new String[size];
            colremarks = new String[size];
            colTypes = new String[size];
            colSizes = new int[size];
            colScale = new int[size];
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                rsmd.getCatalogName(i + 1);
                colnames[i] = rsmd.getColumnName(i + 1).toLowerCase();
                colTypes[i] = rsmd.getColumnTypeName(i + 1).toLowerCase();
                colScale[i] = rsmd.getScale(i + 1);
                ResultSet rs = conn.getMetaData().getColumns(null, "root", tableName, rsmd.getColumnLabel(i + 1));
                while(rs.next()){
                    colremarks[i] = rs.getString("REMARKS");
                }
                if ("datetime".equals(colTypes[i])) {
                    importUtil = true;
                }
                if ("image".equals(colTypes[i]) || "text".equals(colTypes[i])) {
                    importSql = true;
                }
                if (colScale[i] > 0) {
                    importMath = true;
                }
                colSizes[i] = rsmd.getPrecision(i + 1);
            }
            String content = parse();
            try {
                FileWriter fw = new FileWriter(initcap(tablename) + ".java");
                PrintWriter pw = new PrintWriter(fw);
                pw.println(content);
                pw.flush();
                pw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
//            conn.close();
        }
    }

    /**
     * 解析处理(生成实体类主体代码)
     */
    private String parse() {
        StringBuffer sb = new StringBuffer();
        sb.append("\r\nimport java.io.Serializable;\r\n");
        if (importUtil) {
            sb.append("import java.util.Date;\r\n");
        }
        if (importSql) {
            sb.append("import java.sql.*;\r\n\r\n");
        }
        if (importMath) {
            sb.append("import java.math.*;\r\n\r\n");
        }
        // 表注释
        processColnames(sb);
        sb.append("public class " + initcap(tablename)
                + " implements Serializable {\r\n");
        processAllAttrs(sb);
        processAllMethod(sb);
        sb.append("}\r\n");
//        System.out.println(sb.toString());
        return sb.toString();

    }

    /**
     * 处理列名,把空格下划线'_'去掉,同时把下划线后的首字母大写 要是整个列在3个字符及以内,则去掉'_'后,不把"_"后首字母大写.
     * 同时把数据库列名,列类型写到注释中以便查看,
     *
     * @param sb
     */
    private void processColnames(StringBuffer sb) {
        sb.append("\r\n/** " + tablename + "\r\n");
        String colsiz = "";
        for (int i = 0; i < colnames.length; i++) {
            colsiz = colSizes[i] <= 0 ? "" : (colScale[i] <= 0 ? "("
                    + colSizes[i] + ")" : "(" + colSizes[i] + "," + colScale[i]
                    + ")");
            sb.append("\t" + colnames[i].toUpperCase() + "    "
                    + colTypes[i].toUpperCase() + colsiz + "\r\n");
            char[] ch = colnames[i].toCharArray();
            char c = 'a';
            if (ch.length > 3) {
                for (int j = 0; j < ch.length; j++) {
                    c = ch[j];
                    if (c == '_') {
                        if (ch[j + 1] >= 'a' && ch[j + 1] <= 'z') {
                            ch[j + 1] = (char) (ch[j + 1] - 32);
                        }
                    }
                }
            }
            String str = new String(ch);
            colnames[i] = str.replaceAll("_", "");
        }
        sb.append("*/\r\n");
    }

    /**
     * 生成所有的方法
     *
     * @param sb
     */
    private void processAllMethod(StringBuffer sb) {
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tpublic void set"
                    + initcap(colnames[i])
                    + "("
                    + oracleSqlType2JavaType(colTypes[i], colScale[i],
                    colSizes[i]) + " " + colnames[i] + "){\r\n");
            sb.append("\t\tthis." + colnames[i] + "=" + colnames[i] + ";\r\n");
            sb.append("\t}\r\n\r\n");

            sb.append("\tpublic "
                    + oracleSqlType2JavaType(colTypes[i], colScale[i],
                    colSizes[i]) + " get" + initcap(colnames[i])
                    + "(){\r\n");
            sb.append("\t\treturn " + colnames[i] + ";\r\n");
            sb.append("\t}\r\n\r\n");
        }
        sb.append("\r\n\r\n");
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tmodel.set"
                    + initcap(colnames[i])
                    + "();//" + colremarks[i]+ "\r\n");
        }
    }

    /**
     * 解析输出属性
     *
     * @return
     */
    private void processAllAttrs(StringBuffer sb) {
        sb.append("\tprivate static final long serialVersionUID = 1L;\r\n");
        for (int i = 0; i < colnames.length; i++) {
            sb.append("\tprivate "
                    + oracleSqlType2JavaType(colTypes[i], colScale[i],
                    colSizes[i]) + " " + colnames[i] + ";"+ "\t //" + colremarks[i] + "\r\n");
        }
        sb.append("\r\n");
    }

    /**
     * 把输入字符串的首字母改成大写
     *
     * @param str
     * @return
     */
    private String initcap(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * Mysql
     *
     * @param sqlType 字段类型
     * @param scale
     * @return
     */
    private String oracleSqlType2JavaType(String sqlType, int scale, int size) {
        if (sqlType.equals("int") || sqlType.equals("tinyint")
                || sqlType.equals("smallint") || sqlType.equals("mediumint")) {
            return "Integer";
        } else if (sqlType.equals("integer")) {
            return "Integer";
        } else if (sqlType.equals("long")) {
            return "Long";
        } else if (sqlType.equals("float"))
            return "Float";
        else if (sqlType.equals("double")) {
            return "Double";
        } else if (sqlType.equals("number") || sqlType.equals("decimal")
                || sqlType.equals("numeric") || sqlType.equals("real")) {
            return scale == 0 ? (size < 10 ? "Integer" : "Long") : "BigDecimal";
        } else if (sqlType.equals("varchar")
                || sqlType.equals("char") || sqlType.equals("text")
                || sqlType.equals("longtext")|| sqlType.equals("blob")) {
            return "String";
        } else if (sqlType.equals("date")) {
            return "Date";
        } else if (sqlType.equals("datetime") || sqlType.equals("time")
                || sqlType.equals("timestamp")) {
            return "Timestamp";
        }
        return null;
    }

}
