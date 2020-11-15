<template>
  <q-card class="full-height">
    <q-card-section>
      <span class="text-h5">POST 작성</span>
    </q-card-section>

    <q-card-section>

      <q-input label="제목" />

      <div>
        <q-radio v-model="scope" val="private" label="Private" />
        <q-radio v-model="scope" val="group" label="Group" />
      </div>

      <q-editor v-model="content" />

    </q-card-section>

    <q-card-actions class="q-pa-md flex justify-end">
      <q-btn label="취소" to="/main" />
      <q-btn label="작성하기" to="/main" />
    </q-card-actions>
  </q-card>
</template>

<script>

export default {

  name: 'PostEditor',
  data () {
    return {
      scope : 'private',
      content : ''
    }
  }
}
</script>

<style lang="scss">
.q-editor__content {
  min-height: 20rem !important;
}
</style>
