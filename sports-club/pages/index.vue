<!-- eslint-disable -->
<template>
  <v-app>
    <v-container fluid>
      <v-toolbar flat>
        <v-toolbar-title class="headline"> SPORTS CLUB </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn color='black' dark class='mb-2' @click="logout()"> LOGOUT </v-btn>
      </v-toolbar>
      <v-row align="center" justify="center" dense>
          <v-spacer></v-spacer>
          <v-btn color='primary' dark class='mb-2' @click="admins()"> Administradores </v-btn>
          <v-spacer></v-spacer>
          <v-btn color='primary' dark class='mb-2' @click="modalidades()"> Modalidades </v-btn>
          <v-spacer></v-spacer>
          <v-btn color='primary' dark class='mb-2' @click="socios()"> Sócios </v-btn>
          <v-spacer></v-spacer>
          <v-btn color='primary' dark class='mb-2' @click="atletas()"> Atletas </v-btn>
          <v-spacer></v-spacer>
          <v-btn color='primary' dark class='mb-2' @click="treinadores()"> Treinadores </v-btn>
          <v-spacer></v-spacer>
          <v-btn color='primary' dark class='mb-2' @click="produtos()"> Produtos </v-btn>
          <v-spacer></v-spacer>
          <v-btn color='primary' dark class='mb-2' @click="pagamentos()"> Pagamentos </v-btn>
          <v-spacer></v-spacer>
      </v-row>
      <v-data-table
        :loading='loading'
        loading-text='A carregar utilizadores... Aguarde um momento'
        item-key='username'
        expand-icon
        :headers='headers'
        :items='users'
        class='elevation-1'
        no-data-text='Não existem utilizadores'
        :search="search"
      >
      <template v-slot:top>
          <v-toolbar flat color='white'>
            <v-toolbar-title> Utilizadores </v-toolbar-title>
            <v-divider class='mx-4' inset vertical></v-divider>
            <v-text-field
              v-model="search"
              label="Procurar por Username ou Email"
              hide-details
              outlined
              dense
            ></v-text-field>
          </v-toolbar>
        </template>
      </v-data-table>
    </v-container>
  </v-app>
</template>

<script>
/* eslint-disable */
export default {
  data () {
    return {
      loading: true,
      search: '',
      headers: [
        { text: 'Username', value: 'username', align: 'center', sortable: false },
        { text: 'Nome', value: 'nome', align: 'center', sortable: false, filterable: false },
        { text: 'Email', value: 'email', align: 'center', sortable: false }
      ],
      users: []
    }
  },
  created () {
    this.$axios.$get('/api/users').then((users) => {
        this.users = users
        this.loading = false
      })
  },
  methods: {
    admins(){
      this.$router.push('/administradores')
    },
    modalidades(){
      this.$router.push('/modalidades')
    },
    socios(){
      this.$router.push('/socios')
    },
    atletas(){
      this.$router.push('/atletas')
    },
    treinadores(){
      this.$router.push('/treinadores')
    },
    produtos(){
      this.$router.push('/produtos')
    },
    pagamentos(){
      this.$router.push('/pagamentos')
    },
    async logout() {
      let promise = await this.$auth.logout('local');
      this.$toast.success("Obrigado por utilizar a nossa plataforma.");
      this.$router.push('/auth/login')
    }
  }
}
</script>
