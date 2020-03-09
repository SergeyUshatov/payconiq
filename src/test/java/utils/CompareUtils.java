package utils;

import org.json.JSONObject;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CompareUtils {

    public static CustomComparator compareIgnoringUpdatedAt() {
        return new CustomComparator(JSONCompareMode.STRICT,
                new Customization("updated_at", (actual, expected) -> true)
        );
    }

//    public static CustomComparator compareIgnoringUpdatedAt() {
//        return new CustomComparator(JSONCompareMode.STRICT,
//                new Customization("updated_at",
//                        (actual, expected) ->
//                    LocalDateTime.parse(actual.toString()).isAfter(LocalDateTime.parse(expected.toString()))
//                )
//        );
//    }
}
