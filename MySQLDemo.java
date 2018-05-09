package com.runoob.test;
 
import java.sql.*;
import java.util.ArrayList;
 
public class MySQLDemo {
	static Connection connection=null;
	static Statement statement=null;
	static ArrayList<TeacherFreeTime> freeTimes=new ArrayList<>();
	
	public static void mySqlConnect() throws SQLException, ClassNotFoundException {
		final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	    final String DB_URL = "jdbc:mysql://localhost:3306/software";
	    final String USER = "root";
	    final String PASS = "zhang501X";
	    Class.forName("com.mysql.jdbc.Driver");
	    connection = DriverManager.getConnection(DB_URL,USER,PASS);
	    statement = connection.createStatement();
	}
	public static void closeConnect() throws SQLException {
		statement.close();
        connection.close();
	}
	public static boolean login(String account,String password,int num) throws SQLException {
		boolean flag=false;
        String sql1,sql2;
        ResultSet resultSet = null;
        sql1 = "SELECT * FROM student_login";
        sql2="SELECT * FROM student_login";
        if(num==0) {
        	ResultSet rs1=statement.executeQuery(sql1);
        	resultSet=rs1;
        }
        else if(num==1) {
        	ResultSet rs2=statement.executeQuery(sql2);
        	resultSet=rs2;
        }
        while(resultSet.next()){
            String id  = resultSet.getString("studentAccount");
            String pwd = resultSet.getString("studentPassword");
            if(id.equals(account)&&pwd.equals(password)) {
            	flag=true;
            	break;
            }
        }
        resultSet.close();
		if(flag==true) {
			return true;
		}
		else {
			return false;
		}
			
	}
	public static  ArrayList<TeacherFreeTime> searchTeacherFreeTime() throws SQLException {
		ResultSet set = null;
		String sql="SELECT * FROM teacher_freetime";
		set = statement.executeQuery(sql);
		int week = 0,day = 0;
		String id=null,freeTime=null,freeWeekDay;
		while(set.next()){
			id=set.getString("teacherAccount");
            freeTime=set.getString("freeTime");
            freeWeekDay=set.getString("weekDay");
            for (int i = 0; i < freeWeekDay.length(); i++) {
                if(freeWeekDay.substring(i, i + 1).equals("-")){
                      week = Integer.parseInt(freeWeekDay.substring(0,i).trim());
                      day = Integer.parseInt(freeWeekDay.substring(i+1,freeWeekDay.length()).trim());
                }
            }
            TeacherFreeTime freeTime1=new TeacherFreeTime();
            freeTime1.teacherAccount=id;
            freeTime1.freeTime=freeTime;
            freeTime1.freeDay=day;
            freeTime1.freeWeek=week;
            freeTimes.add(freeTime1);
		}
		return freeTimes;
	}
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
    	String account,password;
    	account="150720215";
    	password="zhang501X";
    	mySqlConnect();
        System.out.println(login(account, password,1));
        searchTeacherFreeTime();
        for(int i=0;i<freeTimes.size();++i) {
        	System.out.println(freeTimes.get(i).teacherAccount+" "+freeTimes.get(i).freeWeek+" "+freeTimes.get(i).freeDay+" "+freeTimes.get(i).freeTime);
        }
        closeConnect();
        
    }
}