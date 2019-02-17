<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="../resources/chat/css/styles.css">
  <title>Chats</title>
</head>
<body style="padding-top: 50px;">
  <header class="top-header">
  	
    <div class="header__bottom">
      <div class="header__column">
      </div>
      <div class="header__column">
        <span class="header__text">Friends</span>
        <span class="header__number">1</span>
      </div>
      <div class="header__column">
      </div>
    </div>
  </header>
  <main class="chats">
    <div class="search-bar">
      <i class="fa fa-search"></i>
      <input type="text" placeholder="Find friends, chats, Plus Friends">
    </div>
    <ul class="chats__list">
      <li class="chats__chat">
        <a href="../chat/chat.do">
          <div class="chat__content">
            <img src="../resources/chat/images/avatar.png">
            <div class="chat__preview">
                <h3 class="chat__user">LYNN</h3>
                <span class="chat__last-message">Hello! This is a test message.</span>
            </div>
          </div>
          <span class="chat__date-time">
            15:55
          </span>
        </a>
      </li>
      <li class="chats__chat">
        <a href="../chat/chat.do">
          <div class="chat__content">
            <img src="../resources/chat/images/avatar.png">
            <div class="chat__preview">
                <h3 class="chat__user">KakaoTalk</h3>
                <span class="chat__last-message">You logged in...</span>
            </div>
          </div>
          <span class="chat__date-time">
            Jul 29
          </span>
        </a>
      </li>
    </ul>
      <div class="chat-btn">
        <i class="fa fa-comment"></i>
      </div>

  </main>
  <nav class="tab-bar">
    <a href="../chat/friend.do" class="tab-bar__tab">
      <i class="fa fa-user"></i>
      <span class="tab-bar__title">Friends</span>
    </a>
    <a href="../chat/chats.do" class="tab-bar__tab tab-bar__tab--selected">
      <i class="fa fa-comment"></i>
      <span class="tab-bar__title">Chats</span>
    </a>
    <a href="../chat/find.do" class="tab-bar__tab">
      <i class="fa fa-search"></i>
      <span class="tab-bar__title">Find</span>
    </a>
    <a href="more.html" class="tab-bar__tab">
      <i class="fa fa-ellipsis-h"></i>
      <span class="tab-bar__title">More</span>
    </a>
  </nav>
  <div class="bigScreenText">
    Please make your screen smaller
  </div>
</body>
</html>