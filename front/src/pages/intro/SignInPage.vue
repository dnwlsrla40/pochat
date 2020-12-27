<template>
  <q-page class="column justify-center">
    <div class="col-auto row justify-center">
      <div class="col-2 q-gutter-y-md">
        <div class="text-center">
          <span class="text-h3 text-weight-bold">PoChat</span>
        </div>
        <q-card>

            <!-- <div>"This is SignIn Page."</div> -->
          <q-card-section class="text-h6 text-center q-pb-none">
            LogIn
          </q-card-section>

          <q-form>
            <q-card-section>
              <q-input label="username" v-model.trim="username" @keydown.enter="tryLogin"/>
              <q-input label="password" type="password" v-model.trim="password" @keydown.enter="tryLogin"/>
            </q-card-section>

            <q-card-section class="flex justify-center q-gutter-x-md">
              <q-btn label="signUp" color="purple-3" @click="signup"/>
              <q-btn label="login" color="primary" @click="tryLogin"/>
            </q-card-section>

          </q-form>
        </q-card>
      </div>
    </div>
  </q-page>
</template>

<script>
export default {
  name: 'SignInPage',
   data: function () {
        return {
            username: '',
            password: '',
        }
    },
    methods: {
        triggerNegative : function(messages) {
            this.$q.notify({
                type: 'negative',
                message: messages
            })
        },
        signup: function() {
            this.$router.push('/signup')
        },
        tryLogin: function() {
            if ( this.username == '' ) {
                const errMsg = 'Please enter your username';
                this.triggerNegative(errMsg);
                return;
            }
            if ( this.password == '' ) {
                const errMsg = 'Please enter your password';
                this.triggerNegative(errMsg);
                return;
            }
            this.$axios.post('http://localhost:8080/login',
            {
                username : this.username,
                password : this.password
            },
            {
                headers : {
                    'Access-Control-Allow-Origin': '*',
                    'Content-Type': 'application/json'
                }
            }).then((res) => {
                if(res.data.message == "존재하지 않는 유저 입니다."){
                    const errMsg = "존재하지 않는 유저입니다.";
                    this.triggerNegative(errMsg);
                    return;
                } else if(res.data.status){
                    sessionStorage.clear()
                    sessionStorage.setItem("jwt-auth-token", res.headers["jwt-auth-token"])
                    sessionStorage.setItem("login_user", res.data.data.username)
                    this.$router.push('/main')
                }
            }).catch((e) => {
                console.error(e);
            })
        }
    }
}
</script>
