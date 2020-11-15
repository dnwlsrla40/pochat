<template>
  <q-card>
    <q-card-section>
      <span class="text-h6">채팅 목록</span>
    </q-card-section>

    <q-separator />

    <!-- 오버플로우 Y 넘침 -->
    <q-list>
      <q-item clickable v-ripple v-for="(number) in 10" :key="number">
        <q-item-section avatar>
          <q-avatar>
            <img src="https://cdn.entermedia.co.kr/news/photo/202010/21304_40107_5416.jpg"> 
          </q-avatar>
        </q-item-section>
        <q-item-section>테스형</q-item-section>
      </q-item>
    </q-list>
  </q-card>
</template>

<script>
export default {
  name: 'ChatList'
}
</script>
