package com.example.csvdemo.app.controller.company;

import com.example.csvdemo.app.common.annotation.ApiName;
import com.example.csvdemo.app.common.bean.DemoResponse;
import com.example.csvdemo.app.service.company.CompanyService;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.validation.ValidationErrors;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class CompanyRestController {

  private final CompanyService service;

  private final MessageSource source;

  @Autowired
  public CompanyRestController(CompanyService service, MessageSource source) {
    this.service = service;
    this.source = source;
  }

  @ApiName("会社情報登録")
  @PostMapping("/company/insert")
  public DemoResponse<CompanyResponse> insertCompany(
      @RequestPart("file") MultipartFile file,
      @RequestPart("json") @Validated CompanyRequest request) {

    return new DemoResponse<>();
  }

}
