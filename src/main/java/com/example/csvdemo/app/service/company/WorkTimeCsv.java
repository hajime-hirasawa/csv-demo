package com.example.csvdemo.app.service.company;

import com.example.csvdemo.validator.annotation.FieldName;
import java.util.List;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import org.springframework.format.annotation.NumberFormat;

@Getter
public class WorkTimeCsv {
  @FieldName("営業時間ID") @NotEmpty @NumberFormat
  private final String workTimeId;
  @FieldName("種別(曜日 1、日付: 2)") @NotEmpty @Size(max = 1)
  private final String workTimeType;
  @FieldName("指定内容(曜日または、日付)") @NotEmpty @Size(max = 8)
  private final String workTimeDetail;
  @FieldName("開始時刻") @NotEmpty @Size(max = 4)
  private final String startTime;
  @FieldName("終了時刻") @NotEmpty
  @Size(max = 4)
  private final String endTime;

  public WorkTimeCsv(List<String> row) {
    workTimeId = row.get(CSV_COLUMN.WORK_TIME_ID.getIndex());
    workTimeType = row.get(CSV_COLUMN.WORK_TIME_TYPE.getIndex());
    workTimeDetail = row.get(CSV_COLUMN.WORK_TIME_DETAIL.getIndex());
    startTime = row.get(CSV_COLUMN.START_TIME.getIndex());
    endTime = row.get(CSV_COLUMN.END_TIME.getIndex());
  }
  public enum CSV_COLUMN {
    WORK_TIME_ID(1),
    WORK_TIME_TYPE(2),
    WORK_TIME_DETAIL(3),
    START_TIME(4),
    END_TIME(5);

    private final int index;

    CSV_COLUMN(int index) {
      this.index = index;
    }

    public int getIndex() {
      return index;
    }
  }
}
