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
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>


${person.id}
<br /> ${person.name} <#list persons as person> ${person} </#list>

<!--Map写法  -->
${mx.alingluo}/${mx.lling} <#list mx?keys as k> ${mx[k]} </#list>

<!--List<Map>写法  -->
    <#list maps as m> ${m.id1}/${m.id2} </#list> <#list maps as m> <#list
m?keys as k> ${m[k]} </#list> </#list>

<!--获取当前索引  -->
    <#list persons as p> ${p_index} </#list>

<!-- 在模板中赋值 -->
1:<#assign x=0 /> ${x}
2:<#assign x="${world}" /> ${x}
3:<#assign x>世界太好了</#assign> ${x}
4:<#assign x>
    <#list ["星期一", "星期二", "星期三",
    "星期四", "星期五", "星期六", "星期天"] as n> ${n}
    </#list>
</#assign>
${x}

<!-- 判断 -->
  <#list ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"] as n>
      <#if n != "星期一">
          ${n}
      </#if>
  </#list>

   <#list ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"] as n>
       <#if n_index != 0>
           ${n}
       </#if>
   </#list>

   <#list ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"] as n>
       <#if (n_index == 1) || (n_index == 3)>
           ${n}
       </#if>
   </#list>

<!-- else -->
   <#list ["星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期天"] as n>
       <#if (n_index == 1) || (n_index == 3)>
           ${n} --红色
       <#else>
           ${n} --绿色
       </#if>
   </#list>
<!--时间格式  -->
1:${cur_time?date}
2:${cur_time?datetime}
3:${cur_time?time}
<!-- null -->
${val!}

<!-- 宏定义 -->
1:
    <#macro table u>
        ${u}
    </#macro>
    <@table u=8 />
2:
    <#macro table u>
        ${u}
        <#nested/>
    </#macro>
    <@table u=8 >这是8</@table>

</body>
</html>