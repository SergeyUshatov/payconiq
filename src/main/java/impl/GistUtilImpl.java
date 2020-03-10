package impl;

import logic.GistUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONObject;

import java.util.Random;

public class GistUtilImpl implements GistUtil {
    private static final String FILES = "files";
    private static final String CONTENT = "content";
    private static final String DESCRIPTION = "description";
    private static final String PUBLIC = "public";
    private static int DEFAULT_STRING_LENGTH = 25;
    private static final String FILE_NAME_PREFIX = "dummyFile_";
    private static final String FILE_CONTENT_PREFIX = "dummy-content-";
    private static final String GIST_DESCRIPTION_PREFIX = "dummy-description-";

    private JSONObject gist;
    private JSONObject files;

    public GistUtilImpl() {
        gist = new JSONObject();
    }

    @Override
    public GistUtil dummyGist() {

        String contentData = FILE_CONTENT_PREFIX + getRandomString();
        String description = GIST_DESCRIPTION_PREFIX + getRandomString();
        String dummyFileName = FILE_NAME_PREFIX + getRandomString() + ".txt";

        JSONObject content = new JSONObject();
        content.put(CONTENT, contentData);

        files = new JSONObject();
        files.put(dummyFileName, content);

        gist = new JSONObject();
        gist.put(DESCRIPTION, description);
        gist.put(PUBLIC, true);
        gist.put(FILES, files);
        return this;
    }

    @Override
    public GistUtil dummyGist(int filesCount) {
        String description = GIST_DESCRIPTION_PREFIX + getRandomString();
        boolean isPublic = new Random().nextBoolean();
        files = new JSONObject();
        setFilesObject(filesCount);

        gist = new JSONObject();
        gist.put(DESCRIPTION, description);
        gist.put(PUBLIC, isPublic);
        gist.put(FILES, files);

        return this;
    }

    @Override
    public GistUtil withDescription(String description) {
        gist.put(DESCRIPTION, description);
        return this;
    }

    @Override
    public GistUtil withIsPublic(boolean isPublic) {
        gist.put(PUBLIC, isPublic);
        return this;
    }

    @Override
    public GistUtil withFiles(int filesCount) {
        files = new JSONObject();
        setFilesObject(filesCount);
        gist.put(FILES, files);
        return this;
    }

    private void setFilesObject(int filesCount) {
        for (int i = 0; i < filesCount; i++) {
            String contentData = FILE_CONTENT_PREFIX + getRandomString();
            JSONObject content = new JSONObject();
            content.put(CONTENT, contentData);

            String dummyFileName = FILE_NAME_PREFIX + getRandomString() + ".txt";
            files.put(dummyFileName, content);
        }
    }

    @Override
    public JSONObject get() {
        return gist;
    }

    @Override
    public GistUtil withoutDescription() {
        gist.remove(DESCRIPTION);
        return this;
    }

    @Override
    public GistUtil withoutFiles() {
        gist.remove(FILES);
        return this;
    }

    private String getRandomString() {
        return RandomStringUtils.randomAlphanumeric(DEFAULT_STRING_LENGTH);
    }
}
