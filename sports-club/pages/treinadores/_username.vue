<!-- eslint-disable -->
<template>
  <v-app id="inspier">
    <v-card>
      <v-card-title style="padding-bottom: 0px" class="headline"> Detalhes do Treinador </v-card-title>
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
          no-data-text='Não existem modalidades'>
          <template v-slot:top>
            <v-toolbar flat color='white'>
              <v-toolbar-title> Modalidades Que Treina: </v-toolbar-title>
              <v-spacer></v-spacer>
              <v-dialog v-model='dialog' max-width='500px'>
                <template v-slot:activator='{ on }'>
                  <v-btn color='primary' dark class='mb-2' v-on='on' @click="componentKey+=1"> Nova Modalidade Para Treinar </v-btn>
                </template>
                <inscrever-treinador @close="close" :treinador="treinador" :key="componentKey"></inscrever-treinador>
              </v-dialog>
            </v-toolbar>
          </template>
          <template v-slot:item.actions="{ item }">
            <v-icon small @click="deleteItem(item)"> {{ icons.mdiDelete }} </v-icon>
            <v-dialog v-model='dialogDetalhes'>
              <template v-slot:activator='{ on }'>
                <v-btn color='primary' dark class='mb-2' v-on='on' @click="detalhesKey+=1; show(item)"> DETALHES </v-btn>
              </template>
              <detalhes @close="close" :nome="nome" :escalao="escalao" :key="detalhesKey"></detalhes>
            </v-dialog>
          </template>
        </v-data-table>
      </v-card-text>
      <v-footer color = "white" style="padding-top: 0px;">
        <v-btn color='primary' dark @click='back'> Voltar </v-btn>
      </v-footer>
    </v-card>
  </v-app>
</template>

<script>
/* eslint-disable */
import { mdiDelete } from '@mdi/js'
import IncreverTreinador from './inscrever'
import Detalhes from './detalhesModalidade'

export default {
  components: {
    'inscrever-treinador': IncreverTreinador,
    'detalhes': Detalhes
  },
  data() {
    return {
      loading: true,
      dialog: false,
      dialogDetalhes: false,
      nome:'',
      escalao:'',
      componentKey: 0,
      detalhesKey: 0,
      icons: { mdiDelete },
      treinador: {},
      headers: [
        { text: 'Nome', value: 'nome', align: 'center', sortable: false },
        { text: 'Escalão', value: 'escalao', align: 'center', sortable: false },
        { text: 'Ações', value: 'actions', align: 'center', sortable: false }
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
    dialogDetalhes (val) {
      val || this.close();
    },
  },
  methods: {
    initialize() {
      this.$axios.$get(`/api/treinadores/${this.username}`).then(treinador => (this.treinador = treinador || {}));
      this.getModalidades();
    },
    close() {
      this.dialogDetalhes = this.dialog = false;
      this.loading = true;
      this.getModalidades();
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      }, 300)
    },
    back () {
      this.$router.push('/treinadores')
    },
    getModalidades(){
      this.$axios.$get(`/api/treinadores/${this.username}/modalidades`).then(modalidades => (this.modalidades = modalidades || {}));
      this.loading = false;
    },
    deleteItem (item) {
      confirm(`Tem a certeza que pertende desinscrever esta modalidade: ${item.nome} ${item.escalao}?`) &&
      this.$axios.$delete(`/api/treinadores/${this.username}/modalidades/${item.nome}&&${item.escalao}`, {}).then(() => {
        this.loading = true;
        this.initialize();
      })
    },
    show(item){
      this.nome = item.nome;
      this.escalao = item.escalao;
    },
  }
}
</script>

<style>

</style>