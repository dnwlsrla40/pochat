<template>
  <q-page class="row q-pa-md q-gutter-x-md bg-grey-1">

    <chat-list  ref="chatlist" class="col-2" style="min-width : 317px !important;" />

    <div class="col" style="min-width : 720px !important;">
      <router-view @chatRoomUpdated="onChatRoomUpdated" @postUpdated="onPostUpdated" />
    </div>
    
    <post-list ref="postlist" class="col-2" style="min-width : 317px !important;" />
    
  </q-page>
</template>

<script>
import ChatList from '../../components/fragments/ChatList.vue'
import MainContent from '../../components/fragments/MainContent.vue'
import PostEditor from '../../components/fragments/PostEditor.vue'
import PostList from '../../components/fragments/PostList.vue'
export default {
  components: { MainContent, ChatList, PostEditor, PostList },
  name: 'MainPage',
  data () {
    return {
      postingMode : true
    }
  },
  methods : {
    onPostUpdated : function () {
      console.log("alsdjflasdjflasdlfkalsdjfladlkjlasfdjlkj")
      this.$refs.postlist.connect();
    },
    onChatRoomUpdated : function () {
      this.$refs.chatlist.connect();
    }
  }
}
</script>
