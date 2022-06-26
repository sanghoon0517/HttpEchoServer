package kr.co.jsh.test.echo.http.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

@WebServlet("/main")
public class HttpEchoServer extends HttpServlet{
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//클라이언트로부터 메시지 전송받기
		InputStream is = request.getInputStream();
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] bytes = new byte[1024];
		int cnt;
		while((cnt = is.read(bytes)) != -1) {
			baos.write(bytes);
		}
		byte[] byteData = baos.toByteArray();
		String data = new String(byteData);
		System.out.println("클라이언트로 부터 받은 데이터 : "+data);
		
		//응답 데이터 조작
		JSONObject json = new JSONObject();
		json.put("author", "server");
		json.put("data", "RESPONSE DATA");
		
		String responseData = json.toString();
		System.out.println("클라이언트에게 응답할 JSON 데이터 : "+responseData);
		byte[] byteResponseData = responseData.getBytes();
		
		// 받은 데이터를 클라이언트에 재전송할 객체
		OutputStream out = response.getOutputStream();
		
		//전송
		out.write(byteResponseData);
		out.flush();
		
		is.close();
		out.close();
		
	}
}
