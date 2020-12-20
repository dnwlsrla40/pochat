<template>
  <q-card class="bg-grey-2">

    <q-card-section class="bg-deep-purple-1 flex">
      <div class="text-h6">채팅 목록</div>
      <q-space />
      <q-btn icon="add" flat @click="onNewChat" />
      <new-chat-dialog :show="dialog" @submit="onSubmit" @cancel="onCancel" />
    </q-card-section>

    <q-separator />

    <q-card-section class="no-padding">

      <q-list class="scroll" style="height : calc(100vh - 155px);">
        <q-item clickable v-ripple v-for="(number, index) in 10" :key="index" :active="active === index" @click="active = index;"  active-class="active">
          <q-item-section avatar>
            <q-avatar>
              <img src="https://cdn.entermedia.co.kr/news/photo/202010/21304_40107_5416.jpg"> 
            </q-avatar>
          </q-item-section>
          <q-item-section>테스형</q-item-section>
        </q-item>
      </q-list>
    </q-card-section>
  </q-card>
</template>

<script>
import NewChatDialog from '../dialog/NewChatDialog.vue';
export default {
  name: 'ChatList',
  components : {NewChatDialog},
  data () {
    return {
      active : -1,
      dialog : false
    }
  },
  methods : {
    onNewChat : function () {
      this.dialog = true;
    },
    onSubmit : function (payload) {
      console.log(payload)
      this.dialog = false;
    },
    onCancel : function () {
      this.dialog = false;
    }

  }
}
</script>
