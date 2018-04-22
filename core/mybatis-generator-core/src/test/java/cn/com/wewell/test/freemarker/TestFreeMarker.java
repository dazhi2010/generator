package cn.com.wewell.test.freemarker; /**
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
import freemarker.template.TemplateException;
import org.junit.Test;
import org.mybatis.generator.util.FreeMarkerUtil;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.*;

public class TestFreeMarker {
    @Test
    public void testFreeMarker() throws IOException, TemplateException {
        String dir = URLDecoder.decode(TestFreeMarker.class.getResource("/").getPath(),"utf-8")+"\\templates\\ftl";
        //定义数据
        Map<String,Object> root = new HashMap<String,Object>();
        Person p = new Person();
        p.setId("111");
        p.setName("哈哈哈");
        root.put("person", p);
        root.put("world", "世界你好");
        //遍历List
        List<String> persons = new ArrayList<String>();
        persons.add("阿灵罗");
        persons.add("罗零");
        persons.add("灵罗");
        root.put("persons",persons);
        root.put("cur_time", new Date());
        //遍历Map
        Map<String,String> mx = new HashMap<String,String>();
        mx.put("alingluo", "阿灵罗");
        mx.put("lling", "罗零");
        root.put("mx", mx);
        //遍历List<Map>
        List<Map<String,String>> maps = new ArrayList<Map<String,String>>();
        Map<String,String> pms1 = new HashMap<String,String>();
        pms1.put("id1", "张三");
        pms1.put("id2", "李四");
        Map<String,String> pms2 = new HashMap<String,String>();
        pms2.put("id1", "张三");
        pms2.put("id2", "李四");
        maps.add(pms1);
        maps.add(pms2);
        root.put("maps", maps);
        //定义数据
        root.put("val", null);
        System.out.println(FreeMarkerUtil.processText(dir,"freemarker_demo.ftl",root));
    }
    @Test
    public void testVo() throws IOException, TemplateException {
        String dir = URLDecoder.decode(TestFreeMarker.class.getResource("/").getPath(),"utf-8")+"\\templates\\ftl";
        //定义数据
        Map<String,Object> root = new HashMap<String,Object>();
        List<String> imports = new ArrayList<>();
        imports.add("a.b.util");
        imports.add("java.lang");
        imports.add("java.util.Date");
        root.put("packageFullPath","a.b.vo");
        root.put("imports",imports);
        root.put("classVoName","testVo");
        root.put("className","test");
        System.out.println(FreeMarkerUtil.processText(dir,"java_vo.ftl",root));
    }
}