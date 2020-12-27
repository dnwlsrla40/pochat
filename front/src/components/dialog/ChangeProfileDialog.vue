<template>
    <q-dialog v-model="show" persistent>
        <q-card style="width : 451px;">
            <q-card-section class="q-gutter-y-md">
                <img  id="thumbnailImg" :src="filePath">

                <q-file label="프로필 이미지" v-model="file" accept=".jpg, image/*" @input="profileChange">
                    <template v-slot:append>
                        <q-icon v-if="file" name="send" @click.self="profileUpload" class="cursor-pointer" />
                        <q-icon v-if="file" name="close" @click.self="profileRemove" class="cursor-pointer" />
                        <q-icon v-else name="attachment" @click.stop  class="cursor-pointer" />
                    </template>
                </q-file>
                <!-- <div class="flex justify-end q-gutter-x-sm">
                    <q-btn label="프로필 업로드" @click="profileUpload"/>
                </div> -->
                <div class="flex justify-end q-gutter-x-sm">
                    <q-btn label="확인" @click="onSubmit" />
                    <q-btn label="취소" @click="onCancel" />
                </div>
            </q-card-section>
        </q-card>
    </q-dialog>
</template>

<script>
export default {
    props : [ 'show' ],
    data () {
        return {
            file : null,
            filePath : ''
        }
    },
    computed : {
        token : function () {
            return sessionStorage.getItem("jwt-auth-token");
        },
    },
    methods : {
        triggerPositive : function (messages) {
            this.$q.notify({
                type: 'positive',
                message: messages
            })
        },
        triggerWarning : function(messages) {
            this.$q.notify({
            type: 'warning',
            message: messages
            })
        },
        imageChange: function(e){
            
        },
        profileChange: function(e){
        },
        onSubmit : function() {
            if( this.file != null && this.profile == null){
                const warnMsg = '프로필 업로드 버튼을 눌러주세요.'
                this.triggerWarning(warnMsg);
                return;
            }
            this.$axios.post(
                'http://localhost:8080/thumbnail/update',
                {
                    thumbnail: this.profile
                },
                {
                    headers: {
                        "jwt-auth-token": this.token,
                        'Access-Control-Allow-Origin': '*',
                        'Content-Type': 'application/json'
                    }
                }
            ).then((res) => {
                console.log(res.data);
                if(res.data.status){
                    const messages = "프로필 변경이 완료되었습니다!"
                    this.triggerPositive(messages)
                    this.$emit('submit', res.data.data.thumbnail)
                }
            }).catch((e) => {
                console.error(e);
            });
        },
        onCancel : function () {
            this.$emit('cancel')
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