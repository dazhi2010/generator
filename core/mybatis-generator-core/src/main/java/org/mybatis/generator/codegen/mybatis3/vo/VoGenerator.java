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
package org.mybatis.generator.codegen.mybatis3.vo;

import org.mybatis.generator.api.dom.java.*;
import org.mybatis.generator.codegen.AbstractJavaGenerator;
import java.util.*;

/**
 * 
 * @author yuqinfa
 * 
 */
public class VoGenerator extends AbstractJavaGenerator {

    public VoGenerator() {
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getVoType());
        FreeMarkerGeneratedClass generatedClass = new FreeMarkerGeneratedClass(type);
        Map<String,Object> root = new HashMap<String,Object>();
        List<String> imports = new ArrayList<>();
        imports.add(introspectedTable.getBaseRecordType());
        root.put("packageFullPath",introspectedTable.getContext().getJavaVoGeneratorConfiguration().getTargetPackage());
        root.put("imports",imports);
        root.put("classVoName",new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType()).getShortName()+"Vo");
        root.put("className",new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType()).getShortName());
        generatedClass.setParams(root);
        generatedClass.setTemplateFileName("java_vo.ftl");
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelExampleClassGenerated(
                generatedClass, introspectedTable)) {
            answer.add(generatedClass);
        }
        return answer;
    }

}
