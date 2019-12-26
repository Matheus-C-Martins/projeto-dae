<!-- eslint-disable -->
<template>
  <v-app id='inspire'>
    <v-card>
      <v-data-table
        :loading='loading'
        loading-text='A carregar modalidades... Aguarde um momento'
        item-key='nome'
        expand-icon
        :headers='headers'
        :items='modalidades'
        class='elevation-1'
        no-data-text='Não existem modalidades'
      >
      <template v-slot:top>
          <v-toolbar flat color='white'>
            <v-toolbar-title> Modalidades </v-toolbar-title>
            <v-divider class='mx-4' inset vertical></v-divider>
          </v-toolbar>
        </template>
      </v-data-table>
      <v-footer color = "white" style="padding-top: 0px;">
        <v-btn color='primary' dark @click='back'> Voltar </v-btn>
      </v-footer>
    </v-card>
  </v-app>
</template>
<script>
/* eslint-disable */
export default {
  data () {
    return {
      loading: true,
      headers: [
        { text: 'Nome', value: 'nome', align: 'center', sortable: false }
      ],
      modalidades: []
    }
  },
  created () {
    this.$axios.$get('/api/modalidades').then((modalidades) => {
      this.modalidades = modalidades
      this.loading = false
    })
  },
  methods: {
    back () {
      this.$router.push('/')
    }
  }
}
</script>
<style>
</style>
