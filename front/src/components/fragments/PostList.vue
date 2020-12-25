<template>
  <q-card class="bg-grey-2">

    <q-card-section class="bg-deep-purple-1">
      <div class="flex justify-between items-center">
        <span class="text-h6">{{list_name}} 목록</span>
        <div class="q-gutter-x-sm">
          <q-btn size="sm" color="primary" label="추가" to="/main/post/editor" />
          <q-btn size="sm" color="primary" label="즐겨찾기" @click="getFavoriteList"/>
        </div>
      </div>
    </q-card-section>

    <q-separator />

    <q-card-section class="no-padding" >

      <q-list class="scroll" style="height : calc(100vh - 155px);">

        <q-item clickable v-ripple v-for="(item, index) in postList" :key="`post-${index}`" :active="active === item.post_id" @click="getPostDetail(item)"  active-class="active">
          <q-item-section>
            <q-item-label>{{item.title}}</q-item-label>
            <q-item-label caption lines="2">{{item.short_description}}</q-item-label>
          </q-item-section>
        </q-item>
        
      </q-list>
    </q-card-section>
  </q-card>
</template>

<script>

const storage = window.sessionStorage;
const token = storage.getItem("jwt-auth-token");
const login_user = storage.getItem("login_user");

export default {
  name: 'PostList',
  data () {
    return {
      active : -1,
      postList: [],
      post_id: '',
      list_name: '',
    }
  },
  created(){
        if(token != null && token.length > 0){
            this.connect();
        } else {
            this.$router.push('/');
        }
    },
    methods: {
        connect: function() {
            this.$axios.get('http://localhost:8080/post/list',{
                headers:{
                    "jwt-auth-token": storage.getItem("jwt-auth-token")
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
        getFavoriteList: function() {
            this.$axios.get('http://localhost:8080/post/favorite',{
                headers:{
                    "jwt-auth-token": storage.getItem("jwt-auth-token")
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
                            }
                        );
                    }
                }
            }).catch((e) => {
                console.error(e);
            })
        }
    }
}
</script>
