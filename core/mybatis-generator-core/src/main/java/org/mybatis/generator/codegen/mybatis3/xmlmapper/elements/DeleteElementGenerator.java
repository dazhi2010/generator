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
package org.mybatis.generator.codegen.mybatis3.xmlmapper.elements;

import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.dom.xml.Attribute;
import org.mybatis.generator.api.dom.xml.TextElement;
import org.mybatis.generator.api.dom.xml.XmlElement;
import org.mybatis.generator.codegen.mybatis3.MyBatis3FormattingUtilities;

/**
 * 假删除xml
 * @author yuqinfa
 * 
 */
public class DeleteElementGenerator extends
        AbstractXmlElementGenerator {

    private boolean isSimple;

    public DeleteElementGenerator(boolean isSimple) {
        super();
        this.isSimple = isSimple;
    }

    @Override
    public void addElements(XmlElement parentElement) {
        XmlElement answer = new XmlElement("update"); //$NON-NLS-1$

        answer.addAttribute(new Attribute(
                "id", introspectedTable.getDeleteStatementId())); //$NON-NLS-1$
        String parameterClass = "java.util.List";//直接修改为传入列表进行批量删除
        answer.addAttribute(new Attribute("parameterType", //$NON-NLS-1$
                parameterClass));

        context.getCommentGenerator().addComment(answer);

        StringBuilder sb = new StringBuilder();
        sb.append("update "); //$NON-NLS-1$
        sb.append(introspectedTable.getFullyQualifiedTableNameAtRuntime());//获取表名
        sb.append(" set ");//deleted=1
        sb.append(introspectedTable.getTableConfigurationProperty("deletedField"));
        sb.append(" = 1");
        answer.addElement(new TextElement(sb.toString()));
        answer.addElement(new TextElement("where false"));//$NON-NLS-1$

        XmlElement foreachE = new XmlElement("foreach");//foreach标签
        foreachE.addAttribute(new Attribute("collection", //$NON-NLS-1$
                "list"));
        foreachE.addAttribute(new Attribute("index", //$NON-NLS-1$
                "index"));
        foreachE.addAttribute(new Attribute("item", //$NON-NLS-1$
                "item"));
        boolean and = false;
        int keyCount = introspectedTable.getPrimaryKeyColumns().size();//主键个数
        for (int i = 0;i<keyCount;i++) {
            IntrospectedColumn introspectedColumn = introspectedTable.getPrimaryKeyColumns().get(i);
            sb.setLength(0);
            if (and) {
                sb.append(" and "); //$NON-NLS-1$
            } else {
                and = true;
                sb.append(" or ");
            }
            if(keyCount>1 && i==0){//如果是多个主键且第一个循环，加上前半个括号
                sb.append("(");
            }
            sb.append(MyBatis3FormattingUtilities
                    .getEscapedColumnName(introspectedColumn));
            sb.append(" = "); //$NON-NLS-1$
            if(keyCount>1){//如果是多个主键，用列名匹配
                sb.append(MyBatis3FormattingUtilities
                        .getParameterClause(introspectedColumn,"item."));
            }else{//否则直接用item匹配
                sb.append("#{item}");
            }

            if(keyCount>1 && i==keyCount-1){//如果是多个主键且最后一个循环，加上后半个括号
                sb.append(")");
            }
            foreachE.addElement(new TextElement(sb.toString()));
        }
        answer.addElement(foreachE);
        if (context.getPlugins()
                .sqlMapDeleteByPrimaryKeyElementGenerated(answer,
                        introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
