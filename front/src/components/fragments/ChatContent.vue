<template>
  <q-card class="bg-purple-1 full-height" flat square>
    <q-card-section class="bg-deep-purple-1 row justify-between" >
      <div class="col-auto text-h6 text-overflow-hidden">채팅상대</div>

      <div class="col-auto">
          <q-btn size="sm" color="grey-7" round flat icon="more_vert">
            <q-menu auto-close>
              <q-list>
                <q-item clickable>
                  <q-item-section @click="onAddFriend">인원추가</q-item-section>
                </q-item>
                <q-item clickable>
                  <q-item-section @click="deleteChatRoom">나가기</q-item-section>
                </q-item>
              </q-list>
            </q-menu>
          </q-btn>
      </div>
    </q-card-section>

    <q-separator />
    <q-card-section ref="scollArea" class="scroll" style="height : calc(100vh - 236px); min-width : 320px;">
      <template v-for="(item,index) in recvList" >
            
        <template v-if="item.type=='newUser'">
          <div :key="index"
            v-if="login_user!=item.username"
            class="system-message"
             v-text="item.message" 
          />
        </template>
        <q-chat-message
          v-else
          :key="index"
          :name="item.username"
          :bg-color="login_user == item.username ? 'yellow-13' : 'white'"
          :avatar="item.imgSource"
          :text="[item.message]"
          :sent="login_user == item.username"
        />
      </template>
    </q-card-section>

    <add-friend-dialog v-if="addFriendDialog" :show="addFriendDialog" @cancel="addFriendDialog = false" @submit="AddFriendSubmit"/>
  </q-card>
</template>

<script>
import AddFriendDialog from 'src/components/dialog/AddFriendDialog.vue'
import Stomp from 'webstomp-client'
import SockJs from 'sockjs-client'

export default {
  name: 'ChatContent',
  components : {AddFriendDialog},
  data() {
    return {
      username: this.login_user,
      message: "",
      recvList: [],
      currentChatId: -1,
      addFriendDialog : false
    }
  },
  computed : {
    token : function () {
      return sessionStorage.getItem("jwt-auth-token");
    },
    login_user : function () {
      return sessionStorage.getItem("login_user");
    },
    chatId : function () {
      return this.$route.params.chatId;
    }
  },
  mounted(){
    if(this.token != null){
      this.onChangeChatId();
    } else {
        this.$router.push('/');
    }
  },
  watch: {
    'chatId' : 'onChangeChatId',
  },
  updated : function () {
    this.$refs.scollArea.$el.scrollTop = this.$refs.scollArea.$el.scrollHeight;
  },
  methods : {
    triggerPositive : function (messages) {
      this.$q.notify({
        type: 'positive',
        message: messages
      })
    },
    onMessageReceived(payload){

        console.log("payload: ");
        var sub_message = JSON.parse(payload.body);
        if(sub_message.type === 'newUser') {
          
            sub_message.message = sub_message.username + '님이 입장하셨습니다.'
            this.username = this.login_user;
        } else if (sub_message.type === 'Leave') {
            sub_message.message = sub_message.username + ' has left the chat';
        }
        console.log("=======================받았다!!!!!!!!!!!!!!!!!!!!")
        this.recvList.push(sub_message);
    },
    onCreateConnection: function(){
      console.log('chatid 업데이트됨')
      console.log('this.stompClient : ' + this.stompClient + "this.currentChatId : " + this.currentChatId)
      if(this.stompClient) {
        this.stompClient.unsubscribe(this.currentChatId);
      }
      const serverURL = "http://localhost:8080/websocketApp"
      let socket = new SockJs(serverURL);
      this.stompClient = Stomp.over(socket);
      this.stompClient.debug = () => {}
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stompClient.connect({}, () => {
        this.currentChatId = this.chatId
        this.stompClient.subscribe("/topic/" + this.chatId, this.onMessageReceived, {id : this.chatId});
        this.stompClient.send("/app/chat.newUser", JSON.stringify({
            username: this.login_user,
            type: 'newUser',
            channel: this.chatId
        }), {});
      });
      // 소켓 연결 성공
      // console.log("========================newUser 성공~!!!!!!!!!!!!!!!")
    },
    onChangeChatId : function () {
      console.log("=====================지운다!!!!!!!!!!!!!!!!!!!")
      this.recvList=[];
      this.onCreateConnection();
      this.getChatHistory();
      // this.getChatHistory();
    },
    // sendMessage (e) {
    //     console.log("here")
    //     console.log("e.keyCode: "+e.keyCode +" username: " + this.username + " message: " + this.message)
    //     if(e.keyCode === 13 && this.username !== '' && this.message !== ''){
    //         this.send()
    //         this.message = ''
    //     }
    // },    
    // send() {
    //     console.log("Send message:" + this.message);
    //     if (this.stompClient && this.stompClient.connected) {
    //         const msg = { 
    //         username: this.login_user,
    //         message: this.message,
    //         type: 'CHAT',
    //         channel: this.chatId
    //         };
    //         this.stompClient.send("/app/chat.sendMessage", JSON.stringify(msg), {});
    //     }
    // },

    onMessageSend (payload) {
      // console.log("============sdfsdfhere")
      if (this.stompClient && this.stompClient.connected) {
            const msg = { 
            username: this.login_user,
            message: payload,
            type: 'CHAT',
            channel: this.chatId
            };
            this.stompClient.send("/app/chat.sendMessage", JSON.stringify(msg), {});
        }
    },
    getChatHistory: function (){
      this.$axios.get('http://localhost:8080/chat/history/' + this.chatId,{
          headers:{
              "jwt-auth-token":this.token
          }
      }).then((res) => {
        console.log(res.data);
        if(res.data.status){
          console.log("+=========================넣는다!!!!!!!!!!!!!!!!!!")
          for(var i=0; i<res.data.data.length; i++){
              this.recvList.push(
                  {
                      username : res.data.data[i].sender,
                      message : res.data.data[i].message,
                      imgSource : res.data.data[i].sender_thumbnail
                  }
              );
          }
          //this.onCreateConnection();
        }
      }).catch((e) => {
        console.error(e);
      })
    },
    deleteChatRoom: function () {
        this.$axios.delete(
            'http://localhost:8080/chatroommember/delete/' + this.chatId,
            {
                headers: {
                    "jwt-auth-token":this.token,
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'multipart/form-data'
                }
            }
        ).then((res) => {
            const messages = "채팅방을 나갔습니다!"
            this.triggerPositive(messages)
            console.log(res.data)
            this.$router.push("/main")
            this.$emit('chatRoomUpdated', this.chatId);
        }).catch((e) => {
            console.error(e);
        })
        },
        onAddFriend: function () {
        this.addFriendDialog = true;
        },
        AddFriendSubmit: function(){
        this.addFriendDialog = false;
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
.system-message {
  text-align: center;
  font-weight : 700;
}
</style>
