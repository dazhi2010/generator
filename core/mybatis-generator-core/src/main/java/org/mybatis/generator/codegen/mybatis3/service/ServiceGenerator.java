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
package org.mybatis.generator.codegen.mybatis3.service;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.FreeMarkerGeneratedClass;
import org.mybatis.generator.api.dom.java.FullyQualifiedJavaType;
import org.mybatis.generator.codegen.AbstractJavaGenerator;

import java.util.*;

/**
 * 
 * @author yuqinfa
 * 
 */
public class ServiceGenerator extends AbstractJavaGenerator {

    public ServiceGenerator() {
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getServiceType());
        FreeMarkerGeneratedClass generatedClass = new FreeMarkerGeneratedClass(type);
        Map<String,Object> root = new HashMap<String,Object>();
        Set<String> imports = new HashSet<>();
        imports.add(introspectedTable.getBaseRecordType());
        imports.add(introspectedTable.getVoType());
        imports.add("java.util.List");
        root.put("packageFullPath",introspectedTable.getContext().getJavaServiceGeneratorConfiguration().getTargetPackage());

        root.put("serviceName",type.getShortName());
        root.put("modelName",new FullyQualifiedJavaType(
                introspectedTable.getBaseRecordType()).getShortName());
        root.put("voName",new FullyQualifiedJavaType(
                introspectedTable.getVoType()).getShortName());
        List<IntrospectedColumn> introspectedColumns = introspectedTable.getPrimaryKeyColumns();
        int keyCount = introspectedColumns.size();
        FullyQualifiedJavaType typeArgument = null;
        if(keyCount==1){
            typeArgument = introspectedColumns.get(0) .getFullyQualifiedJavaType();
        }else{
            typeArgument = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        }
        imports.add(typeArgument.getFullyQualifiedName());
        List<IntrospectedColumn> pks = introspectedTable.getPrimaryKeyColumns();
        StringBuilder pkString = new StringBuilder();
        for (int i = 0; i < pks.size(); i++) {
            IntrospectedColumn ic = pks.get(i);
            pkString.append(ic.getFullyQualifiedJavaType().getShortName());
            pkString.append(" ");
            pkString.append(ic.getJavaProperty());
            if(i<pks.size()-1){
                pkString.append(",");
            }
            imports.add(ic.getFullyQualifiedJavaType().getFullyQualifiedName());
        }
        root.put("pkFields",pkString.toString());
        root.put("deleteClass",typeArgument.getShortName());

        root.put("imports",imports);
        generatedClass.setParams(root);
        generatedClass.setTemplateFileName("java_service.ftl");
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelExampleClassGenerated(
                generatedClass, introspectedTable)) {
            answer.add(generatedClass);
        }
        return answer;
    }

}
