package com.sevnis.jmhdemo;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@State(Scope.Benchmark)
public class JmhSpringBootTestRun {

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(JmhSpringBootTestRun.class.getSimpleName())
        .forks(1)
        .warmupIterations(2)
        .measurementIterations(2)
        .build();

    new Runner(opt).run();
  }

  private MockMvc mockMvc;
  private ConfigurableApplicationContext ctx;
  private String dataStr;


  public JmhSpringBootTestRun() {

    String[] args = {"--logging.level.root=off", "--spring.main.banner-mode=off"};

    ctx = SpringApplication.run(Application.class, args);
    mockMvc = MockMvcBuilders.webAppContextSetup((WebApplicationContext) ctx).build();

    List data = new ArrayList<>();
    for (int i = 0; i < 1000; i++) {
      data.add("string" + i);
    }
    Collections.shuffle(data);
    try {
      dataStr = new ObjectMapper().writeValueAsString(RequestData.builder().data(data).build());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  public void benchmarkEqualIgnoreCase() throws Exception {

    mockMvc.perform(post("/equalignorecase")
        .content(dataStr)
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  public void benchmarkEqualNotIgnoreCase() throws Exception {
    mockMvc.perform(post("/equalonly")
        .content(dataStr)
        .contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(status().isOk());
  }
}
