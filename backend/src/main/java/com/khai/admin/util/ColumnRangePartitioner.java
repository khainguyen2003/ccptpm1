package com.khai.admin.util;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;

import java.util.HashMap;
import java.util.Map;

public class ColumnRangePartitioner implements Partitioner {
    private int min;
    private int max;

    public ColumnRangePartitioner() {
        int min = 1;
        int max = 1000;
    }

    public ColumnRangePartitioner(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public Map<String, ExecutionContext> partition(int gridSize) {//2
        int targetSize = (this.max - this.min) / gridSize + 1;//500
        System.out.println("targetSize : " + targetSize);
        Map<String, ExecutionContext> result = new HashMap<>();

        int number = 0;
        int start = this.min;
        int end = start + targetSize - 1;
        //1 to 500
        // 501 to 1000
        while (start <= this.max) {
            ExecutionContext value = new ExecutionContext();
            result.put("partition" + number, value);

            if (end >= this.max) {
                end = this.max;
            }
            value.putInt("minValue", start);
            value.putInt("maxValue", end);
            start += targetSize;
            end += targetSize;
            number++;
        }
        System.out.println("partition result:" + result.toString());
        return result;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
