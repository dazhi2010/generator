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
package org.mybatis.generator.codegen.mybatis3.restful;

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
public class RestfulGenerator extends AbstractJavaGenerator {

    public RestfulGenerator() {
        super();
    }

    @Override
    public List<CompilationUnit> getCompilationUnits() {
        FullyQualifiedJavaType type = new FullyQualifiedJavaType(
                introspectedTable.getRestfulType());
        FreeMarkerGeneratedClass generatedClass = new FreeMarkerGeneratedClass(type);
        Map<String,Object> root = new HashMap<String,Object>();
        Set<String> imports = new HashSet<>();
        imports.add(introspectedTable.getBaseRecordType());
        imports.add(introspectedTable.getVoType());
        imports.add(introspectedTable.getServiceType());
        root.put("packageFullPath",introspectedTable.getContext().getJavaRestfulGeneratorConfiguration().getTargetPackage());
        root.put("serviceName",new FullyQualifiedJavaType(introspectedTable.getServiceType()).getShortName());
        String modelName = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType()).getShortName();
        root.put("modelName",modelName);
        root.put("voName",new FullyQualifiedJavaType(introspectedTable.getVoType()).getShortName());
        root.put("pathName", Character.toLowerCase(modelName.charAt(0)) + modelName.substring(1));
        List<IntrospectedColumn> pks = introspectedTable.getPrimaryKeyColumns();
        int keyCount = pks.size();
        FullyQualifiedJavaType typeArgument = null;
        if(keyCount==1){
            typeArgument = pks.get(0) .getFullyQualifiedJavaType();
        }else{
            typeArgument = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        }
        imports.add(typeArgument.getFullyQualifiedName());
        root.put("deleteClass",typeArgument.getShortName());

        root.put("imports",imports);
        generatedClass.setParams(root);
        generatedClass.setTemplateFileName("java_restful.ftl");
        List<CompilationUnit> answer = new ArrayList<CompilationUnit>();
        if (context.getPlugins().modelExampleClassGenerated(
                generatedClass, introspectedTable)) {
            answer.add(generatedClass);
        }
        return answer;
    }

}
