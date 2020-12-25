<template>
  <q-card class="bg-purple-1 full-height" flat square>
    <q-card-section class="bg-deep-purple-1" >
      <span class="text-h6 text-overflow-hidden">채팅상대</span>
    </q-card-section>

    <q-separator />
    <q-card-section class="scroll" style="height : calc(100vh - 236px); min-width : 320px;">
      <q-chat-message
        name="아이유"
        bg-color="white"
        avatar="https://cdnimg.melon.co.kr/cm2/album/images/103/46/650/10346650_1000.jpg?14a08050b8c6adc879b6e0cf587d456a/melon/quality/80/optimize"
        :text="['unlucky', '그 사람', 'Blueming', '시간의 바깥', '자장가', 'Love Poemdddddddddddddddddddddddddddddddddddddddddd']"
      />
      <q-chat-message
        name="나"
        bg-color="yellow-13"
        avatar="http://images.battlecomics.co.kr/users/274806/page/item/f5356aee-25e4-4e25-8601-2cc926f871f4.jpg"
        :text="['이건 함정이야!']"
        sent
      />
      <q-chat-message
        name="아이유"
        bg-color="white"
        avatar="https://cdnimg.melon.co.kr/cm2/album/images/103/46/650/10346650_1000.jpg?14a08050b8c6adc879b6e0cf587d456a/melon/quality/80/optimize"
      >
        <q-spinner-dots size="2rem" />
      </q-chat-message>
    </q-card-section>
  </q-card>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJs from 'sockjs-client'

const storage = window.sessionStorage;

export default {
  name: 'ChatContent',
  data() {
    return {
      username: storage.getItem("login_user"),
            message: "",
            recvList: []
    }
  },
  created(){
    if(storage.getItem("jwt-auth-token") != null){
        this.onChangeChatId();    
    } else {
        this.$router.push('/');
    }
  },
  computed: {
    chatId : function () {
      return this.$route.params.chatId;
    }
  },
  watch: {
    'chatId' : 'onChangeChatId'
  },
  methods : {
    onChangeChatId : function () {
      // console.log('chatid 업데이트됨')
      const serverURL = "http://localhost:8080/websocketApp"
      let socket = new SockJs(serverURL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.debug = () => {}
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect({},
          frame => {
              // 소켓 연결 성공
              this.connected = true;
              console.log('소켓 연결 성공', frame);
              // endpoint 구독
              this.stompClient.subscribe("/topic/" + this.$route.params.id, this.onMessageReceived);
              this.stompClient.send("/app/chat.newUser", JSON.stringify({
                  //username: "testUser",
                  username: storage.getItem("login_user"),
                  type: 'newUser',
                  channel: this.$route.params.id
              }), {});
          },
          error => {
              // 소켓 연결 실패
              console.log('소켓 연결 실패', error);
              this.connected = false;
          }
      )
    },
    sendMessage (e) {
        console.log("here")
        console.log("e.keyCode: "+e.keyCode +" username: " + this.username + " message: " + this.message)
        if(e.keyCode === 13 && this.username !== '' && this.message !== ''){
            console.log("here2")
            this.send()
            this.message = ''
        }
    },    
    send() {
        console.log("Send message:" + this.message);
        if (this.stompClient && this.stompClient.connected) {
            const msg = { 
            username: storage.getItem("login_user"),
            message: this.message,
            type: 'CHAT',
            channel: this.$route.params.id
            };
            this.stompClient.send("/app/chat.sendMessage", JSON.stringify(msg), {});
        }
    },
    onMessageReceived(payload){
        console.log("payload: " + payload);
        var sub_message = JSON.parse(payload.body);
        if(sub_message.type === 'newUser') {
            sub_message.message = sub_message.username + ' has joined the pochat'
            this.username = storage.getItem("login_user");
        } else if (sub_message.type === 'Leave') {
            sub_message.message = sub_message.username + ' has left the chat';
        }
        this.recvList.push(sub_message);
    }
  }
}
</script>

<style lang="scss">
.q-message-container > div {

  & > div {
    float: left;
    clear: left;
    width : auto;
  }

  & > div:last-child {
    min-height: 0px;
  }
}
</style>
