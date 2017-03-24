/**
 * SUMMARY
 *
 * @author Isuru Samarasinghe
 * @version 1, 2017-03-17.
 */

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;
import twitter4j.auth.RequestToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/TwitterCallback")
public class TwitterCallbackServlet  extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
		RequestToken requestToken = (RequestToken) request.getSession().getAttribute("requestToken");
		System.out.println( "TwitterCallbackServlet:requestToken:"+requestToken);
		String verifier = request.getParameter("oauth_verifier");
		try {
			twitter.getOAuthAccessToken(requestToken, verifier);
			request.getSession().removeAttribute("requestToken");

			response.setContentType( "text/HTML" );
			PrintWriter out = response.getWriter();
			User user = twitter.verifyCredentials();
			user.getEmail();
			user.getId();
			user.getName();

			out.write( "Email: " + user.getEmail() );
			out.write( "<br>" );
			out.write( "ID: " + user.getId() );
			out.write( "<br>" );
			out.write( "Name: " + user.getName() );
			out.write( "<br>" );
			out.close();


		} catch (TwitterException e) {
			throw new ServletException(e);
		}
//		response.sendRedirect(request.getContextPath() + "/");
	}
}