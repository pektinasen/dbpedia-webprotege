import net.sourceforge.jwbf.core.contentRep.Article;
import net.sourceforge.jwbf.mediawiki.bots.MediaWikiBot;

import org.junit.Test;

public class JavaWikiBotTest {

	@Test
	public void postChange() throws Exception {
		MediaWikiBot wikiBot = new MediaWikiBot("http://160.45.114.250/mediawiki/");
		Article article = wikiBot.getArticle("OntologyClass:Manga");
		String revisionId = article.getRevisionId();
		System.out.println(revisionId);
//		System.out.println(article.getText());
		// HITCHHIKER'S GUIDE TO THE GALAXY FANS
//		change(article);
//		wikiBot.login("sascha", "sascha88");
//		article.save();

	}

	private void change(Article article) {
		// TODO Auto-generated method stub
	}

}
