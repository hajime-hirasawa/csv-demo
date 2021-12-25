package com.example.csvdemo.app.controller.store;

import com.example.csvdemo.app.common.bean.DemoResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StoreRestController {


  @PostMapping("/store/insert")
  public DemoResponse<StoreResponse> insertStore() {
    return new DemoResponse<>();
  }

}
