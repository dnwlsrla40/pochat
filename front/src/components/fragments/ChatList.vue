<template>
  <q-card class="bg-grey-2">

    <q-card-section class="bg-deep-purple-1 flex">
      <div class="text-h6">채팅 목록</div>
      <q-space />
      <q-btn icon="add" flat @click="onNewChat" />
      <new-chat-dialog :show="dialog" @submit="onSubmit" @cancel="onCancel" />
    </q-card-section>

    <q-separator />

    <q-card-section class="no-padding">

      <q-list class="scroll" style="height : calc(100vh - 155px);">
        <q-item clickable v-ripple v-for="(item, index) in chatRoomList" :key="index" :active="active === index" @click="enterChatRoom(item)"  active-class="active">
          <q-item-section>{{ item.name }}</q-item-section>
        </q-item>
      </q-list>
    </q-card-section>
  </q-card>
</template>

<script>
import NewChatDialog from '../dialog/NewChatDialog.vue';

export default {
  name: 'ChatList',
  components : {NewChatDialog},
  data () {
    return {
      active : -1,
      dialog : false,
      chatRoomList: []
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
      } else {
          this.$router.push('/');
      }
  },
  methods : {
    onNewChat : function () {
      this.dialog = true;
    },
    onSubmit : function (payload) {
      console.log(payload)
      this.chatRoomList.push(payload);
      this.dialog = false;
    },
    onCancel : function () {
      this.dialog = false;
    },
    connect: function() {
      //process.env.VUE_APP_API + 
        this.$axios.get('http://localhost:8080/chatroom/list',{
            headers:{
                "jwt-auth-token":this.token
            }
        })
        .then((res) => {
            console.log(res.data);
            if(res.data.status){
                for(var i=0; i<res.data.data.length; i++){
                    this.chatRoomList.push(
                        {
                            name : res.data.data[i].name,
                            chatroom_id : res.data.data[i].id
                        }
                    );
                }
            }
        }).catch((e) => {
            console.error(e);
        })
    },
    enterChatRoom: function(item) {
      this.active = item.chatroom_id;
      // this.$router.push({
      //     path: '/chat/content/'+item.chatroom_id, 
      //   });
      const params = this.$route.params
      if (!params.postId) {
        params.postId = -1
      }
      params.chatId = item.chatroom_id
      
      this.$router.push({
        //path : `/main/content/${item.chatroom_id}`
        name : 'content',
        params :params
      })
    },
  }
}
</script>
