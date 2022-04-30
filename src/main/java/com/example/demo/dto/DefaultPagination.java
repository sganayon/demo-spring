package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;

@Data
@With
@AllArgsConstructor
@NoArgsConstructor
public class DefaultPagination {
    int pageSize = 50;
    int pageNumber = 0;
    String sortElement = "";
    boolean asc = true;
}
