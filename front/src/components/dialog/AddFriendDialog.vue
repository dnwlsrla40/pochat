<template>
    <q-dialog v-model="show" persistent>
        <q-card style="width : 451px;">

            <q-card-section class="text-h5">
                채팅 멤버 추가
            </q-card-section>
            <q-card-section>
                <q-expansion-item label="현재 멤버" expand-separator>
                    <q-list class="scroll">
                    <q-item clickable v-ripple v-for="(item, index) in chatRoomMemberList" :key="index">
                        <q-item-section avatar>
                        <q-avatar>
                            <img :src="item.imgSource"> 
                        </q-avatar>
                        </q-item-section>
                        <q-item-section>{{item.chatRoomMemberName}}</q-item-section>
                    </q-item>
                    </q-list>
                </q-expansion-item>
            </q-card-section>
            <!-- <q-scroll-area style="height : 280px;">
                <q-list class="scroll">
                    <q-item clickable v-ripple v-for="(item, index) in friendList" :key="index">
                    <q-item-section avatar>
                        <q-avatar>
                        <img :src="item.imgSource"> 
                        </q-avatar>
                    </q-item-section>
                    <q-item-section>{{item.friendName}}</q-item-section>
                    <q-item-section class="items-end">
                        <q-checkbox :val="item.friendName" v-model="select" />
                    </q-item-section>
                    </q-item>
                </q-list>
            </q-scroll-area> -->

            <div class="q-gutter-x-xs">
                <q-chip :label="item" removable v-for="item in select" :key="item.chatroom_id" @remove="onRemove(item)" />
            </div>
      
            <q-card-actions class="justify-end">
                <q-btn color="primary" label="추가" @click="onSubmit" />
                <q-btn label="취소" @click="onCancel" />
            </q-card-actions>
        </q-card>
    </q-dialog>
</template>

<script>

export default {
    props : [ 'show' ],
    data () {
        return {
            select : [],
            // friendList: [],
            chatRoomMemberList: [],
        }
    },
    computed : {
        token : function () {
            return sessionStorage.getItem("jwt-auth-token");
        },
        login_user : function () {
            return sessionStorage.getItem("login_user");
        },
        chatId : function () {
            return this.$route.params.chatId;
        }
    },
    mounted(){
        if(this.token != null && this.token.length > 0){
            // this.getNewFriendList();
            this.getChatRoomMemberList();
        } else {
            this.$router.push('/login');
        }
    },
    methods : {
        triggerPositive : function (messages) {
            this.$q.notify({
                type: 'positive',
                message: messages
            })
        },
        onRemove : function (val) {
            // 보여주기식 구현이라 그대로 따라하면 안될 가능성이 높음
            this.select.pop(val);
        },
        onSubmit : function () {
            console.log("newMember: " + JSON.stringify(this.select));
            this.$q.loading.show()
            this.$axios.post('http://localhost:8080/chatroommember/add',JSON.stringify(
            {
                id : this.chatId,
                newMember : this.select
            }),
            {
                headers:{
                    "jwt-auth-token": this.token,
                    'Content-Type': 'application/json'
                }
            })
            .then((res) => {
                console.log(res)
                // console.log(res.data.data)
                // res.data.data.chatroom_id = res.data.data.id
                // this.$emit('submit', res.data.data)
                // this.$q.loading.hide()
            }).catch((e) => {
                console.error(e)
                // this.$q.loading.hide()
            })
        },
        onCancel : function () {
            this.$emit('cancel')
        },
        // getNewFriendList: function(){
        //     this.$axios.get('http://localhost:8080/friend/accept/'+this.chatId,{
        //         headers:{
        //             "jwt-auth-token": this.token
        //         },
        //     })
        //     .then((res) => {
        //         console.log(res.data.data);
        //         if(res.data.status){
        //             for(var i=0; i<res.data.data.length; i++){
        //                 if(this.login_user == res.data.data[i].sender.username){
        //                     this.friendList.push(
        //                         {
        //                             imgSource: "http://localhost:8080/img" + res.data.data[i].recipient.thumbnail,
        //                             friendName: res.data.data[i].recipient.username
        //                         }
        //                     )
        //                 } else if(this.login_user == res.data.data[i].recipient.username){
        //                     this.friendList.push(
        //                         {
        //                             imgSource: "http://localhost:8080/img" + res.data.data[i].sender.thumbnail,
        //                             friendName: res.data.data[i].sender.username
        //                         }
        //                     )
        //                 }
        //             }
        //         }
        //     }).catch((e) => {
        //         console.error(e);
        //     })
        // },
        getChatRoomMemberList : function() {
            this.$axios.get('http://localhost:8080/chatroommember/'+this.chatId,{
                headers:{
                    "jwt-auth-token": this.token
                },
            })
            .then((res) => {
                console.log(res.data);
                if(res.data.status){
                    for(var i=0; i<res.data.data.length; i++){
                        this.chatRoomMemberList.push(
                            {
                                imgSource: "http://localhost:8080/img" + res.data.data[i].thumbnail,
                                chatRoomMemberName: res.data.data[i].username
                            }
                        )
                    }
                }
            }).catch((e) => {
                console.error(e);
            })
        }
    }
}
</script>