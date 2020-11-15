<template>
  <q-page class="row q-pa-md q-gutter-x-md">

    <chat-list class="col-2" />

    <div class="col">
      <!-- <template v-if="postingMode">
        <post-editor />
      </template>

      <tmeplete v-else>
        <main-content />
      </tmeplete> -->
      <router-view />
    </div>
    
    <post-list class="col-2" />
    
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
  }
}
</script>
