<template>
  <form @submit.prevent="edit">
    <p>Username: {{ administrador.username }}</p>
    <div>
      Nome:
      <input v-model="administrador.nome" type="text"/>
    </div>
    <div>
      Password:
      <input v-model="password" type="password"/>
    </div>
    <div>
      Email:
      <input v-model="administrador.email" type="email"/>
    </div>
    <br>
    <button>
      <nuxt-link to="/administradores">Return</nuxt-link>
    </button>
    <button @click.prevent="edit">EDIT</button>
  </form>
</template>
<script>
export default {
  data () {
    return {
      password: '',
      administrador: {}
    }
  },
  computed: {
    username () {
      return this.$route.params.username
    }
  },
  created () {
    this.$axios.$get(`/api/administradores/${this.username}`)
      .then(administrador => (this.administrador = administrador || {}))
  },
  methods: {
    edit () {
      if (this.password !== '') {
        console.log(this.password)
        this.$axios.$put(`/api/administradores/${this.username}`, {
          password: this.password,
          nome: this.administrador.nome,
          email: this.administrador.email
        })
          .then(() => {
            this.$router.push('/administradores')
          })
      } else {
        this.$axios.$put(`/api/administradores/${this.username}`, {
          password: null,
          nome: this.administrador.nome,
          email: this.administrador.email
        })
          .then(() => {
            this.$router.push('/administradores')
          })
      }
    }
  }
}
</script>
