<template>
  <q-card class="full-height">
    <q-card-section>
      <span class="text-h5">POST {{action}}</span>
    </q-card-section>

    <q-card-section class="q-gutter-y-sm" @reset="onReset">

      <q-input label="제목" v-model="title"/>

      <div>
        <q-radio v-model="scope" :val="true" label="Private" />
        <q-radio v-model="scope" :val="false" label="Group" />
      </div>

      <q-input label="소개글" outlined v-model="shortdescription"/>

      <q-editor v-model="body" />

    </q-card-section>

    <q-card-actions class="q-pa-md flex justify-end">
      <q-btn label="취소" @click="$router.go(-1)" />
      <q-btn label="리셋" @click="onReset"/>
      <q-btn v-if="action=='작성'" label="작성하기" @click="createPost"/>
      <q-btn v-else label="수정하기" @click="updatePost"/>
    </q-card-actions>
  </q-card>
</template>

<script>
export default {

  name: 'PostEditor',
  data () {
    return {
      scope : true,
      title: '',
      shortdescription: '',
      body: '',
      link: '',
      action : '작성'
    }
  },
  computed : {
    token : function () {
      return sessionStorage.getItem("jwt-auth-token");
    },
    login_user : function () {
      return sessionStorage.getItem("login_user");
    },
    postId : function() {
      return this.$route.query.id;
    },
    chatId : function() {
      return this.$route.params.chatId;
    }
  },
    mounted(){
        if(this.token != null && this.token.length > 0){
          console.log("=========================================" + this.chatId)
        } else {
            this.$router.push('/login');
        }
        if(this.$route.query.id) {
          this.action = '수정'
          // 그 아이디로 데이터를 불러와서 채워넣으면 되고
          this.$axios.get('http://localhost:8080/post/'+this.postId, {
              headers:{
                  "jwt-auth-token": this.token
              },
          })
          .then((res) => {
              const postDetail = res.data.data
              console.log(postDetail)
              this.title = postDetail.title
              this.body = postDetail.body
              this.shortdescription = postDetail.shortDescription
              this.scope = postDetail.isPrivate
          }).catch((e) => {
              console.error(e);
          })
        }
    },
    methods: {
      triggerPositive : function (messages) {
        this.$q.notify({
            type: 'positive',
            message: messages
        })
      },
      triggerNegative : function(messages) {
        this.$q.notify({
            type: 'negative',
            message: messages
        })
      },
      updatePost : function () {
        this.$axios.post('http://localhost:8080/post/update'
            ,JSON.stringify({
                id : this.postId,
                title : this.title,
                body : this.body,
                shortDescription: this.shortdescription,
                isPrivate: this.scope
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
                  const Msg = 'post 수정에 성공하였습니다!';
                  this.triggerPositive(Msg);
                  this.$router.go(-1);
                  this.$emit('postUpdated');
                }
            }).catch((e) => {
                console.error(e);
            })
      },
        createPost: function() {
            if ( this.title == '' ) {
                const errMsg = 'Please enter post title';
                this.triggerNegative(errMsg);
                return;
            }
            if ( this.body == '' ) {
                const errMsg = 'Please enter post body';
                this.triggerNegative(errMsg);
                return;
            }

            this.$axios.post('http://localhost:8080/post/create',{
                title: this.title,
                body: this.body,
                shortDescription: this.shortdescription,
                isPrivate: this.scope,
                chatId : this.chatId
            },{
                headers:{
                    "jwt-auth-token": this.token
                },
            })
            .then((res) => {
                console.log(res.data);
                if(res.data.status){
                    var post = res.data.data;
                    console.log("post: " + post);
                    const Msg = 'post 생성에 성공하였습니다!';
                    this.triggerPositive(Msg);
                    // this.$router.push("/main")
                    this.$router.go(-1)
                    this.$emit('postUpdated');
                }
            }).catch((e) => {
                console.error(e);
            })
        },
        onReset(evt) {
            evt.preventDefault()
            // Reset our form values
            this.title = ''
            this.shortdescription = ''
            this.body = ''
        }
    }
}
</script>

<style lang="scss">
.q-editor__content {
  min-height: 16rem !important;
}
</style>
