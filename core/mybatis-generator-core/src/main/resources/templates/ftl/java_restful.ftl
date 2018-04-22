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

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.missionsky.framework.DefaultAjaxResult;
import com.missionsky.framework.DefaultRequestCode;
import com.missionsky.framework.annotations.JsonParameter;
import com.wewell.framework.BasicRestFulController;
import com.wewell.plugin.logs.common.Logger;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
<#list imports as i>
import  ${i};
</#list>

@RestController
@RequestMapping("/${pathName}")
public class ${modelName}Restful extends BasicRestFulController {
    @Autowired
    private ${serviceName} service;

    /**
     * @api {post} /${pathName}/list
     * @apiGroup ${pathName}
     * @apiName  查询多个信息
     * @apiDescription 查询多个信息
     * @apiParamExample {json} 示例
     * {
     *	     "record":{}
     * }
     * @Author
     * @return
     */
    @RequestMapping(value="/list", method=RequestMethod.POST)
    public DefaultAjaxResult<?> list(
            @JsonParameter("record")${voName} record,
            @JsonParameter("page") Integer page,
            @JsonParameter("limit") Integer limit,
            @JsonParameter("orderBy")String orderBy
    ) {
        try {
            if(page !=null&&limit!=null){
                PageHelper.startPage(page,limit);
            }
            List<${modelName}> list =  service.selectSelective(record,orderBy);
            PageInfo<${modelName}> info = new PageInfo<>(list);
            return new DefaultAjaxResult<List<${modelName}>>(DefaultRequestCode.REQUEST_SUCCESS,"查询成功",Integer.valueOf(info.getTotal()+""),list);
        } catch (Exception e) {
            logger.error("查询${modelName}失败",e);
            return new DefaultAjaxResult<Integer>(DefaultRequestCode.REQUEST_API_EXCEPTION,"查询失败："+e.getMessage(),0);
        }
    }
    /**
     * @api {post} /${pathName}/add 新增单个信息
     * @apiGroup ${pathName}
     * @apiName 新增单个信息
     * @apiDescription 新增单个信息
     * @apiParamExample {json} 示例
     * {
     *	     "record":{
     *
     *	     }
     * }
     * @Author 
     * @return
     */
    @RequestMapping(value="/add", method=RequestMethod.POST)
    public DefaultAjaxResult<?> add(@JsonParameter("record")${modelName} record) {
        try {
            DefaultAjaxResult<Integer> result =
                    new DefaultAjaxResult<Integer>(DefaultRequestCode.REQUEST_SUCCESS,"",service.insert(record));
            logService.info(Logger.MODULE_BASEINFO, Logger.OPERATE_ADD, "新增单个${modelName}信息：");
            return result;
        } catch (Exception e) {
            logger.error("新增单个${modelName}信息失败",e);
            return new DefaultAjaxResult<Integer>(DefaultRequestCode.REQUEST_API_EXCEPTION,"添加失败",0);
        }
    }
    /**
     * @api {post} /${pathName}/update 修改单个活动的信息
     * @apiGroup ${pathName}
     * @apiName update
     * @apiDescription 修改单个活动的信息
     * @apiParamExample {json} 示例
     * {
     *	     "record":{
     *
     *	     }
     * }
     * @Author 
     * @return
     */
    @RequestMapping(value="/update", method=RequestMethod.POST)
    public DefaultAjaxResult<?> updateByPrimaryKey(@JsonParameter("record")${modelName} record) {
        try {
            DefaultAjaxResult<Integer> result =
                    new DefaultAjaxResult<Integer>(DefaultRequestCode.REQUEST_SUCCESS,"",service.updateByPrimaryKeySelective(record));
            logService.info(Logger.MODULE_BASEINFO, Logger.OPERATE_UPDATE, "修改单个${modelName}信息，ID："+record.getId());
            return  result;
        } catch (Exception e) {
            logger.error("修改单个${modelName}信息失败",e);
            return new DefaultAjaxResult<Integer>(DefaultRequestCode.REQUEST_API_EXCEPTION,"修改失败",0);
        }
    }
    /**
     * @api {get} /${pathName}/delete
     * @apiGroup ${pathName}
     * @apiName 删除多个信息
     * @apiDescription 删除多个信息
     * @apiParam List<Long> list id数组
     * @apiParamExample {json} 示例
     *
     *		[1,2,3]
     *
     * @Author
     * @date
     * @returns
     */
    @RequestMapping(value="/delete", method=RequestMethod.POST)
    public DefaultAjaxResult<Integer> delete(@JsonParameter("list") List<${deleteClass}> list) {
        try {
            DefaultAjaxResult<Integer> result =
                    new DefaultAjaxResult<Integer>(DefaultRequestCode.REQUEST_SUCCESS,"删除成功",
                            service.delete(list));
            logService.info(Logger.MODULE_USER, Logger.OPERATE_DELETE, "删除${modelName}信息");
            return result;
        } catch (Exception e) {
            logger.error("删除${modelName}信息失败",e);
            return new DefaultAjaxResult<Integer>(DefaultRequestCode.REQUEST_API_EXCEPTION,"删除失败",0);
        }
    }
    /**
    * @api {get} /${pathName}/deletePhysical
    * @apiGroup ${pathName}
    * @apiName 物理删除多个信息
    * @apiDescription 物理删除多个信息
    * @apiParam List<Long> list id数组
    * @apiParamExample {json} 示例
    *
    *		[1,2,3]
    *
    * @Author
    * @date
    * @returns
    */
    @RequestMapping(value = "/deletePhysical", method=RequestMethod.POST)
    public DefaultAjaxResult<?> deletePhysical(@JsonParameter("list") List<${deleteClass}> list){
        try{
            int result =  service.deletePhysical(list);
            logService.info(Logger.MODULE_USER, Logger.OPERATE_DELETE, "物理删除多个${modelName}信息");
            return new DefaultAjaxResult<>(DefaultRequestCode.REQUEST_SUCCESS,"删除成功",0);
        }catch (Exception e){
            logger.error("物理删除${modelName}信息失败",e);
            return new DefaultAjaxResult<Integer>(DefaultRequestCode.REQUEST_API_EXCEPTION,"查询失败："+e.getMessage(),0);
        }
    }
}
