package com.epam.jwd.service.pagination;

import com.epam.jwd.controller.context.RequestContext;

import java.util.Map;

public interface Pagination {
    int getPage(RequestContext requestContext);
    Map<String,Integer> getPagination(int page);
}
