<template>
  <q-card class="bg-grey-2">

    <q-card-section class="bg-deep-purple-1">
      <span class="text-h6">채팅 목록</span>
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
export default {
  name: 'ChatList',
  data () {
    return {
      active : -1
    }
  }
}
</script>
