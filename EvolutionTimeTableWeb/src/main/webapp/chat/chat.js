const popup = document.querySelector('.chat-popup');
const chatBtn = document.querySelector('.chat-btn');
const submitBtn = document.querySelector('.submit');
const chatArea = document.querySelector('.chat-area');
const inputElm = document.querySelector('#chatInput');
const chatServletUrl = "/EvolutionTimeTableWeb/ChatServlet";
const ChatRefreshRate = 200;
var failedToGetChatRecordsCounter = 0;
var chatInterval;



$(function () {
  chatArea.messages = 0;
  chatInterval = setInterval(getAndPopulateChat, ChatRefreshRate);
})

//   chat button toggler 

chatBtn.addEventListener('click', () => {
  popup.classList.toggle('show');
})

inputElm.addEventListener("change", function () {
  submitBtn.click();
});


// send msg 
submitBtn.addEventListener('click', () => {

  let userInput = inputElm.value;

  if (userInput == "") {
    return;
  }
  inputElm.value = "";

  $.ajax({
    method: "POST",
    data: "message=" + userInput,
    url: chatServletUrl,
    timeout: 2000,
    success: function () {

    }
  });

})

function getAndPopulateChat() {

  $.ajax({

    url: chatServletUrl,
    timeout: 2000,
    error:function(){
    
      failedToGetChatRecordsCounter ++;
      if(failedToGetChatRecordsCounter == 2)
      {
         //goToLoginPage();
      }
    
    },
    success: function (chatRecords) {
    
      failedToGetChatRecordsCounter = 0;
      if (chatArea.messages == chatRecords.length) {
        return;
      }


      const myUserName = getMyUserName();
      chatArea.messages = chatRecords.length;
      chatArea.innerHTML = "";
      chatRecords.forEach(record => {
        const message = record.username + " : " + record.message;

        let temp = `<div class="out-msg">
    <span class="my-msg">${message}</span>
    </div>`;

        if (record.username != myUserName) {
          temp = `<div class="income-msg">
        <span class="my-msg">${message}</span>
        </div>`;

        }

        chatArea.insertAdjacentHTML("beforeend", temp);
      });

      var scroller = $("#chatarea");
      var height = scroller[0].scrollHeight - $(scroller).height();
      $(scroller).stop().animate({ scrollTop: height }, "slow");
    }
  });

}