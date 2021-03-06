package com.hb.freeboard.detail;

import java.io.File;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hb.freeboard.FreeBoardVO;

@Service
@Transactional
public class FreeBoardDetailServiceImpl implements IFreeBoardDetailService {
	@Inject
	IBoardDetailDAOMapper iBoardDetailDAOMapper;
	@Inject
	private ServletContext  application;
	
	@Override
	public FreeBoardVO board_detail(FreeBoardVO dto) {
		dto = iBoardDetailDAOMapper.dbDetail(dto);
		return dto;
	}

	@Override
	public void board_DetailCount(FreeBoardVO dto) {
		iBoardDetailDAOMapper.dbDetailCount(dto);
	}

	@Override
	public void board_DetailEdit(FreeBoardVO dto) {
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
				System.out.println("FreeBoardDetailController write()");
			}
			dto.setUpload_file(img);
			dto.setStore_upload_file(filename);
			dto.setUpload_file_size(dto.getMultipartFile().getSize());
		} else {
			dto.setUpload_file("");
			dto.setStore_upload_file("");
			dto.setUpload_file_size((long)0.0);
		}
		iBoardDetailDAOMapper.dbDetailEdit(dto);
	}
	
	@Override
	public void board_DetailDelete(FreeBoardVO dto) {
		iBoardDetailDAOMapper.dbDetailDelete(dto);
	}
}
