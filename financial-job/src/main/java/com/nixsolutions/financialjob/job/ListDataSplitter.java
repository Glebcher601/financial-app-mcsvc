package com.nixsolutions.financialjob.job;

import static com.google.common.collect.Lists.newArrayList;
import static java.math.RoundingMode.DOWN;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.stereotype.Component;
import com.google.common.collect.ImmutableMap;
import com.google.common.math.IntMath;

@SuppressWarnings("unchecked")
@Component
public class ListDataSplitter implements BatchJobDataSplitter<List>
{
  private static final String PARTITION_TITLE = "partition";
  private static final String EXEC_CONTEXT_DATA = "data";

  public Map<String, ExecutionContext> splitData(List data, int gridSize)
  {
    if (data.size() < gridSize)
    {
      return deficientSplitResult(data);
    }

    IntFunction<Pair<Integer, Pair<Integer, Integer>>> intervalSplittingFn =
        toIndexIntervalPair(gridSize, data.size());

    Map<String, ExecutionContext> executionContextMap =
        IntStream.rangeClosed(1, gridSize)
            .mapToObj(intervalSplittingFn)
            .map(pair -> toExecutionContextEntry(pair, data))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    return executionContextMap;
  }

  private Pair<String, ExecutionContext> toExecutionContextEntry(Pair<Integer, Pair<Integer, Integer>>
                                                                     indexIntervalPair, List data)
  {
    return Pair.of(PARTITION_TITLE + indexIntervalPair.getLeft(),
        createExecContext(subList(data, indexIntervalPair.getRight())));
  }

  private List subList(List data, Pair<Integer, Integer> interval)
  {
    return newArrayList(data.subList(interval.getLeft(), interval.getRight()));
  }

  private IntFunction<Pair<Integer, Pair<Integer, Integer>>> toIndexIntervalPair(int gridSize, int
      boundaryValue)
  {
    int initialApprox = IntMath.divide(boundaryValue, gridSize, DOWN);
    int range = ((boundaryValue - (initialApprox * (gridSize - 1))) / gridSize > 0) ? initialApprox + 1 : initialApprox;

    return (val) -> Pair.of(val, (val == gridSize) ?
        Pair.of(val * range - range, boundaryValue) : Pair.of(val * range - range, val * range));
  }

  private ExecutionContext createExecContext(List data)
  {
    ExecutionContext executionContext = new ExecutionContext();
    executionContext.put(EXEC_CONTEXT_DATA, data);
    return executionContext;
  }

  private Map<String, ExecutionContext> deficientSplitResult(List data)
  {
    if (data.isEmpty())
    {
      return ImmutableMap.of();
    }

    return ImmutableMap.of(PARTITION_TITLE + 1, createExecContext(data));
  }
}
