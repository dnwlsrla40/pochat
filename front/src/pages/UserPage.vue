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
          <q-btn label="친구추가"  />
        </div>
      </q-card-section>
    </q-card-section>

    <q-card-section>
      <q-expansion-item label="친구목록" expand-separator>
        <q-list class="scroll">
          <q-item clickable v-ripple v-for="(number, index) in 10" :key="index">
            <q-item-section avatar>
              <q-avatar>
                <img src="https://cdn.entermedia.co.kr/news/photo/202010/21304_40107_5416.jpg"> 
              </q-avatar>
            </q-item-section>
            <q-item-section>테스형</q-item-section>
          </q-item>
        </q-list>
      </q-expansion-item>
      <q-expansion-item label="보낸 친구신청" expand-separator>
        <q-list class="scroll">
          <q-item clickable v-ripple v-for="(number, item) in friendSendList" :key="item">
            <q-item-section avatar>
              <q-avatar>
                <img src="https://cdn.entermedia.co.kr/news/photo/202010/21304_40107_5416.jpg"> 
              </q-avatar>
            </q-item-section>
            <q-item-section>테스형</q-item-section>
            <q-item-section class="items-end">
              <q-btn label="취소" flat />
            </q-item-section>
          </q-item>
        </q-list>
      </q-expansion-item>
      <q-expansion-item label="받은 친구신청" expand-separator>
        <q-list class="scroll">
          <q-item clickable v-ripple v-for="(number, index) in 10" :key="index">
            <q-item-section avatar>
              <q-avatar>
                <img src="https://cdn.entermedia.co.kr/news/photo/202010/21304_40107_5416.jpg"> 
              </q-avatar>
            </q-item-section>
            <q-item-section>테스형</q-item-section>
            <q-item-section class="items-end">
              <q-btn label="승인" color="primary" flat />
            </q-item-section>
          </q-item>
        </q-list>
      </q-expansion-item>

    </q-card-section>
    
  </q-card>

  <change-profile-dialog :show="dialog" @cancel="dialog = false" />

</q-page>
</template>

<script>
import ChangeProfileDialog from 'src/components/dialog/ChangeProfileDialog.vue'
const storage = window.sessionStorage;
const token = storage.getItem("jwt-auth-token");
const login_user = storage.getItem("login_user");


export default {
  name: 'UserPage',
  components : {ChangeProfileDialog},
  data () {
    return {
      username: '',
      created_at: '',
      recipient: '',
      friendSendList: [],
      friendRequestList: [],
      friendAcceptList: [],
      friend_id: '',
      thumbnail: '',
      imgSource : "https://cdnimg.melon.co.kr/cm2/album/images/103/46/650/10346650_1000.jpg?14a08050b8c6adc879b6e0cf587d456a/melon/quality/80/optimize",
      dialog : false
    }
  },
  created(){
    if(token != null && token.length > 0){
        this.connect();
        this.getFriendSendList();
        this.getFriendRequestList();
        this.getFriendAcceptList();
    } else {
        this.$router.push('/');
    }
  },
  methods: {
      connect: function() {
          this.$axios.get('http://localhost:8080/user/'+login_user,{
              headers:{
                  "jwt-auth-token": storage.getItem("jwt-auth-token")
              },
          })
          .then((res) => {
              console.log(res.data);
              this.username = res.data.username;
              const date = String(res.data.created_at).split('T')[0].split('-');
              this.created_at = date[0] + "년 " + date[1] + "월 "+ date[2] +"일";
              this.thumbnail = res.data.thumbnail;
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
                  "jwt-auth-token": storage.getItem("jwt-auth-token")
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
                  "jwt-auth-token": storage.getItem("jwt-auth-token")
              },
          })
          .then((res) => {
              console.log(res.data.data);
              if(res.data.status){
                  for(var i=0; i<res.data.data.length; i++){
                      this.friendSendList.push(
                          {
                              recipient: res.data.data[i].recipient.username,
                              isAccept: res.data.data[i].isAccept
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
              recipientName: login_user
          },
          {
              headers:{
                  "jwt-auth-token": storage.getItem("jwt-auth-token")
              },
          })
          .then((res) => {
              console.log(res.data.data);
              if(res.data.status){
                  this.created();
              }
          }).catch((e) => {
              console.error(e);
          })
      },
      getFriendRequestList: function(){
          this.$axios.get('http://localhost:8080/friend/request/list',{
              headers:{
                  "jwt-auth-token": storage.getItem("jwt-auth-token")
              },
          })
          .then((res) => {
              console.log(res.data.data);
              if(res.data.status){
                  for(var i=0; i<res.data.data.length; i++){
                      this.friendRequestList.push(
                          {
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
                  "jwt-auth-token": storage.getItem("jwt-auth-token")
              },
          })
          .then((res) => {
              console.log(res.data.data);
              if(res.data.status){
                  for(var i=0; i<res.data.data.length; i++){
                      if(login_user == res.data.data[i].sender.username){
                          this.friendAcceptList.push(
                              {
                                  friendName: res.data.data[i].recipient.username
                              }
                          )
                      } else if(login_user == res.data.data[i].recipient.username){
                          this.friendAcceptList.push(
                              {
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
        this.dialog = true;
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
