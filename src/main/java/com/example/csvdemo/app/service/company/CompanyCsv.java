package com.example.csvdemo.app.service.company;

import com.example.csvdemo.validator.annotation.FieldName;
import java.util.List;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CompanyCsv {
  @FieldName("会社コード")
  @Size(max = 32)
  private final String companyCd;
  @FieldName("会社名")
  @NotEmpty
  @Size(max = 100)
  private final String companyName;

  @FieldName("事業区分") @NotEmpty @Size(max = 2)
  private final String businessDivision;
  @FieldName("代表者名") @NotEmpty @Size(max = 50)
  private final String representativeName;
  @FieldName("住所") @NotEmpty @Size(max = 100)
  private final String companyAddress;
  @FieldName("電話番号") @NotEmpty @Size(max = 11)
  private final String companyTel;
  @FieldName("FAX") @Size(max = 12)
  private final String companyFax;
  @FieldName("メールアドレス") @Size(max = 100)
  private final String companyMail;
  @FieldName("ホームページ") @Size(max = 200)
  private final String companyhomepage;

  public CompanyCsv(List<String> row) {
    companyCd = row.get(CSV_COLUMN.COMPANY_CD.getIndex());
    companyName = row.get(CSV_COLUMN.COMPANY_NAME.getIndex());
    businessDivision = row.get(CSV_COLUMN.BUSINESS_DIVISION.getIndex());
    representativeName = row.get(CSV_COLUMN.REPRESENTATIVE_NAME.getIndex());
    companyAddress = row.get(CSV_COLUMN.COMPANY_ADDRESS.getIndex());
    companyTel = row.get(CSV_COLUMN.COMPANY_TEL.getIndex());
    companyFax = row.get(CSV_COLUMN.COMPANY_FAX.getIndex());
    companyMail = row.get(CSV_COLUMN.COMPANY_MAIL.getIndex());
    companyhomepage = row.get(CSV_COLUMN.COMPANY_HOMEPAGE.getIndex());
  }

  public enum CSV_COLUMN {
    COMPANY_CD(1),
    COMPANY_NAME(2),
    BUSINESS_DIVISION(3),
    REPRESENTATIVE_NAME(4),
    COMPANY_ADDRESS(5),
    COMPANY_TEL(6),
    COMPANY_FAX(7),
    COMPANY_MAIL(8),
    COMPANY_HOMEPAGE(9);

    private final int index;

    CSV_COLUMN(int index) {
      this.index = index;
    }

    public int getIndex() {
      return index;
    }
  }
}
