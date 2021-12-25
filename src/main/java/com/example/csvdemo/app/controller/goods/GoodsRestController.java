package com.example.csvdemo.app.controller.goods;

import com.example.csvdemo.app.common.bean.DemoResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsRestController {


  @PostMapping("/goods/insert")
  public DemoResponse<GoodsResponse> insertGoods() {
    return new DemoResponse<>();
  }

}
