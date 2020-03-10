package logic;

public interface GistHelper {
    ResponseHelper createGist();
    ResponseHelper createGist(RequestHelper request);
    ResponseHelper getGist(String id);
    ResponseHelper getGist(RequestHelper requestHelper);
    ResponseHelper deleteGist(RequestHelper requestHelper);
    ResponseHelper deleteGist(String id);
    ResponseHelper updateGist(RequestHelper updateRequest);
    ResponseHelper updateGist(String id);
}
