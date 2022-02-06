package com.epam.jwd.service.pagination.impl;

import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.service.pagination.Pagination;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaginationImplTest {

    RequestContext requestContext = Mockito.mock(RequestContext.class);
    Pagination pagination;

    private final Integer numberOfRecords = 18;

    @BeforeEach
    void setUp() {
        pagination = new PaginationImpl(numberOfRecords);
    }

    @Test
    void getPage() {
        requestContext.addAttributeToJSP("current_page",2);
       Integer result = pagination.getPage(requestContext);
       assertEquals(2,result);
    }

    @Test
    void getPagination() {

    }

    @AfterEach
    void tearDown() {
    }
}