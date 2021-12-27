package com.example.csvdemo.app.service.company;

import com.example.csvdemo.app.controller.company.CompanyRequest;
import com.example.csvdemo.app.service.company.CompanyCsv.CSV_COLUMN;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvListReader;
import org.supercsv.io.ICsvListReader;
import org.supercsv.prefs.CsvPreference;

@Service
public class CompanyService {

  private final Validator validator;

  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired
  public CompanyService(Validator validator) {
    this.validator = validator;
  }


  public CsvReadResult<String> readCsv(CompanyRequest request, MultipartFile csvFile) {

    // 単純なCSVであればCsvBeanReaderが楽だが、今回はCSV内で長さが変わるのでCsvListReader

    // SJIS + CsvPreference.STANDARD_PREFERENCE でEXCELで作成したCSVに対応
    try (InputStream is = csvFile.getInputStream();
        InputStreamReader isr = new InputStreamReader(is, Charset.forName("Shift_JIS"));
        ICsvListReader reader = new CsvListReader(isr, CsvPreference.STANDARD_PREFERENCE)) {

      List<String> row;
      List<String> warns = new ArrayList<>();

      List<CompanyCsv> companyCsvList = new ArrayList<>();
      List<StoreCsv> storeCsvList = new ArrayList<>();
      List<WorkTimeCsv> workTimeCsvList = new ArrayList<>();
      while (Objects.nonNull(row = reader.read())) {
        String lineNumber = Integer.toString(reader.getLineNumber());
        if (row.isEmpty() || Objects.isNull(row.get(0))) {
          return new CsvReadResult<>(null, Collections.singletonList("[" + lineNumber + "行目] empty row."));
        }

        // レコード種別を見て判定
        switch (row.get(0)) {
          case "CP":
            if (row.size() != (CompanyCsv.CSV_COLUMN.values().length) + 1) {
              warns.add("[" + lineNumber + "行目] カラム長不正のためスキップ");
              break;
            }
            CompanyCsv companyCsv = new CompanyCsv(row);
            addAllWarns(companyCsv, lineNumber, warns);
            companyCsvList.add(companyCsv);
            break;
          case "ST":
            if (row.size() != (StoreCsv.CSV_COLUMN.values().length) + 1) {
              warns.add("[" + lineNumber + "行目] カラム長不正のためスキップ");
            }
            StoreCsv storeCsv = new StoreCsv(row);
            addAllWarns(storeCsv, lineNumber, warns);
            storeCsvList.add(storeCsv);
            break;
          case "WT":
            if (row.size() != (WorkTimeCsv.CSV_COLUMN.values().length) + 1) {
              warns.add("[" + lineNumber + "行目] カラム長不正のためスキップ");
            }
            WorkTimeCsv workTimeCsv = new WorkTimeCsv(row);
            addAllWarns(workTimeCsv, lineNumber, warns);
            workTimeCsvList.add(workTimeCsv);
            break;
          default:
            return new CsvReadResult<>(null, Collections.singletonList("[" + lineNumber + "行目] invalidType."));
        }

      }

      if (!warns.isEmpty()) {
        return new CsvReadResult<>(null, warns);
      }

      Map<String, List<?>> map = new HashMap<>();
      map.put("companyList", companyCsvList);
      map.put("storeList", storeCsvList);
      map.put("workTimeList", workTimeCsvList);
      return new CsvReadResult<>(mapper.writeValueAsString(map), Collections.emptyList());

    } catch (FileNotFoundException e) {
      throw new RuntimeException("file not found.", e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private void addAllWarns(Object target, String lineNumber, List<String> warns) {
    // 本当はSuperCsvのバリデーションを使いたいが
    // 今回のケースでは使用不可のためSpringのバリデータを使用する。
    Errors error = new BeanPropertyBindingResult(target, target.getClass().getSimpleName());
    validator.validate(target, error);
    warns.addAll(error.getAllErrors().stream()
        .map(err -> String.join("", "[", lineNumber , "行目]", err.getDefaultMessage()))
        .collect(Collectors.toList()));
  }
}
