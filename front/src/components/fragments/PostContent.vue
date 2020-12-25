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
                <q-item clickable>
                  <q-item-section>수정하기</q-item-section>
                </q-item>
                <q-item clickable>
                  <q-item-section>삭제하기</q-item-section>
                </q-item>
              </q-list>
            </q-menu>
          </q-btn>
        </div>
      </div>
    </q-card-section>

    <q-separator />
    
    <q-card-section class="scroll" style="height : calc(100vh - 236px);">
      <div v-html="body" />
    </q-card-section>

  </q-card>
</template>

<script>

const storage = window.sessionStorage;
const token = storage.getItem("jwt-auth-token");
const login_user = storage.getItem("login_user");

export default {
  name: 'PostContent',
    data () {
        return {
            loading: true,
            post: false,
            title: '',
            writer: '',
            body: '',
        }
    },
    computed: {
      postId : function () {
        return this.$route.params.postId;
      }
    },
    watch: {
        'postId': 'connect'
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
            this.post = false
            this.loading = true
            this.$axios.get('http://localhost:8080/post/'+this.$route.params.id, {
                headers:{
                    "jwt-auth-token": storage.getItem("jwt-auth-token")
                },
            })
            .then((res) => {
                this.loading = false
                this.post = true
                const postDetail = res.data.data
                this.title = postDetail.title
                this.writer = postDetail.user.username
                this.body = postDetail.body
            }).catch((e) => {
                console.error(e);
            })
        },
        addFavorite: function() {
            this.$axios.post('http://localhost:8080/post/favorite'
            ,JSON.stringify({
                id : this.$route.params.id
            }),
            {
                headers:{
                    "jwt-auth-token": storage.getItem("jwt-auth-token"),
                    'Content-Type': 'application/json'
                }
            })
            .then((res) => {
                console.log(res.data.data);
            }).catch((e) => {
                console.error(e);
            })
        }
    }
}
</script>
