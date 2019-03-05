  
  # 문제점
      - <br>과 같은 태그 채팅 입력시 오작동
      - 웹소켓 전송시 @inject null 발생 (스프링 컨텍스트와 별개로 생성되는 문제를 발견하고 컨텍스트를 이어줌)
      - 뒤로가기 버튼 누를 시 자동 새로고침 이슈
      - 채팅방 목록을 뽑아올 때 쿼리문 (채팅방에 대한 인덱스 없이 쿼리문으로 해결)
  
  # 02-14
      - log4j-remix 적용
      - Lombok 적용
      - sessionScope 처리
      - @Valid 처리
      
  # 02-15
      - ajax / Valid / multipart 간 데이터 전송
  
  # 02-16
      - 스마트에디터를 이용한 에디터 설정
  
  # 02-17
      - 채팅 UI 디자인
  
  # 02-18 
      - 채팅방 상태유지(페이지 이동시)
      - 채팅관련 VO 설계
      - 웹소켓 연결
      
  # 02-19
      - 클라이언트와 서버간 json 객체 웹소켓 전송
      - 모든 사람에게 채팅 전송
      - 채팅 VO에 따른 채팅 출력
      - 채팅방 UI 개선
      - 채팅 DB저장 (@inject Null 확인 필요)
      - 자신의 채팅 모두 출력(보낸사람, 받는사람 상관없이 자신이 포함된 모든 정보 출력)
      
  # 02-20
      - 채팅방 목록 출력
      - 간이 채팅방 생성
      - 특정 유저에게만 메시지 전송
      - 브라우저의 뒤로가기 버튼 누를 시 iframe이 이동하는 이슈 해결
      - 채팅목록 창에서 메시지 올시 출력완료
      - 채팅목록을 뽑아내는 쿼리 수정 필요.
      
  # 02-21
      - 채팅목록을 뽑아오는데 인덱스 필드 없이 쿼리문으로 조정
      - 프로필 이미지 채팅창 출력
      - 채팅 읽었는지 실시간 확인 (채팅방 1 출력)
  
  # 02-22
      - 채팅창 목록과 버튼에 읽지 않은 메세지 개수 출력
      - 이벤트에 따라서 실시간 개수 처리
      - 유저와 대화 시도시 존재하는 사용자 인지 확인
      - 방나가기 버튼
      - 프렌즈, 찾기, 모어 버튼에서의 실시간 개수 처리
      - 채팅방 매크로로 보내기 설정
      - 채팅 문서 중간 정리 
      
  # 02-23
      - 문서 정리 
      - 메인화면 디자인
      
  # 02-24
      - 메인화면 디자인 및 설계
      - 반응형 디자인 구현
  
  # 02-25
      - 서울 지도 캡쳐 및 사이즈 조정
  
  # 02-26 
      - 메인 검색 테이블 뷰 설계
      - 메인 주차지역 검색 데이터 출력
      - 네이버 맵 출력
      - 메인화면 애니메이션 
  
  # 02-27
      - 메인화면 재구성 
      - 캔버스 적용(삭제)
      - 메인화면 로고 수정
      - 예약 말풍선 출력
      - 메인화면 완성
      
  # 02-28
      - 예약요청 기능 완료 (예약요청시 알림 미완성)
      
      
  # 03-01 ~ 03-03
      - 예약과정 채팅 결합(디버깅 필요)
  
  # 03-04 
      - 예약과정 채팅 결합(디버깅까지 완료)
      - 페이징 전환시 채팅창 뷰 수정
      - 메인화면 로그인 버튼 활성화
  
  # 03-05
      - FAQ 게시판 설계 
      - 몇가지 스타일 수정
      
      
      
