/**
 *    Copyright 2006-2018 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package org.mybatis.generator.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.*;

public class FreeMarkerUtil {
    /**
     * 根据模板生成文件并放入流
     * @param templateFolder 模板目录
     * @param templateFileName 模板文件名称
     * @param out 生成后的文件输出方式
     * @param params 参数
     * @throws IOException
     * @throws TemplateException
     */
    public static void process(String templateFolder,String templateFileName,Writer out,Map<String,Object> params) throws IOException, TemplateException {
        //第一步：实例化Freemarker的配置类
        Configuration conf = new Configuration();
        //第二步：给配置类设置路径
        conf.setClassForTemplateLoading(FreeMarkerUtil.class,templateFolder);
        Template template = conf.getTemplate(templateFileName);
        //第三步：处理模板及数据之间将数据与模板合成一个HTML
        //执行生成
        template.process(params, out);
        out.flush();
        out.close();
    }

    /**
     * 根据模板生成字符串
     * @param templateFolder
     * @param templateFileName
     * @param params
     * @return
     * @throws IOException
     * @throws TemplateException
     */
    public static String processText(String templateFolder,String templateFileName,Map<String,Object> params) throws IOException, TemplateException {
        Writer out = new StringWriter();
        process(templateFolder,templateFileName,out,params);
        return out.toString();
    }
}
