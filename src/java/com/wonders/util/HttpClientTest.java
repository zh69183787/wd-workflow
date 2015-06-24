package com.wonders.util;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhoushun
 * Date: 2015/1/21
 * Time: 11:23
 * To change this template use File | Settings | File Templates.
 */
public class HttpClientTest {
    public static void main(String[] args) throws Exception {
        HttpClient client = new HttpClient();
        PostMethod method = new PostMethod("https://upload.api.weibo.com/2/statuses/upload.json");

        List<Header> headers = new ArrayList<Header>();
        headers.add(new Header("Authorization", "OAuth2 " + "2.00Tw_PoBdBYEND600e0aa317Sm_qoC"));
        headers.add(new Header("API-RemoteIP", "http://10.1.41.252"));
        client.getHostConfiguration().getParams()
                .setParameter("http.default-headers", headers);
        String s = "弗格森迭戈迭戈丰东股份的";
        File f = new File("E:\\Users\\zhoushun\\Downloads\\ce187bf40ad162d9f98819ea10dfa9ec8b13cdf6.jpg");
        byte b[] = new byte[(int) f.length()];
        FileInputStream fs = new FileInputStream(f);
        fs.read(b);
        fs.close();
        FileOutputStream fo = new FileOutputStream(new File("D://zs.jpg"));
        fo.write(b);
        fo.close();
         ((PostMethod) method).addParameter("status", s);

        System.out.println(new MimetypesFileTypeMap().getContentType(f));
        HttpMethodParams param = method.getParams();
        GetMethod get = new GetMethod("https://www.verisign.com/products/index.html");
        HttpGet httpGet = new HttpGet("");
        get.setRequestHeader(null);


        HttpGet aa = new HttpGet("http://www.cnblogs.com/loveyakamoz/archive/2011/07/21/2113252.html");

        HttpRequestBase httpget = new HttpRequestBase() {
            @Override
            public String getMethod() {
                return null;
            }
        };
        HttpGet aaa = new HttpGet("http://www.cnblogs.com/loveyakamoz/archive/2011/07/21/2113252.html");



        //設置httpGet的头部參數信息

        aaa.setHeader("Accept", "Accept text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");



    }
}
