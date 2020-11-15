<template>
  <q-card>

    <q-card-section>
      <div class="flex justify-center q-gutter-x-sm">
        <span class="text-h6">포스트 목록</span>
        <q-btn label="추가" to="/main/post" />
        <q-btn label="즐겨찾기" />
      </div>
    </q-card-section>

    <q-separator />
    <q-card-section class="no-padding">

      <!-- 오버플로우 Y 넘침 -->
      <q-list>

        <!-- 즐겨찾기 -->
        <q-item clickable v-ripple v-for="(number) in 5" :key="number">
          <q-item-section>
            <q-item-label>즐겨찾기 된 포스팅</q-item-label>
            <q-item-label caption lines="2">Secondary line text. Lorem ipsum dolor sit amet, consectetur adipiscit elit.</q-item-label>
          </q-item-section>

          <q-item-section side top>
            <q-item-label caption>5 min ago</q-item-label>
            <q-icon name="star" color="yellow" />
          </q-item-section>
        </q-item>

        <!-- 일반포스팅 -->
        <q-item clickable v-ripple v-for="(number) in 5" :key="number">
          <q-item-section>
            <q-item-label>일반 포스팅</q-item-label>
            <q-item-label caption lines="2">Secondary line text. Lorem ipsum dolor sit amet, consectetur adipiscit elit.</q-item-label>
          </q-item-section>

          <q-item-section side top>
            <q-item-label caption>5 min ago</q-item-label>
          </q-item-section>
        </q-item>
        
      </q-list>
    </q-card-section>
  </q-card>
</template>

<script>
export default {
  name: 'PostList'
}
</script>
