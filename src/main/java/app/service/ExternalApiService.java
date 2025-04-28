package app.service;

import app.exception.ExternalServiceException;

public interface ExternalApiService {


    public String fetchDataWithRetry(String param);

    public String recoverFetchData(ExternalServiceException e, String param);

    public int getExecutionCount();

    public void resetExecutionCount();

    public String exactlyOnceExecution(String param);

}
