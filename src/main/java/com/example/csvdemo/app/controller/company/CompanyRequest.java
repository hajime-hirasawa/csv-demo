package com.example.csvdemo.app.controller.company;

import com.example.csvdemo.validator.annotation.FieldName;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyRequest {

  @NotEmpty
  @Size(max = 50)
  @FieldName("更新者名")
  private String editor;

  @Size(max = 500)
  @FieldName("更新メモ")
  private String comment;
}
