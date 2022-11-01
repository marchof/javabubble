import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Generate a following import CSV file from stdin and outputs
 * it to stdout.
 *
 * e.g. java GenerateImport.java &lt; README.md > following.csv
 */
public class GenerateImport {
  public static void main(String... args) throws IOException {
    System.out.println("Account address,Show boosts,Notify on new posts,Languages");
    try (BufferedReader in = new BufferedReader(
        new InputStreamReader(System.in))) {
      Pattern pattern = Pattern.compile("@[^ ]*@[^ |\t]*");
      in.lines()
          .map(pattern::matcher)
          .flatMap(Matcher::results)
          .map(MatchResult::group)
          .map(id -> id.replaceAll("`", "") + ",true,false,")
          .forEach(System.out::println);
    }
  }
}
