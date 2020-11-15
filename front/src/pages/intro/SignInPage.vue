<template>
  <q-page class="column justify-center" >
    <div class="col-auto row justify-center">
      <div class="col-2 q-gutter-y-md">
        <div class="text-center">
          <span class="text-h3 text-weight-bold">PoChat</span>
        </div>
        <q-card>
          <q-form>
            <q-card-section>
              <q-input label="username" />
              <q-input label="password" type="password" />
            </q-card-section>

            <q-card-section class="flex justify-center q-gutter-x-md">
              <q-btn label="signUp" />
              <q-btn label="login" color="purple" to="main" />
            </q-card-section>
          </q-form>
        </q-card>
      </div>
    </div>
  </q-page>
</template>

<script>
export default {
  name: 'SignInPage'
}
</script>
