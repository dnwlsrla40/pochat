<template>
  <q-card class="bg-grey-2">

    <q-card-section class="bg-deep-purple-1">
      <div class="flex justify-between items-center">
        <span class="text-h6">{{list_name}} 목록</span>
        <div class="q-gutter-x-sm">
          <q-btn size="sm" color="primary" label="추가" @click="onCreate"/>
          <q-btn size="sm" color="primary" v-if="isPostLike" label="즐겨찾기" @click="getPostLikeList"/>
          <q-btn size="sm" color="primary" v-if="!isPostLike" label="포스트 목록" @click="connect"/>
        </div>
      </div>
    </q-card-section>

    <q-separator />

    <q-card-section class="no-padding" >

      <q-list class="scroll" style="height : calc(100vh - 155px);">

        <q-item clickable v-ripple v-for="(item, index) in postList" :key="`post-${index}`" :active="active === item.post_id" @click="getPostDetail(item)"  active-class="active">
          <q-item-section>
            <q-item-label class="ellipsis" >{{item.title}}</q-item-label>
            <q-item-label class="ellipsis" caption lines="2">{{item.short_description}}</q-item-label>
          </q-item-section>
        </q-item>
        
      </q-list>
    </q-card-section>
  </q-card>
</template>

<script>
export default {
  name: 'PostList',
  data () {
    return {
      active : -1,
      postList: [],
      post_id: '',
      list_name: '포스트',
      isPostLike: true
    }
  },
  computed : {
    token : function () {
      return sessionStorage.getItem("jwt-auth-token");
    },
    login_user : function () {
      return sessionStorage.getItem("login_user");
    },
    chatId : function() {
      return this.$route.params.chatId;
    }
  },
  watch: {
      'chatId': 'connect'
  },
  mounted(){
        if(this.token != null && this.token.length > 0){
            this.connect();
        } else {
            this.$router.push('/');
        }
    },
    methods: {
        connect: function() {
          if(this.postId == undefined || this.postId < 0) return;
            this.isPostLike = true
            this.$axios.get('http://localhost:8080/post/list/' + this.chatId,{
                headers:{
                    "jwt-auth-token": this.token
                }
            })
            .then((res) => {
                if(res.data.status){
                    this.list_name = "포스트"
                    this.postList = new Array;
                    for(var i=0; i<res.data.data.length; i++){
                        this.postList.push(
                            {
                                title: res.data.data[i].title,
                                short_description: res.data.data[i].shortDescription,
                                post_id: res.data.data[i].id
                            }
                        );
                    }
                }
            }).catch((e) => {
                console.error(e);
            })
        },
        getPostDetail: function(item){
            console.log(item);
            this.active = item.post_id;
            const params = this.$route.params

            if(!params.chatId) {
              params.chatId = -1
            }
            params.postId = item.post_id
            this.$router.push(
              {
                name : 'content',
                params : params
              }
            );
        },
        getPostLikeList: function() {
            this.isPostLike = false
            this.$axios.get('http://localhost:8080/postlike/' + this.chatId,{
                headers:{
                    "jwt-auth-token": this.token
                },
            })
            .then((res) => {
                if(res.data.status){
                  this.list_name = "즐겨찾기"
                    this.postList = new Array;
                    for(var i=0; i<res.data.data.length; i++){
                        this.postList.push(
                            {
                                title: res.data.data[i].title,
                                short_description: res.data.data[i].shortDescription,
                                post_id: res.data.data[i].id
                            }
                        );
                    }
                }
            }).catch((e) => {
                console.error(e);
            })
        },
        onCreate : function () {
          if (this.chatId > -1){
            this.$router.push({name : 'posteditor'})
          }
          else {
            // this.q .notify
          }
        }
    }
}
</script>
