<template>
  <q-dialog v-model="show" persistent>
    <q-card style="width : 551px;">
      <q-card-section class="text-h5">
        채팅방 생성
      </q-card-section>
      <q-card-section class="q-gutter-y-md">
        <q-input label="채팅방 이름" />

        <q-scroll-area style="height : 280px;">
          <q-list class="scroll">
            <q-item clickable v-ripple v-for="(number, index) in 10" :key="index">
              <q-item-section avatar>
                <q-avatar>
                  <img src="https://cdn.entermedia.co.kr/news/photo/202010/21304_40107_5416.jpg"> 
                </q-avatar>
              </q-item-section>
              <q-item-section>테스형</q-item-section>
              <q-item-section class="items-end">
                <q-checkbox :val="number" v-model="select" />
              </q-item-section>
            </q-item>
          </q-list>
        </q-scroll-area>

        <div class="q-gutter-x-xs">
          <q-chip label="홍길동" removable v-for="item in select" :key="item" @remove="onRemove(item)" />
        </div>
      </q-card-section>
      
      <q-card-actions class="justify-end">
        <q-btn color="primary" label="만들기" @click="onSubmit" />
        <q-btn label="취소" @click="onCancel" />
      </q-card-actions>

    </q-card>
  </q-dialog>
</template>

<script>
export default {
  name : "NewChatDialog",
  props : [ 'show' ],
  data () {
    return {
      select : []
    }
  },
  methods : {
    onRemove : function (val) {
      // 보여주기식 구현이라 그대로 따라하면 안될 가능성이 높음
      this.select.pop(val);
    },
    onSubmit : function () {
      this.$emit('submit', {some : 'data'})
    },
    onCancel : function () {
      this.$emit('cancel')
    }

  }
}
</script>