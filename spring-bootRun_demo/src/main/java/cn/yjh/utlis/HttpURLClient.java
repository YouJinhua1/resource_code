package cn.yjh.utlis;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * @description:
 * @author: You Jinhua
 * @create: 2021-02-25 20:07
 */
public class HttpURLClient {

    public static String HttpURLConnectionPost(String strURL, Map<String,String> params) throws Exception {
        String body = "";
        String concat = "&";
        int i = 0;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if(i != 0) {
                body += concat + entry.getKey() + "=" + entry.getValue();
            }else{
                body += entry.getKey() + "=" + entry.getValue();
                i++;
            }
        }
        URL url=new URL(strURL);
        HttpURLConnection httpConn=(HttpURLConnection)url.openConnection();

        //设置参数
        httpConn.setDoOutput(true);		//需要输出
        httpConn.setDoInput(true);		//需要输入
        httpConn.setUseCaches(false); 	//不允许缓存
        httpConn.setRequestMethod("POST"); 		//设置POST方式连接
        httpConn.setRequestProperty("Charset", "UTF-8");
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(httpConn.getOutputStream(), "UTF-8"));
        writer.write(body);
        writer.flush();
        writer.close();
        StringBuffer sb=new StringBuffer();
        //获得响应状态
        int resultCode=httpConn.getResponseCode();
        if(HttpURLConnection.HTTP_OK==resultCode){
            String readLine=new String();
            BufferedReader responseReader=new BufferedReader(new InputStreamReader(httpConn.getInputStream(),"UTF-8"));
            while((readLine=responseReader.readLine())!=null){
                sb.append(readLine).append("\n");
            }
            responseReader.close();
        }
        return sb.toString();
    }

}
