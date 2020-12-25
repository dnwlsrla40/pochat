<template>
  <q-page class="column justify-center">
    <div class="col-auto row justify-center">
      <div class="col-2 q-gutter-y-md">
        <div class="text-center">
          <span class="text-h3 text-weight-bold">PoChat</span>
        </div>
        <q-card>
          <q-form>
            <q-card-section>
              <q-input label="username" v-model.trim="username"/>
              <q-input label="password" type="password" v-model.trim="password"/>
            </q-card-section>

            <q-dialog variant="danger" v-model="showAlert">{{ errMsg }}</q-dialog>

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

const storage = window.sessionStorage;

export default {
  name: 'SignInPage',
   data: function () {
        return {
            username: '',
            password: '',
            showAlert: false,
            errMsg: '',
        }
    },
    methods: {

        signup: function() {
            if ( this.username == '' ) {
                this.showAlert = true;
                this.errMsg = 'Please enter your username';
                return;
            }
            if ( this.password == '' ) {
                this.showAlert = true;
                this.errMsg = 'Please enter the password';
                return;
            }
            this.showAlert = false;
            this.$axios.post(
                'http://localhost:8080/signup',
                {
                    username: this.username,
                    password: this.password
                },
                {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'Content-Type': 'application/json'
                    }
                }
            ).then((res) => {
                console.log(res.data);
            }).catch((e) => {
                console.error(e);
            });
        },
        tryLogin: function() {
            storage.setItem("jwt-auth-token", "");
            storage.setItem("lgoin_user", "");

            if ( this.username == '' ) {
                this.showAlert = true;
                this.errMsg = 'Please enter your username';
                return;
            }
            if ( this.password == '' ) {
                this.showAlert = true;
                this.errMsg = 'Please enter the password';
                return;
            }
            this.showAlert = false;

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
                if(res.data.status){
                    storage.setItem("jwt-auth-token", res.headers["jwt-auth-token"]);
                    storage.setItem("login_user", res.data.data.username);
                    this.$router.push('/main');
                }
            }).catch((e) => {
                console.error(e);
            })
        }
    }
}
</script>
