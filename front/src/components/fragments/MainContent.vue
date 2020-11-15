<template>

  <q-card class="overflow-hidden full-height">
    <q-card-section class="no-padding full-height">
      <div class="column justify-center full-height">

        <div class="col">
          <q-splitter class="full-height" v-model="splitter" style="max-height : 780px;" :limits="[0, 100]">
            <template v-slot:before>
              <chat-content  />
            </template>
            
            <template v-slot:after>
              <post-content />
            </template>
          </q-splitter>
        </div>

        <div class="col-auto row q-pa-md">
          <div class="col">
            <q-input />
          </div>
          <div class="col-auto">
            <q-btn label="ENTER" />
          </div>
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
      splitter : 50
    }
  }
}
</script>
