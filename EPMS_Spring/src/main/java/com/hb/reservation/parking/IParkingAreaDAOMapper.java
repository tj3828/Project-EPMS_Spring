package com.hb.reservation.parking;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.hb.reservation.ReservationVO;

import common.Paging;
import config.Mapper;

@Mapper
public interface IParkingAreaDAOMapper {
	
	@Select("select r.* " + 
			"from ( " + 
			"	select p.*, rownum as rn " + 
			"	from ( " + 
			"		select * " + 
			"		from parking_area "	+ 
			"		order by pa_address asc ) p " + 
			"	where pa_address like '%' || #{keyword} || '%' and pa_address like '%' || #{keyfield} || '%' ) r " + 
			"where rn " + 
			"between #{end} and #{start} " )
	List<ParkingAreaVO> selectSearchList(Paging paging);
	
	@Select("select count(*) from parking_area")
	int selectParkingAreaListCount();
	
	@Select("select count(*) from parking_area where pa_address like '%' || #{keyword} || '%' and pa_address like '%' || #{keyfield} || '%' ")
	int selectSearchParkingAreaListCount(Paging	paging);
	
	@Select("select * " + 
			"from parking_area_personal c "	+ 
			"where c.pap_address =  #{r_address} and c.pap_area " + 
			"not in ( "	+ 
			"	select distinct p.pap_area " + 
			"	from parking_area_personal p " + 
			"	inner join reservation r " + 
			"	on ( " + 
			"			( r.r_status ='예약중' or r.r_status = '예약완료') and "	+ 
			"			  p.pap_area = r.r_area and r.r_address= #{r_address} and " + 
			"				((r.r_from <= To_Date(#{r_from},'yyyy-mm-dd hh24:mi') and " + 
			"					r.r_to > To_Date(#{r_from},'yyyy-mm-dd hh24:mi')) " + 
			"				or (r.r_from < To_Date(#{r_to},'yyyy-mm-dd hh24:mi') and " + 
			"				    r.r_to >= To_Date(#{r_to},'yyyy-mm-dd hh24:mi'))))) "	+ 
			"order by c.pap_area asc")
	List<PersonalParkingAreaVO> selectPersonalAreaList(ReservationVO dto);
}