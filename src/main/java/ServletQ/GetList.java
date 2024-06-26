package ServletQ;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Dao.ConnectionDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "gl", urlPatterns = "/getList")
public class GetList extends HttpServlet {

	static ArrayList<String> list=new ArrayList();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String course = req.getParameter("course");
		String branch = req.getParameter("branch");
		String sem = req.getParameter("sem");

		list.clear();
		
		PrintWriter pw = resp.getWriter();

		try {
			PreparedStatement ps = ConnectionDao.getConnection()
					.prepareStatement("select * from student where course=? and branch=? and sem=?");
			ps.setString(1, course);
			ps.setString(2, branch);
			ps.setString(3, sem);
			ResultSet rs = ps.executeQuery();

				pw.print("<!DOCTYPE html>\r\n" + "<html>\r\n" + "\r\n" + "<head>\r\n"
						+ "	<meta charset=\"UTF-8\">\r\n"
						+ "	<meta name=\"viewport\" content=\"width=device-width , initial-scale=1.0\">\r\n"
						+ "	<title>MTEC</title>\r\n" + "	<link rel=\"stylesheet\" href=\"style.css\">\r\n"
						+ "	<script src=\"script.js\"></script>\r\n	<style>\r\n" + "\r\n"
						+ "	</style>\r\n" + "\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n" + "\r\n" + "	<header>\r\n"
						+ "		<h1>MOTHER THERESSA COLLEGE</h1>\r\n" + "	</header>\r\n" + "\r\n"
						+ "	<div class=\"nav\">\r\n" + "		<a href=#>Home</a>\r\n"
						+ "		<a href=\"#\">About Us</a>\r\n" + "		<a href=\"#\">Services</a>\r\n"
						+ "		<a href=\"#\">Locations</a>\r\n" + "		<a href=\"#\">Contact Us</a>\r\n" + "\r\n"
						+ "		<div class=\"dropdown\">\r\n"
						+ "			<div class=\"loginbtn\" onclick=\"logout()\">*logout</div>\r\n" + "		</div>\r\n"
						+ "	</div>\r\n" + "	\r\n" + "	<p class=\"username\">Faculty name : ");
						
				String teacherName="";
				Cookie[] cookies = req.getCookies();
				if(cookies.length!=0) {
				for(Cookie c:cookies) {
					if(c.getName().equals("teacher")) {
						teacherName = c.getValue();
					}
				}
				}
						
						pw.printf(TeacherQ.name+"</p>\r\n" + "\r\n"
						+ "	<h3>Select the below options to get the list and take attendance</h3>\r\n"
						+ "<form action=getList method=post>"
						+ "	<div class=\"select\">\r\n"
						+ "		Select Course : <select name=\"course\" id=\"course\" >\r\n"
						+ "			<option value=\"B.Tech\">B.Tech</option>\r\n"
						+ "			<option value=\"M.Tech\">M.Tech</option>\r\n"
						+ "			<option value=\"Diploma\">Diploma</option>\r\n" + "		</select>\r\n"
						+ "		<div class=\"gap\"></div>\r\n"
						+ "		Select Branch : <select name=\"branch\" id=\"branch\">\r\n"
						+ "			<option value=\"CSE\">CSE</option>\r\n"
						+ "			<option value=\"ECE\">ECE</option>\r\n"
						+ "			<option value=\"EEE\">EEE</option>\r\n" + "		</select>\r\n"
						+ "		<div class=\"gap\"></div>\r\n"
						+ "		Select semester : <select name=\"sem\" id=\"sem\">\r\n"
						+ "			<option value=\"I\">I</option>\r\n"
						+ "			<option value=\"II\">II</option>\r\n"
						+ "			<option value=\"III\">III</option>\r\n"
						+ "			<option value=\"IV\">IV</option>\r\n"
						+ "			<option value=\"V\">V</option>\r\n"
						+ "			<option value=\"VI\">VI</option>\r\n" + "		</select>\r\n" + "		\r\n"
								+ "<input type=submit value=GetList class=button , GetList>"
								+ "</form>"
						+ "	</div>\r\n" + "\r\n" 
						
						+"<p>Course : "+course+" , Branch : "+branch+" , Sem : "+sem+"</p>"
						+"<table border=\"1px\">\r\n" 
						+"<th>Id</th>\r\n"
						+"<th>Student name</th>\r\n"
						+"<th>Present/Absent</th>\r\n"
						+ "<form action=attendance method=post>" );
						String name="";
						while(rs.next()) {
							name = rs.getString(2);
							list.add(name);
							pw.print("<tr>"
									+ "<td> "+rs.getString(1)+ "</td>"
									+ "<td> "+name.toUpperCase()+"</td>"
									+ "<td>	<div class=present><input type=radio value=present name="+name+"at>Present</div>"
									+ "<div class=absent><input type=radio value=absent name="+name+"at class=absent>Absent</div>"+ "</td></tr>");
						}
						
						pw.print("</table>\r\n<br>"
						+ "<input type=submit class=button value=submit>"
						+ "</form><br><br><br><br><br><br>"
						+ "\r\n" + "	<footer class=\"footer\">\r\n"
						+ "		<p>&copy; 2024 QUniversity. All rights reserved.</p>\r\n" + "\r\n"
						+ "		<section class=\"section\">\r\n" + "			<h2>Contact Us</h2>\r\n"
						+ "			<p>Have questions? Need assistance? Our friendly team is here to help.</p>\r\n"
						+ "			<p>Phone: 1-800-QUNIVERSITY</p>\r\n"
						+ "			<p>Email: info@quniversity.com</p>\r\n" + "			<p>Visit Us: [Address]</p>\r\n"
						+ "		</section>\r\n" + "	</footer>\r\n" + "\r\n"  + "\r\n" + "</body>\r\n" +"</html>");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
