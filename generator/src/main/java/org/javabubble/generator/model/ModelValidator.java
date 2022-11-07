package org.javabubble.generator.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.Collator;
import java.time.Duration;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ModelValidator {

    public enum Validator {
        FEDIVERSE(Pattern.compile("^@([A-Za-z0-9_]+)@([a-z0-9\\-]+(\\.[a-z0-9\\-]+)+)$"),
            (Matcher m) ->
                "https://%s/@%s".formatted(m.group(2), m.group(1)), 3)
        , GITHUB(Pattern.compile("^([A-Za-z0-9_\\-]+)$"),
            (Matcher m) -> "https://github.com/%s".formatted(m.group(1)))
        , TWITTER(Pattern.compile("^@([A-Za-z0-9_]+)$")
            // Twitter happily returns 200 for any GET request to its web page
            //                , (Matcher m) -> "https://twitter.com/%s".formatted(m.group(1)), 0
            // Even some heuristics didn't work so far as the response always contains the failure handler
            //                , (String body) -> body.contains("input type=\"submit\" value=\"Try again\"")
            // -> Postpone proper checking of Twitter handles via the "normal" web page OR use the API???
            // For the time being we assume that the person has a valid Twitter handle
            )
        ;

        private static final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(30))
            .followRedirects(HttpClient.Redirect.ALWAYS)
            .build();
        private static final Logger LOG = LoggerFactory.getLogger(Validator.class);

        private final Pattern pattern;
        private final Function<Matcher, String> platformUrl;
        // Some Platforms are not very reliable, so retry several times if necessary
        private final int maxRetries;
        private final Predicate<String> responseBodyIsInvalid;

        Validator(final Pattern pattern) {
            this(pattern, null, 0, null);
        }

        Validator(final Pattern pattern, final Function<Matcher, String> platformUrl) {
            this(pattern, platformUrl, 0, null);
        }

        Validator(final Pattern pattern, final Function<Matcher, String> platformUrl, final int maxRetries) {
            this(pattern, platformUrl, maxRetries, null);
        }

        Validator(final Pattern pattern, final Function<Matcher, String> platformUrl,
                  final int maxRetries, final Predicate<String> responseBodyIsInvalid) {
            this.pattern = pattern;
            this.platformUrl = platformUrl;
            this.maxRetries = maxRetries;
            this.responseBodyIsInvalid = responseBodyIsInvalid;
        }

        public void validate(final String value) {
            if (value != null) {
                Matcher matcher = pattern.matcher(value);
                if (!matcher.matches()) {
                    throw new IllegalArgumentException("Field %s has unexpected value %s".formatted(name(), value));
                }
                if (null != platformUrl) {
                        HttpResponse<String> strResponse = tryToValidateOnPlatform(matcher);
                        if (null != responseBodyIsInvalid && responseBodyIsInvalid.test(strResponse.body())) {
                            throw new IllegalArgumentException("Looks like '%s' does not know '%s'".formatted(name(),
                                value));
                        }
                }
            }
        }

        private HttpResponse<String> tryToValidateOnPlatform(Matcher matcher) {
            String pfUrl = platformUrl.apply(matcher);
            LOG.debug("Checking '{}' (Connection Timeout: {})", pfUrl,
                httpClient.connectTimeout().orElse(Duration.ZERO));
			int retry = 0;
            for (;;) {
                HttpResponse<String> result = tryToValidateOnPlatform(pfUrl);
                if (null != result) {
                    return result;
                }
				retry++;
				if (retry > maxRetries) {
					break;
				}
                LOG.warn("Retrying '{}' ({}/{})", pfUrl, retry, maxRetries);
            }
            throw new IllegalArgumentException("Cannot access '%s' for URL '%s'".formatted(name(), pfUrl));
        }

        private static HttpResponse<String> tryToValidateOnPlatform(String pfUrl) {
            try {
                URI uri = new URI(pfUrl);
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();
                HttpResponse<String> strResponse = httpClient.send(request,
                    HttpResponse.BodyHandlers.ofString());
                if (strResponse.statusCode() == 200) {
                    return strResponse;
                }
            } catch (IOException e) {
				LOG.warn("Caught an (expected) IOException, but will probably continue retrying: {}", e, e);
			} catch (URISyntaxException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
				LOG.error("Interrupted while checking URL '{}': {}", pfUrl, e, e);
				Thread.currentThread().interrupt();
			}
            return null;
        }
    }

	private static final Collator COLLATOR = Collator.getInstance(Locale.ENGLISH);
	private static final Comparator<JavaPerson> ORDER = Comparator.comparing(p -> p.name().split("-|\s"),
			(a1, a2) -> Arrays.compare(a1, a2, COLLATOR));

	public static void validate(JavaBubble bubble) {
		validate(bubble.people());
	}

	private static void validate(List<JavaPerson> people) {
		people.forEach(ModelValidator::validate);
		people.stream().reduce(ModelValidator::checkOrder);
	}

    private static void validate(JavaPerson person) {
        checkNonEmpty("name", person.name());
        Validator.TWITTER.validate(person.twitter());
        Validator.FEDIVERSE.validate(person.fediverse());
        Validator.GITHUB.validate(person.github());
    }

	private static void checkNonEmpty(String field, String value) {
		if (value == null || value.isBlank()) {
			throw new IllegalArgumentException("Field %s must not be empty".formatted(field));
		}
	}

	private static JavaPerson checkOrder(JavaPerson a, JavaPerson b) {
		if (ORDER.compare(a, b) > 0) {
			throw new IllegalArgumentException(
					"Invalid ordering: %s should be listed before %s".formatted(b.name(), a.name()));
		}
		return b;
	}

}
