<!-- eslint-disable -->
<template>
  <v-app id='inspire'>
    <v-card>
      <v-data-table
        :loading='loading'
        loading-text='A carregar modalidades... Aguarde um momento'
        item-key='nome, escalao'
        expand-icon
        :headers='headers'
        :items='modalidades'
        class='elevation-1'
        no-data-text='Não existem modalidades'
        :search="search"
      >
      <template v-slot:top>
          <v-toolbar flat color='white'>
            <v-toolbar-title> Modalidades </v-toolbar-title>
            <v-divider class='mx-4' inset vertical></v-divider>
            <v-text-field
              v-model="search"
              label="Procurar por nome"
              hide-details
              outlined
              dense
            ></v-text-field>
            <v-spacer></v-spacer>
            <v-dialog v-model='dialog' max-width='500px'>
              <template v-slot:activator='{ on }'>
                <v-btn color='primary' dark class='mb-2' v-on='on' @click="criarKey+=1"> Criar Modalidade </v-btn>
              </template>
              <create-modalidade @close="close" :key="criarKey" :modalidade="modalidade"></create-modalidade>
            </v-dialog>
          </v-toolbar>
        </template>
        <template v-slot:item.action="{ item }"> 
          <v-icon small @click="expandItem(item)"> {{ icons.mdiPlus }} </v-icon>
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
import { mdiPlus } from '@mdi/js'
import CriarModalidade from './criar'

export default {
  components: {
    'create-modalidade': CriarModalidade
  },
  data () {
    return {
      loading: true,
      dialog: false,
      search: '',
      icons: {
        mdiPlus
      },
      headers: [
        { text: 'Nome', value: 'nome', align: 'center', sortable: false },
        { text: 'Ver Escalões', value: 'action', align: 'center', sortable: false, filterable: false }
      ],
      modalidades: [],
      criarKey: 0,
      editedIndex: -1,
      modalidade: {
        username: '',
        nome: '',
        email: '',
        password: ''
      },
      default: {
        username: '',
        nome: '',
        email: '',
        password: ''
      }
    }
  },
  created () {
     this.getModalidades();
  },
  watch: {
    dialog (val) {
      val || this.close()
    }
  },
  methods: {
    close () {
      this.dialog = false;
      this.getModalidades();
      setTimeout(() => {
        this.modalidade = Object.assign({}, this.default);
        this.editedIndex = -1;
      }, 300);
    },
    back () {
      this.$router.push('/');
    },
    expandItem (item) {
      this.$router.push(`/modalidades/${item.nome}`);
    },
    getModalidades(){
      this.$axios.$get('/api/modalidades').then((modalidades) => {
        this.modalidades = modalidades;
        this.loading = false;
      });
    }
  }
}
</script>
<style>
</style>
