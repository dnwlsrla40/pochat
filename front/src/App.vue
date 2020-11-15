<template>
  <div id="q-app" @resize="test">
    <router-view />
  </div>
</template>
<script>
export default {
  name: 'App',
  methods : {
    test () {
      console.log('hello')
    }
  }
}
</script>
