package com.epam.jwd.service.pagination.impl;

import com.epam.jwd.controller.context.RequestContext;
import com.epam.jwd.service.pagination.Pagination;

import java.util.HashMap;
import java.util.Map;

public class PaginationImpl implements Pagination {

    private static final Integer RECORDS_ON_PAGE = 5;
    private final Integer numberOfRecords;
    private final Integer numberOfPages;

    private static final String TO = "to";
    private static final String FROM = "from";
    private static final String PAGE = "page";
    private static final String NUMBER_OF_PAGES = "numberOfPages";

    public PaginationImpl(Integer numberOfRecords) {

        this.numberOfRecords = numberOfRecords;
        this.numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_ON_PAGE);
    }

    @Override
    public int getPage(RequestContext requestContext) {
        int page = 1;

        if(requestContext.getParameterFromJSP(PAGE) != null){
            page = Integer.parseInt(requestContext.getParameterFromJSP(PAGE));
        }
        if (page < 1 || page > numberOfPages){
            page = 1;
        }
        return page;
    }

    @Override
    public Map<String, Integer> getPagination(int page) {
        Map<String,Integer> pagination = new HashMap<>();
        pagination.put(NUMBER_OF_PAGES ,numberOfPages);
        pagination.put(FROM,(page-1) * RECORDS_ON_PAGE);
        pagination.put(TO,Math.min(page * RECORDS_ON_PAGE,numberOfRecords));
        return pagination;
    }
}
