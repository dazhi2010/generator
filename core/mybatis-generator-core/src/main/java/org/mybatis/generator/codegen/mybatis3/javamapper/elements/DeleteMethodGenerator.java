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
 * @author Jeff Butler
 * 
 */
public class DeleteMethodGenerator extends
        AbstractJavaMapperMethodGenerator {

    private boolean isSimple;

    public DeleteMethodGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addInterfaceElements(Interface interfaze) {
        Set<FullyQualifiedJavaType> importedTypes = new TreeSet<FullyQualifiedJavaType>();
        Method method = new Method();
        method.setVisibility(JavaVisibility.PUBLIC);
        method.setReturnType(FullyQualifiedJavaType.getIntInstance());
        method.setName(introspectedTable.getDeleteStatementId());
        List<IntrospectedColumn> introspectedColumns = introspectedTable
                .getPrimaryKeyColumns();
        int keyCount = introspectedColumns.size();
        FullyQualifiedJavaType typeArgument = null;
        if(keyCount==1){
            typeArgument = introspectedColumns.get(0) .getFullyQualifiedJavaType();
        }else{
            typeArgument = new FullyQualifiedJavaType(introspectedTable.getBaseRecordType());
        }
        FullyQualifiedJavaType type = FullyQualifiedJavaType.getNewListInstance();
        type.addTypeArgument(typeArgument);
        importedTypes.add(type);
        Parameter parameter = new Parameter(type, "list");
                method.addParameter(parameter);

        context.getCommentGenerator().addGeneralMethodComment(method,
                introspectedTable);

        addMapperAnnotations(method);
        
        if (context.getPlugins().clientDeleteByPrimaryKeyMethodGenerated(
                method, interfaze, introspectedTable)) {
            addExtraImports(interfaze);
            interfaze.addImportedTypes(importedTypes);
            interfaze.addMethod(method);
        }
    }

    public void addMapperAnnotations(Method method) {
    }

    public void addExtraImports(Interface interfaze) {
    }
}
