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
            item-key='username'
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
                <v-dialog v-model='dialogTreinador' max-width='700px'>
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
            item-key='username'
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
                <v-dialog v-model='dialogAtleta' max-width='700px'>
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
      <v-row>
        <v-col>
          <v-data-table
            :loading='loadingHorarios'
            loading-text='A carregar horários... Aguarde um momento'
            expand-icon
            :headers='headersHorarios'
            :items='horarios'
            class='elevation-1'
            no-data-text='Não existem horários'
            :search="searchHorario">
            <template v-slot:top>
              <v-toolbar flat color='white'>
                <v-toolbar-title> Horários </v-toolbar-title>
                <v-divider class='mx-4' inset vertical></v-divider>
                <v-text-field
                  v-model="searchHorario"
                  label="Procurar por dia da semana"
                  hide-details
                  outlined
                  dense
                  ></v-text-field>
                <v-spacer></v-spacer>
                <v-divider class='mx-4' inset vertical></v-divider>
                <v-dialog v-model='dialogHorario' max-width='700px'>
                  <template v-slot:activator='{ on }'>
                    <v-btn color='primary' dark class='mb-2' v-on='on' @click="inserirHorarioKey+=1"> Adicionar Horário </v-btn>
                  </template>
                  <inserir-horario @close="close" :nome="nome" :escalao="escalao" :key="inserirHorarioKey"></inserir-horario>
                </v-dialog>
              </v-toolbar>
            </template>
            <template v-slot:item.unroll="{ item }">
              <v-icon small @click="deleteItem(item)"> {{ icons.mdiDelete }} </v-icon>
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
import AdicionarHorario from './adicionarHorario'

export default {
  components: {
    'inserir-treinador': AdicionarTreinador,
    'inserir-atleta': AdicionarAtleta,
    'inserir-horario': AdicionarHorario
  },
  data () {
    return {
      loadingTreinadores: true,
      loadingAtletas: true,
      loadingHorarios: true,
      dialogTreinador: false,
      dialogAtleta: false,
      dialogHorario: false,
      searchTreinador: '',
      searchAtleta: '',
      searchHorario: '',
      inserirTreinadorKey: 0,
      inserirAtletaKey: 0,
      inserirHorarioKey: 0,
      icons: { mdiDelete },
      headers: [
        { text: 'Username', value: 'username', align: 'center', sortable: false, filterable: false },
        { text: 'Nome', value: 'nome', align: 'center', sortable: false, filterable: false },
        { text: 'Email', value: 'email', align: 'center', sortable: false}
      ],
      headersHorarios: [
        { text: 'Dia da Semana', value: 'diaSemana', align: 'center', sortable: false },
        { text: 'Hora de Início', value: 'horaInicio', align: 'center', sortable: false, filterable: false },
        { text: 'Hora de Fim', value: 'horaFim', align: 'center', sortable: false, filterable: false},
        { text: '', value: 'unroll', align: 'center', sortable: false, filterable: false},
      ],
      treinadores: [],
      atletas: [],
      horarios: []
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
    dialogHorario (val) {
      val || this.close();
    },
  },
  methods: {
    close () {
      this.dialogHorario = this.dialogTreinador = this.dialogAtleta = false;
      this.loadingHorarios = this.loadingTreinadores = this.loadingAtletas = true;
      this.getTreinadoresAndAtletas();
    },
    back () {
      this.$router.push(`/modalidades/${this.nome}`);
    },
    deleteItem (item) {
      confirm(`Tem a certeza que pertende eliminar o Horário: ${item.diaSemana}, ${item.horaInicio}-${item.horaFim}?`) &&
      this.$axios.$delete(`/api/horarios/${item.diaSemana}&&${item.horaInicio}&&${item.horaFim}`, {}).then(() => {
        this.loadingHorarios = true
        this.getTreinadoresAndAtletas()
      })
    },
    getTreinadoresAndAtletas(){
      this.$axios.$get(`/api/modalidades/${this.nome}&&${this.escalao}`).then(modalidade => {
        this.treinadores = modalidade.treinadores;
        this.atletas = modalidade.atletas;
        this.horarios = modalidade.horarios;
        this.loadingHorarios = this.loadingTreinadores = this.loadingAtletas = false;
      });
    }
  }
}
</script>
