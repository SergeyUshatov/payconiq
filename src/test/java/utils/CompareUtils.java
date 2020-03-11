package utils;

import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

public class CompareUtils {

    /**
     * This comparator compares 2 Json strings
     * updated_at field is ignored because I receive difference in 1 millisecond on POST and GET requests
     * @return
     */
    public static CustomComparator compareIgnoringUpdatedAt() {
        return new CustomComparator(JSONCompareMode.STRICT,
                new Customization("updated_at", (actual, expected) -> true)
        );
    }
}
