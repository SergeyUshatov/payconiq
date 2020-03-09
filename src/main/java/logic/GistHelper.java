package logic;

public interface GistHelper {
    ResponseHelper createGist();
    ResponseHelper createGist(RequestHelper request);
    ResponseHelper getGist(RequestHelper requestHelper);
    ResponseHelper deleteGist(RequestHelper requestHelper);
    ResponseHelper updateGist(RequestHelper updateRequest);
}
