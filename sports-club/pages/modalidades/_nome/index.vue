<!-- eslint-disable -->
<template>
  <v-app id="inspier">
    <v-card>
      <v-card-text class="pa-0">
        <v-data-table
          :loading='loading'
          loading-text='A carregar escalões... Aguarde um momento'
          expand-icon
          :headers='headers'
          :items='escaloes'
          class='elevation-1'
          no-data-text='Não existem escalões'>
          <template v-slot:top>
            <v-toolbar flat color='white'>
              <v-toolbar-title class="headline"> Escalões da Modalidade {{ nome }}: </v-toolbar-title>
              <v-spacer></v-spacer>
              <v-dialog v-model='dialog' max-width='500px'>
                <template v-slot:activator='{ on }'>
                  <v-btn color='primary' dark class='mb-2' v-on='on' @click="componentKey+=1"> Inserir Novo Escalão </v-btn>
                </template>
                <inserir-escalao @close="close" :nome="nome" :key="componentKey"></inserir-escalao>
              </v-dialog>
            </v-toolbar>
          </template>
          <template v-slot:item.actions="{ item }">
            <v-icon small @click="expandItem(item)"> {{ icons.mdiPlus }} </v-icon>
            <v-icon small @click="deleteItem(item)"> {{ icons.mdiDelete }} </v-icon>
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
import { mdiDelete, mdiPlus } from '@mdi/js'
import InserirEscalao from './inserir'

export default {
  components: {
    'inserir-escalao': InserirEscalao
  },
  data() {
    return {
      loading: true,
      dialog: false,
      componentKey: 0,
      icons: { mdiDelete, mdiPlus },
      modalidade:{},
      headers: [
        { text: 'Escalão', value: 'escalao', align: 'center', sortable: false },
        { text: 'Ações', value: 'actions', align: 'center', sortable: false }
      ],
      escaloes: []
    };
  },
  computed: {
    nome() {
      return this.$route.params.nome;
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
      this.getEscaloes();
    },
    close() {
      this.dialog = false;
      this.loading = true;
      this.getEscaloes();
      setTimeout(() => {
        this.editedItem = Object.assign({}, this.defaultItem);
        this.editedIndex = -1;
      }, 300)
    },
    back () {
      this.$router.push('/modalidades')
    },
    getEscaloes(){
      this.$axios.$get(`/api/modalidades/${this.nome}`).then(escaloes => (this.escaloes = escaloes || {}));
      this.loading = false;
    },
    deleteItem (item) {
      confirm(`Tem a certeza que pertende remover este escalão: ${item.escalao}?`) &&
      this.$axios.$delete(`/api/modalidades/${this.nome}&&${item.escalao}`, {}).then(() => {
        this.loading = true;
        this.initialize();
      })
    },
    expandItem (item) {
      this.$router.push(`/modalidades/${this.nome}/${item.escalao}`);
    },
  }
}
</script>
