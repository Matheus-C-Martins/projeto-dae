<template>
  <form @submit.prevent="remover">
    <p>Username: {{ administrador.username }}</p>
    <h3>Tem a certeza que quer remover este administrador?</h3>
    <button>
      <nuxt-link to="/administradores">Voltar</nuxt-link>
    </button>
    <button @click.prevent="remover">Remover</button>
  </form>
</template>
<script>
export default {
  data () {
    return {
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
    remover () {
      this.$axios
        .$delete(`/api/administradores/${this.username}`, {
        })
        .then(() => {
          this.$router.push('/administradores')
        })
    }
  }
}
</script>
