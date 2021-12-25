package com.example.csvdemo.app.controller.company;

import com.example.csvdemo.app.common.annotation.ApiName;
import com.example.csvdemo.app.common.bean.DemoResponse;
import com.example.csvdemo.app.service.company.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CompanyRestController {

  private final CompanyService service;

  @Autowired
  public CompanyRestController(CompanyService service) {
    this.service = service;
  }

  @ApiName("会社情報登録")
  @PostMapping("/company/insert")
  public DemoResponse<CompanyResponse> insertCompany(
      @RequestPart("file") MultipartFile file,
      @RequestPart("json") CompanyRequest request) {
    return new DemoResponse<>();
  }

}
