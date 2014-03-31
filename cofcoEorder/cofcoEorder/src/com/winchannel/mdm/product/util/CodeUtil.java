package com.winchannel.mdm.product.util;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

public class CodeUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn_ = conn();
		query(conn_, "MDM_PRODUCT");
	}
	public static void buildAction(String directory,String className,String servName ,String actionName)
	{
//		StringBuffer buff = new StringBuffer();
//       
//       buff.append("<td class=\"formTable\">"+ColumnName+":</td>");
//       buff.append("\r\n");
//       buff.append("<td align=\"left\">");
//       buff.append("\r\n");
//       buff.append("<html:text styleId=\""+ColumnName+"\" property=\"saleCity."+ColumnName+"\" />");
//       buff.append("\r\n");
//       buff.append("</td>");
//       buff.append("\r\n");
//       buff.append("\r\n");
//       
//       System.out.println(buff.toString());  
	}
	public static Connection conn() {
		try {
			// 加载JDBC驱动
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			// 创建数据库连接
			Connection con = DriverManager.getConnection(
					"jdbc:jtds:sqlserver://192.168.1.10:1433/baseapp", "sa","winchannel");
			return con;
		} catch (ClassNotFoundException cnf) {
			System.out.println("driver not find:" + cnf);
			return null;
		} catch (SQLException sqle) {
			System.out.println("can't connection db:" + sqle);
			return null;
		} catch (Exception e) {
			System.out.println("Failed to load JDBC/ODBC driver.");
			return null;
		}
	}

	public static void query(Connection con, String databasename) {
		try {
			// 声明语句
			Statement stmt = con.createStatement();
			// 执行查询
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + databasename);
			ResultSetMetaData rsmd = rs.getMetaData();
			// 获得数据字段个数
			int numColumns = rsmd.getColumnCount();

			for (int i = 1; i < numColumns + 1; i++) {
				// 字段名
				String ColumnName = rsmd.getColumnName(i).toLowerCase();
				// 字段类型
				String ColumnTypeName = rsmd.getColumnTypeName(i);
				// 字段类型对应的java类名
				String ColumnClassName = rsmd.getColumnClassName(i);
				// 显示的长度
				String ColumnDisplaySize = String.valueOf(rsmd
						.getColumnDisplaySize(i));
				// Precision
				String Precision = String.valueOf(rsmd.getPrecision(i));
				// Scale
				String Scale = String.valueOf(rsmd.getScale(i));

				//System.out.println(ColumnName + "---" + ColumnTypeName + "---"
				//		+ ColumnClassName + "---" + ColumnDisplaySize + "---"
				//		+ Precision + "---" + Scale);
				 //buildJava(ColumnName,ColumnTypeName);
				  //buildJavaForm(ColumnName,ColumnTypeName);
				 buildHbm(ColumnName,ColumnTypeName,Precision,Scale);
				 //buildJsp(ColumnName,con,databasename);
			}

		} catch (Exception e) {
			System.out.println("query error:" + e);
		}
	}
	public static void buildJsp(String ColumnName,Connection con,String DataBaseName)
	{
		String query="select t.name,CASE t0.value WHEN '' THEN '未标明字段' ELSE t0.value END  from syscolumns t,sysproperties t0,sysobjects t1 where t0.id=t.id  and t1.id=t.id   and t0.smallid=t.colid and t1.name='eC_customer'";
		try{
		     //声明语句
		     //Statement stmt = con.createStatement();
		     //执行查询
		     //ResultSet rs = stmt.executeQuery(query);
		    // while(rs.next())
		         {
		    	 
		    	  // String sColumnName=rs.getString(0).toLowerCase();
		          // String sComments=rs.getString(1);
		           //System.out.println(sColumnName+"---"+sComments);
		           StringBuffer buff = new StringBuffer();
		           

//					<td class="formTable">客户集团名称<font color="#FF0000">＊</font></td>
//					<td align="left"><html:text styleId="groupname" property="custGroup.groupname" maxlength="50"/></td>
		           
		           buff.append("<td class=\"formTable\">"+ColumnName+":</td>");
		           buff.append("\r\n");
		           buff.append("<td align=\"left\">");
		           buff.append("\r\n");
		           buff.append("<html:text styleId=\""+ColumnName+"\" property=\"saleCity."+ColumnName+"\" />");
		           buff.append("\r\n");
		           buff.append("</td>");
		           buff.append("\r\n");
		           buff.append("\r\n");
		           
		           System.out.println(buff.toString());        
		         }
		    }catch(Exception e){
		      System.out.println("query error:"+e);
		    }
	}
	public static void buildJavaForm(String ColumnName,String ColumnTypeName)
	{
		if("varchar".equals(ColumnTypeName) || "nvarchar".equals(ColumnTypeName) || "char".equals(ColumnTypeName) ||"nchar".equals(ColumnTypeName))
		{
			String s="private String sjh_"+ColumnName+";";
			System.out.println(s);
		}
		if("int".equals(ColumnTypeName) || "decimal".equals(ColumnTypeName)|| "float".equals(ColumnTypeName)|| "bit".equals(ColumnTypeName))
		{
			String s="private Double sjh_"+ColumnName+";";
			System.out.println(s);
		}
		if("datetime".equals(ColumnTypeName.toLowerCase()))
		{
			String s="private Date sjh_"+ColumnName+";";
			System.out.println(s);
		}
	}
	public static void buildJava(String ColumnName,String ColumnTypeName)
	{
		if("varchar".equals(ColumnTypeName) || "nvarchar".equals(ColumnTypeName) || "char".equals(ColumnTypeName) ||"nchar".equals(ColumnTypeName))
		{
			String s="private String "+ColumnName+";";
			System.out.println(s);
		}
		if("int".equals(ColumnTypeName) || "decimal".equals(ColumnTypeName)|| "float".equals(ColumnTypeName)|| "bit".equals(ColumnTypeName))
		{
			String s="private Double "+ColumnName+";";
			System.out.println(s);
		}
		if("numeric".equals(ColumnTypeName))
		{
			String s="private Long "+ColumnName+";";
			System.out.println(s);
		}
		if("datetime".equals(ColumnTypeName.toLowerCase()))
		{
			String s="private Date "+ColumnName+";";
			System.out.println(s);
		}
	}
	public static void buildHbm(String ColumnName,String ColumnTypeName,String Precision,String Scale)
	{
		if("varchar".equals(ColumnTypeName) || "nvarchar".equals(ColumnTypeName) || "char".equals(ColumnTypeName) ||"nchar".equals(ColumnTypeName))
		{
			String s="        <property name=\""+ColumnName+"\" type=\"java.lang.String\" column=\""+ColumnName+"\" length=\""+Precision+"\"/>";
			System.out.println(s);
		}
		if("int".equals(ColumnTypeName) || "decimal".equals(ColumnTypeName)|| "float".equals(ColumnTypeName)|| "bit".equals(ColumnTypeName))
		{
			String s="        <property name=\""+ColumnName+"\" type=\"java.lang.Double\" column=\""+ColumnName+"\" precision=\""+Precision+"\" scale=\""+Scale+"\""+"/>"; 
			System.out.println(s);
		}
		if("numeric".equals(ColumnTypeName))
		{
			String s="        <property name=\""+ColumnName+"\" type=\"java.lang.Long\" column=\""+ColumnName+"\" precision=\""+Precision+"\" scale=\""+Scale+"\""+"/>"; 
			System.out.println(s);
		}
		
		if("datetime".equals(ColumnTypeName.toLowerCase()))
		{
			String s="         <property name=\""+ColumnName+"\" type=\"java.util.Date\" column=\""+ColumnName+"\""+"/>"; 
			System.out.println(s);
		}
	}
}
