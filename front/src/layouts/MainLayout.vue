<template>
  <q-layout>

    <q-header elevated>
      <q-toolbar>
        <q-toolbar-title>
          <span>PoChat</span>
        </q-toolbar-title>

        <q-space  />

        <div class="q-gutter-x-md">
          <q-btn label="MYPAGE" />
          <q-btn label="LOGOUT" to="/" />
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
  name: 'MainLayout'
}
</script>
