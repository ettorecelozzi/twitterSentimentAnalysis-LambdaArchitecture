package masterdataset;

import cascalog.CascalogFunction;
import com.twitter.maple.tap.StdoutTap;
import jcascalog.Api;
import jcascalog.Playground;
import jcascalog.Subquery;
import jcascalog.op.LT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MDatasetQuery {
    static List ages;

    public MDatasetQuery() {
        ages = Arrays.asList(
                Arrays.asList("ettore", 22),
                Arrays.asList("marco", 55),
                Arrays.asList("luca", 18));
    }

    public static void tweetProcessing(List tweet) {
        Api.execute(
                new StdoutTap(),
                new Subquery("?tweet", "?sentiment")
                        .predicate(tweet, "?tweet")
                        .predicate(new SentimentAnalysis(), "?tweet").out("?tweet", "?sentiment")
        );
    }
}
