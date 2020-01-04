<!-- eslint-disable -->
<template>
  <v-app id="inspier">
    <v-card>
      <v-card-title style="padding-bottom: 0px" class="headline"> O Meu Perfil </v-card-title>
      <v-card-text class="pa-0">
        <v-form align="center">
          <v-row dense>
            <v-col>
              <v-card color="#e2e2e2">
                <h6 style=""> Username: </h6> {{treinador.username}}
              </v-card>
            </v-col>
            <v-col>
              <v-card color="#e2e2e2">
                <h6 style=""> Nome: </h6> {{treinador.nome}}
              </v-card>
            </v-col>
          </v-row>
          <v-row dense>
            <v-col>
              <v-card color="#e2e2e2">
                <h6 style=""> Email: </h6> {{treinador.email}}
              </v-card>
            </v-col>
          </v-row>
        </v-form>
        <v-data-table
          :loading='loading'
          loading-text='A carregar modalidades... Aguarde um momento'
          expand-icon
          :headers='headers'
          :items='modalidades'
          class='elevation-1'
          no-data-text='Não treina nehuma modalidade'>
          <template v-slot:top>
            <v-toolbar flat color='white'>
              <v-toolbar-title> Modalidades Que Treina: </v-toolbar-title>
              <v-spacer></v-spacer>
              <v-btn color='primary' dark class='mb-2' @click="logout()"> LOGOUT </v-btn>
            </v-toolbar>
          </template>
          <template v-slot:item.actions="{ item }">
            <v-dialog v-model='dialog'>
              <template v-slot:activator='{ on }'>
                <v-btn color='primary' dark class='mb-2' v-on='on' @click="componentKey+=1; show(item)"> DETALHES </v-btn>
              </template>
              <detalhes @close="close" :nome="nome" :escalao="escalao" :key="componentKey"></detalhes>
            </v-dialog>
          </template>
        </v-data-table>
      </v-card-text>
    </v-card>
  </v-app>
</template>

<script>
/* eslint-disable */
import Detalhes from '../treinadores/detalhesModalidade'

export default {
  components: {
    'detalhes': Detalhes
  },
  data() {
    return {
      loading: true,
      dialog: false,
      componentKey: 0,
      nome:'',
      escalao:'',
      treinador: {},
      headers: [
        { text: 'Nome', value: 'nome', align: 'center', sortable: false },
        { text: 'Escalão', value: 'escalao', align: 'center', sortable: false },
        { text: '', value: 'actions', align: 'center', sortable: false }
      ],
      modalidades: []
    };
  },
  computed: {
    username() {
      return this.$route.params.username;
    },
  },
  created() {
    this.initialize();
  },
  watch: {
    dialog (val) {
      val || this.close();
    },
  },
  methods: {
    initialize() {
      this.$axios.$get(`/api/treinadores/${this.username}`).then(treinador => (this.treinador = treinador || {}));
      this.getModalidades();
    },
    close() {
      this.dialog = false;
    },
    getModalidades() {
      this.$axios.$get(`/api/treinadores/${this.username}/modalidades`).then(modalidades => (this.modalidades = modalidades || {}));
      this.loading = false;
    },
    show(item){
      this.nome = item.nome;
      this.escalao = item.escalao;
    },
    async logout() {
      let promise = await this.$auth.logout('local');
      this.$toast.success("Obrigado por utilizar a nossa plataforma.");
      this.$router.push('/')
    }
  }
}
</script>
