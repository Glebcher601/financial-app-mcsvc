package com.nixsolutions.financialjob.job;

import java.util.Map;
import org.springframework.batch.item.ExecutionContext;

public interface BatchJobDataSplitter<T>
{
  Map<String, ExecutionContext> splitData(T data, int gridSize);
}
