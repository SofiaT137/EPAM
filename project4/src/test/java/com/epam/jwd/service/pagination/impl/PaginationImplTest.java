package com.epam.jwd.service.pagination.impl;


import com.epam.jwd.service.pagination.Pagination;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaginationImplTest {

    Pagination pagination;

    private final Integer numberOfRecords = 18;

    @BeforeEach
    void setUp() {
        pagination = new PaginationImpl(numberOfRecords);
    }


    @Test
    void getPagination() {
        Map<String,Integer> checkMap = pagination.getPagination(1);
        assertEquals(4,checkMap.get("numberOfPages"));
        assertEquals(0,checkMap.get("from"));
        assertEquals(5,checkMap.get("to"));

        checkMap = pagination.getPagination(3);
        assertEquals(4,checkMap.get("numberOfPages"));
        assertEquals(10,checkMap.get("from"));
        assertEquals(15,checkMap.get("to"));

        checkMap = pagination.getPagination(4);
        assertEquals(4,checkMap.get("numberOfPages"));
        assertEquals(15,checkMap.get("from"));
        assertEquals(18,checkMap.get("to"));
    }

    @AfterEach
    void tearDown() {
    }
}