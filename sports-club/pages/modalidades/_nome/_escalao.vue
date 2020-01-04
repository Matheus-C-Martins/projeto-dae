<!-- eslint-disable -->
<template>
  <v-app id='inspire'>
    <v-container fluid>
      <v-toolbar-title class="headline"> {{nome}} {{escalao}}: </v-toolbar-title>
      <v-row dense>
        <v-col>
          <v-data-table
            :loading='loadingTreinadores'
            loading-text='A carregar treinadores... Aguarde um momento'
            item-key='username, nome, email'
            expand-icon
            :headers='headers'
            :items='treinadores'
            class='elevation-1'
            no-data-text='Não existem treinadores'
            :search="searchTreinador">
            <template v-slot:top>
              <v-toolbar flat color='white'>
                <v-toolbar-title> Treinadores </v-toolbar-title>
                <v-divider class='mx-4' inset vertical></v-divider>
                <v-text-field
                  v-model="searchTreinador"
                  label="Procurar por email"
                  hide-details
                  outlined
                  dense
                  ></v-text-field>
                <v-spacer></v-spacer>
                <v-divider class='mx-4' inset vertical></v-divider>
                <v-dialog v-model='dialogTreinador' max-width='500px'>
                  <template v-slot:activator='{ on }'>
                    <v-btn color='primary' dark class='mb-2' v-on='on' @click="inserirTreinadorKey+=1"> Adicionar Treinador </v-btn>
                  </template>
                  <inserir-treinador @close="close" :nome="nome" :escalao="escalao" :key="inserirTreinadorKey"></inserir-treinador>
                </v-dialog>
              </v-toolbar>
            </template>
          </v-data-table>
         </v-col>
        <v-col>
          <v-data-table
            :loading='loadingAtletas'
            loading-text='A carregar atletas... Aguarde um momento'
            item-key='username, nome, email'
            expand-icon
            :headers='headers'
            :items='atletas'
            class='elevation-1'
            no-data-text='Não existem atletas'
            :search="searchAtleta">
            <template v-slot:top>
              <v-toolbar flat color='white'>
                <v-toolbar-title> Atletas </v-toolbar-title>
                <v-divider class='mx-4' inset vertical></v-divider>
                <v-text-field
                  v-model="searchAtleta"
                  label="Procurar por email"
                  hide-details
                  outlined
                  dense
                  ></v-text-field>
                <v-spacer></v-spacer>
                <v-divider class='mx-4' inset vertical></v-divider>
                <v-dialog v-model='dialogAtleta' max-width='500px'>
                  <template v-slot:activator='{ on }'>
                    <v-btn color='primary' dark class='mb-2' v-on='on' @click="inserirAtletaKey+=1"> Adicionar Atleta </v-btn>
                  </template>
                  <inserir-atleta @close="close" :nome="nome" :escalao="escalao" :key="inserirAtletaKey"></inserir-atleta>
                </v-dialog>
              </v-toolbar>
            </template>
          </v-data-table>
        </v-col>
      </v-row>
      <v-footer color = "white" style="padding-top: 0px;">
        <v-btn color='primary' dark @click='back'> Voltar </v-btn>
      </v-footer>
    </v-container>
  </v-app>
</template>

<script>
/* eslint-disable */
import { mdiDelete } from '@mdi/js'
import AdicionarTreinador from './adicionarTreinador'
import AdicionarAtleta from './adicionarAtleta'

export default {
  components: {
    'inserir-treinador': AdicionarTreinador,
    'inserir-atleta': AdicionarAtleta
  },
  data () {
    return {
      loadingTreinadores: true,
      loadingAtletas: true,
      dialogTreinador: false,
      dialogAtleta: false,
      searchTreinador: '',
      searchAtleta: '',
      inserirTreinadorKey: 0,
      inserirAtletaKey: 0,
      headers: [
        { text: 'Username', value: 'username', align: 'center', sortable: false, filterable: false },
        { text: 'Nome', value: 'nome', align: 'center', sortable: false, filterable: false },
        { text: 'Email', value: 'email', align: 'center', sortable: false}
      ],
      treinadores: [],
      atletas: [],
    }
  },
  computed: {
    nome() {
      return this.$route.params.nome;
    },
    escalao() {
      return this.$route.params.escalao;
    }
  },
  created () {
    this.getTreinadoresAndAtletas();
  },
  watch: {
    dialogTreinador (val) {
      val || this.close();
    },
    dialogAtleta (val) {
      val || this.close();
    },
  },
  methods: {
    close () {
      this.dialogTreinador = this.dialogAtleta = false;
      this.getTreinadoresAndAtletas();
    },
    back () {
      this.$router.push(`/modalidades/${this.nome}`);
    },
    getTreinadoresAndAtletas(){
      this.$axios.$get(`/api/modalidades/${this.nome}&&${this.escalao}`).then(modalidade => {
        this.treinadores = modalidade.treinadores;
        this.atletas = modalidade.atletas;
        this.loadingTreinadores = this.loadingAtletas = false;
      });
    }
  }
}
</script>
