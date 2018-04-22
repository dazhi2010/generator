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
package org.mybatis.generator.api.dom.java;

import freemarker.template.TemplateException;
import org.mybatis.generator.logging.LogFactory;
import org.mybatis.generator.util.FreeMarkerUtil;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public class FreeMarkerGeneratedClass extends TopLevelClass {
    private Map<String,Object> params;
    private String templateFileName;
    public FreeMarkerGeneratedClass(FullyQualifiedJavaType type) {
        super(type);
    }

    @Override
    public String getFormattedContent() {
        String result = null;
        try {
            result = FreeMarkerUtil.processText("/templates/ftl",templateFileName,params);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public String getTemplateFileName() {
        return templateFileName;
    }

    public void setTemplateFileName(String templateFileName) {
        this.templateFileName = templateFileName;
    }

}
