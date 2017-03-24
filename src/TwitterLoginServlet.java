/**
 * SUMMARY
 *
 * @author Isuru Samarasinghe
 * @version 1, 2017-03-17.
 */

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;

@WebServlet("/TwitterLogin")
public class TwitterLoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Properties props = new Properties(  );
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println( "TwitterLoginServlet:doGet" );
//		ConfigurationBuilder cb = new ConfigurationBuilder();

//		if( !props.contains( "twitterConsumerKey" ))
//		{
//			props.put( "twitterConsumerKey", "x6kIRTVZFNnDjtMjHn9DNOunM" );
//			props.put( "twitterConsumerSecret", "UEqgFTtpN2SFWoGYxoo4yx1STCtOuLuCmHeUncEHzHmIdAapGb" );
//			props.put( "twitterRequestTokenURL", "https://api.twitter.com/oauth/request_token" );
//			props.put( "twitterAuthorizeURL", "https://api.twitter.com/oauth/authorize" );
//			props.put( "twitterAccessTokenURL", "https://api.twitter.com/oauth/access_token" );
//		}
//		cb.setDebugEnabled(true)
//				.setOAuthConsumerKey((String)props.get("twitterConsumerKey"))
//				.setOAuthConsumerSecret((String)props.get("twitterConsumerSecret"))
//				.setOAuthRequestTokenURL((String)props.get("twitterRequestTokenURL"))
//				.setOAuthAuthorizationURL((String)props.get("twitterAuthorizeURL"))
//				.setOAuthAccessTokenURL((String)props.get("twitterAccessTokenURL"));



		TwitterFactory tf = new TwitterFactory();//cb.build());
		Twitter twitter = tf.getInstance();
		request.getSession().setAttribute("twitter", twitter);
		try {
			StringBuffer callbackURL = request.getRequestURL();
			System.out.println( "TwitterLoginServlet:callbackURL:"+callbackURL );
			int index = callbackURL.lastIndexOf("/");
			callbackURL.replace(index, callbackURL.length(), "").append("/TwitterCallback");

			RequestToken requestToken = twitter.getOAuthRequestToken(callbackURL.toString());
			request.getSession().setAttribute("requestToken", requestToken);
			System.out.println( "requestToken.getAuthenticationURL():"+requestToken.getAuthenticationURL() );
			response.sendRedirect(requestToken.getAuthenticationURL());

		} catch (TwitterException e) {
			throw new ServletException(e);
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		System.out.println("Unexpected doPost ...");
	}
}