package imgscrap.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;

import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;

import imgscrap.service.ImgScrapService;
import imgscrap.vo.ImgScrapCreateRequestVo;
import imgscrap.vo.ImgScrapDeleteRequestVo;
import imgscrap.vo.ImgScrapListRequestVo;
import imgscrap.vo.ImgScrapModifyRequestVo;
import lombok.RequiredArgsConstructor;

@AutoConfigureMockMvc
@SpringBootTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@RequiredArgsConstructor
public class ImgscrapControllerTest {
	private final MockMvc mockMvc;
	private final ObjectMapper mapper;
	
	@Autowired
	ImgScrapService service;
	
	private final static String IMGSCRAP_URL = "/imgscrap";
	
	@DisplayName("이미지 스크랩 등록")
	@Test
	public void create() throws Exception{
		ImgScrapCreateRequestVo request = new ImgScrapCreateRequestVo();
		request.setTitle("제목");
		request.setCont("내용");
		request.setImg_url("https://ssl.pstatic.net/melona/libs/1413/1413020/a9bb1a410eb239f5aca1_20220922154456392.jpg");
		request.setUser_no("A001");
		
		MockHttpServletResponse response = getResponse(IMGSCRAP_URL, request, HttpMethod.POST);
		
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
	}
	
	
	@DisplayName("이미지 스크랩 목록조회")
	@Test
	public void list() throws Exception{
		ImgScrapListRequestVo request = new ImgScrapListRequestVo();
		request.setPage(1);
		request.setPage_size(3);
		
		MockHttpServletResponse response = getResponse(IMGSCRAP_URL, request, HttpMethod.GET);
		
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
	}
	
	
	@DisplayName("이미지 스크랩 수정")
	@Test
	public void modify() throws Exception{
		ImgScrapModifyRequestVo request = new ImgScrapModifyRequestVo();
		request.setTitle("제목");
		request.setCont("내용");
		request.setUser_no("A001");
		long scrapNo = 1;
		
		MockHttpServletResponse response = getResponse(IMGSCRAP_URL+"/"+scrapNo, request, HttpMethod.PATCH);
		
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
	}
	
	@DisplayName("이미지 스크랩 수정 사용자 권한 체크(권한 없는사용자가 수정을 요청할 경우 status 403 발생)")
	@Test
	public void modify_user_no_auth() throws Exception{
		ImgScrapModifyRequestVo request = new ImgScrapModifyRequestVo();
		request.setTitle("제목_수정");
		request.setCont("내용_수정");
		request.setUser_no("A002");	// 사용자 번호 오류
		long scrapNo = 1;
		
		MockHttpServletResponse response = getResponse(IMGSCRAP_URL+"/"+scrapNo, request, HttpMethod.PATCH);
		
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_FORBIDDEN);
		
	}
	
	@DisplayName("이미지 스크랩 수정 스크랩번호 체크(스크랩번호가 DB에 없는 번호이거나 삭제된 번호일경우  status 400 발생)")
	@Test
	public void modify_scrap_no_check() throws Exception{
		ImgScrapModifyRequestVo request = new ImgScrapModifyRequestVo();
		request.setTitle("제목_수정");
		request.setCont("내용_수정");
		request.setUser_no("A001");
		long scrapNo = 2;		// 스크랩번호 오류
		
		MockHttpServletResponse response = getResponse(IMGSCRAP_URL+"/"+scrapNo, request, HttpMethod.PATCH);
		
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
		
	}
	
	@DisplayName("이미지 스크랩 삭제 사용자 권한 체크(권한 없는사용자가 수정을 요청할 경우 status 403 발생)")
	@Test
	public void remove_user_no_auth() throws Exception{
		ImgScrapDeleteRequestVo request = new ImgScrapDeleteRequestVo();
		request.setUser_no("A002");	// 사용자 번호 오류
		long scrapNo = 1;
		
		MockHttpServletResponse response = getResponse(IMGSCRAP_URL+"/"+scrapNo, request, HttpMethod.DELETE);
		
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_FORBIDDEN);
		
	}
	
	@DisplayName("이미지 스크랩 삭제 스크랩번호 체크(스크랩번호가 DB에 없는 번호이거나 삭제된 번호일경우  status 400 발생)")
	@Test
	public void remove_scrap_no_check() throws Exception{
		ImgScrapDeleteRequestVo request = new ImgScrapDeleteRequestVo();
		request.setUser_no("A001");
		long scrapNo = 2;		// 스크랩번호 오류
		
		MockHttpServletResponse response = getResponse(IMGSCRAP_URL+"/"+scrapNo, request, HttpMethod.DELETE);
		
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
		
	}
	
	@DisplayName("이미지 스크랩 삭제")
	@Test
	public void remove() throws Exception{
		ImgScrapDeleteRequestVo request = new ImgScrapDeleteRequestVo();
		request.setUser_no("A002");	// 사용자 번호 오류
		long scrapNo = 1;
		
		MockHttpServletResponse response = getResponse(IMGSCRAP_URL+"/"+scrapNo, request, HttpMethod.DELETE);
		
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_FORBIDDEN);
	}
	
	@DisplayName("이미지 스크랩 상세조회")
	@Test
	public void get() throws Exception{
		long scrapNo = 1;
		
		MockHttpServletResponse response = getResponse(IMGSCRAP_URL+"/"+scrapNo, null, HttpMethod.GET);
		
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_OK);
	}
	
	@DisplayName("이미지 스크랩 상세조회 스크랩번호 체크(스크랩번호가 DB에 없는 번호이거나 삭제된 번호일경우  status 400 발생)")
	@Test
	public void get_scrap_no_check() throws Exception{
		long scrapNo = 2;
		
		MockHttpServletResponse response = getResponse(IMGSCRAP_URL+"/"+scrapNo, null, HttpMethod.GET);
		
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_BAD_REQUEST);
	}
	/*
	@DisplayName("이미지 스크랩 상세조회 activemq 체크(activemq가 실행 안됐을 경우  status 409 발생)")
	@Test
	public void get_activemq_check() throws Exception{
		long scrapNo = 1;
		
		MockHttpServletResponse response = getResponse(IMGSCRAP_URL+"/"+scrapNo, null, HttpMethod.GET);
		
		assertThat(response.getStatus()).isEqualTo(HttpServletResponse.SC_CONFLICT);
	}
	*/
	
	public MockHttpServletResponse getResponse(String requestUrl, Object requestBody, HttpMethod method) throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.request(method, requestUrl)
				.content(mapper.writeValueAsString(requestBody))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
				.characterEncoding(StandardCharsets.UTF_8.displayName());
		
		MockHttpServletResponse response = mockMvc.perform(requestBuilder)
				//.andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
		
		return response;
		
	}
	
}
