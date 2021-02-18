package ca.jrvs.apps.trading.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResponseExceptionUtil {

  public static ResponseStatusException getResponseStatusException(Exception ex){

    if (ex instanceof  IllegalArgumentException){
      return new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }else{
      return new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
          "Internal issues ongoing: " + ex.getMessage());
    }

  }

}
