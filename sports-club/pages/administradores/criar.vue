<template>
  <form @submit.prevent="create">
    <div>
      Username:
      <input v-model="username" type="text" />
    </div>
    <div>
      Nome:
      <input v-model="nome" type="text" />
    </div>
    <div>
      Password:
      <input v-model="password" type="password" />
    </div>
    <div>
      Email:
      <input v-model="email" type="email" />
    </div>
    <nuxt-link to="/administradores"> Return </nuxt-link>
    <button type="reset"> RESET </button>
    <button @click.prevent="create"> CRIAR </button>
  </form>
</template>

<script>
export default {
  data () {
    return {
      username: null,
      password: null,
      nome: null,
      email: null
    }
  },
  methods: {
    create () {
      this.$axios
        .$post('/api/administradores', {
          username: this.username,
          password: this.password,
          nome: this.nome,
          email: this.email
        })
        .then(() => {
          this.$router.push('/administradores')
        })
    }
  }
}
</script>
