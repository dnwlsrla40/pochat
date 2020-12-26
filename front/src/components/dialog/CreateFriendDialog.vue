<template>
    <q-dialog v-model="show" persistent>
        <q-card style="width : 551px;">

            <q-card-section class="text-h5">
                친구추가
            </q-card-section>
            <q-card-section class="q-gutter-y-md">
                <q-input label="친구 이름" v-model="friendName" required/>
            </q-card-section>

            <q-card-section class="q-gutter-y-md">
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
            friendName: ''
        }
    },
    computed : {
        token : function () {
            return sessionStorage.getItem("jwt-auth-token");
        }
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
        triggerNegative : function (messages) {
            this.$q.notify({
                type: 'negative',
                message: messages
            })
        },
        onSubmit : function() {
            if( this.friendName == ''){
                const warnMsg = '추가할 친구를 적어주세요.'
                this.triggerWarning(warnMsg);
                return;
            }
            this.$axios.post(
                'http://localhost:8080/friend',
                {
                    recipientName: this.friendName
                },
                {
                    headers: {
                        "jwt-auth-token": this.token,
                    }
                }
            ).then((res) => {
                console.log(res)
                console.log(res.data);
                if(res.data.status){
                    if(res.data.message == "잘못된 요청입니다."){
                        const errMsg = "잘못된 요청입니다.";
                        this.triggerNegative(errMsg);
                        return;
                    } else if(res.data.message == "존재하지 않는 유저 입니다."){
                        const messages = "존재하지 않는 유저입니다."
                        this.triggerNegative(messages)
                        return;
                    } else if(res.data.message == "이미 존재하는 친구"){
                        const messages = "이미 친구입니다."
                        this.triggerPositive(messages)
                        return;
                    }
                    const messages = "친구 요청이 완료되었습니다!"
                    this.triggerPositive(messages)
                    this.$emit('submit', res.data.data)
                }
            }).catch((e) => {
                console.error(e);
            });
        },
        onCancel : function () {
            this.$emit('cancel')
        }
    }
}
</script>