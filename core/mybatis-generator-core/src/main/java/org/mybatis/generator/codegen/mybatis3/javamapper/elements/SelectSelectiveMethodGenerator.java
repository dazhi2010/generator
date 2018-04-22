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
package org.mybatis.generator.codegen.mybatis3.javamapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.java.*;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author 于钦法
 * 
 */
public class SelectSelectiveMethodGenerator extends
        AbstractJavaMapperMethodGenerator {

    private boolean isSimple;

    public SelectSelectiveMethodGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);

        FullyQualifiedJavaType returnType = FullyQualifiedJavaType.getNewListInstance();
        returnType.addTypeArgument(introspectedTable.getRules().calculateAllFieldsClass());
        method.setReturnType(returnType);
        importedTypes.add(returnType);

        method.setName(introspectedTable.getSelectSelectiveStatementId());
        importedTypes.add(new FullyQualifiedJavaType(
                "org.apache.ibatis.annotations.Param"));

        FullyQualifiedJavaType type = new FullyQualifiedJavaType(introspectedTable.getVoType());
        importedTypes.add(type);
        Parameter parameterRecord = new Parameter(type, "record");
        StringBuilder sb = new StringBuilder();
        sb.append("@Param(\""); //$NON-NLS-1$
        sb.append("record");
        sb.append("\")"); //$NON-NLS-1$
        parameterRecord.addAnnotation(sb.toString());
        method.addParameter(parameterRecord);

        Parameter parameterOrderBy = new Parameter(FullyQualifiedJavaType.getStringInstance(), "orderBy");
        sb.setLength(0);
        sb.append("@Param(\""); //$NON-NLS-1$
        sb.append("orderBy");
        sb.append("\")"); //$NON-NLS-1$
        parameterOrderBy.addAnnotation(sb.toString());
        method.addParameter(parameterOrderBy);
        addMapperAnnotations(interfaze, method);

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        if (context.getPlugins().clientSelectByPrimaryKeyMethodGenerated(
                method, interfaze, introspectedTable)) {
            addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Interface interfaze, Method method) {
    }

    public void addExtraImports(Interface interfaze) {
    }
}
