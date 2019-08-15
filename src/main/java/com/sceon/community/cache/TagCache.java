package com.sceon.community.cache;

import com.sceon.community.dto.TagDto;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/14 18:36
 */
public class TagCache {
    public static List<TagDto> get(){
        ArrayList<TagDto> tagDtos = new ArrayList<>();
        TagDto tagDto = new TagDto();
        tagDto.setCategoryName("开发语言");
        tagDto.setTags(Arrays.asList("javascript","php","css","html","html5","java","node.js","python","c++","c","golang","objective-c","typescript","shell","c#","swift","sass","bash",",ruby","less","asp.net","lua","scala","coffeescript","actionscript","rust","erlang","perl"));
        TagDto framework = new TagDto();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("laravel",  "spring",  "express",  "django",  "flask",  "yii",  "ruby-on-rails", "tornado",  "koa  struts"));

        TagDto server = new TagDto();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","apache",",ubuntu","centos","缓存", "tomcat","负载均衡","unix","hadoop","windows-server"));

        TagDto database = new TagDto();
        database.setCategoryName("数据库和缓存");
        database.setTags(Arrays.asList("mysql","redis","mongodb","sql","oracle","nosql","memcached","sqlserver","postgresql","sqlite"));

        TagDto ide = new TagDto();
        ide.setCategoryName("开发工具");
        ide.setTags(Arrays.asList("git","github","visual-studio-code","vim","sublime-text","xcode","intellij-idea","eclipse","maven","ide","svn","visual-studio","atom", "emacs","textmate","hg"));
        tagDtos.add(ide);

        TagDto system = new TagDto();
        system.setCategoryName("系统设备");
        system.setTags(Arrays.asList("android","ios","chrome","windows","iphone","firefox","internet-explorer","safari","ipad","opera","apple-watch"));
        tagDtos.add(system);
        tagDtos.add(database);
        tagDtos.add(server);
        tagDtos.add(framework);
        tagDtos.add(tagDto);
        return tagDtos;
    }
    public static String filterInvalid(String tags){
        String[] tagArray = StringUtils.split(tags, ",");
        List<TagDto> tagDtos = get();
        List<String> tagList = tagDtos.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String inValid = Arrays.stream(tagArray).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));

        return inValid;
    }
}
