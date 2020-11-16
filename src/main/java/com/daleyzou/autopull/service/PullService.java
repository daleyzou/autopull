package com.daleyzou.autopull.service;

/**
 * PullService
 * @description TODO
 * @author daleyzou
 * @date 2020年11月16日 16:11
 * @version 1.3.1
 */

import com.daleyzou.autopull.util.ShellUtil;

import java.io.IOException;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

@Service
@EnableAsync
@Slf4j
public class PullService {
    private static final String TYPE = "both";

    private static final String GRAY_SS = "grayss.dev.aixuexi.com";

    private static final String GRAY_SS_HOST = "172.16.28.242";

    private static final String GRAY_RELEASE = "gray.dev.aixuexi.com";

    private static final String GRAY_RELEASE_HOST = "172.16.5.27";

    public void pullCode(List<String> locations, String type) throws IOException {
        for (String url : locations) {
            if ("grayss.dev.aixuexi.com".equals(url)) {
                ShellUtil.executeShell("ssh -p 29133 root@172.16.28.242 'sh /data/icode_3_0_0_code/deploy-fe.sh'", 0, "");
                if ("both".equals(type))
                    ShellUtil.executeShell("ssh -p 29133 root@172.16.28.242 'sh /data/icode_3_0_0_code/deploy.sh'", 0, "");
            }
            if ("gray.dev.aixuexi.com".equals(url)) {
                ShellUtil.executeShell("sh /data/icode_3_0_0_code/deploy-fe.sh", 0, "");
                if ("both".equals(type))
                    ShellUtil.executeShell("sh /data/icode_3_0_0_code/deploy.sh", 0, "");
            }
        }
    }

    @Async
    public void gitlabIflowHook(String postData) throws IOException {
        ShellUtil.executeShell("sh /data/icode_3_0_0_code/deploy.sh'", 0, "");
    }

    @Async
    public void gitlabIflowfeHook(String postData) throws IOException {
        ShellUtil.executeShell("sh /data/icode_3_0_0_code/deploy-fe.sh", 0, "");
    }
}

