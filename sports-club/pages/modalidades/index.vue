<template>
  <b-container>
    <b-table :items="modalidades" :fields="fields" striped over />
  </b-container>
</template>
<script>
export default {
  data () {
    return {
      fields: ['nome'],
      modalidades: []
    }
  },
  created () {
    this.$axios.$get('/api/modalidades').then((modalidades) => {
      this.modalidades = modalidades
    })
  }
}
</script>
<style>
</style>
