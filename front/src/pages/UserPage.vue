<template>
<q-page class="row q-pa-md bg-grey-1 justify-center">

  <q-card class="col-12 col-lg-6 col-md-8" >

    <q-card-section class="row">
      <q-card-section class="col flex justify-end">
        <img class="profile-img" :src="imgSource" />
      </q-card-section>

      <q-card-section class="col column justify-end">
        <div class="col-auto text-h3">아이유</div>
        <div class="col-auto text-h4">2020-12-20</div>
      </q-card-section>
    </q-card-section>

    <q-card-section>
      <q-expansion-item label="친구목록" expand-separator>
        <q-list class="scroll">
          <q-item clickable v-ripple v-for="(number, index) in 10" :key="index">
            <q-item-section avatar>
              <q-avatar>
                <img src="https://cdn.entermedia.co.kr/news/photo/202010/21304_40107_5416.jpg"> 
              </q-avatar>
            </q-item-section>
            <q-item-section>테스형</q-item-section>
          </q-item>
        </q-list>
      </q-expansion-item>
      <q-expansion-item label="보낸 친구신청" expand-separator>
        <q-list class="scroll">
          <q-item clickable v-ripple v-for="(number, index) in 10" :key="index">
            <q-item-section avatar>
              <q-avatar>
                <img src="https://cdn.entermedia.co.kr/news/photo/202010/21304_40107_5416.jpg"> 
              </q-avatar>
            </q-item-section>
            <q-item-section>테스형</q-item-section>
            <q-item-section class="items-end">
              <q-btn label="취소" flat />
            </q-item-section>
          </q-item>
        </q-list>
      </q-expansion-item>
      <q-expansion-item label="받은 친구신청" expand-separator>
        <q-list class="scroll">
          <q-item clickable v-ripple v-for="(number, index) in 10" :key="index">
            <q-item-section avatar>
              <q-avatar>
                <img src="https://cdn.entermedia.co.kr/news/photo/202010/21304_40107_5416.jpg"> 
              </q-avatar>
            </q-item-section>
            <q-item-section>테스형</q-item-section>
            <q-item-section class="items-end">
              <q-btn label="승인" color="primary" flat />
            </q-item-section>
          </q-item>
        </q-list>
      </q-expansion-item>

    </q-card-section>
    
  </q-card>
</q-page>
</template>

<script>
export default {
  name: 'UserPage',
  data () {
    return {
      imgSource : "https://cdnimg.melon.co.kr/cm2/album/images/103/46/650/10346650_1000.jpg?14a08050b8c6adc879b6e0cf587d456a/melon/quality/80/optimize"
    }
  },

  mounted : function () {

  }
}
</script>

<style lang="scss">
.profile-img {
  max-width : 250px;
  max-height : 250px;
  border-radius: 50%;
}
.profile-label-name {
  font-size : 56px;
}
.profile-label-registered {
  font-size : 36px;
}
</style>
