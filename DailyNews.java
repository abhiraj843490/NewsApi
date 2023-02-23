import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;

public class DailyNews {
	public static void main(String[]args) throws IOException, InterruptedException, ParseException {

		Scanner sc = new Scanner(System.in);
		System.out.println("ENTER CATOGERY: ");
		String s=sc.next();
		System.out.println("ENTER NUMBER OF HEADLINES: ");
		String n=sc.next();
		
		URL url1 = new URL("https://newsapi.org/v2/top-headlines?country=in&apiKey=a967859cafb74d35ac830595d49ba8f0");
		URL url = new URL(url1.getProtocol(), url1.getHost(), url1.getPort(), url1.getPath() + "?"+"category="+s +"&"+"pageSize="+ n +"&"+url1.getQuery(), null);
		//URL url = new URL("https://newsapi.org/v2/top-headlines?category=entertainment&pageSize=10&country=in&apiKey=a967859cafb74d35ac830595d49ba8f0");
		HttpURLConnection con = (HttpURLConnection)url.openConnection();
		//System.out.println(con.getResponseCode());
		File file = new File("F:\\news.json");
		
		BufferedWriter b = new BufferedWriter(new FileWriter(file));
		
		if(con.getResponseCode()==200) {
			InputStream in = con.getInputStream();
			StringBuilder sb = new StringBuilder();
			
			// Reading 
			BufferedReader bf = new BufferedReader(new InputStreamReader(in));
			String line = bf.readLine();
			while(line!=null) {
				b.write(line);    // write on BufferWriter
				sb.append(line);
				//System.out.println(line);
				line = bf.readLine();
				
			}
			//System.out.println(sb.toString());
			JSONParser jsonparser = new JSONParser(sb.toString()); 
			LinkedHashMap<String, Object> map = jsonparser.parseObject();

//			map.forEach((k,v) -> System.out.println(k + " " + v));
//			System.out.println(map.get("articles"));
			List articles = (List) map.get("articles");
			
			JSONParser jsonparser1 = new JSONParser(articles.toString());
			
			//int n = sc.nextInt();
			
			for(int i=0;i<articles.size();i++) {
//				System.out.print(articles.get(i));
					LinkedHashMap<String, Object> temp = (LinkedHashMap<String, Object>) articles.get(i);
					System.out.println("                      --Title --                        ");
					System.out.println(temp.get("title"));
					System.out.println();
					System.out.println("                      --Description --                  ");
					System.out.println(temp.get("description"));
					System.out.println();
					System.out.println("                      --Content --                      ");
					System.out.println(temp.get("content"));
				//LinkedHashMap<String, Object> map1 = jsonparser1.parseObject();
				//map1.forEach((k,v)->System.out.println(k+" "+v));
				}
			

		}
		b.close();
		
		}

}
