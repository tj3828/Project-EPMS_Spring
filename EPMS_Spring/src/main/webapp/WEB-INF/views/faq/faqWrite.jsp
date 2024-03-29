<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>FAQ - 글쓰기</title>
	<link rel="stylesheet" href="../resources/css/freeboard.css">
</head>

<body>
	<div class="wrap">
		<jsp:include page="../common/top.jsp"></jsp:include>
		<div class="container">
			<div class="title_label_div">
				<span class="fas fa-edit"></span>
				<font class="title_label" >FAQ - 글쓰기</font>
			</div>
			<div class="panel panel-default"> 
				<div class="panel-body">
					<div class="container container-out"> 
						<form id="writeForm" >  
							<div class="row"> 
								<div class="col-md-12">
									<div class="form-group"> 
										<label for="name">NAME</label> 
										<input type="text" class="form-control" name="nickname" id="nickname" value="${sessionScope.dto.nickname}" readonly="readonly" placeholder="Enter name"> 
										<input type="hidden" name="profile_img" id="profile_img" value="${sessionScope.dto.profile_img}">
									</div> 
								</div>
							</div>
							<div class="form-group content-div"> 
								<label for="subject">Title</label> 
								<input type="text" class="form-control" name ="title" id="title" placeholder="30글자까지 입력가능합니다." maxlength="30"> 
								<span class="counter" id="counter1">###</span>
							</div> 
							<div class="form-group content-div"> 
								<label for="content">Comment:</label> 
								<textarea class="form-control" rows="10" name="content" id="content" style="width:auto; !important;"></textarea> 
								<span class="counter" id="counter" style="bottom: 30px; right:25px;">###</span>
							</div>
							<br>
							<div class="form-group"> 
								<label for="File">File input : &nbsp;</label> 
								<input type="file" name="multipartFile" id="multipartFile"> 
							</div>
							 <br>
							<hr>
							<br>
							<div align="center"> 
								<input type="button" class="btn btn-outline-success" id="save" value="저장하기">&nbsp;&nbsp;<input type="reset" class="btn btn-outline-danger" value="다시쓰기">&nbsp;&nbsp;<input type="button" class="btn btn-outline-secondary" value="뒤로가기" onclick="history.back(1)">
							</div> 
						</form>
					</div> 
				</div> 
			</div>
		</div>
	</div>
	<jsp:include page="../common/chatBt.jsp"></jsp:include>
	<script type="text/javascript">
	    //전역변수
	    var obj = [];              
	    //스마트에디터 프레임생성
	    nhn.husky.EZCreator.createInIFrame({
	        oAppRef: obj,
	        elPlaceHolder: "content",
	        sSkinURI: "../resources/editor/SmartEditor2Skin.html",
	        htParams : {
	            // 툴바 사용 여부
	            bUseToolbar : true,            
	            // 입력창 크기 조절바 사용 여부
	            bUseVerticalResizer : true,    
	            // 모드 탭(Editor | HTML | TEXT) 사용 여부
	            bUseModeChanger : false,
	        }
	    });
	
	
		$('#save').on('click',function(){
			
			obj.getById["content"].exec("UPDATE_CONTENTS_FIELD", []);
			$('#content').val($('#content').val().replace(/[\u200B-\u200D\uFEFF]/g, '')); 
			var title =  $('#title').val();
			var content = $('#content').val();
			
			if( title == null || title.trim() == "" ) {
				swal('글쓰기 오류','제목을 입력해주세요.','error');
				return false;
			}
			
			if( content == null || content.trim() == "") {
				swal('글쓰기 오류','내용을 입력해주세요.','error');
				return false;
			}
			
			var formData = new FormData();
			var json = $('form').serializeObject();
			var file = $('#multipartFile').val();
			formData.append("serialData",new Blob([JSON.stringify(json)], {
			    type: "application/json"
			})); 
			
			formData.append("file",$("#multipartFile")[0].files[0]);
			
	        $.ajax({
				type:"POST",
				url:"../faq/faqWrite.do",
				processData: false,
				contentType: false,
				data: formData,
				success: function(data) {
					location.href="../faq/faq.do";
				},
				error: function (request) {
					swal('글쓰기 오류',request.responseText+"\n관리자에게 문의하세요.",'error');
				}
			});
			
		});
		
		$('#title').keyup(function (e){
		    var title = $(this).val();
		    $('#counter1').html(title.length + '/30');
		});
		$('#title').keyup();
		
		function setCounter(a) {
			 $('#counter').html(a + '/300');
		}
	</script>
</body>
</html>
<jsp:include page="../common/footer.jsp"></jsp:include>