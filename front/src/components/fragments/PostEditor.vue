<template>
  <q-card class="full-height">
    <q-card-section>
      <span class="text-h5">POST 작성</span>
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

    <q-dialog variant="danger" :show="showAlert">{{ errMsg }}</q-dialog>

    <q-card-actions class="q-pa-md flex justify-end">
      <q-btn label="취소" to="/main" />
      <q-btn label="리셋" @click="onReset"/>
      <q-btn label="작성하기" @click="createPost"/>
    </q-card-actions>
  </q-card>
</template>

<script>

const storage = window.sessionStorage;
const token = storage.getItem("jwt-auth-token");
const login_user = storage.getItem("login_user");

export default {

  name: 'PostEditor',
  data () {
    return {
      scope : true,
      title: '',
      shortdescription: '',
      body: '',
      // url: '',
      link: '',
      showAlert: false,
      errMsg: ''
    }
  },
    created(){
        if(token != null && token.length > 0){
        } else {
            this.$router.push('/login');
        }
    },
    methods: {
        createPost: function() {
            if ( this.title == '' ) {
                this.showAlert = true;
                this.errMsg = 'Please enter post title';
                return;
            }
            if ( this.body == '' ) {
                this.showAlert = true;
                this.errMsg = 'Please enter post body';
                return;
            }
            // if ( this.url == '' ) {
            //     this.showAlert = true;
            //     this.errMsg = 'Please enter post url';
            //     return;
            // }
            this.showAlert = false;

            this.$axios.post('http://localhost:8080/post/create',{
                title: this.title,
                body: this.body,
                shortDescription: this.shortdescription,
                url: "test",
                isPrivate: this.scope
            },{
                headers:{
                    "jwt-auth-token": storage.getItem("jwt-auth-token")
                },
            })
            .then((res) => {
                console.log(res.data);
                if(res.data.status){
                    var post = res.data.data;
                    console.log("post: " + post);
                    this.$router.push("/main")
                    this.$emit('create');
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
            this.url = ''
        }
    }
}
</script>

<style lang="scss">
.q-editor__content {
  min-height: 16rem !important;
}
</style>
