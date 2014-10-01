package ch.gennri.dpw.bot;

import net.sourceforge.jwbf.core.actions.HttpActionClient;
import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;
import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by peterr on 20.06.14.
 */
public class TokenBot {
    private MediaWikiBot bot = null;

    public TokenBot(String session_name, String session_id, String url) {
        HttpActionClient client = null;
        try {
            client = create_client(session_name,
                        session_id,
                        url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        this.bot = new MediaWikiBot(client);
    }

    public TokenModifyContent save_article(String title, String text, String token) {
        Article article = new Article(this.bot, title);
        article.setText(text);

        article.setEditSummary("Change DBpedia Ontology with webprotege");
        return this.bot.getPerformedAction(new TokenModifyContent(article, token));
    }

    public static HttpActionClient create_client(String session_name, String session, String url) throws MalformedURLException {
        return create_client(session_name, session, new URL(url));
    }

    public static HttpActionClient create_client(String session_name, String session, URL url) {
        HttpClientBuilder builder = HttpClientBuilder.create();

        builder.setUserAgent("dbpedia-webprotege/1.0-SNAPSHOT");

        CookieStore cookieStore = new BasicCookieStore();
        BasicClientCookie c1 = new BasicClientCookie(session_name + "_session", session);
        c1.setDomain(url.getHost());
        cookieStore.addCookie(c1);

        builder.setDefaultCookieStore(cookieStore);

        return new HttpActionClient(builder, url);
    }
}
