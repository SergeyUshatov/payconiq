package logic;

import org.json.JSONObject;

public interface GistUtil {
    GistUtil dummyGist();
    GistUtil dummyGist(int filesCount);
    GistUtil withDescription(String description);
    GistUtil withIsPublic(boolean isPublic);
    GistUtil withFiles(int filesCount);
    JSONObject get();

    GistUtil withoutDescription();

    GistUtil withoutFiles();
}
