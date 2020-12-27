<template>
  <q-card flat square class="bg-grey-2 full-height">
    <q-card-section class="bg-deep-purple-1 ">
      <div class="row items-center no-wrap">
        <div class="col text-overflow-hidden">
          <div class="text-h6 " v-text="title" />
          <div v-if="writer" class="text-subtitle2 absolute" style="bottom : 2px;" v-text="`by ${writer}`" />
        </div>

        <div class="col-auto">
          <q-btn size="sm" color="grey-7" round flat icon="more_vert">
            <q-menu auto-close>
              <q-list>
                <q-item clickable v-if="!isfavorite">
                  <q-item-section @click="addFavorite">즐겨찾기</q-item-section>
                </q-item>
                <q-item clickable v-if="isfavorite">
                  <q-item-section @click="addFavorite">즐겨찾기 취소</q-item-section>
                </q-item>
                <q-item clickable>
                  <q-item-section @click="updatePost">수정하기</q-item-section>
                </q-item>
                <q-item clickable>
                  <q-item-section @click="deletePost">삭제하기</q-item-section>
                </q-item>
              </q-list>
            </q-menu>
          </q-btn>
        </div>
      </div>
    </q-card-section>

    <q-separator />
    
    <q-card-section class="scroll content" style="height : calc(100vh - 236px);">
      <div v-html="body" />
    </q-card-section>

  </q-card>
</template>

<script>
export default {
  name: 'PostContent',
    data () {
        return {
            loading: true,
            post: false,
            title: '',
            writer: '',
            body: '',
            isfavorite: null
        }
    },
    computed: {
      postId : function () {
        return this.$route.params.postId;
      },
      token : function () {
        return sessionStorage.getItem("jwt-auth-token");
      },
      login_user : function () {
        return sessionStorage.getItem("login_user");
      }
    },
    watch: {
        'postId': 'connect'
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
            this.post = false
            this.loading = true
            this.$axios.get('http://localhost:8080/post/'+this.postId, {
                headers:{
                    "jwt-auth-token": this.token
                },
            })
            .then((res) => {
                this.loading = false
                this.post = true
                const postDetail = res.data.data
                this.title = postDetail.title
                this.writer = postDetail.user.username
                this.body = postDetail.body
                this.isfavorite = postDetail.favorite
            }).catch((e) => {
                console.error(e);
            })
        },
        triggerPositive : function (messages) {
            this.$q.notify({
                type: 'positive',
                message: messages
            })
        },
        addFavorite: function() {
            this.$axios.post('http://localhost:8080/post/favorite'
            ,JSON.stringify({
                id : this.postId
            }),
            {
                headers:{
                    "jwt-auth-token": this.token,
                    'Content-Type': 'application/json'
                }
            })
            .then((res) => {
                console.log(res.data.data);
                if(res.data.status){
                  if(res.data.data.favorite) {
                    const messages = "즐겨찾기에 추가되었습니다!"
                    this.triggerPositive(messages)
                  } else if(!res.data.data.favorite) {
                    const messages = "즐겨찾기가 취소되었습니다!"
                    this.triggerPositive(messages)
                  }
                }
            }).catch((e) => {
                console.error(e);
            })
        },
        updatePost: function(){
            this.$router.push(`/main/post/editor?id=${this.postId}`)
            // this.$axios.post('http://localhost:8080/post/update'
            // ,JSON.stringify({
            //     id : this.postId
            // }),
            // {
            //     headers:{
            //         "jwt-auth-token": this.token,
            //         'Content-Type': 'application/json'
            //     }
            // })
            // .then((res) => {
            //     console.log(res.data.data);
            // }).catch((e) => {
            //     console.error(e);
            // })
        },
        deletePost: function(){
            this.$axios.delete('http://localhost:8080/post/delete/' + this.postId,
            {
                headers:{
                    "jwt-auth-token": this.token,
                    'Content-Type': 'application/json'
                }
            })
            .then((res) => {
                console.log("==========================" + res.data);
                if(res.data.status){
                  const messages = "post가 삭제되었습니다!"
                  this.triggerPositive(messages)
                  this.$emit('postUpdated')
                }
            }).catch((e) => {
                console.error(e);
            })
        }
    }
}
</script>

<style lang="scss" scoped>
.content {
  width : 100%;
  word-break: break-all;
  word-wrap: break-word;
  white-space: normal;
}
</style>>