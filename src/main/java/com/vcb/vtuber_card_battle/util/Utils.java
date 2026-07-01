package com.vcb.vtuber_card_battle.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class Utils {
    public static Pageable getPageable(int page, int size, String sortBy, String direction) {
        // 建立 Pageable
        Sort.Direction sortDirection = direction.equalsIgnoreCase("desc")
                ? Sort.Direction.DESC : Sort.Direction.ASC;

       return  (Pageable) PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
    }
}
