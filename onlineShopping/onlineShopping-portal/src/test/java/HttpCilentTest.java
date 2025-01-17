/*
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class HttpCilentTest {
    @Test
    public void httpGetTest() throws Exception{
//        第一步：把HttpClient使用的jar包添加到工程中。
//        第二步：创建一个HttpClient的测试类
//        第三步：创建测试方法。
//        第四步：创建一个HttpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        第五步：创建一个HttpGet对象，需要制定一个请求的url
        HttpGet httpGet = new HttpGet("http://www.baidu.com");
//        第六步：执行请求。
        CloseableHttpResponse response = httpClient.execute(httpGet);
//        第七步：接收返回结果。HttpEntity对象。
        HttpEntity entity = response.getEntity();
//        第八步：取响应的内容。
        String s = EntityUtils.toString(entity);
        System.out.println(s);
//        第九步：关闭HttpGet、HttpClient。
        response.close();
        httpClient.close();
    }

    @Test
    public void httpPostTest() throws Exception{
//        第一步：创建一个httpClient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
//        第二步：创建一个HttpPost对象。需要指定一个url
        HttpPost httpPost = new HttpPost("http://localhost:8082/posttest.html");
//        第三步：创建一个list模拟表单，list中每个元素是一个NameValuePair对象
        List<NameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("name","周方鹏"));
        list.add(new BasicNameValuePair("pass","123"));
//        第四步：需要把表单包装到Entity对象中。StringEntity
        StringEntity entity = new UrlEncodedFormEntity(list,"utf-8");
        httpPost.setEntity(entity);
//        第五步：执行请求。
        CloseableHttpResponse response = httpClient.execute(httpPost);
//        第六步：接收返回结果
        HttpEntity httpEntity = response.getEntity();
        String result = EntityUtils.toString(httpEntity);
        System.out.println(result);
//        第七步：关闭流。
        httpClient.close();
        response.close();

    }
}
*/
