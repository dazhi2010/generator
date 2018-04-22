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

import com.skywares.safety.wewell.service.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<#list imports as i>
import  ${i};
</#list>
@Service
public class ${serviceImplName} extends BaseServiceImpl implements ${serviceName}{
    @Autowired
    private ${MapperName} mapper;

    @Override
    @Transactional
    public int delete(List<${deleteClass}> list){
        return mapper.delete(list);
    }

    @Override
    @Transactional
    public int insert(${modelName} record){
        return mapper.insert(record);
    }

    @Override
    public ${modelName} selectByPrimaryKey(${pkFields}){
        return mapper.selectByPrimaryKey(${pkFieldsNoType});
    }

    @Override
    @Transactional
    public int updateByPrimaryKeySelective(${modelName} record){
        return mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<${modelName}> selectSelective(${voName} record, String orderBy){
        return mapper.selectSelective(record,orderBy);
    }

    @Override
    public int deletePhysical(List<${deleteClass}> list){
        return mapper.deletePhysical(list);
    }
    @Override
    protected String[] getRegularParams() {
        return new String[0];
    }
}
