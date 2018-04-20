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
import org.mybatis.generator.config.PropertyRegistry;
import org.mybatis.generator.internal.util.JavaBeansUtil;

import java.util.Iterator;

/**
 * 
 * @author yuqinfa
 * 
 */
public class WhereFullClauseElementGenerator extends AbstractXmlElementGenerator {

    public WhereFullClauseElementGenerator() {
        super();
    }
    @Override
    public void addElements(XmlElement parentElement) {
        String deletedField = introspectedTable.getTableConfigurationProperty(PropertyRegistry.TABLE_DELETED_FIELD);//标记删除字段
        String deletedJavaField = JavaBeansUtil.getCamelCaseString(deletedField,false);//标记删除字段的bean中属性名称
        XmlElement answer = new XmlElement("sql"); //$NON-NLS-1$
        answer.addAttribute(new Attribute("id", //$NON-NLS-1$
                "where_full"));
        XmlElement where = new XmlElement("where");
        XmlElement choose = new XmlElement("choose");
        XmlElement when1 = new XmlElement("when");
        //查询已删除的
        when1.addAttribute(new Attribute("test","record."+ deletedJavaField+" == 1"));
        when1.addElement(new TextElement(deletedField + " = 1"));
        choose.addElement(when1);
        //查询全部的
        XmlElement when2 = new XmlElement("when");
        when2.addAttribute(new Attribute("test","record."+ deletedJavaField+" == 2"));
        when2.addElement(new TextElement(" true "));
        choose.addElement(when2);
        //默认查询未删除的
        XmlElement otherwise = new XmlElement("otherwise");
        otherwise.addElement(new TextElement(" IFNULL("+deletedField+",0) != 1 "));
        choose.addElement(otherwise);
        where.addElement(choose);
        Iterator<IntrospectedColumn> iter = introspectedTable.getAllColumns().iterator();
        while (iter.hasNext()) {
            IntrospectedColumn introspectedColumn = iter.next();
            String columnName = MyBatis3FormattingUtilities.getEscapedColumnName(introspectedColumn);
            if(columnName.equals(deletedField)){//删除标识字段不再重复添加
                continue;
            }
            String parameterName = introspectedColumn.getJavaProperty();
            XmlElement ifElement = new XmlElement("if");
            ifElement.addAttribute(new Attribute("test","record."+ parameterName+" != null"));
            ifElement.addElement(new TextElement(" AND "+columnName+" = "+MyBatis3FormattingUtilities.getParameterClause(introspectedColumn,"record.")));
            where.addElement(ifElement);
        }
        answer.addElement(where);
        XmlElement orderByElement = new XmlElement("if");
        orderByElement.addAttribute(new Attribute("test","orderBy!=null and orderBy!=''"));
        orderByElement.addElement(new TextElement("ORDER BY ${orderBy}"));
        answer.addElement(orderByElement);
        if (context.getPlugins().sqlMapBlobColumnListElementGenerated(
                answer, introspectedTable)) {
            parentElement.addElement(answer);
        }
    }
}
