<template>
  <div class="row justify-center">
    <div class='q-pa-md'>
      <span class="text-h6">채팅상대</span>
    </div>

    <q-separator />
    <!-- Y 오버플로우 확인안함 -->
    <div class="q-pa-md" style="width: 100%;">
      <q-chat-message
        name="아이유"
        avatar="https://cdnimg.melon.co.kr/cm2/album/images/103/46/650/10346650_1000.jpg?14a08050b8c6adc879b6e0cf587d456a/melon/quality/80/optimize"
        :text="['unlucky', '그 사람', 'Blueming', '시간의 바깥', '자장가', 'Love Poem']"
      />
      <q-chat-message
        name="나"
        avatar="http://images.battlecomics.co.kr/users/274806/page/item/f5356aee-25e4-4e25-8601-2cc926f871f4.jpg"
        :text="['이건 함정이야!']"
        sent
      />
      <q-chat-message
        name="아이유"
        avatar="https://cdnimg.melon.co.kr/cm2/album/images/103/46/650/10346650_1000.jpg?14a08050b8c6adc879b6e0cf587d456a/melon/quality/80/optimize"
      >
        <q-spinner-dots size="2rem" />
      </q-chat-message>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ChatContent'
}
</script>

<style lang="scss">

</style>
