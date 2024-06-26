package ServletQ;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ListIterator;

import Dao.ConnectionDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name="as" , urlPatterns="/attendance")
public class AttendanceSubmission extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ArrayList<String> list=GetList.list;
		String op="";
		ListIterator<String> li = list.listIterator();
		while(li.hasNext()) {
			String name=li.next();
			String attendance=req.getParameter(name+"at");
			
			Statement st =null;
			String teacherName="";
			Cookie[] cookies = req.getCookies();
			if(cookies.length!=0) {
			for(Cookie c:cookies) {
				if(c.getName().equals("teacher")) {
					teacherName = c.getValue();
					
					Cookie teacher = new Cookie("teacher" , "");
					teacher.setMaxAge(0);
					resp.addCookie(teacher);
				}
			}
			}
			op="Attendance submitted by "+teacherName;
			
			
			
			try {
				st = ConnectionDao.getConnection().createStatement();
				st.execute("create table "+name+"(Date date unique,Attendance varchar(10), Faculty varchar(20))");
			} catch (SQLException e) {
				//System.out.println("Table already exists");
			}
			
			try {
				st.execute("insert into "+name+" values(curdate() , '"+attendance+"' , '"+TeacherQ.name+"')");
				//System.out.println(op);
			} catch (SQLException e) {
				op="The attendance has been already taken";
				System.out.println(op);
			}
		}
			resp.getWriter().print("<html><body styles=text-align:center>"+op+"<br><a href=index.html>Home</a></body></html>");
	}
}
