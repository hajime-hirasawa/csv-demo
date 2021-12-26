package com.example.csvdemo.app.controller.company;

import com.example.csvdemo.app.common.annotation.FieldName;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class CompanyRequest {

  @NotEmpty(message="{NotEmpty}")
  @NotEmpty
  @NotEmpty(message="{javax.validation.constraints.NotEmpty.message2}")
  @Size(max = 50)
  @FieldName("更新者名")
  private String editor;

  @Size(max = 500)
  @FieldName("更新メモ")
  private String comment;
}
