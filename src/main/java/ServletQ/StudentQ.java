package ServletQ;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Dao.ConnectionDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "s", urlPatterns = "/Student")
public class StudentQ extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String name = req.getParameter("username");
		String password = req.getParameter("password");
		String tableName = "";
		String id = "";
		String course = "";
		String sem = "";
		String branch = "";
		try {
			Statement st = ConnectionDao.getConnection().createStatement();
			ResultSet rs = st.executeQuery("select * from student where name='" + name + "' and password='" + password + "'");
			PrintWriter pw = resp.getWriter();

			pw.print("<!DOCTYPE html>\r\n" + "<html>\r\n" + "\r\n" + "<head>\r\n" + "	<meta charset=\"UTF-8\">\r\n"
					+ "	<meta name=\"viewport\" content=\"width=device-width , initial-scale=1.0\">\r\n"
					+ "	<title>MTEC</title>\r\n" + "	<link rel=\"stylesheet\" href=\"style.css\">\r\n" + "\r\n"
					+ "</head>\r\n" + "\r\n" + "<body>\r\n" + "\r\n" + "	<header>\r\n"
					+ "		<h1>MOTHER THERESSA COLLEGE</h1>\r\n" + "	</header>\r\n" + "\r\n"
					+ "	<div class=\"nav\">\r\n" + "		<a href=\"#\">Home</a>\r\n"
					+ "		<a href=\"#\">About Us</a>\r\n" + "		<a href=\"#\">Services</a>\r\n"
					+ "		<a href=\"#\">Locations</a>\r\n" + "		<a href=\"#\">Contact Us</a>\r\n");

			if (rs.next()) {
				id = rs.getString(1);
				tableName = rs.getString(2);
				course = rs.getString(3);
				branch = rs.getString(4);
				sem = rs.getString(5);

				Statement stList = ConnectionDao.getConnection().createStatement();
				ResultSet rsList = stList.executeQuery("select * from " + tableName);

				pw.print("<div class=\"dropdown\">" + "<div class=\"loginbtn\" onclick=\"logout()\">*logout</div>"
						+ "</div>" + "</div>" + "<h3>Id:" + id + " , Name : " + tableName.toUpperCase() + " , Course "
						+ course.toUpperCase() + " , Branch " + branch.toUpperCase() + "  , Sem: " + sem.toUpperCase()
						+ "</h3>" + "<table border=1px><th>Date</th><th>Status</th><th>Faculty</th>");

				int percent=0;
				int c=0;
				while (rsList.next()) {
					String Date = rsList.getString(1);
					String attendance = rsList.getString(2);
					//System.out.println(attendance);
					String faculty = rsList.getString(3);
					pw.print("<tr><td>" + Date + "</td>");
					c++;
					if(attendance.equals("present")) {
						pw.print("<td class='p'>");
						percent+=100;
					}else {
						pw.print("<td class='a'>");
					}
					pw.print(attendance + "</td><td>" + faculty + "</td></tr>");
				}
				float p=(float)percent/(float)c;
				Statement stp = ConnectionDao.getConnection().createStatement();
				stp.execute("update student set Attendance="+p+" where name='"+tableName+"'");

				pw.print("<tr><td>Percentage</td><td colspan=2>"+p+"</td></tr></table><br><br><br><br>\r\n");
			} else {
				pw.print("</div><br><br><h2 class=error>Invalid Credentials</h2>"
						+ "<a href=index.html>Click here to login again</a><br><br><br><br><br><br>");
			}
			pw.print("	<footer class=\"footer\">\r\n"
					+ "		<p>&copy; 2024 QUniversity. All rights reserved.</p>\r\n"
					+ "		<section class=\"section\">\r\n" + "			<h2>Contact Us</h2>\r\n"
					+ "			<p>Have questions? Need assistance? Our friendly team is here to help.</p>\r\n"
					+ "			<p>Phone: 1-800-QUNIVERSITY</p>\r\n"
					+ "			<p>Email: info@quniversity.com</p>\r\n" + "			<p>Visit Us: [Address]</p>\r\n"
					+ "		</section>\r\n" + "	</footer>\r\n" + "<script src=script.js></script>" + "</body>\r\n"
					+ "\r\n" + "</html>");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
