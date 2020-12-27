<template>
  <q-page class="column justify-center">
    <div class="col-auto row justify-center">
      <div class="col-2 q-gutter-y-md">
        <div class="text-center">
          <span class="text-h3 text-weight-bold">PoChat</span>
        </div>
        <q-card>
          <q-form>

              <div>"This is SignUp Page."</div>

            <q-card-section class="q-gutter-y-md">
                <img  id="thumbnailImg" :src="filePath">
                <q-file label="프로필 이미지" v-model="file" accept=".jpg, image/*" @input="profileChange">
                    <template v-slot:append>
                        <q-icon v-if="file" name="close" @click.self="profileRemove" class="cursor-pointer" />
                    </template>
                </q-file>
                <div class="flex justify-end q-gutter-x-sm">
                    <q-btn label="프로필 업로드" @click="profileUpload"/>
                </div>
            </q-card-section>

            <q-card-section>
              <q-input label="username" v-model.trim="username"/>
              <q-input label="password" type="password" v-model.trim="password"/>
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
            file: null,
            filePath: '',
            profile: null
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
        triggerWarning : function(messages) {
            this.$q.notify({
            type: 'warning',
            message: messages
            })
        },
        signup: function() {
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
            if( this.file != null && this.profile == null){
                const warnMsg = '프로필 업로드 버튼을 눌러주세요.'
                this.triggerWarning(warnMsg);
                return;
            }
            this.$axios.post(
                'http://localhost:8080/signup',
                {
                    username: this.username,
                    password: this.password,
                    thumbnail: this.profile
                },
                {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'Content-Type': 'application/json'
                    }
                }
            ).then((res) => {
                console.log(res.data);
                if(res.data.message == "이미 존재하는 유저"){
                    const errMsg = '이미 존재하는 username입니다.';
                    this.triggerNegative(errMsg);
                    return;
                } else if(res.status){
                    const Msg = '회원가입에 성공하였습니다!';
                    this.triggerPositive(Msg);
                    this.$router.push('/')
                }
            }).catch((e) => {
                console.error(e);
            });
        },
        tryLogin: function() {
            this.$router.push('/')
        },
        profileChange: function(e){
        },
        profileUpload: function() {
            var frm = new FormData();
            frm.append("file", this.file)
            this.$axios.post(
                'http://localhost:8080/thumbnail',
                frm,
                {
                    headers: {
                        'Access-Control-Allow-Origin': '*',
                        'Content-Type': 'multipart/form-data'
                    }
                }
            ).then((res) => {
                this.profile = res.data
                const messages = "프로필이 업로드되었습니다!"
                this.triggerPositive(messages)
                console.log(res.data)
            }).catch((e) => {
                console.error(e);
            })
        },
        profileRemove: function(){
            this.file = null
        }
    }
}
</script>
