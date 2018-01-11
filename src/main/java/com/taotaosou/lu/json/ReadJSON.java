package com.taotaosou.lu.json;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

/**
 * json测试用例
 */
public class ReadJSON {
    public static void main(String args[]) throws IOException{
        try {
            JsonParser parser=new JsonParser();  //创建JSON解析器
            JsonObject object=(JsonObject) parser.parse(new FileReader("C:\\mysoft\\eclipse\\eclipse-workspace\\lu\\src\\main\\java\\com\\taotaosou\\lu\\json\\test.json"));  //创建JsonObject对象
//            System.out.println("cat="+object.get("cat").getAsString()); //将json数据转为为String型的数据
//            System.out.println("pop="+object.get("pop").getAsBoolean()); //将json数据转为为boolean型的数据
            JsonObject response=object.getAsJsonObject("itemprops_get_response");
            JsonObject item_props=response.getAsJsonObject("item_props");
            JsonArray array=item_props.get("item_prop").getAsJsonArray();    //得到为json的数组
            File wrietFile=new File("C:\\mysoft\\eclipse\\eclipse-workspace\\lu\\src\\main\\java\\com\\taotaosou\\lu\\json\\write.json");
            for(int i=0;i<array.size();i++){
                //System.out.println("---------------");
                JsonObject subObject=array.get(i).getAsJsonObject();
               // System.out.println(subObject.get("pid")+":"+subObject.get("name"));
                String line="insert ignore into taobao_property(pid,name) values("+subObject.get("pid")+","+subObject.get("name")+");\n";
                System.out.println(line);
                FileUtils.writeStringToFile(wrietFile, line, true);
                // if(!"货号".equals(subObject.get("name").getAsString()))continue;
            	  JsonObject prop_values= subObject.getAsJsonObject("prop_values");
            	  if(prop_values==null)continue;
            			 
                JsonArray propValueArray=prop_values.get("prop_value").getAsJsonArray();    //得到为json的数组
                for (JsonElement jsonElement : propValueArray) {
                	 JsonObject propValue=jsonElement.getAsJsonObject();
					//System.out.println("\t"+propValue.get("vid")+":"+propValue.get("name"));
                	  line="\t"+"insert ignore into taobao_property_value(vid,name,pid) values("+propValue.get("vid")+","+propValue.get("name")+","+subObject.get("pid")+");\n";
					System.out.println(line);
				FileUtils.writeStringToFile(wrietFile, line, true);
                }
//                System.out.println("name="+subObject.get("name").getAsString());
//                System.out.println("ide="+subObject.get("ide").getAsString());
            }
             
        } catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}