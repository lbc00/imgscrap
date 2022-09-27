package imgscrap.controller;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
import imgscrap.vo.ImgScrapInfoDetailVo;
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
	
	@DisplayName("이미지 스크랩 목록조회")
	@Test
	public void list() throws Exception{
		ImgScrapListRequestVo request = new ImgScrapListRequestVo();
		request.setPage(1);
		request.setPage_size(3);
		
		//ObjectMapper mapper = new ObjectMapper();
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(IMGSCRAP_URL)
				.content(mapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
				.characterEncoding(StandardCharsets.UTF_8.displayName());
		
		MockHttpServletResponse response = mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
	}
	
	@DisplayName("이미지 스크랩 등록")
	@Test
	public void create() throws Exception{
		ImgScrapCreateRequestVo request = new ImgScrapCreateRequestVo();
		request.setTitle("제목");
		request.setCont("내용");
		request.setImg_url("https://ssl.pstatic.net/melona/libs/1413/1413020/a9bb1a410eb239f5aca1_20220922154456392.jpg");
		request.setUser_no("A001");
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(IMGSCRAP_URL)
				.content(mapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
				.characterEncoding(StandardCharsets.UTF_8.displayName());
		
		MockHttpServletResponse response = mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
	}
	
	@DisplayName("이미지 스크랩 수정")
	@Test
	public void modify() throws Exception{
		ImgScrapModifyRequestVo request = new ImgScrapModifyRequestVo();
		request.setTitle("제목");
		request.setCont("내용");
		request.setUser_no("A001");
		long scrapNo = 1;
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.patch(IMGSCRAP_URL+"/"+scrapNo)
				.content(mapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
				.characterEncoding(StandardCharsets.UTF_8.displayName());
		
		MockHttpServletResponse response = mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
	}
	
	@DisplayName("이미지 스크랩 수정 사용자 권한 조회")
	@Test
	public void modify_user_no_auth() throws Exception{
		ImgScrapModifyRequestVo request = new ImgScrapModifyRequestVo();
		request.setTitle("제목_수정");
		request.setCont("내용_수정");
		request.setUser_no("A002");	// 오류 수정
		long scrapNo = 2;
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.patch(IMGSCRAP_URL+"/"+scrapNo)
				.content(mapper.writeValueAsString(request))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN)
				.characterEncoding(StandardCharsets.UTF_8.displayName());
		
		MockHttpServletResponse response = mockMvc.perform(requestBuilder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
				.andReturn()
				.getResponse();
	}
}
