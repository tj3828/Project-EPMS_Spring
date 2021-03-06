package com.hb.freeboard;

import java.io.File;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.freeboard.reply.IBoardReplyDAOMapper;

import common.Paging;

@Service
@Transactional
public class FreeBoardServiceImpl implements IFreeBoardService {

	@Inject
	IBoardDAOMapper iBoardDAOMapper;
	@Inject 
	IBoardReplyDAOMapper iBoardReplyDAOMapper;
	@Inject
	private ServletContext  application;
	
	@Override
	public void board_write(FreeBoardVO dto) {
		if(dto.getMultipartFile() != null && !dto.getMultipartFile().isEmpty()) {
			String path=application.getRealPath("/resources/upload");
			String img=dto.getMultipartFile().getOriginalFilename();
			
			String uuid = UUID.randomUUID().toString().replaceAll("-", "");
			String extension = img.substring(img.lastIndexOf("."));
			
			String filename = uuid + extension;
			File temp = new File(path);
			if(!temp.exists()) {
				temp.mkdirs();
			}
			File file = new File(path, filename);
			System.out.println(file + " / " + img);
			try{
				dto.getMultipartFile().transferTo(file);
			}catch(Exception ex){
				System.out.println("FreeBoardController write()");
			}
			dto.setUpload_file(img);
			dto.setStore_upload_file(filename);
			dto.setUpload_file_size(dto.getMultipartFile().getSize());
		} else {
			dto.setUpload_file("");
			dto.setStore_upload_file("");
			dto.setUpload_file_size((long)0.0);
		}
		iBoardDAOMapper.dbInsert(dto);
		System.out.println(dto.getContent());
	}

	@Override
	public List<FreeBoardVO> board_list(Paging paging) {
		
		 int totalCount = iBoardDAOMapper.dbListAccount(); 
		 int searchCount =  iBoardDAOMapper.dbSearchAccount(paging); 
			  paging.setSearchCount(searchCount);
			  paging.setColPageNum(10); 
			  paging.setRowPageNum(3);
			  paging.setTotalCount(totalCount); 
			  paging.calulate();
		
		 return iBoardDAOMapper.dbSelect(paging);
	}
	
	// Reply ==============================================================================
	



	
	
	
}
