<template>
  <b-container>
    <b-table :items="administradores" :fields="fields" striped over />
  </b-container>
</template>
<script>
export default {
  data () {
    return {
      fields: ['username', 'nome', 'email'],
      administradores: []
    }
  },
  created () {
    this.$axios.$get('/api/administradores').then((administradores) => {
      this.administradores = administradores
    })
  }
}
</script>
<style>
</style>
