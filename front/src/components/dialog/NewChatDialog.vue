<template>
  <q-dialog v-model="show" persistent>
    <q-card style="width : 551px;">
      <q-card-section class="text-h5">
        채팅방 생성
      </q-card-section>
      <q-card-section class="q-gutter-y-md">
        <q-input label="채팅방 이름" v-model="chatRoomName"/>

        <q-scroll-area style="height : 280px;">
          <q-list class="scroll">
            <q-item clickable v-ripple v-for="(item, index) in friendList" :key="index">
              <q-item-section avatar>
                <q-avatar>
                  <img :src="item.imgSource"> 
                </q-avatar>
              </q-item-section>
              <q-item-section>{{item.friendName}}</q-item-section>
              <q-item-section class="items-end">
                <q-checkbox :val="item.friendName" v-model="select" />
              </q-item-section>
            </q-item>
          </q-list>
        </q-scroll-area>

        <div class="q-gutter-x-xs">
          
          <q-chip :label="item" removable v-for="item in select" :key="item.chatroom_id" @remove="onRemove(item)" />
        </div>
      </q-card-section>
      
      <q-card-actions class="justify-end">
        <q-btn color="primary" label="만들기" @click="onSubmit" />
        <q-btn label="취소" @click="onCancel" />
      </q-card-actions>

    </q-card>
  </q-dialog>
</template>

<script>

export default {
  name : "NewChatDialog",
  props : [ 'show' ],
  data () {
    return {
      select : [],
      friendList: [],
      chatRoomName: ''
    }
  },
  computed : {
    token : function () {
      return sessionStorage.getItem("jwt-auth-token");
    },
    login_user : function () {
      return sessionStorage.getItem("login_user");
    }
  },
  mounted(){
    if(this.token != null && this.token.length > 0){
        this.getAcceptedFriendList();
    } else {
        this.$router.push('/login');
    }
  },
  methods : {
    onRemove : function (val) {
      // 보여주기식 구현이라 그대로 따라하면 안될 가능성이 높음
      this.select.pop(val);
    },
    onSubmit : function () {
      console.log("roomMember: " + JSON.stringify(this.select));
      this.$axios.post('http://localhost:8080/chatroom',JSON.stringify(
      {
          name : this.chatRoomName,
          roomMember : this.select
      }),
      {
          headers:{
              "jwt-auth-token": this.token,
              'Content-Type': 'application/json'
          }
      })
      .then((res) => {
          console.log(res.data.data)
          res.data.data.chatroom_id = res.data.data.id
          this.$emit('submit', res.data.data)
      }).catch((e) => {
          console.error(e)
      })
    },
    onCancel : function () {
      this.$emit('cancel')
    },
    getAcceptedFriendList: function(){
      this.$axios.get('http://localhost:8080/friend/accept',{
          headers:{
              "jwt-auth-token": this.token
          },
      })
      .then((res) => {
          console.log(res.data.data);
          if(res.data.status){
              for(var i=0; i<res.data.data.length; i++){
                  if(this.login_user == res.data.data[i].sender.username){
                      this.friendList.push(
                          {
                              imgSource: "http://localhost:8080/img" + res.data.data[i].recipient.thumbnail,
                              friendName: res.data.data[i].recipient.username
                          }
                      )
                  } else if(this.login_user == res.data.data[i].recipient.username){
                      this.friendList.push(
                          {
                              imgSource: "http://localhost:8080/img" + res.data.data[i].sender.thumbnail,
                              friendName: res.data.data[i].sender.username
                          }
                      )
                  }
              }
          }
      }).catch((e) => {
          console.error(e);
      })
    },

  }
}
</script>