package com.daleyzou.autopull.util;

/**
 * ShellUtil
 * @description TODO
 * @author daleyzou
 * @date 2020年11月16日 16:08
 * @version 1.3.1
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShellUtil {
    private static final Logger logger = LoggerFactory.getLogger(ShellUtil.class);

    private static final String basePath = "/data/shared/source/iteration/shell/";

    private static final String executeShellLogFile = "/data/shared/source/iteration/shell/executeShell.log";

    public static final int return_Type_0 = 0;

    public static final int return_Type_1 = 1;

    public static final String succes_str_1 = "1";

    public static final String fail_str_0 = "0";

    public static String executeShell(String shellCommand, int returnType, String checkStr) throws IOException {
        logger.info("exec shell cmd =" + shellCommand + " check=" + checkStr);
        String success = "0";
        boolean success_check = (checkStr == null);
        StringBuilder returnInfo = new StringBuilder("");
        StringBuilder stringBuilder = new StringBuilder();
        BufferedReader bufferedReader = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss::SSS ");
        try {
            stringBuilder.append(dateFormat.format(new Date())).append("").append(shellCommand)
                    .append(" \r\n");
            Process pid = null;
            String[] cmd = { "/bin/sh", "-c", shellCommand };
            pid = Runtime.getRuntime().exec(cmd);
            if (pid != null) {
                stringBuilder.append("进程号：").append(pid.toString()).append("\r\n");
                        bufferedReader = new BufferedReader(new InputStreamReader(pid.getInputStream()), 1024);
                pid.waitFor();
            } else {
                stringBuilder.append("\r\n");
            }
            stringBuilder.append(dateFormat.format(new Date())).append("Shell\r\n");
            String line = null;
            while (bufferedReader != null && (line = bufferedReader.readLine()) != null) {
                returnInfo.append(line).append("\r\n");
                if (!success_check && checkStr != null &&
                        line.indexOf(checkStr) != -1)
                    success_check = Boolean.TRUE.booleanValue();
            }
            stringBuilder.append(returnInfo.toString()).append("\r\n");
            success = "1";
        } catch (Exception ioe) {
            stringBuilder.append("\r\n").append(ioe.getMessage()).append("\r\n");
            logger.error("error executeShell= " + stringBuilder.toString());
        } finally {
            if (bufferedReader != null) {
                OutputStreamWriter outputStreamWriter = null;
                try {
                    bufferedReader.close();
                    OutputStream outputStream = new FileOutputStream("/data/shared/source/iteration/shell/executeShell.log", true);
                    outputStreamWriter = new OutputStreamWriter(outputStream, "UTF-8");
                    outputStreamWriter.write(stringBuilder.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    outputStreamWriter.close();
                }
            }
        }
        String returnStr = returnInfo.toString();
        if (returnStr != null &&
                returnStr.indexOf("404 Not Found") != -1)
            success = "0";
        logger.info(returnType + " <exec shell cmd  end=" + returnStr);
        if (success.equals("1") && success_check) {
            success = "1";
        } else {
            success = "0";
        }
        if (0 == returnType)
            return success;
        return returnInfo.toString();
    }

    public static List<String> callShell(String script, String args) {
        List<String> shellOutPutList = new ArrayList<>();
        try {
            String cmd = "sh " + script + " " + args;
            Process process = Runtime.getRuntime().exec(cmd, (String[])null, (File)null);
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = "";
            while ((line = input.readLine()) != null)
                shellOutPutList.add(line);
            input.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return shellOutPutList;
    }
}
