package impl;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import java.util.Random;

public class GistUtils {
    private static final String FILES = "files";
    private static final String CONTENT = "content";
    private static final String DESCRIPTION = "description";
    private static final String PUBLIC = "public";
    private static int DEFAULT_STRING_LENGTH = 25;
    private static final String FILE_NAME_PREFIX = "dummyFile_";
    private static final String FILE_CONTENT_PREFIX = "dummy-content-";
    private static final String GIST_DESCRIPTION_PREFIX = "dummy-description-";

    
    public static JSONObject dummyGist(int filesCount) {
        String description = dummyDescription();
        boolean isPublic = new Random().nextBoolean();
        JSONObject files = getDummyFiles(filesCount);

        JSONObject gist = new JSONObject();
        gist.put(DESCRIPTION, description);
        gist.put(PUBLIC, isPublic);
        gist.put(FILES, files);

        return gist;
    }

    private static String getRandomString() {
        return RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_LENGTH);
    }

    public static JSONObject getDummyFiles(int filesCount) {
        JSONObject files = new JSONObject();
        for (int i = 0; i < filesCount; i++) {
            String contentData = FILE_CONTENT_PREFIX + getRandomString();
            JSONObject content = new JSONObject();
            content.put(CONTENT, contentData);

            String dummyFileName = FILE_NAME_PREFIX + getRandomString() + ".txt";
            files.put(dummyFileName, content);
        }
        return files;
    }

    public static String dummyDescription() {
        return GIST_DESCRIPTION_PREFIX + getRandomString();
    }
}
