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

	private static final String GITHUB_REPO_CONTRIBUTORS = GITHUB_REPO + "graphs/contributors";

	private static final String CONTACT = "https://javabubble.social/@marcandsweep";

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
						SocialIcons.symbols(), //
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
						a("help of the Java community").withHref(GITHUB_REPO_CONTRIBUTORS), //
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
						li(a("following.csv").withHref(FollowingCSV.LOCATION), text(" - for Mastodon following import")), //,
						li(a("list.csv").withHref(ListCSV.LOCATION), text(" - for Mastodon list import")) //
				), //
				p("""
						Handles are validated in a nightly build except for Twitter due to their
						API restrictions.
						"""));
	}

	private DomContent people() {
		return each( //
				h2("Social Handles"), //
				table( //
						thead(tr( //
								th("Name"), //
								th("Handles"), //
								th("Platform Links").withColspan("8"))), //
						tbody(bubble.people().stream().map(this::person).toArray(DomContent[]::new))));
	}

	private DomContent person(JavaPerson person) {
		return tr( //
				td(person.name()), //
				td(String.join(", ", person.getUniqueHandles())), //
				handleWithLink(person, person.fediverse(), SocialIcons.mastodon, "Mastodon"), //
				handleWithLink(person, person.twitter(), SocialIcons.twitter, "Twitter"), //
				handleWithLink(person, person.github(), SocialIcons.github, "GitHub"), //
				handleWithLink(person, person.reddit(), SocialIcons.reddit, "Reddit"), //
				handleWithLink(person, person.linkedin(), SocialIcons.linkedin, "LinkedIn"), //
				handleWithLink(person, person.bluesky(), SocialIcons.bluesky, "Bluesky"), //
				handleWithLink(person, person.youtube(), SocialIcons.youtube, "YouTube"), //
				handleWithLink(person, person.twitch(), SocialIcons.twitch, "Twitch"));
	}

	private DomContent handleWithLink(JavaPerson person, Handle handle, SocialIcons icon, String title) {
		if (handle != null) {
			return td(a(icon.svg()).withTitle(title + ": " + handle.getHandle()).withHref(handle.getWebLink()));
		} else {
			return td();
		}
	}

}
