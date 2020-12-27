<template>
  <q-layout class="window-height">

    <q-header elevated>
      <q-toolbar class="bg-purple-4 q-px-lg">
        <q-toolbar-title>
          <span @click="goMain" class="cursor-pointer">PoChat</span>
        </q-toolbar-title>

        <q-space  />

        <div class="q-gutter-x-sm">
          <q-btn label="MYPAGE" color="primary" to="/user"/>
          <q-btn label="LOGOUT" color="primary" to="/" @click="Logout"/>
        </div>
      </q-toolbar>
    </q-header>
    
    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>

export default {
  name: 'MainLayout',
  data () {
    return {
      active : -1,
      dialog : false,
      chatRoomList: []
    }
  },
  methods:{
    Logout : function(){
      sessionStorage.clear()
      this.$router.push('/')
    },
    goMain: function(){
      this.$router.push('/main')
    }
  }
}
</script>
