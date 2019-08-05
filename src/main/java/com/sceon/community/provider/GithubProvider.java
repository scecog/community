package com.sceon.community.provider;

import com.alibaba.fastjson.JSON;
import com.sceon.community.pojo.AccessTokenPojo;
import com.sceon.community.pojo.GithubUserPojo;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author shichenchong@inspur.com
 * data   2019/8/5 22:03
 */
@Component
public class GithubProvider {
    public String getAccessToken(AccessTokenPojo accessTokenPojo){
        MediaType mediaType = MediaType.get("application/json; charset=utf-8");
        OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(mediaType, JSON.toJSONString(accessTokenPojo));
            Request request = new Request.Builder()
                    .url("https://github.com/login/oauth/access_token")
                    .post(body)
                    .build();
            try (Response response = client.newCall(request).execute()) {
                String string = response.body().string();
                String token = string.split("&")[0].split("=")[1];
                System.out.println(token);
                return token;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
    }
    public GithubUserPojo getUserInfo(String accessToken){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://api.github.com/user?access_token=" + accessToken)
                .build();
        try {
            Response response = client.newCall(request).execute();
            String string = response.body().string();
            GithubUserPojo githubUserPojo = JSON.parseObject(string, GithubUserPojo.class);
            return githubUserPojo;
        }catch (Exception e){
        }
        return null;
    }
}
