<!-- eslint-disable -->
<template>
  <v-app>
    <b-container>
      <nuxt-link to="/administradores">Administradores</nuxt-link>
      <br />
      <nuxt-link to="/modalidades">Modalidades</nuxt-link>
      <br />
      <nuxt-link to="/socios">Sócios</nuxt-link>
      <br />
      <nuxt-link to="/atletas">Atletas</nuxt-link>
      <br />
      <nuxt-link to="/treinadores">Treinadores</nuxt-link>
      <br />
      <nuxt-link to="/produtos">Produtos</nuxt-link>
      <br />
      <v-btn color='primary' dark class='mb-2' @click="logout()"> LOGOUT </v-btn>
    </b-container>
  </v-app>
</template>

<script>
/* eslint-disable */
export default {
  methods: {
    async logout() {
      let promise = await this.$auth.logout('local');
      this.$toast.success("Obrigado por utilizar a nossa plataforma.");
      this.$router.push('/auth/login')
    }
  }
}
</script>
