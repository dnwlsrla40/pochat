<template>
  <q-card class="bg-grey-2">

    <q-card-section class="bg-deep-purple-1">
      <div class="flex justify-between items-center">
        <span class="text-h6">포스트 목록</span>
        <div class="q-gutter-x-sm">
          <q-btn size="sm" color="primary" label="추가" to="/main/post" />
          <q-btn size="sm" color="primary" label="즐겨찾기" />
        </div>
      </div>
    </q-card-section>

    <q-separator />

    <q-card-section class="no-padding" >

      <q-list class="scroll" style="height : calc(100vh - 155px);">

        <q-item clickable v-ripple v-for="(number, index) in 10" :key="`post-${index}`" :active="active === index" @click="active = index;"  active-class="active">
          <q-item-section>
            <q-item-label>포스트</q-item-label>
            <q-item-label caption lines="2">Secondary line text. Lorem ipsum dolor sit amet, consectetur adipiscit elit.</q-item-label>
          </q-item-section>

          <q-item-section side top>
            <q-item-label caption>5 min ago</q-item-label>
            <q-icon v-if="index % 2 == 0" name="star" color="yellow" />
          </q-item-section>
        </q-item>
        
      </q-list>
    </q-card-section>
  </q-card>
</template>

<script>
export default {
  name: 'PostList',
  data () {
    return {
      active : -1
    }
  }
}
</script>
