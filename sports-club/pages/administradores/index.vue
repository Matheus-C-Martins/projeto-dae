<template>
  <b-container>
    <b-table :items="administradores" :fields="fields" striped over>
        <template v-slot:cell(editar)="row">
        <button>
          <nuxt-link class="btn-link" :to="`/administradores/${row.item.username}/editar`">Editar</nuxt-link>
        </button>
      </template>
      <template v-slot:cell(remover)="row">
        <button>
          <nuxt-link class="btn-link" :to="`/administradores/${row.item.username}/remover`">Remover</nuxt-link>
        </button>
      </template>
    </b-table>
    <nuxt-link to="/administradores/criar">Criar novo administrador</nuxt-link>
  </b-container>
</template>
<script>
export default {
  data () {
    return {
      fields: ['username', 'nome', 'email', 'editar', 'remover'],
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
