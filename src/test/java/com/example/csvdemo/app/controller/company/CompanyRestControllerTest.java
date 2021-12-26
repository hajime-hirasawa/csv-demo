package com.example.csvdemo.app.controller.company;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.Validator;

@SpringJUnitWebConfig
@SpringBootTest
public class CompanyRestControllerTest {

  private final MockMvc mockMvc;

  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired
  public CompanyRestControllerTest(CompanyRestController target, Validator validator) {
    mockMvc = MockMvcBuilders.standaloneSetup(target)
        .addFilter((request, response, chain) -> {
          response.setCharacterEncoding(StandardCharsets.UTF_8.name());
          chain.doFilter(request, response);
        })
        .setValidator(validator)
        .build();
  }

  @Test
  public void insertCompany_request_editor_null() throws Exception {
    CompanyRequest request = new CompanyRequest();
    MockPart jsonPart = new MockPart("json", mapper.writeValueAsBytes(request));
    jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    MockPart filePart = new MockPart("file", "file.csv", getByteFromResource(
        "requestTest.csv"));
    mockMvc.perform(multipart("/company/insert").part(jsonPart).part(filePart)).andDo(print());
  }

  @Test
  public void insertCompany_request_editor_empty() throws Exception {
    CompanyRequest request = new CompanyRequest();
    request.setEditor("");
    MockPart jsonPart = new MockPart("json", mapper.writeValueAsBytes(request));
    jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    MockPart filePart = new MockPart("file", "file.csv", getByteFromResource(
        "requestTest.csv"));
    mockMvc.perform(multipart("/company/insert").part(jsonPart).part(filePart)).andDo(print());
  }

  @Test
  public void insertCompany_request_editor_blank() throws Exception {
    CompanyRequest request = new CompanyRequest();
    request.setEditor("  ");
    MockPart jsonPart = new MockPart("json", mapper.writeValueAsBytes(request));
    jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    MockPart filePart = new MockPart("file", "file.csv", getByteFromResource(
        "requestTest.csv"));
    mockMvc.perform(multipart("/company/insert").part(jsonPart).part(filePart)).andDo(print());
  }

  @Test
  public void insertCompany_request_editor_max_size() throws Exception {
    CompanyRequest request = new CompanyRequest();
    request.setEditor("0---|----#1---|----#2---|----#3---|----#4---|----#5---|----#");
    MockPart jsonPart = new MockPart("json", mapper.writeValueAsBytes(request));
    jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    MockPart filePart = new MockPart("file", "file.csv", getByteFromResource(
        "requestTest.csv"));
    mockMvc.perform(multipart("/company/insert").part(jsonPart).part(filePart)).andDo(print());
  }

  @Test
  public void insertCompany_request_editor_max_size_over() throws Exception {
    CompanyRequest request = new CompanyRequest();
    request.setEditor("0---|----#1---|----#2---|----#3---|----#4---|----#5---|----#6");
    MockPart jsonPart = new MockPart("json", mapper.writeValueAsBytes(request));
    jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    MockPart filePart = new MockPart("file", "file.csv", getByteFromResource(
        "requestTest.csv"));
    mockMvc.perform(multipart("/company/insert").part(jsonPart).part(filePart))
        .andDo(print())
        .andExpect(ResultMatcher.matchAll(
            jsonPath("resultCode").value("99"),
            jsonPath("messages").isArray(),
            jsonPath("messages").value("0~50で入力してください。"),
            jsonPath("result").isEmpty()
            ));
  }

  private byte[] getByteFromResource(String fileName) {
    try (InputStream is = getClass().getResourceAsStream(fileName)) {
      if (Objects.isNull(is)) {
        throw new RuntimeException("file not found");
      }
      return IOUtils.toByteArray(is);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
