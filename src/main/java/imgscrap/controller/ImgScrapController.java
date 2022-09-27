package imgscrap.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.Queue;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import imgscrap.exception.BizException;
import imgscrap.exception.CustomExceptionHandler;
import imgscrap.service.ImgScrapService;
import imgscrap.util.MessageUtil;
import imgscrap.util.PageInfo;
import imgscrap.vo.BaseResponseVo;
import imgscrap.vo.ImgScrapCreateRequestVo;
import imgscrap.vo.ImgScrapDeleteRequestVo;
import imgscrap.vo.ImgScrapDetailResponseVo;
import imgscrap.vo.ImgScrapInfoDetailVo;
import imgscrap.vo.ImgScrapInfoListVo;
import imgscrap.vo.ImgScrapListRequestVo;
import imgscrap.vo.ImgScrapListResponseVo;
import imgscrap.vo.ImgScrapModifyRequestVo;
import imgscrap.vo.ImgScrapVo;

@Validated
@RestController
@RequestMapping("/imgscrap")
public class ImgScrapController extends CustomExceptionHandler {
	final static Logger logger = LoggerFactory.getLogger(ImgScrapController.class);
	
	@Autowired
	private ImgScrapService serivce;
	@Autowired
	private MessageUtil msg;
	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private Queue queue_hits;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	/**
	 * 이미지스크랩 저장
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody @Valid ImgScrapCreateRequestVo request, BindingResult result) throws Exception {
		
		BaseResponseVo response = new BaseResponseVo();
		
		try {
			// 이미지 스크랩 서비스 호출
			serivce.create(request);
			response.setMsg(msg.getMessage("MSG001"));
		} catch(BizException be) {
			logger.error(be.getMessage(), be);
			response.setMsg(be.getMessage());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			response.setMsg(msg.getMessage("MSG002", "이미지스크랩"));
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * 이미지스크랩 목록 조회
	 * @param request
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<?> list(@RequestBody @Valid ImgScrapListRequestVo request, BindingResult result) throws Exception {
		
		ImgScrapListResponseVo response = new ImgScrapListResponseVo();
		
		try {
			// 쿼리 조회용 map
			Map<String, Object> paramMap = new HashMap<>();
			
			int totCnt = serivce.selectImgScrapListTotCnt(paramMap);
			
			PageInfo pi = new PageInfo(totCnt, request.getPage(), request.getPage_size());
			paramMap.put("offset", pi.getOffset());
			paramMap.put("limit", pi.getPageSize());
			List<ImgScrapInfoListVo> list = serivce.selectImgScrapList(paramMap);
			
			response.setTot_cnt(totCnt);
			response.setTotal_page(pi.getTotalPageNo());
			response.setData_list(list);
			
			response.setMsg(msg.getMessage("MSG001"));
		} catch(BizException be) {
			logger.error(be.getMessage(), be);
			response.setMsg(be.getMessage());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			response.setMsg(msg.getMessage("MSG002", "이미지스크랩 목록 조회"));
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * 이미지스크랩 상세조회
	 * @param scrapNo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{scrap_no}", method=RequestMethod.GET)
	public ResponseEntity<?> get(@PathVariable(value="scrap_no", required=true) @Positive long scrapNo
			) throws Exception {
		
		ImgScrapDetailResponseVo response = new ImgScrapDetailResponseVo();
		
		try {
			ImgScrapInfoDetailVo detailVo = serivce.selectImgScrapDetail(scrapNo);
			if(detailVo == null) {
				response.setMsg(msg.getMessage("MSG003"));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			}
			
			ImgScrapVo vo = new ImgScrapVo();
			vo.setScrapNo(scrapNo);
			
			ObjectMapper mapper = new ObjectMapper();
			jmsTemplate.convertAndSend(queue_hits, mapper.writeValueAsString(vo));
			
			response.setData(detailVo);
			response.setMsg(msg.getMessage("MSG001"));
		} catch(BizException be) {
			logger.error(be.getMessage(), be);
			response.setMsg(be.getMessage());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			response.setMsg(msg.getMessage("MSG002", "이미지스크랩 상세 조회"));
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * 이미지스크랩 수정
	 * @param scrapNo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{scrap_no}", method=RequestMethod.PATCH)
	public ResponseEntity<?> modify(@PathVariable(value="scrap_no", required=true) @Positive long scrapNo,
			@RequestBody @Valid ImgScrapModifyRequestVo request, BindingResult result) throws Exception {
		
		BaseResponseVo response = new BaseResponseVo();
		
		try {
			
			ImgScrapInfoDetailVo detailVo = serivce.selectImgScrapDetail(scrapNo);
			if(detailVo == null) {
				response.setMsg(msg.getMessage("MSG003"));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else if(detailVo.getInsertUserNo() != null && !detailVo.getInsertUserNo().equals(request.getUser_no())) {	// 스크랩 등록사용자번호와 파라미터 사용자번호가 다를경우 권한 체크
				response.setMsg(msg.getMessage("MSG004", "수정"));
				return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
			} 
			
			ImgScrapVo vo = new ImgScrapVo();
			vo.setScrapNo(scrapNo);
			vo.setTitle(request.getTitle());
			vo.setCont(request.getCont());
			vo.setUserNo(request.getUser_no());
			serivce.modifyImgScrap(vo);
			
			response.setMsg(msg.getMessage("MSG001"));
		} catch(BizException be) {
			logger.error(be.getMessage(), be);
			response.setMsg(be.getMessage());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			response.setMsg(msg.getMessage("MSG002", "이미지스크랩 수정"));
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	/**
	 * 이미지스크랩 삭제
	 * 이미지 물리적 삭제는 하지않는다.
	 * @param scrapNo
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="{scrap_no}", method=RequestMethod.DELETE)
	public ResponseEntity<?> remove(@PathVariable(value="scrap_no", required=true) @Positive long scrapNo,
			@RequestBody @Valid ImgScrapDeleteRequestVo request, BindingResult result) throws Exception {
		
		BaseResponseVo response = new BaseResponseVo();
		
		try {
			
			ImgScrapInfoDetailVo detailVo = serivce.selectImgScrapDetail(scrapNo);
			if(detailVo == null) {
				response.setMsg(msg.getMessage("MSG003"));
				return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
			} else if(detailVo.getInsertUserNo() != null && !detailVo.getInsertUserNo().equals(request.getUser_no())) {	// 스크랩 등록사용자번호와 파라미터 사용자번호가 다를경우 권한 체크
				response.setMsg(msg.getMessage("MSG004", "삭제"));
				return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
			} 
			
			ImgScrapVo vo = new ImgScrapVo();
			vo.setScrapNo(scrapNo);
			vo.setUserNo(request.getUser_no());
			vo.setDelYn("Y");
			serivce.removeImgScrap(vo);
			
			response.setMsg(msg.getMessage("MSG001"));
		} catch(BizException be) {
			logger.error(be.getMessage(), be);
			response.setMsg(be.getMessage());
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
			response.setMsg(msg.getMessage("MSG002", "이미지스크랩 삭제"));
			return new ResponseEntity<>(response, HttpStatus.CONFLICT);
		}
		
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
