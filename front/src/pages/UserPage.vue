<template>
<q-page class="row q-pa-md bg-grey-1 justify-center">

  <q-card class="col-12 col-lg-6 col-md-8" >

    <q-card-section class="row">
      <q-card-section class="col flex justify-end">
        <img class="profile-img" :src="imgSource" />
      </q-card-section>

      <q-card-section class="q-my-lg col column justify-between">
        <div class="col-auto">
          <div class="text-h3">{{username}}</div>
          <div class="text-h4">{{created_at}}</div>
        </div>
        <div class="col-auto q-gutter-x-sm">
          <q-btn label="프로필 변경" @click="onChangeProfile" />
          <q-btn label="친구추가"  @click="onCreateFriend"/>
        </div>
      </q-card-section>
    </q-card-section>

    <q-card-section>
      <q-expansion-item label="친구목록" expand-separator>
        <q-list class="scroll">
          <q-item clickable v-ripple v-for="(item, index) in friendAcceptList" :key="index">
            <q-item-section avatar>
              <q-avatar>
                <img :src="item.imgSource"> 
              </q-avatar>
            </q-item-section>
            <q-item-section>{{item.friendName}}</q-item-section>
          </q-item>
        </q-list>
      </q-expansion-item>
      <q-expansion-item label="보낸 친구신청" expand-separator>
        <q-list class="scroll">
          <q-item clickable v-ripple v-for="(item, index) in friendSendList" :key="index">
            <q-item-section avatar>
              <q-avatar>
                <img :src="item.imgSource"> 
              </q-avatar>
            </q-item-section>
            <q-item-section>{{item.recipient}}</q-item-section>
            <q-item-section class="items-end">
              <q-btn label="취소" flat @click="cancelFriend(item)"/>
            </q-item-section>
          </q-item>
        </q-list>
      </q-expansion-item>
      <q-expansion-item label="받은 친구신청" expand-separator>
        <q-list class="scroll">
          <q-item clickable v-ripple v-for="(item, index) in friendRequestList" :key="index">
            <q-item-section avatar>
              <q-avatar>
                <img :src="item.imgSource"> 
              </q-avatar>
            </q-item-section>
            <q-item-section>{{item.sender}}</q-item-section>
            <q-item-section class="items-end">
              <q-btn label="승인" color="primary" flat @click="acceptFriend(item)" />
            </q-item-section>
          </q-item>
        </q-list>
      </q-expansion-item>

    </q-card-section>
    
  </q-card>

  <change-profile-dialog v-if="profileDialog" :show="profileDialog" @cancel="profileDialog = false" @submit="onProfileSubmit"/>
  <create-friend-dialog v-if="friendDialog" :show="friendDialog" @cancel="friendDialog = false" @submit="onFriendSubmit"/>

</q-page>
</template>

<script>
import ChangeProfileDialog from 'src/components/dialog/ChangeProfileDialog.vue'
import CreateFriendDialog from 'src/components/dialog/CreateFriendDialog.vue'

export default {
  name: 'UserPage',
  components : {ChangeProfileDialog, CreateFriendDialog},
  data () {
    return {
      username: '',
      created_at: '',
      recipient: '',
      friendSendList: [],
      friendRequestList: [],
      friendAcceptList: [],
      friend_id: '',
      imgSource : "",
      profileDialog : false,
      friendDialog : false
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
        this.connect();
        this.getFriendSendList();
        this.getFriendRequestList();
        this.getFriendAcceptList();
    } else {
        this.$router.push('/');
    }
  },
  methods: {
      triggerPositive : function (messages) {
          this.$q.notify({
              type: 'positive',
              message: messages
          })
      },
      connect: function() {
          this.$axios.get('http://localhost:8080/user/'+this.login_user,{
              headers:{
                  "jwt-auth-token": this.token
              },
          })
          .then((res) => {
              console.log(res.data);
              this.username = res.data.username;
              const date = String(res.data.created_at).split('T')[0].split('-');
              this.created_at = date[0] + "년 " + date[1] + "월 "+ date[2] +"일";
              this.imgSource = "http://localhost:8080/img" + res.data.thumbnail;
          }).catch((e) => {
              console.error(e);
          })
      },
      addFriend: function(){
          console.log("recipentName: "+this.recipient);
          this.$axios.post('http://localhost:8080/friend'
          ,{
              recipientName: this.recipient
          },{
              headers:{
                  "jwt-auth-token": this.token
              },
          })
          .then((res) => {
              if(res.data.status){
                  console.log(res.data.data)
                  this.getFriendSendList();
              }
          }).catch((e) => {
              console.error(e);
          })
      },
      getFriendSendList: function(){
          this.$axios.get('http://localhost:8080/friend/send/list',{
              headers:{
                  "jwt-auth-token": this.token
              },
          })
          .then((res) => {
              console.log(res.data.data);
              if(res.data.status){
                  for(var i=0; i<res.data.data.length; i++){
                      this.friendSendList.push(
                          {
                              imgSource: "http://localhost:8080/img" + res.data.data[i].recipient.thumbnail,
                              recipient: res.data.data[i].recipient.username,
                          }
                      );
                  }
              }
          }).catch((e) => {
              console.error(e);
          })
      },
      acceptFriend: function(item){
          console.log(item)
          this.$axios.post('http://localhost:8080/friend/accept',
          {
              senderName: item.sender,
              recipientName:this.login_user
          },
          {
              headers:{
                  "jwt-auth-token": this.token
              },
          })
          .then((res) => {
              console.log(res.data.data);
              if(res.data.status){
                  const messages = "친구 요청을 수락하였습니다!"
                  this.triggerPositive(messages)
                  this.friendRequestList.splice(this.friendRequestList.indexOf(item), 1);
                  this.friendAcceptList = []
                  this.getFriendAcceptList();
              }
          }).catch((e) => {
              console.error(e);
          })
      },
      getFriendRequestList: function(){
          this.$axios.get('http://localhost:8080/friend/request/list',{
              headers:{
                  "jwt-auth-token": this.token
              },
          })
          .then((res) => {
              console.log(res.data.data);
              if(res.data.status){
                  for(var i=0; i<res.data.data.length; i++){
                      this.friendRequestList.push(
                          {
                              imgSource: "http://localhost:8080/img" + res.data.data[i].sender.thumbnail,
                              sender: res.data.data[i].sender.username
                          }
                      );
                  }
              }
          }).catch((e) => {
              console.error(e);
          })
      },
      getFriendAcceptList: function(){
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
                          this.friendAcceptList.push(
                              {
                                  imgSource: "http://localhost:8080/img" + res.data.data[i].recipient.thumbnail,
                                  friendName: res.data.data[i].recipient.username
                              }
                          )
                      } else if(this.login_user == res.data.data[i].recipient.username){
                          this.friendAcceptList.push(
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
      onChangeProfile: function () {
        this.profileDialog = true;
      },
      onProfileSubmit: function(thumbnail) {
        this.imgSource = "http://localhost:8080/img/" + thumbnail;
        this.profileDialog = false;
      },
      onCreateFriend: function() {
        this.friendDialog = true;
      },
      onFriendSubmit: function(newFriend) {
        this.friendSendList.push({
            imgSource: "http://localhost:8080/img" + newFriend.recipient.thumbnail,
            recipient: newFriend.recipient.username
        })
        this.friendDialog = false;
      },
      cancelFriend: function(item){
        console.log("==========================" + item.recipient)
        this.$axios.post('http://localhost:8080/friend/cancel',
        {
            cancelUser: this.login_user,
            canceledUser: item.recipient
        },
        {
              headers:{
                  "jwt-auth-token": this.token
              },
          })
          .then((res) => {
              console.log(res.data.data);
              if(res.data.status){
                  const messages = "요청이 취소되었습니다!"
                  this.triggerPositive(messages)
                  this.friendSendList.splice(this.friendSendList.indexOf(item), 1);
              }
          }).catch((e) => {
              console.error(e);
          })
      }
  }
}
</script>

<style lang="scss">
.profile-img {
  max-width : 250px;
  max-height : 250px;
  border-radius: 50%;
}
.profile-label-name {
  font-size : 56px;
}
.profile-label-registered {
  font-size : 36px;
}
</style>
