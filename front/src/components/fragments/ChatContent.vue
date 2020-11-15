<template>
  <q-card class="bg-purple-1 full-height" flat square>
    <q-card-section class="bg-deep-purple-1" >
      <span class="text-h6 text-overflow-hidden">채팅상대</span>
    </q-card-section>

    <q-separator />
    <q-card-section class="scroll" style="height : calc(100vh - 236px); min-width : 320px;">
      <q-chat-message
        name="아이유"
        bg-color="white"
        avatar="https://cdnimg.melon.co.kr/cm2/album/images/103/46/650/10346650_1000.jpg?14a08050b8c6adc879b6e0cf587d456a/melon/quality/80/optimize"
        :text="['unlucky', '그 사람', 'Blueming', '시간의 바깥', '자장가', 'Love Poemdddddddddddddddddddddddddddddddddddddddddd']"
      />
      <q-chat-message
        name="나"
        bg-color="yellow-13"
        avatar="http://images.battlecomics.co.kr/users/274806/page/item/f5356aee-25e4-4e25-8601-2cc926f871f4.jpg"
        :text="['이건 함정이야!']"
        sent
      />
      <q-chat-message
        name="아이유"
        bg-color="white"
        avatar="https://cdnimg.melon.co.kr/cm2/album/images/103/46/650/10346650_1000.jpg?14a08050b8c6adc879b6e0cf587d456a/melon/quality/80/optimize"
      >
        <q-spinner-dots size="2rem" />
      </q-chat-message>
    </q-card-section>
  </q-card>
</template>

<script>
export default {
  name: 'ChatContent'
}
</script>

<style lang="scss">
.q-message-container > div {

  & > div {
    float: left;
    clear: left;
    width : auto;
  }

  & > div:last-child {
    min-height: 0px;
  }
}
</style>
