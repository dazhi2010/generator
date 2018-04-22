<#--

       Copyright 2006-2018 the original author or authors.

       Licensed under the Apache License, Version 2.0 (the "License");
       you may not use this file except in compliance with the License.
       You may obtain a copy of the License at

          http://www.apache.org/licenses/LICENSE-2.0

       Unless required by applicable law or agreed to in writing, software
       distributed under the License is distributed on an "AS IS" BASIS,
       WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
       See the License for the specific language governing permissions and
       limitations under the License.

-->
package ${packageFullPath};

<#list imports as i>
import  ${i};
</#list>

public interface ${serviceName}{
    /**
     * 假删除，可删除多个/单个
     * @param list
     * @return
     */
    int delete(List<${deleteClass}> list);
    /**
     * 新增单个
     * @param record
     * @return
     */
    int insert(${modelName} record);
    /**
     * 查询单个完整信息
     * @param id
     * @return
     */
    ${modelName} selectByPrimaryKey(${pkFields});
    /**
     * 修改单个基本信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(${modelName} record);
    /**
     * 查询多个基本信息
     * @param record
     * @param orderBy
     * @return
     */
    List<${modelName}> selectSelective(${voName} record, String orderBy);
    /**
    * 物理删除
    * @param list
    * @return
    */
    int deletePhysical(List<${deleteClass}> list);
}
