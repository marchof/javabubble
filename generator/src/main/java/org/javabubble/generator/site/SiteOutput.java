package org.javabubble.generator.site;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public interface SiteOutput {

	public static final Charset TEXT_ENCODING = StandardCharsets.UTF_8;

	Writer newTextDocument(String path) throws IOException;

}
