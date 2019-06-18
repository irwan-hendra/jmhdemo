package com.sevnis.jmhdemo;

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

@State(Scope.Benchmark)
public class JmhTestRun {

  public static void main(String[] args) throws RunnerException {
    Options opt = new OptionsBuilder()
        .include(JmhTestRun.class.getSimpleName())
        .forks(1)
        .build();

    new Runner(opt).run();
  }

  private List<String> data;

  public JmhTestRun() {

    data = new ArrayList<>();
    for(int i = 0; i < 1000; i++) {
      data.add("string" + i);
    }

    Collections.shuffle(data);
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  public void benchmarkEqualIgnoreCase() {
    for(int i = 0; i < data.size(); i++) {
      if(data.get(i).equalsIgnoreCase("string500")) {
        break;
      }
    }
  }

  @Benchmark
  @BenchmarkMode(Mode.Throughput)
  public void benchmarkEqualNotIgnoreCase() {
    for(int i = 0; i < data.size(); i++) {
      if(data.get(i).equals("string500")) {
        break;
      }
    }
  }
}
