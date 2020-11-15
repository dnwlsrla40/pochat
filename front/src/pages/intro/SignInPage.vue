<template>
  <q-page class="column justify-center">
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
              <q-btn label="signUp" color="purple-3" />
              <q-btn label="login" color="primary" to="main" />
            </q-card-section>

            <!-- 지워 -->
            <div class="text-center">컬러 맘에 안들면 바꿔줄게</div>

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
