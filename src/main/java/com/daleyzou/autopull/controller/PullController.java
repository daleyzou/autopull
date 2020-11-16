package com.daleyzou.autopull.controller;

/**
 * PullController
 * @description TODO
 * @author daleyzou
 * @date 2020年11月16日 16:12
 * @version 1.3.1
 */

import com.daleyzou.autopull.service.PullService;
import com.daleyzou.autopull.vo.RsVo;
import com.google.common.base.Splitter;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class PullController {
    @Autowired
    PullService pullService;

    private static final Splitter RELEASES_SPLITTER = Splitter.on(",").omitEmptyStrings().trimResults();

    private static final String USER_NAME = "daleyzou";

    private static final String PASSWORD = "daleyzouAutoPull";

    private static final String TYPE = "both";

    @GetMapping({"/code/pull"})
    public RsVo pullCode(@RequestParam(defaultValue = "") String location, @RequestParam(defaultValue = "") String userName, @RequestParam(defaultValue = "") String password, @RequestParam(defaultValue = "") String type) throws IOException {
        if (StringUtils.isEmpty(location) || !USER_NAME.equals(userName) || !PASSWORD.equals(password)) {
            return RsVo.fail("param is illegal");
        }
                    List<String> locations = RELEASES_SPLITTER.splitToList(location);
        if (CollectionUtils.isEmpty(locations)) {
            return RsVo.fail("param is illegal");
        }
        this.pullService.pullCode(locations, type);
        return RsVo.success("");
    }

    @PostMapping("/gitlab/iflow/hook")
    public RsVo gitlabIflowHook(HttpServletRequest request) throws IOException {
        String postData = getPostData(request);
        pullService.gitlabIflowHook(postData);
        return RsVo.success("success");
    }

    @PostMapping("/gitlab/iflowfe/hook")
    public RsVo gitlabIflowfeHook(HttpServletRequest request) throws IOException {
        String postData = getPostData(request);
        pullService.gitlabIflowfeHook(postData);
        return RsVo.success("success");
    }

    private static String getPostData(HttpServletRequest request) {
        StringBuffer data = new StringBuffer();
        String line;
        BufferedReader reader;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine())) {
                data.append(line);
            }
        } catch (IOException ioException) {
            log.error("/gitlab/hook, get data from request error!", ioException);
        }
        return data.toString();
    }
}

