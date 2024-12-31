package org.example.laboratoryappointment.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchInformation {
//    on不能命中JSON索引！！！
    public static final int MAX_SIZE = 200;
    public static final List<Integer> INIT_START = List.of(1, 1, 1);
    public static final List<Integer> INIT_END = List.of(20, 7, 12);
    private int size;
    private List<String> info;
    private List<Integer> startTime;
    private List<Integer> endTime;
    private LocalDateTime createTime;
}
