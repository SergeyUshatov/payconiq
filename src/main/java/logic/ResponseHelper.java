package logic;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public interface ResponseHelper {
    Map asMap();
    List<Map> asListOfMaps();

    Object getOriginal();

    int getStatus();

    JSONObject asJson();
}
