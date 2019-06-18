package com.sevnis.jmhdemo;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  @PostMapping("/equalignorecase")
  public String equalIgnoreCase(@RequestBody RequestData requestData) {
    for (int i = 0; i < requestData.getData().size(); i++) {
      if (requestData.getData().get(i).equalsIgnoreCase("string500")) {
        break;
      }
    }

    return "OK";
  }

  @PostMapping("/equalonly")
  public String equalOnly(@RequestBody RequestData requestData) {
    for (int i = 0; i < requestData.getData().size(); i++) {
      if (requestData.getData().get(i).equals("string500")) {
        break;
      }
    }

    return "OK";
  }
}
