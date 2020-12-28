<template>
  <q-card class="overflow-hidden full-height">
    <q-card-section class="no-padding full-height">
      <div class="column justify-center full-height">

        <div class="col">
          <q-splitter v-model="sppliter" separator-class="bg-purple-3" separator-style="width: 2px" :limits="[0, 100]">
            <template v-slot:before>
              <chat-content @chatRoomUpdated="(payload)=>$emit('chatRoomUpdated',payload)" ref="chatContent" />
            </template>
            
            <template v-slot:after>
              <post-content @postUpdated="$emit('postUpdated');$router.go(-1)" />
            </template>
          </q-splitter>
        </div>

        <div class="col-auto row q-gutter-x-md q-pa-md bg-grey-1">
          <q-input v-model="message" class="col" @keydown.enter="onMessageSend" />
          <q-btn class="col-auto" color="primary" label="보내기" @click="onMessageSend" />
        </div>
      </div>
    </q-card-section>
  </q-card>
</template>

<script>
import ChatContent from './ChatContent.vue'
import PostContent from './PostContent.vue'
export default {
  components: { ChatContent, PostContent },
  name: 'MainContent',
  data () {
    return {
      //sppliter : 50,
      message : ''
    }
  },

  computed : {
    chatId :function () {
      return this.$route.params.chatId
    },
    postId : function () {
      return this.$route.params.postId
    },
    sppliter : function () {
      if ( this.chatId < 0 ) return 0;
      else if ( this.postId < 0 ) return 100;
      else return 50;
    }
  },

  // watch : {
  //   'chatId' : 'showPostOnly',
  //   'postId' : 'showChatOnly'
  // },
  mounted : function () {

  },
  methods : {
    onMessageSend : function () {
      if(this.message != ''){
        this.$refs.chatContent.onMessageSend(this.message)
        this.message = ''
      }
    }
  }
}
</script>

<style lang="scss">
.q-splitter__panel {
  overflow-x : hidden;
}
</style>
