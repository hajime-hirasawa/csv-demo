package com.example.csvdemo.app.service.company;

import com.example.csvdemo.validator.annotation.FieldName;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;

@Getter
public class StoreCsv {
  @FieldName("店舗コード") @Size(max = 32)
  private final String storeCd;
  @FieldName("店舗名") @NotEmpty @Size(max = 100)
  private final String storeName;
  @FieldName("責任者名") @NotEmpty @Size(max = 50)
  private final String responsiblePersonName;
  @FieldName("住所") @NotEmpty @Size(max = 100)
  private final String storeAddress;
  @FieldName("電話番号") @NotEmpty @Size(max = 11)
  private final String storeTell;
  @FieldName("FAX") @Size(max = 12)
  private final String storeFax;
  @FieldName("メールアドレス") @Size(max = 100)
  private final String storeMailAddress;
  @FieldName("ホームページ") @Size(max = 200)
  private final String storeHomepage;
  @FieldName("店舗面積") @NumberFormat
  private final String storeArea;
  @FieldName("駐車場") @Size(max = 500)
  private final String parking;
  @FieldName("支払い方法") @Size(max = 500)
  private final String pay;

  public StoreCsv(List<String> row) {
    storeCd = row.get(CSV_COLUMN.STORE_CD.getIndex());
    storeName = row.get(CSV_COLUMN.STORE_NAME.getIndex());
    responsiblePersonName = row.get(CSV_COLUMN.RESPONSIBLE_PERSON_NAME.getIndex());
    storeAddress = row.get(CSV_COLUMN.STORE_ADDRESS.getIndex());
    storeTell = row.get(CSV_COLUMN.STORE_TELL.getIndex());
    storeFax = row.get(CSV_COLUMN.STORE_FAX.getIndex());
    storeMailAddress = row.get(CSV_COLUMN.STORE_MAIL_ADDRESS.getIndex());
    storeHomepage = row.get(CSV_COLUMN.STORE_HOMEPAGE.getIndex());
    storeArea = row.get(CSV_COLUMN.STORE_AREA.getIndex());
    parking = row.get(CSV_COLUMN.PARKING.getIndex());
    pay = row.get(CSV_COLUMN.PAY.getIndex());
  }
  public enum CSV_COLUMN {
    STORE_CD(1),
    STORE_NAME(2),
    RESPONSIBLE_PERSON_NAME(3),
    STORE_ADDRESS(4),
    STORE_TELL(5),
    STORE_FAX(6),
    STORE_MAIL_ADDRESS(7),
    STORE_HOMEPAGE(8),
    STORE_AREA(9),
    PARKING(10),
    PAY(11);

    private final int index;

    CSV_COLUMN(int index) {
      this.index = index;
    }

    public int getIndex() {
      return index;
    }
  }
}
