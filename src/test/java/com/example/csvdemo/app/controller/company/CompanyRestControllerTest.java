package com.example.csvdemo.app.controller.company;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockPart;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringJUnitWebConfig
@SpringBootTest
public class CompanyRestControllerTest {

  private final MockMvc mockMvc;

  private final ObjectMapper mapper = new ObjectMapper();

  @Autowired
  public CompanyRestControllerTest(CompanyRestController target) {
    mockMvc = MockMvcBuilders.standaloneSetup(target).build();
  }

  @Test
  public void requestTest() throws Exception {
    CompanyRequest request = new CompanyRequest();
    request.setId("123456");
    MockPart jsonPart = new MockPart("json", mapper.writeValueAsBytes(request));
    jsonPart.getHeaders().setContentType(MediaType.APPLICATION_JSON);
    MockPart filePart = new MockPart("file", "file.csv", getByteFromResource(
        "requestTest.csv"));
    mockMvc.perform(multipart("/company/insert").part(jsonPart).part(filePart)).andDo(print());
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
