package org.javabubble.generator.site;

import static j2html.TagCreator.a;
import static j2html.TagCreator.body;
import static j2html.TagCreator.document;
import static j2html.TagCreator.each;
import static j2html.TagCreator.h1;
import static j2html.TagCreator.h2;
import static j2html.TagCreator.head;
import static j2html.TagCreator.html;
import static j2html.TagCreator.li;
import static j2html.TagCreator.meta;
import static j2html.TagCreator.p;
import static j2html.TagCreator.table;
import static j2html.TagCreator.tbody;
import static j2html.TagCreator.td;
import static j2html.TagCreator.text;
import static j2html.TagCreator.th;
import static j2html.TagCreator.thead;
import static j2html.TagCreator.title;
import static j2html.TagCreator.tr;
import static j2html.TagCreator.ul;

import java.io.IOException;
import java.io.Writer;

import org.javabubble.generator.model.Handle;
import org.javabubble.generator.model.JavaBubble;
import org.javabubble.generator.model.JavaPerson;

import j2html.tags.DomContent;

class ReadmeHTML extends TextArtifact {

	static final String LOCATION = "index.html";

	private static final String GITHUB_REPO = "https://github.com/marchof/javabubble/";

	private static final String GITHUB_REPO_HISTORY = GITHUB_REPO + "commits/main";

	private static final String CONTACT = "https://mastodon.social/@marcandsweep";

	ReadmeHTML(JavaBubble bubble) {
		super(bubble, LOCATION);
	}

	@Override
	void generate(Writer writer) throws IOException {
		writer.write(document(html( //
				head( //
						meta().withCharset(SiteOutput.TEXT_ENCODING.name()), //
						meta().withName("viewport").withContent("width=device-width, initial-scale=1.0"), //
						Style.gitHubStyle(), //
						title("Java Bubble")), //
				body( //
						intro(), //
						remarks(), //
						export(), //
						people() //
				).withClass("markdown-body") //
		)));
	}

	private DomContent intro() {
		return each( //
				h1("Java Bubble Backup"), //
				p(text("""
						I love the welcoming atmosphere of the Java folks I got to know over the last
						decades. To continue having a welcoming and safe place to learn about Java and
						software technology I created this backup list for a potential alternative
						social platform with the\s"""), //
						a("help of the Java community").withHref(GITHUB_REPO_HISTORY), //
						text(".")), //
				p(text("Please feel free to "), a("create a pull request").withHref(GITHUB_REPO), //
						text(" create PRs to add yourself or Java people you think should be on this list.")));
	}

	private DomContent remarks() {
		return each( //
				h2("Personal remarks"), //
				ul( //
						li("""
								I do not promote any platform over another. The sole purpose of this
								list is	having a backup if one of them stops working for me."""), //
						li("""
								Please consider sponsoring your fediverse server operator so they
								don't have to create a annoying business model."""), //
						li(text("""
								I fully respect your privacy. The data has been collected from public
								sources or have been added by pull requests from the community. Please\s"""),
								a("drop me a message").withHref(CONTACT).withRel("me"), text(" or open an "),
								a("issue or pull").withHref(GITHUB_REPO),
								text(" request if you want to get removed from the list."))));
	}

	private DomContent export() {
		return each( //
				h2("Data Export"), //
				p("""
						The data is available in various data formats:
						"""), //
				ul( //
						li(a("people.yaml").withHref(PeopleYaml.LOCATION), text(" - raw data")), //
						li(a("people.json").withHref(PeopleJson.LOCATION), text(" - raw data")), //
						li(a("following.csv").withHref(FollowingCSV.LOCATION), text(" - for Mastodon import")) //
				));
	}

	private DomContent people() {
		return each( //
				h2("Social Handles"), //
				table( //
						thead(tr( //
								th("Name"), //
								th("Fediverse"), //
								th("Twitter"), //
								th("GitHub"), //
								th("reddit") //
						)), //
						tbody(bubble.people().stream().map(this::person).toArray(DomContent[]::new) //
						)));
	}

	private DomContent person(JavaPerson person) {
		return tr( //
				td(person.name()), //
				td(handleWithLink(person, person.fediverse())), //
				td(handleWithLink(person, person.twitter())), //
				td(handleWithLink(person, person.github())), //
				td(handleWithLink(person, person.reddit())));
	}

	private DomContent handleWithLink(JavaPerson person, Handle handle) {
		if (handle != null) {
			return a(handle.getHandle()).withHref(handle.getWebLink());
		} else {
			return text("-");
		}
	}

}
