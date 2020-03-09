package logic;

public interface RestClient {
    ResponseHelper get(RequestHelper requestHelper);
    ResponseHelper post(RequestHelper requestHelper);
    ResponseHelper patch(RequestHelper requestHelper);
    ResponseHelper delete(RequestHelper requestHelper);
}
