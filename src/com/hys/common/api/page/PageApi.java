package com.hys.common.api.page;

import com.hys.common.api.base.BaseApi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageApi implements BaseApi {

    @RequestMapping(value = "/api/page/{page}", method = RequestMethod.GET)
    public String page(@PathVariable("page") String page) {
        return page;
    }
}
