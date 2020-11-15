<template>
  <q-card class="overflow-hidden full-height">
    <q-card-section class="no-padding full-height">
      <div class="column justify-center full-height">

        <div class="col">
          <q-splitter v-model="splitter" separator-class="bg-purple-3" separator-style="width: 2px" :limits="[0, 100]">
            <template v-slot:before>
              <chat-content  />
            </template>
            
            <template v-slot:after>
              <post-content />
            </template>
          </q-splitter>
        </div>

        <div class="col-auto row q-gutter-x-md q-pa-md bg-grey-1">
          <q-input class="col" />
          <q-btn class="col-auto" color="primary" label="보내기" />
        </div>
      </div>
    </q-card-section>
  </q-card>
</template>

<script>
import ChatContent from './ChatContent.vue'
import PostContent from './PostContent.vue'
export default {
  components: { ChatContent, PostContent },
  name: 'MainContent',
  data () {
    return {
      splitter : 32
    }
  },

  mounted : function () {

  }
}
</script>

<style lang="scss">
.q-splitter__panel {
  overflow-x : hidden;
}
</style>
