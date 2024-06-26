package ServletQ;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Dao.ConnectionDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "a", urlPatterns = { "/admin", "/adminTeacherDetails", "/adminStudentDetails" })
public class AdminQ extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Header(req, resp);
		PrintWriter pw = resp.getWriter();
		pw.print("<div class=\"dropdown\"><div class=\"loginbtn\" onclick=\"logout()\">*logout</div></div></div><br><br><table>");

		String uri = req.getRequestURI();

		if (uri.endsWith("/adminTeacherDetails")) {
			try {
				ResultSet td = getTeacherData();
				pw.print("<tr><th>Name</th><th>Subject</th></tr>");
				
				while(td.next()) {
					pw.print("<tr><td>"+td.getString(1)+"</td><td>"+td.getString(2)+"</td></tr>");
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (uri.endsWith("/adminStudentDetails")) {
			pw.print("<tr><th>Student Id</th><th>Name</th><th>Course</th><th>Branch</th><th>Sem</th><th>Attendance Percentage</th></tr>");
			try {
				Statement st = ConnectionDao.getConnection().createStatement();
				ResultSet rss = st.executeQuery("select * from student");

				while (rss.next()) {
					pw.print("<tr><td>" + rss.getString(1) + "</td>");
					pw.print("<td>" + rss.getString(2) + "</td>");
					pw.print("<td>" + rss.getString(3) + "</td>");
					pw.print("<td>" + rss.getString(4) + "</td>");
					pw.print("<td>" + rss.getString(5).toUpperCase() + "</td>");
					float percent=rss.getFloat(7);
					if(percent>=75) {
						pw.print("<span class='p'><td>");
					}else {
						pw.print("<span class='a'><td>");
					}
					pw.print(percent + "</td></span></tr>");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		Footer(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String u = req.getParameter("username");
		String p = req.getParameter("password");

		PrintWriter pw = resp.getWriter();

		Header(req, resp);

		try {
			PreparedStatement ps = ConnectionDao.getConnection()
					.prepareStatement("select * from admin where username=? AND password=?");
			ps.setString(1, u);
			ps.setString(2, p);

			ResultSet rsa = ps.executeQuery();

			if (rsa.next()) {
				pw.print(
						"<div class=\"dropdown\"><div class=\"loginbtn\" onclick=\"logout()\">*logout</div></div></div>");
				pw.print("<br><br><br><br><br><a href='adminTeacherDetails'>Get Teachers List</a>"
						+ "<br><a href='adminStudentDetails'>Get Students List</a>");
			} else {
				pw.print("</div><br><br><br><br><br><h2 class=error>Invalid Credentials</h2>"
						+ "<a href=index.html>Click here to login again</a><br><br><br><br><br><br>");
			}

			// ==========================================================

		} catch (SQLException e) {
			e.printStackTrace();
			// System.out.println("Couldnt get connection");
		}
		Footer(req, resp);

	}

	public static ResultSet getTeacherData() throws SQLException {
		Statement st = ConnectionDao.getConnection().createStatement();
		ResultSet rst = st.executeQuery("select * from teachers");

		return rst;
	}

	public static void Header(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter pw = resp.getWriter();
		pw.print("<!DOCTYPE html>\r\n" + "<html>\r\n" + "\r\n" + "<head>\r\n" + "	<meta charset=\"UTF-8\">\r\n"
				+ "	<meta name=\"viewport\" content=\"width=device-width , initial-scale=1.0\">\r\n"
				+ "	<title>MTEC</title>\r\n" + "	<link rel=\"stylesheet\" href=\"style.css\">\r\n"
				+ "	<script src=\"script.js\"></script>\r\n" + "\r\n" + "	<style>\r\n" + "\r\n" + "	</style>\r\n"
				+ "\r\n" + "</head>\r\n" + "\r\n" + "<body>\r\n" + "\r\n" + "	<header>\r\n"
				+ "		<h1>MOTHER THERESSA COLLEGE</h1>\r\n" + "	</header>\r\n" + "\r\n" + "	<div class=\"nav\">\r\n"
				+ "		<a href=#>Home</a>\r\n" + "		<a href=\"#\">About Us</a>\r\n"
				+ "		<a href=\"#\">Services</a>\r\n" + "		<a href=\"#\">Locations</a>\r\n"
				+ "		<a href=\"#\">Contact Us</a>");
	}

	public static void Footer(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter pw = resp.getWriter();
		pw.print("</table><br><br><br><br><br><footer class=\"footer\">\r\n"
				+ "		<p>&copy; 2024 QUniversity. All rights reserved.</p>\r\n" + "\r\n"
				+ "		<section class=\"section\">\r\n" + "			<h2>Contact Us</h2>\r\n"
				+ "			<p>Have questions? Need assistance? Our friendly team is here to help.</p>\r\n"
				+ "			<p>Phone: 1-800-QUNIVERSITY</p>\r\n" + "			<p>Email: info@quniversity.com</p>\r\n"
				+ "			<p>Visit Us: [Address]</p>\r\n" + "		</section>\r\n" + "	</footer>\r\n" + "	\r\n"
				+ "	<script src=\"script.js\">\r\n" + "		\r\n" + "	</script>\r\n" + "\r\n" + "</body>\r\n" + "\r\n"
				+ "</html>");
	}

}

